package com.development.productioncenter.controller.command.impl.signing;

import com.development.productioncenter.controller.command.*;
import com.development.productioncenter.exception.ServiceException;
import com.development.productioncenter.model.service.UserService;
import com.development.productioncenter.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static com.development.productioncenter.controller.command.RequestParameter.*;

public class SignUpCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String SIGN_UP_ERROR_MESSAGE = "Ошибка регистрации. Проверьте введённые данные и повторите попытку.";
    private final UserService userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
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
                return new Router(PagePath.HOME, Router.RouterType.REDIRECT);
            } else {
                request.setAttribute(RequestAttribute.USER_DATA, userData);
                request.setAttribute(RequestAttribute.SIGN_UP_ERROR, true);
                request.setAttribute(RequestAttribute.MESSAGE, SIGN_UP_ERROR_MESSAGE);
                return new Router(PagePath.SIGN_UP, Router.RouterType.REDIRECT);
            }
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while signing in: " + exception);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }
    }
}
