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

import java.util.LinkedHashMap;
import java.util.Map;

import static com.dev.productioncenter.controller.command.RequestAttribute.PAGE;
import static com.dev.productioncenter.controller.command.RequestAttribute.USERS;

public class GoToTeachersCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String DEFAULT_PAGE = "1";
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        String page = request.getParameter(RequestParameter.PAGE);
        if (page == null) {
            page = DEFAULT_PAGE;
        }
        try {
            Map<User, String> users = userService.findUsers(UserRole.USER);
            Map<User, String> teachers = userService.findUsers(UserRole.TEACHER);
            Map<User, String> allUsers = new LinkedHashMap<>();
            allUsers.putAll(teachers);
            allUsers.putAll(users);
            request.setAttribute(USERS, allUsers);
            request.setAttribute(PAGE, page);
            return new Router(PagePath.TEACHERS, Router.RouterType.FORWARD);
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while redirecting to teachers page: " + exception);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }
    }
}
