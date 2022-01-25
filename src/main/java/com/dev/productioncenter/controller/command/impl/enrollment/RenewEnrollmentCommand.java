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

public class RenewEnrollmentCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String RENEW_ENROLLMENT_ERROR_MESSAGE_KEY = "error.enrollment.no_places";
    private static final String RENEW_ENROLLMENT_CONFIRM_MESSAGE_KEY = "confirm.enrollment.renew";
    private final EnrollmentService enrollmentService = EnrollmentServiceImpl.getInstance();
    private final CourseService courseService = CourseServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        long chosenEnrollmentId = Long.parseLong(request.getParameter(CHOSEN_ENROLLMENT_ID));
        try {
            Optional<Enrollment> enrollment = enrollmentService.findEnrollment(chosenEnrollmentId);
            if (enrollment.isPresent() && courseService.reservePlaceAtCourse(enrollment.get().getCourse().getId())) {
                if (enrollmentService.updateStatus(chosenEnrollmentId, EnrollmentStatus.RENEWED)
                        && enrollmentService.checkEnrollmentsReservationStatus()) {
                    Map<Enrollment, LocalDate> enrollments = enrollmentService.findEnrollments(user);
                    session.setAttribute(SessionAttribute.ENROLLMENTS, enrollments);
                    session.setAttribute(SessionAttribute.MESSAGE, RENEW_ENROLLMENT_CONFIRM_MESSAGE_KEY);
                    return new Router(PagePath.SHOW_USER_ENROLLMENTS, Router.RouterType.REDIRECT);
                } else {
                    session.setAttribute(SessionAttribute.MESSAGE, RENEW_ENROLLMENT_ERROR_MESSAGE_KEY);
                    return new Router(PagePath.SHOW_USER_ENROLLMENTS, Router.RouterType.FORWARD);
                }
            }
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while renewing user enrollment: " + exception);
        }
        return new

                Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
    }
}
