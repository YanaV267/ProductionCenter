package com.dev.productioncenter.controller.command.impl;

import com.dev.productioncenter.controller.command.*;
import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.entity.UserRole;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.service.UserService;
import com.dev.productioncenter.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static com.dev.productioncenter.controller.command.SessionAttribute.*;

public class ChangeUserRoleCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String CHANGE_ROLES_CONFIRM_MESSAGE_KEY = "confirm.change.user_roles";
    private static final int DEFAULT_PAGE = 1;
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String[] logins = request.getParameterValues(RequestParameter.LOGIN);
        String[] roles = request.getParameterValues(RequestParameter.ROLE);
        Map<String, UserRole> usersRoles = new HashMap<>();
        for (int i = 0; i < logins.length; i++) {
            usersRoles.put(logins[i], UserRole.valueOf(roles[i].toUpperCase()));
        }
        try {
            if (userService.updateRoles(usersRoles)) {
                Map<User, String> users = userService.findUsersTeachers(DEFAULT_PAGE);
                Map<User, String> nextUsers = userService.findUsersTeachers(DEFAULT_PAGE + 1);
                session.setAttribute(USERS, users);
                session.setAttribute(PAGE, DEFAULT_PAGE);
                session.setAttribute(LAST, nextUsers.isEmpty());
                session.setAttribute(SessionAttribute.MESSAGE, CHANGE_ROLES_CONFIRM_MESSAGE_KEY);
                return new Router(PagePath.TEACHERS, Router.RouterType.REDIRECT);
            }
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while changing users' roles: " + exception);
        }
        return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
    }
}
