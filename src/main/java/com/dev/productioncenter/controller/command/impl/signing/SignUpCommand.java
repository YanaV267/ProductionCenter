package com.dev.productioncenter.controller.command.impl.signing;

import com.dev.productioncenter.controller.command.Command;
import com.dev.productioncenter.controller.command.PagePath;
import com.dev.productioncenter.controller.command.Router;
import com.dev.productioncenter.controller.command.SessionAttribute;
import com.dev.productioncenter.model.service.UserService;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static com.dev.productioncenter.controller.command.RequestAttribute.MESSAGE;
import static com.dev.productioncenter.controller.command.RequestAttribute.USER;
import static com.dev.productioncenter.controller.command.RequestParameter.*;

/**
 * @project Production Center
 * @author YanaV
 * The type Sign up command.
 */
public class SignUpCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String SIGN_UP_CONFIRM_MESSAGE_KEY = "confirm.sign_up";
    private static final String SIGN_UP_ERROR_MESSAGE_KEY = "error.sign_up";
    private static final String LOGIN_AVAILABILITY_ERROR_MESSAGE_KEY = "error.login_availability";
    private static final String EMAIL_AVAILABILITY_ERROR_MESSAGE_KEY = "error.email_availability";
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map<String, String> userData = new HashMap<>();
        userData.put(LOGIN, request.getParameter(LOGIN));
        userData.put(PASSWORD, request.getParameter(PASSWORD));
        userData.put(REPEATED_PASSWORD, request.getParameter(REPEATED_PASSWORD));
        userData.put(SURNAME, request.getParameter(SURNAME));
        userData.put(NAME, request.getParameter(NAME));
        userData.put(EMAIL, request.getParameter(EMAIL));
        userData.put(PHONE_NUMBER, request.getParameter(PHONE_NUMBER));
        try {
            if (userService.isLoginOccupied(userData.get(LOGIN))) {
                request.setAttribute(USER, userData);
                session.setAttribute(SessionAttribute.MESSAGE, LOGIN_AVAILABILITY_ERROR_MESSAGE_KEY);
                return new Router(PagePath.SIGN_UP, Router.RouterType.FORWARD);
            }
            if (userService.isEmailOccupied(userData.get(EMAIL))) {
                request.setAttribute(USER, userData);
                request.setAttribute(MESSAGE, EMAIL_AVAILABILITY_ERROR_MESSAGE_KEY);
                return new Router(PagePath.SIGN_UP, Router.RouterType.FORWARD);
            }
            if (userService.registerUser(userData)) {
                session.setAttribute(SessionAttribute.MESSAGE, SIGN_UP_CONFIRM_MESSAGE_KEY);
                return new Router(PagePath.HOME, Router.RouterType.REDIRECT);
            } else {
                request.setAttribute(USER, userData);
                request.setAttribute(MESSAGE, SIGN_UP_ERROR_MESSAGE_KEY);
                return new Router(PagePath.SIGN_UP, Router.RouterType.FORWARD);
            }
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while signing in: " + exception);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }
    }
}
