package com.development.productioncenter.controller.command.impl;

import com.development.productioncenter.controller.command.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class ChangeLocaleCommand implements Command {
    private static final String LANGUAGE_ENGLISH = "EN";
    private static final String LANGUAGE_RUSSIAN = "RU";
    private static final String LOCALE_ENGLISH = "en_EN";
    private static final String LOCALE_RUSSIAN = "ru_RU";

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(SessionAttribute.CURRENT_PAGE);
        String language = request.getParameter(RequestParameter.LANGUAGE);
        switch (language) {
            case LANGUAGE_ENGLISH -> session.setAttribute(SessionAttribute.LOCALE, LOCALE_ENGLISH);
            case LANGUAGE_RUSSIAN -> session.setAttribute(SessionAttribute.LOCALE, LOCALE_RUSSIAN);
        }
        session.setAttribute(SessionAttribute.LANGUAGE, language);
        return new Router(currentPage, Router.RouterType.REDIRECT);
    }
}
