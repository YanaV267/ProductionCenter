package com.dev.productioncenter.controller.command.impl.enrollment;

import com.dev.productioncenter.controller.command.Command;
import com.dev.productioncenter.controller.command.PagePath;
import com.dev.productioncenter.controller.command.Router;
import com.dev.productioncenter.controller.command.SessionAttribute;
import com.dev.productioncenter.entity.Enrollment;
import com.dev.productioncenter.entity.EnrollmentStatus;
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

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import static com.dev.productioncenter.controller.command.RequestParameter.CHOSEN_ENROLLMENT_ID;

public class CancelEnrollmentCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String DELETE_ENROLLMENT_CONFIRM_MESSAGE_KEY = "confirm.delete_enrollment";
    private static final String DELETE_ENROLLMENT_ERROR_MESSAGE_KEY = "error.delete_enrollment";
    private final EnrollmentService enrollmentService = new EnrollmentServiceImpl();
    private final CourseService courseService = new CourseServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        long chosenEnrollmentId = Long.parseLong(request.getParameter(CHOSEN_ENROLLMENT_ID));
        try {
            Optional<Enrollment> enrollment = enrollmentService.findEnrollment(chosenEnrollmentId);
            if (enrollment.isPresent()) {
                if (enrollment.get().getEnrollmentStatus() == EnrollmentStatus.PAID) {
                    session.setAttribute(SessionAttribute.MESSAGE, DELETE_ENROLLMENT_ERROR_MESSAGE_KEY);
                    return new Router(PagePath.SHOW_USER_ENROLLMENTS, Router.RouterType.REDIRECT);
                }
                if (enrollmentService.cancelEnrollment(chosenEnrollmentId)
                        && courseService.releasePlaceAtCourse(enrollment.get().getCourse().getId())) {
                    Map<Enrollment, LocalDate> enrollments = enrollmentService.findEnrollments(user);
                    session.setAttribute(SessionAttribute.ENROLLMENTS, enrollments);
                    session.setAttribute(SessionAttribute.MESSAGE, DELETE_ENROLLMENT_CONFIRM_MESSAGE_KEY);
                    return new Router(PagePath.SHOW_USER_ENROLLMENTS, Router.RouterType.REDIRECT);
                }
            }
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while deleting user enrollment: " + exception);
        }
        return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
    }
}
