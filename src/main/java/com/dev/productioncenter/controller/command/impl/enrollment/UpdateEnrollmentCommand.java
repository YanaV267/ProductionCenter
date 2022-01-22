package com.dev.productioncenter.controller.command.impl.enrollment;

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
import java.util.HashMap;
import java.util.Map;

import static com.dev.productioncenter.controller.command.RequestParameter.ENROLLMENT_ID;
import static com.dev.productioncenter.controller.command.RequestParameter.LESSON_AMOUNT;

public class UpdateEnrollmentCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String UPDATE_ENROLLMENT_CONFIRM_MESSAGE_KEY = "confirm.update_enrollment";
    private final EnrollmentService enrollmentService = new EnrollmentServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        String[] enrollmentsId = request.getParameterValues(ENROLLMENT_ID);
        String[] lessonAmounts = request.getParameterValues(LESSON_AMOUNT);
        Map<String, String> enrollmentsLessonAmount = new HashMap<>();
        for (int i = 0; i < enrollmentsId.length; i++) {
            enrollmentsLessonAmount.put(enrollmentsId[i], lessonAmounts[i]);
        }
        try {
            if (enrollmentService.updateLessonAmounts(enrollmentsLessonAmount)) {
                Map<Enrollment, LocalDate> enrollments = enrollmentService.findEnrollment(user);
                session.setAttribute(SessionAttribute.ENROLLMENTS, enrollments);
                session.setAttribute(SessionAttribute.MESSAGE, UPDATE_ENROLLMENT_CONFIRM_MESSAGE_KEY);
                return new Router(PagePath.SHOW_USER_ENROLLMENTS, Router.RouterType.REDIRECT);
            }
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while deleting user enrollment: " + exception);
        }
        return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
    }
}
