package com.dev.productioncenter.controller.command.impl.signing;

import com.dev.productioncenter.controller.command.*;
import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.model.service.UserService;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.service.impl.UserServiceImpl;
import com.dev.productioncenter.util.PhoneNumberFormatter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.dev.productioncenter.controller.command.RequestAttribute.*;
import static com.dev.productioncenter.controller.command.RequestParameter.*;

public class SignInCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String SIGN_IN_ERROR_MESSAGE_KEY = "error.sign_in";
    private final UserService userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        try {
            Optional<User> user = userService.findUser(login, password);
            if (user.isPresent()) {
                request.setAttribute(SIGN_IN_ERROR, false);
                String number = PhoneNumberFormatter.format(user.get().getPhoneNumber());
                session.setAttribute(NUMBER, number);
                session.setAttribute(SessionAttribute.USER, user.get());
                session.setAttribute(SessionAttribute.ROLE, user.get().getUserRole().getRole());
                return new Router(PagePath.HOME, Router.RouterType.REDIRECT);
            } else {
                request.setAttribute(USER_LOGIN, login);
                request.setAttribute(USER_PASSWORD, password);
                request.setAttribute(SIGN_IN_ERROR, true);
                request.setAttribute(MESSAGE, SIGN_IN_ERROR_MESSAGE_KEY);
                return new Router(PagePath.SIGN_IN, Router.RouterType.FORWARD);
            }
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while signing in: " + exception);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }
    }
}
