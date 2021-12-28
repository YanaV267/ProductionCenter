package com.dev.productioncenter.controller.listener;

import com.dev.productioncenter.controller.command.SessionAttribute;
import com.dev.productioncenter.entity.UserRole;
import com.dev.productioncenter.controller.command.PagePath;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebListener
public class HttpSessionListenerImpl implements HttpSessionListener {
    private static final String DEFAULT_LOCALE = "ru_RU";
    private static final String DEFAULT_LANGUAGE = "RU";

    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {
        HttpSession session = sessionEvent.getSession();
        session.setAttribute(SessionAttribute.CURRENT_PAGE, PagePath.HOME);
        session.setAttribute(SessionAttribute.LOCALE, DEFAULT_LOCALE);
        session.setAttribute(SessionAttribute.LANGUAGE, DEFAULT_LANGUAGE);
        session.setAttribute(SessionAttribute.ROLE, UserRole.GUEST.getRole());
    }
}
