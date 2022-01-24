package com.dev.productioncenter.controller.command.impl.go.activity;

import com.dev.productioncenter.controller.command.*;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.service.ActivityService;
import com.dev.productioncenter.model.service.impl.ActivityServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GoToAddActivityCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final ActivityService activityService = new ActivityServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        try {
            List<String> categories = activityService.findCategories();
            session.setAttribute(SessionAttribute.CATEGORIES, categories);
            return new Router(PagePath.ADD_ACTIVITY, Router.RouterType.FORWARD);
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while redirecting to activities page: " + exception);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }
    }
}
