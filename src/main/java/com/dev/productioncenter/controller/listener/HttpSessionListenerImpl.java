package com.dev.productioncenter.controller.listener;

import com.dev.productioncenter.entity.UserRole;
import com.dev.productioncenter.controller.command.PagePath;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import static com.dev.productioncenter.controller.command.SessionAttribute.*;

/**
 * @project Production Center
 * @author YanaV
 * The type Http session listener.
 */
@WebListener
public class HttpSessionListenerImpl implements HttpSessionListener {
    private static final String DEFAULT_LOCALE = "en_US";
    private static final String DEFAULT_LANGUAGE = "EN";

    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {
        HttpSession session = sessionEvent.getSession();
        session.setAttribute(CURRENT_PAGE, PagePath.HOME);
        session.setAttribute(LOCALE, DEFAULT_LOCALE);
        session.setAttribute(LANGUAGE, DEFAULT_LANGUAGE);
        session.setAttribute(ROLE, UserRole.GUEST.getRole());
    }
}
