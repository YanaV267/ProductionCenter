package com.dev.productioncenter.controller.command.impl.go.enrollment;

import com.dev.productioncenter.controller.command.Command;
import com.dev.productioncenter.controller.command.PagePath;
import com.dev.productioncenter.controller.command.Router;
import com.dev.productioncenter.controller.command.SessionAttribute;
import com.dev.productioncenter.entity.Course;
import com.dev.productioncenter.entity.Lesson;
import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.entity.UserRole;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.service.ActivityService;
import com.dev.productioncenter.model.service.CourseService;
import com.dev.productioncenter.model.service.EnrollmentService;
import com.dev.productioncenter.model.service.LessonService;
import com.dev.productioncenter.model.service.impl.ActivityServiceImpl;
import com.dev.productioncenter.model.service.impl.CourseServiceImpl;
import com.dev.productioncenter.model.service.impl.EnrollmentServiceImpl;
import com.dev.productioncenter.model.service.impl.LessonServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

import static com.dev.productioncenter.controller.command.RequestAttribute.*;
import static com.dev.productioncenter.controller.command.RequestParameter.CHOSEN_COURSE_ID;

public class GoToEnrollOnCourseCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String ENROLLMENT_ROLE_PERMISSION_ERROR_MESSAGE_KEY = "error.enrollment_role_permission";
    private static final String ENROLLMENT_EXIST_ERROR_MESSAGE_KEY = "error.enrollment_exist";
    private static final String ENROLLMENT_NO_PLACES_ERROR_MESSAGE_KEY = "error.enrollment_no_places";
    private final LessonService lessonService = new LessonServiceImpl();
    private final CourseService courseService = new CourseServiceImpl();
    private final ActivityService activityService = new ActivityServiceImpl();
    private final EnrollmentService enrollmentService = new EnrollmentServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute(SessionAttribute.ROLE);
        long chosenId = Long.parseLong(request.getParameter(CHOSEN_COURSE_ID));
        try {
            Optional<Course> course = courseService.findCourse(chosenId);
            List<Course> courses = courseService.findAvailableCourses();
            List<String> categories = activityService.findCategories();
            if (course.isPresent()) {
                request.setAttribute(COURSES, courses);
                request.setAttribute(CATEGORIES, categories);
                if (UserRole.valueOf(role.toUpperCase()) == UserRole.GUEST) {
                    request.setAttribute(MESSAGE, ENROLLMENT_ROLE_PERMISSION_ERROR_MESSAGE_KEY);
                    return new Router(PagePath.SHOW_COURSES, Router.RouterType.FORWARD);
                }
                User user = (User) session.getAttribute(SessionAttribute.USER);
                if (enrollmentService.findEnrollments(user, chosenId)) {
                    request.setAttribute(MESSAGE, ENROLLMENT_EXIST_ERROR_MESSAGE_KEY);
                    return new Router(PagePath.SHOW_COURSES, Router.RouterType.FORWARD);
                }
                if (course.get().getStudentAmount() == 0) {
                    request.setAttribute(MESSAGE, ENROLLMENT_NO_PLACES_ERROR_MESSAGE_KEY);
                    return new Router(PagePath.SHOW_COURSES, Router.RouterType.FORWARD);
                }
                List<Lesson> lessons = lessonService.findLessons(chosenId);
                request.setAttribute(COURSE, course.get());
                request.setAttribute(LESSONS, lessons);
                return new Router(PagePath.ENROLL_ON_COURSE, Router.RouterType.FORWARD);
            }
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while redirecting to enrollment page: " + exception);
        }
        return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
    }
}
