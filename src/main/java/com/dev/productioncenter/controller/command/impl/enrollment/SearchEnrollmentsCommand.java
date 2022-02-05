package com.dev.productioncenter.controller.command.impl.enrollment;

import com.dev.productioncenter.controller.command.Command;
import com.dev.productioncenter.controller.command.PagePath;
import com.dev.productioncenter.controller.command.RequestParameter;
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

import static com.dev.productioncenter.controller.command.RequestAttribute.*;
import static com.dev.productioncenter.controller.command.RequestParameter.STATUS;

/**
 * @author YanaV
 * @project Production Center
 * The type Search enrollments command.
 */
public class SearchEnrollmentsCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int DEFAULT_PAGE = 1;
    private final EnrollmentService enrollmentService = EnrollmentServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        String status = request.getParameter(STATUS);
        int page;
        if (request.getParameter(RequestParameter.PAGE) == null) {
            page = DEFAULT_PAGE;
        } else {
            page = Integer.parseInt(request.getParameter(RequestParameter.PAGE));
        }
        try {
            Map<Enrollment, LocalDate> enrollments = enrollmentService.findEnrollments(status, page);
            Map<Enrollment, LocalDate> nextEnrollments = enrollmentService.findEnrollments(status, page + 1);
            request.setAttribute(ENROLLMENTS, enrollments);
            request.setAttribute(SELECTED_STATUS, status);
            request.setAttribute(PAGE, page);
            request.setAttribute(LAST, nextEnrollments.isEmpty());
            return new Router(PagePath.SHOW_ENROLLMENTS, Router.RouterType.FORWARD);
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while searching enrollments: " + exception);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }
    }
}
