package com.dev.productioncenter.controller.command.impl.enrollment;

import com.dev.productioncenter.controller.command.*;
import com.dev.productioncenter.entity.Enrollment;
import com.dev.productioncenter.entity.EnrollmentStatus;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.service.EnrollmentService;
import com.dev.productioncenter.model.service.impl.EnrollmentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ChangeEnrollmentStatusCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String CHANGE_STATUSES_CONFIRM_MESSAGE_KEY = "confirm.enrollment.change_statuses";
    private final EnrollmentService enrollmentService = EnrollmentServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String[] enrollments = request.getParameterValues(RequestParameter.ENROLLMENT_ID);
        String[] statuses = request.getParameterValues(RequestParameter.STATUS);
        Map<String, EnrollmentStatus> enrollmentsStatuses = new HashMap<>();
        for (int i = 0; i < enrollments.length; i++) {
            enrollmentsStatuses.put(enrollments[i], EnrollmentStatus.valueOf(statuses[i].toUpperCase()));
        }
        try {
            if (enrollmentService.updateStatus(enrollmentsStatuses) && enrollmentService.checkEnrollmentsReservationStatus()) {
                Map<Enrollment, LocalDate> allEnrollments = enrollmentService.findEnrollments();
                session.setAttribute(SessionAttribute.ENROLLMENTS, allEnrollments);
                session.setAttribute(SessionAttribute.MESSAGE, CHANGE_STATUSES_CONFIRM_MESSAGE_KEY);
                return new Router(PagePath.SHOW_ENROLLMENTS, Router.RouterType.REDIRECT);
            }
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while changing enrollments' statuses: " + exception);
        }
        return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
    }
}
