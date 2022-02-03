package com.dev.productioncenter.controller.command.impl.enrollment;

import com.dev.productioncenter.controller.command.Command;
import com.dev.productioncenter.controller.command.PagePath;
import com.dev.productioncenter.controller.command.Router;
import com.dev.productioncenter.controller.command.SessionAttribute;
import com.dev.productioncenter.entity.Course;
import com.dev.productioncenter.entity.User;
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
import static com.dev.productioncenter.controller.command.RequestParameter.LESSON_AMOUNT;

/**
 * @project Production Center
 * @author YanaV
 * The type Enroll on course command.
 */
public class EnrollOnCourseCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String ENROLLMENT_CONFIRM_MESSAGE_KEY = "confirm.enrolling_on_course";
    private static final String ENROLLMENT_ERROR_MESSAGE_KEY = "error.enrolling_on_course";
    private final EnrollmentService enrollmentService = EnrollmentServiceImpl.getInstance();
    private final CourseService courseService = CourseServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        long chosenCourseId = Long.parseLong(request.getParameter(CHOSEN_COURSE_ID));
        String lessonAmount = request.getParameter(LESSON_AMOUNT);
        try {
            if (enrollmentService.enrollOnCourse(user, chosenCourseId, lessonAmount)
                    && courseService.reservePlaceAtCourse(chosenCourseId)) {
                session.setAttribute(SessionAttribute.MESSAGE, ENROLLMENT_CONFIRM_MESSAGE_KEY);
                return new Router(PagePath.SHOW_COURSES, Router.RouterType.REDIRECT);
            } else {
                Optional<Course> course = courseService.findCourse(chosenCourseId);
                if (course.isPresent()) {
                    request.setAttribute(COURSE, course.get());
                    request.setAttribute(MESSAGE, ENROLLMENT_ERROR_MESSAGE_KEY);
                    return new Router(PagePath.ENROLL_ON_COURSE, Router.RouterType.FORWARD);
                }
            }
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while enrolling on course: " + exception);
        }
        return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
    }
}
