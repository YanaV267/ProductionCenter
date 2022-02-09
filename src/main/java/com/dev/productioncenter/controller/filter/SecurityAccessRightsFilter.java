package com.dev.productioncenter.controller.filter;

import com.dev.productioncenter.controller.command.CommandType;
import com.dev.productioncenter.controller.command.PagePath;
import com.dev.productioncenter.controller.command.RequestParameter;
import com.dev.productioncenter.controller.command.SessionAttribute;
import com.dev.productioncenter.entity.UserRole;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;

import static com.dev.productioncenter.controller.command.CommandType.*;
import static com.dev.productioncenter.entity.UserRole.*;

/**
 * @author YanaV
 * The type Security access rights filter.
 * @project Production Center
 */
@WebFilter(urlPatterns = "/controller")
public class SecurityAccessRightsFilter implements Filter {
    private static final String DEFAULT_COMMAND = "go_to_home";
    private EnumMap<UserRole, List<CommandType>> accessibleCommands;

    @Override
    public void init(FilterConfig filterConfig) {
        accessibleCommands = new EnumMap<>(UserRole.class);
        accessibleCommands.put(ADMIN, List.of(GO_TO_HOME, GO_TO_COURSES, GO_TO_COURSE_INFO, GO_TO_ENROLLED_ON_COURSE,
                GO_TO_ADD_COURSE, GO_TO_UPDATE_COURSE, GO_TO_ACCOUNT, GO_TO_UPDATE_ACCOUNT_DATA, GO_TO_ACTIVITIES,
                GO_TO_ADD_ACTIVITY, GO_TO_ENROLLMENTS, GO_TO_USERS, GO_TO_TEACHERS, CHANGE_LOCALE, SIGN_OUT,
                UPDATE_ACCOUNT_DATA, UPLOAD_PROFILE_PICTURE, CHANGE_USER_STATUS, CHANGE_USER_ROLE,
                CHANGE_ENROLLMENT_STATUS, SEARCH_ENROLLMENTS, SEARCH_USERS, SEARCH_TEACHERS, SEARCH_COURSES, ADD_COURSE,
                ADD_ACTIVITY, UPDATE_COURSE, DEFAULT));
        accessibleCommands.put(TEACHER, List.of(GO_TO_HOME, GO_TO_COURSES, GO_TO_COURSE_INFO, GO_TO_ENROLLED_ON_COURSE,
                GO_TO_ACCOUNT, GO_TO_UPDATE_ACCOUNT_DATA, GO_TO_ACTIVITIES, GO_TO_TIMETABLE, SEARCH_COURSES,
                CHANGE_LOCALE, SIGN_OUT, GO_TO_CONTACTS, UPDATE_ACCOUNT_DATA, UPLOAD_PROFILE_PICTURE, DEFAULT));
        accessibleCommands.put(USER, List.of(GO_TO_HOME, GO_TO_COURSES, GO_TO_COURSE_INFO, GO_TO_ACCOUNT,
                GO_TO_REPLENISH_BALANCE, GO_TO_UPDATE_ACCOUNT_DATA, GO_TO_ACTIVITIES, GO_TO_ENROLL_ON_COURSE,
                GO_TO_USER_ENROLLMENTS, GO_TO_PAY_FOR_ENROLLMENT, GO_TO_TIMETABLE, GO_TO_CONTACTS, SEARCH_COURSES,
                CHANGE_LOCALE, SIGN_OUT, UPDATE_ACCOUNT_DATA, REPLENISH_BALANCE, UPLOAD_PROFILE_PICTURE, ENROLL_ON_COURSE,
                UPDATE_ENROLLMENT, CANCEL_ENROLLMENT, RENEW_ENROLLMENT, PAY_FOR_ENROLLMENT, DEFAULT));
        accessibleCommands.put(GUEST, List.of(GO_TO_HOME, GO_TO_SIGN_IN, GO_TO_SIGN_UP, GO_TO_COURSES, GO_TO_COURSE_INFO,
                GO_TO_ACTIVITIES, GO_TO_ENROLL_ON_COURSE, GO_TO_CONTACTS, SEARCH_COURSES, CHANGE_LOCALE, SIGN_IN,
                SIGN_UP, DEFAULT));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession();
        UserRole role = UserRole.valueOf(String.valueOf(session.getAttribute(SessionAttribute.ROLE)).toUpperCase());
        String command = request.getParameter(RequestParameter.COMMAND);
        if (command == null) {
            command = DEFAULT_COMMAND;
        }
        CommandType commandType = CommandType.valueOf(command.toUpperCase());
        Optional<CommandType> foundCommandType = accessibleCommands.get(role)
                .stream()
                .filter(c -> c == commandType)
                .findFirst();
        if (foundCommandType.isEmpty()) {
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + PagePath.ERROR_403);
            return;
        }
        chain.doFilter(request, response);
    }
}