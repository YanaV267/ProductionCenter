package com.dev.productioncenter.controller.command.impl.go.enrollment;

import com.dev.productioncenter.controller.command.*;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @project Production Center
 * @author YanaV
 * The type Go to pay for enrollment command.
 */
public class GoToPayForEnrollmentCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        long chosenEnrollmentId = Long.parseLong(request.getParameter(RequestParameter.CHOSEN_ENROLLMENT_ID));
        request.setAttribute(RequestAttribute.ENROLLMENT_ID, chosenEnrollmentId);
        return new Router(PagePath.PAY_FOR_ENROLLMENT, Router.RouterType.FORWARD);
    }
}
