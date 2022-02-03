package com.dev.productioncenter.controller.command.impl;

import com.dev.productioncenter.controller.command.*;
import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.service.UserService;
import com.dev.productioncenter.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static com.dev.productioncenter.controller.command.RequestAttribute.*;
import static com.dev.productioncenter.controller.command.RequestParameter.*;

/**
 * @project Production Center
 * @author YanaV
 * The type Search users command.
 */
public class SearchUsersCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int DEFAULT_PAGE = 1;
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        int page;
        if (request.getParameter(RequestParameter.PAGE) == null) {
            page = DEFAULT_PAGE;
        } else {
            page = Integer.parseInt(request.getParameter(RequestParameter.PAGE));
        }
        Map<String, String> userData = new HashMap<>();
        userData.put(SURNAME, request.getParameter(SURNAME));
        userData.put(NAME, request.getParameter(NAME));
        userData.put(STATUS, request.getParameter(STATUS));
        try {
            Map<User, String> users = userService.findUsers(userData, page);
            Map<User, String> nextUsers = userService.findUsers(userData, page + 1);
            request.setAttribute(USERS, users);
            request.setAttribute(USER, userData);
            request.setAttribute(RequestAttribute.PAGE, page);
            request.setAttribute(LAST, nextUsers.isEmpty());
            return new Router(PagePath.USERS, Router.RouterType.FORWARD);
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while searching users: " + exception);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }
    }
}
