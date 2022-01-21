package com.dev.productioncenter.controller.command.impl;

import com.dev.productioncenter.controller.command.*;
import com.dev.productioncenter.entity.UserStatus;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.service.UserService;
import com.dev.productioncenter.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class ChangeUserStatusCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String CHANGE_STATUSES_CONFIRM_MESSAGE_KEY = "confirm.change_user_statuses";
    private final UserService userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String[] logins = request.getParameterValues(RequestParameter.LOGIN);
        String[] statuses = request.getParameterValues(RequestParameter.STATUS);
        Map<String, UserStatus> usersStatuses = new HashMap<>();
        for (int i = 0; i < logins.length; i++) {
            usersStatuses.put(logins[i], UserStatus.valueOf(statuses[i].toUpperCase()));
        }
        try {
            if (userService.updateStatuses(usersStatuses)) {
                session.setAttribute(SessionAttribute.MESSAGE, CHANGE_STATUSES_CONFIRM_MESSAGE_KEY);
                return new Router(PagePath.HOME, Router.RouterType.REDIRECT);
            }
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while changing users' statuses: " + exception);
        }
        return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
    }
}
