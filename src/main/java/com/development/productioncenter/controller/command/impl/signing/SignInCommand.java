package com.development.productioncenter.controller.command.impl.signing;

import com.development.productioncenter.controller.command.*;
import com.development.productioncenter.entity.User;
import com.development.productioncenter.exception.ServiceException;
import com.development.productioncenter.model.service.UserService;
import com.development.productioncenter.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.development.productioncenter.controller.command.RequestParameter.*;

public class SignInCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final UserService userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String login = request.getParameter(LOGIN_PARAMETER);
        String password = request.getParameter(PASSWORD_PARAMETER);
        try {
            Optional<User> user = userService.findUser(login, password);
            if (user.isPresent()) {
                request.setAttribute(RequestAttribute.SIGN_IN_ERROR, false);
                session.setAttribute(SessionAttribute.LOGIN, user.get().getLogin());
                session.setAttribute(SessionAttribute.ROLE, user.get().getUserRole().getRole());
                return new Router(PagePath.HOME, Router.RouterType.REDIRECT);
            } else {
                request.setAttribute(RequestAttribute.USER_LOGIN, login);
                request.setAttribute(RequestAttribute.USER_PASSWORD, password);
                request.setAttribute(RequestAttribute.SIGN_IN_ERROR, true);
                return new Router(PagePath.SIGN_IN, Router.RouterType.REDIRECT);
            }
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while signing in: " + exception);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }
    }
}
