package com.development.productioncenter.controller.listener;

import com.development.productioncenter.controller.command.PagePath;
import com.development.productioncenter.controller.command.SessionAttribute;
import com.development.productioncenter.entity.UserRole;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebListener
public class HttpSessionListenerImpl implements HttpSessionListener {
    private static final String DEFAULT_LOCALE = "ru_RU";
    private static final String DEFAULT_LANGUAGE = "RU";

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setAttribute(SessionAttribute.CURRENT_PAGE, PagePath.HOME);
        session.setAttribute(SessionAttribute.LOCALE, DEFAULT_LOCALE);
        session.setAttribute(SessionAttribute.LANGUAGE, DEFAULT_LANGUAGE);
        session.setAttribute(SessionAttribute.ROLE, UserRole.GUEST.getRole());
    }
}
