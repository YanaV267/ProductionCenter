package com.dev.productioncenter.controller.filter;

import com.dev.productioncenter.controller.command.CommandType;
import com.dev.productioncenter.controller.command.RequestParameter;
import com.dev.productioncenter.controller.command.SessionAttribute;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {"/controller"})
public class CurrentPageFilter implements Filter {
    private static final String COMMAND_DELIMITER = "?";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession();
        String commandType = httpServletRequest.getParameter(RequestParameter.COMMAND);
        if (CommandType.CHANGE_LOCALE != CommandType.valueOf(commandType.toUpperCase())) {
            String currentPage = httpServletRequest.getServletPath() + COMMAND_DELIMITER + httpServletRequest.getQueryString();
            session.setAttribute(SessionAttribute.CURRENT_PAGE, currentPage);
        }
        chain.doFilter(request, response);
    }
}
