package com.dev.productioncenter.controller.command.impl.enrollment;

import com.dev.productioncenter.controller.command.*;
import com.dev.productioncenter.entity.Course;
import com.dev.productioncenter.entity.Lesson;
import com.dev.productioncenter.entity.User;
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
import static com.dev.productioncenter.controller.command.RequestParameter.*;

public class EnrollOnCourseCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String ENROLLMENT_CONFIRM_MESSAGE_KEY = "confirm.enrollment";
    private static final String ENROLLMENT_ERROR_MESSAGE_KEY = "error.enrollment";
    private final EnrollmentService enrollmentService = new EnrollmentServiceImpl();
    private final LessonService lessonService = new LessonServiceImpl();
    private final CourseService courseService = new CourseServiceImpl();
    private final ActivityService activityService = new ActivityServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        long chosenCourseId = Long.parseLong(request.getParameter(CHOSEN_COURSE_ID));
        String lessonAmount = request.getParameter(LESSON_AMOUNT);
        try {
            if (enrollmentService.enrollOnCourse(user, chosenCourseId, lessonAmount)) {
                List<Course> courses = courseService.findAvailableCourses();
                List<String> categories = activityService.findCategories();
                session.setAttribute(COURSES, courses);
                session.setAttribute(CATEGORIES, categories);
                session.setAttribute(CATEGORIES, categories);
                session.setAttribute(MESSAGE, ENROLLMENT_CONFIRM_MESSAGE_KEY);
                return new Router(PagePath.SHOW_COURSES, Router.RouterType.REDIRECT);
            } else {
                Optional<Course> course = courseService.findCourse(chosenCourseId);
                List<Lesson> lessons = lessonService.findLessons(chosenCourseId);
                if (course.isPresent()) {
                    request.setAttribute(COURSE, course.get());
                    request.setAttribute(LESSONS, lessons);
                    request.setAttribute(MESSAGE, ENROLLMENT_ERROR_MESSAGE_KEY);
                    return new Router(PagePath.ENROLL_ON_COURSE, Router.RouterType.FORWARD);
                }
            }
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while redirecting to enrollment page: " + exception);
        }
        return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
    }
}
