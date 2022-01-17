package com.dev.productioncenter.controller.filter;

import com.dev.productioncenter.controller.command.PagePath;
import com.dev.productioncenter.controller.command.SessionAttribute;
import com.dev.productioncenter.entity.UserRole;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

//@WebFilter(urlPatterns = {"/pages/home.jsp"})
public class SecurityAccessFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession();
        String current_role = (String) session.getAttribute(SessionAttribute.ROLE);
        if (current_role == null) {
            session.setAttribute(SessionAttribute.ROLE, UserRole.GUEST.getRole());
        }
        httpServletRequest.getRequestDispatcher(PagePath.HOME).forward(httpServletRequest, httpServletResponse);
    }
}
