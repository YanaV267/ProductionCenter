package com.dev.productioncenter.controller.command.impl;

import com.dev.productioncenter.controller.command.Command;
import com.dev.productioncenter.controller.command.RequestParameter;
import com.dev.productioncenter.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static com.dev.productioncenter.controller.command.SessionAttribute.*;

public class ChangeLocaleCommand implements Command {
    private static final String LANGUAGE_ENGLISH = "EN";
    private static final String LANGUAGE_RUSSIAN = "RU";
    private static final String LOCALE_ENGLISH = "en_US";
    private static final String LOCALE_RUSSIAN = "ru_RU";

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        String language = request.getParameter(RequestParameter.LANGUAGE);
        switch (language) {
            case LANGUAGE_ENGLISH -> session.setAttribute(LOCALE, LOCALE_ENGLISH);
            case LANGUAGE_RUSSIAN -> session.setAttribute(LOCALE, LOCALE_RUSSIAN);
        }
        session.setAttribute(LANGUAGE, language);
        return new Router(currentPage, Router.RouterType.REDIRECT);
    }
}
