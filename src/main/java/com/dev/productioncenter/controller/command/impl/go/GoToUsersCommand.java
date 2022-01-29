package com.dev.productioncenter.controller.command.impl.go;

import com.dev.productioncenter.controller.command.Command;
import com.dev.productioncenter.controller.command.PagePath;
import com.dev.productioncenter.controller.command.RequestParameter;
import com.dev.productioncenter.controller.command.Router;
import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.entity.UserRole;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.service.UserService;
import com.dev.productioncenter.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static com.dev.productioncenter.controller.command.RequestAttribute.*;

public class GoToUsersCommand implements Command {
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
        try {
            Map<User, String> users = userService.findUsers(UserRole.USER, page);
            Map<User, String> nextUsers = userService.findUsers(UserRole.USER, page + 1);
            request.setAttribute(USERS, users);
            request.setAttribute(PAGE, page);
            request.setAttribute(LAST, nextUsers.isEmpty());
            return new Router(PagePath.USERS, Router.RouterType.FORWARD);
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while redirecting to users page: " + exception);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }
    }
}
