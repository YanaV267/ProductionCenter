package com.development.productioncenter.controller.command.impl;

import com.development.productioncenter.controller.command.Command;
import com.development.productioncenter.controller.command.PagePath;
import com.development.productioncenter.controller.command.SessionAttribute;
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
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String login = request.getParameter(LOGIN_PARAMETER);
        String password = request.getParameter(PASSWORD_PARAMETER);
        try {
            Optional<User> user = userService.findUser(login, password);
            if (user.isPresent()) {
                session.setAttribute(SessionAttribute.ROLE, user.get().getUserRole().getRole());
                return PagePath.HOME;
            } else {
                session.setAttribute(SessionAttribute.LOGIN_ERROR, true);
                return PagePath.SIGN_IN;
            }
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while signing in: " + exception);
            return PagePath.ERROR;
        }
    }
}
