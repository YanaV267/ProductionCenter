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

public class GoToUserEnrollmentsCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final EnrollmentService enrollmentService = new EnrollmentServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        try {
            Map<Enrollment, LocalDate> enrollments = enrollmentService.findEnrollment(user);
            session.setAttribute(SessionAttribute.ENROLLMENTS, enrollments);
            return new Router(PagePath.SHOW_USER_ENROLLMENTS, Router.RouterType.FORWARD);
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while redirecting to user's enrollments page: " + exception);
        }
        return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
    }
}
