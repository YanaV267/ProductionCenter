package com.dev.productioncenter.controller.command.impl.go.enrollment;

import com.dev.productioncenter.controller.command.Command;
import com.dev.productioncenter.controller.command.PagePath;
import com.dev.productioncenter.controller.command.Router;
import com.dev.productioncenter.controller.command.SessionAttribute;
import com.dev.productioncenter.entity.Enrollment;
import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.service.EnrollmentService;
import com.dev.productioncenter.model.service.impl.EnrollmentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.Map;

import static com.dev.productioncenter.controller.command.RequestAttribute.ENROLLMENTS;
import static com.dev.productioncenter.controller.command.RequestAttribute.LAST;

/**
 * @project Production Center
 * @author YanaV
 * The type Go to user enrollments command.
 */
public class GoToUserEnrollmentsCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final EnrollmentService enrollmentService = EnrollmentServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        try {
            if (enrollmentService.checkEnrollmentsReservationStatus()) {
                Map<Enrollment, LocalDate> enrollments = enrollmentService.findEnrollments(user);
                Map<Enrollment, LocalDate> nextEnrollments = enrollmentService.findEnrollments(user);
                request.setAttribute(ENROLLMENTS, enrollments);
                request.setAttribute(LAST, nextEnrollments);
                return new Router(PagePath.SHOW_USER_ENROLLMENTS, Router.RouterType.FORWARD);
            }
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while redirecting to user's enrollments page: " + exception);
        }
        return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
    }
}
