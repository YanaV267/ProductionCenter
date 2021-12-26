package com.development.productioncenter.controller.filter;

import com.development.productioncenter.controller.command.CommandType;
import com.development.productioncenter.controller.command.RequestParameter;
import com.development.productioncenter.controller.command.SessionAttribute;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {"/controller"})
public class CurrentPageFilter implements Filter {
    private static final String COMMAND_DELIMITER = "?";

    public void init(FilterConfig config) {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession();
        String commandType = httpServletRequest.getParameter(RequestParameter.COMMAND_PARAMETER);
        if (!CommandType.CHANGE_LOCALE.equals(CommandType.valueOf(commandType.toUpperCase()))) {
            String currentPage = httpServletRequest.getServletPath() + COMMAND_DELIMITER + httpServletRequest.getQueryString();
            session.setAttribute(SessionAttribute.CURRENT_PAGE, currentPage);
        }
        chain.doFilter(request, response);
    }
}
