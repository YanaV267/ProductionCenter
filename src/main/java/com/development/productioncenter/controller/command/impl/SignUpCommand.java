package com.development.productioncenter.controller.command.impl;

import com.development.productioncenter.controller.command.Command;
import com.development.productioncenter.controller.command.PagePath;
import com.development.productioncenter.exception.ServiceException;
import com.development.productioncenter.model.service.UserService;
import com.development.productioncenter.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static com.development.productioncenter.controller.command.RequestParameter.*;

public class SignUpCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final UserService userService = new UserServiceImpl();

    @Override
    public String execute(HttpServletRequest request) {
        Map<String, String> userData = new HashMap<>();
        userData.put(LOGIN_PARAMETER, request.getParameter(LOGIN_PARAMETER));
        userData.put(PASSWORD_PARAMETER, request.getParameter(PASSWORD_PARAMETER));
        userData.put(REPEATED_PASSWORD_PARAMETER, request.getParameter(REPEATED_PASSWORD_PARAMETER));
        userData.put(SURNAME_PARAMETER, request.getParameter(SURNAME_PARAMETER));
        userData.put(NAME_PARAMETER, request.getParameter(NAME_PARAMETER));
        userData.put(EMAIL_PARAMETER, request.getParameter(EMAIL_PARAMETER));
        userData.put(PHONE_NUMBER_PARAMETER, request.getParameter(PHONE_NUMBER_PARAMETER));
        try {
            if (userService.registerUser(userData)) {
                return PagePath.HOME;
            } else {
                return PagePath.SIGN_UP;
            }
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while signing in: " + exception);
            return PagePath.ERROR;
        }
    }
}
