package com.dev.productioncenter.controller.filter;

import com.dev.productioncenter.controller.command.CommandType;
import com.dev.productioncenter.controller.command.RequestParameter;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.EnumSet;

import static com.dev.productioncenter.controller.command.CommandType.*;
import static com.dev.productioncenter.controller.command.RequestAttribute.*;

@WebFilter(urlPatterns = {"/controller"})
public class SessionAttributeFilter implements Filter {
    private static final String DEFAULT_COMMAND = "go_to_home";
    private EnumSet<CommandType> excludingCommands;

    @Override
    public void init(FilterConfig filterConfig) {
        excludingCommands = EnumSet.of(SEARCH_COURSES, GO_TO_ADD_ACTIVITY, ADD_ACTIVITY);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession();
        String command = httpServletRequest.getParameter(RequestParameter.COMMAND);
        if (command == null) {
            command = DEFAULT_COMMAND;
        }
        CommandType commandType = CommandType.valueOf(command.toUpperCase());
        if (!excludingCommands.contains(commandType)) {
            session.removeAttribute(COURSES);
            session.removeAttribute(CATEGORIES);
            session.removeAttribute(ACTIVITIES);
        }
        session.removeAttribute(MESSAGE);
        session.removeAttribute(LESSONS);
        session.removeAttribute(PICTURE);
        chain.doFilter(request, response);
    }
}