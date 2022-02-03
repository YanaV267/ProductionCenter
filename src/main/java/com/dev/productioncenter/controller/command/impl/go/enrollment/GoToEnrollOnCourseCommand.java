package com.dev.productioncenter.controller.command.impl.go.enrollment;

import com.dev.productioncenter.controller.command.Command;
import com.dev.productioncenter.controller.command.PagePath;
import com.dev.productioncenter.controller.command.Router;
import com.dev.productioncenter.controller.command.SessionAttribute;
import com.dev.productioncenter.entity.Course;
import com.dev.productioncenter.entity.Enrollment;
import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.entity.UserRole;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.service.CourseService;
import com.dev.productioncenter.model.service.EnrollmentService;
import com.dev.productioncenter.model.service.impl.CourseServiceImpl;
import com.dev.productioncenter.model.service.impl.EnrollmentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.dev.productioncenter.controller.command.RequestAttribute.COURSE;
import static com.dev.productioncenter.controller.command.RequestAttribute.MESSAGE;
import static com.dev.productioncenter.controller.command.RequestParameter.CHOSEN_COURSE_ID;

/**
 * @project Production Center
 * @author YanaV
 * The type Go to enroll on course command.
 */
public class GoToEnrollOnCourseCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String ENROLLMENT_ROLE_PERMISSION_ERROR_MESSAGE_KEY = "error.enrollment.role_permission";
    private static final String ENROLLMENT_EXIST_ERROR_MESSAGE_KEY = "error.enrollment.exist";
    private static final String ENROLLMENT_NO_PLACES_ERROR_MESSAGE_KEY = "error.enrollment.no_places";
    private final CourseService courseService = CourseServiceImpl.getInstance();
    private final EnrollmentService enrollmentService = EnrollmentServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        String role = (String) session.getAttribute(SessionAttribute.ROLE);
        long chosenId = Long.parseLong(request.getParameter(CHOSEN_COURSE_ID));
        try {
            if (UserRole.valueOf(role.toUpperCase()) == UserRole.GUEST) {
                session.setAttribute(SessionAttribute.MESSAGE, ENROLLMENT_ROLE_PERMISSION_ERROR_MESSAGE_KEY);
                return new Router(PagePath.SHOW_COURSES, Router.RouterType.REDIRECT);
            }
            Optional<Enrollment> enrollment = enrollmentService.findEnrollment(user, chosenId);
            if (enrollment.isPresent()) {
                session.setAttribute(SessionAttribute.MESSAGE, ENROLLMENT_EXIST_ERROR_MESSAGE_KEY);
                return new Router(PagePath.SHOW_COURSES, Router.RouterType.REDIRECT);
            }
            Optional<Course> course = courseService.findCourse(chosenId);
            if (course.isPresent()) {
                if (course.get().getStudentAmount() == 0) {
                    request.setAttribute(MESSAGE, ENROLLMENT_NO_PLACES_ERROR_MESSAGE_KEY);
                    return new Router(PagePath.SHOW_COURSES, Router.RouterType.FORWARD);
                }
                request.setAttribute(COURSE, course.get());
                return new Router(PagePath.ENROLL_ON_COURSE, Router.RouterType.FORWARD);
            }
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while redirecting to enrollment page: " + exception);
        }
        return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
    }
}
