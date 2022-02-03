package com.dev.productioncenter.controller.command.impl;

import com.dev.productioncenter.controller.command.*;
import com.dev.productioncenter.entity.Activity;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.service.ActivityService;
import com.dev.productioncenter.model.service.impl.ActivityServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dev.productioncenter.controller.command.RequestParameter.*;

/**
 * @project Production Center
 * @author YanaV
 * The type Add activity command.
 */
public class AddActivityCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String ADD_ACTIVITY_ERROR_MESSAGE_KEY = "error.activity.add";
    private static final String ADD_ACTIVITY_CONFIRM_MESSAGE_KEY = "confirm.activity.add";
    private final ActivityService activityService = ActivityServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String category = request.getParameter(NEW_CATEGORY);
        if (category == null) {
            category = request.getParameter(CATEGORY);
        }
        String type = request.getParameter(TYPE);
        Map<String, String> activityData = new HashMap<>();
        activityData.put(CATEGORY, category);
        activityData.put(TYPE, type);
        try {
            if (activityService.addActivity(activityData)) {
                List<String> categories = activityService.findCategories();
                List<Activity> activities = activityService.findActivities();
                session.setAttribute(RequestAttribute.CATEGORIES, categories);
                session.setAttribute(SessionAttribute.ACTIVITIES, activities);
                session.setAttribute(SessionAttribute.MESSAGE, ADD_ACTIVITY_CONFIRM_MESSAGE_KEY);
                return new Router(PagePath.SHOW_ACTIVITIES, Router.RouterType.REDIRECT);
            } else {
                request.setAttribute(RequestAttribute.ACTIVITY, activityData);
                session.setAttribute(SessionAttribute.MESSAGE, ADD_ACTIVITY_ERROR_MESSAGE_KEY);
                return new Router(PagePath.ADD_ACTIVITY, Router.RouterType.FORWARD);
            }
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while adding activity: " + exception);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }
    }
}
