package com.dev.productioncenter.controller.filter;

import com.dev.productioncenter.controller.command.PagePath;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.dev.productioncenter.controller.command.PagePath.*;

@WebFilter(urlPatterns = "/pages/*")
public class DirectAccessSecurityFilter implements Filter {
    private List<String> accessiblePages;
    private static final String ERROR_PAGE_PATH = "/pages/error";

    @Override
    public void init(FilterConfig filterConfig) {
        accessiblePages = List.of(HOME, SIGN_IN, SIGN_UP);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if (!httpServletRequest.getServletPath().contains(ERROR_PAGE_PATH)) {
            String pagePath = httpServletRequest.getServletPath();
            Optional<String> foundPage = accessiblePages
                    .stream()
                    .filter(p -> p.equals(pagePath))
                    .findFirst();
            if (foundPage.isEmpty()) {
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + PagePath.ERROR_403);
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
