package com.dev.productioncenter.controller.filter;

import com.dev.productioncenter.controller.command.CommandType;
import com.dev.productioncenter.controller.command.PagePath;
import com.dev.productioncenter.controller.command.RequestParameter;
import com.dev.productioncenter.controller.command.SessionAttribute;
import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.entity.UserRole;
import com.dev.productioncenter.entity.UserStatus;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.dev.productioncenter.controller.command.CommandType.*;
import static com.dev.productioncenter.controller.command.PagePath.*;

@WebFilter(urlPatterns = {"/controller", "/pages/*"})
public class SecurityAccessStatusFilter implements Filter {
    private static final String ERROR_PAGE_PATH = "/pages/error";
    private static final String ACCESSIBLE_PAGE = HOME;
    private List<CommandType> accessibleCommands;

    @Override
    public void init(FilterConfig filterConfig) {
        accessibleCommands = List.of(GO_TO_HOME, SIGN_OUT, GO_TO_ACCOUNT);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession();
        UserRole role = UserRole.valueOf(String.valueOf(session.getAttribute(SessionAttribute.ROLE)).toUpperCase());
        if (!httpServletRequest.getServletPath().contains(ERROR_PAGE_PATH) && role != UserRole.GUEST) {
            String pagePath = httpServletRequest.getServletPath();
            CommandType command = CommandType.valueOf(request.getParameter(RequestParameter.COMMAND).toUpperCase());
            User user = (User) session.getAttribute(SessionAttribute.USER);
            Optional<CommandType> foundCommand = accessibleCommands
                    .stream()
                    .filter(p -> p.equals(command))
                    .findFirst();
            if (user.getUserStatus() == UserStatus.BLOCKED && foundCommand.isEmpty() && !pagePath.equals(ACCESSIBLE_PAGE)) {
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + PagePath.ERROR_403);
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
