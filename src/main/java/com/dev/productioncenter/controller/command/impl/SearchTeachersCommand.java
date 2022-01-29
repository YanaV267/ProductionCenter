package com.dev.productioncenter.controller.command.impl;

import com.dev.productioncenter.controller.command.Command;
import com.dev.productioncenter.controller.command.PagePath;
import com.dev.productioncenter.controller.command.RequestParameter;
import com.dev.productioncenter.controller.command.Router;
import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.service.UserService;
import com.dev.productioncenter.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static com.dev.productioncenter.controller.command.RequestAttribute.LAST;
import static com.dev.productioncenter.controller.command.RequestAttribute.USERS;
import static com.dev.productioncenter.controller.command.RequestParameter.*;

public class SearchTeachersCommand implements Command {
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
        Map<String, String> teacherData = new HashMap<>();
        teacherData.put(SURNAME, request.getParameter(SURNAME));
        teacherData.put(NAME, request.getParameter(NAME));
        teacherData.put(STATUS, request.getParameter(STATUS));
        try {
            Map<User, String> teachers = userService.findTeachers(teacherData, page);
            Map<User, String> nextTeachers = userService.findTeachers(teacherData, page + 1);
            request.setAttribute(USERS, teachers);
            request.setAttribute(TEACHER, teacherData);
            request.setAttribute(PAGE, page);
            request.setAttribute(LAST, nextTeachers.isEmpty());
            return new Router(PagePath.TEACHERS, Router.RouterType.FORWARD);
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while searching teachers: " + exception);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }
    }
}
