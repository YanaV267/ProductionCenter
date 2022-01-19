package com.dev.productioncenter.controller.command.impl.go.enrollment;

import com.dev.productioncenter.controller.command.Command;
import com.dev.productioncenter.controller.command.PagePath;
import com.dev.productioncenter.controller.command.Router;
import com.dev.productioncenter.entity.Enrollment;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.service.EnrollmentService;
import com.dev.productioncenter.model.service.impl.EnrollmentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.Map;

import static com.dev.productioncenter.controller.command.RequestAttribute.ENROLLMENTS;

public class GoToEnrollmentsCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final EnrollmentService enrollmentService = new EnrollmentServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {
        try {
            Map<Enrollment, LocalDate> enrollments = enrollmentService.findEnrollments();
            request.setAttribute(ENROLLMENTS, enrollments);
            return new Router(PagePath.SHOW_ENROLLMENTS, Router.RouterType.FORWARD);
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while redirecting to enrollments page: " + exception);
        }
        return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
    }
}
