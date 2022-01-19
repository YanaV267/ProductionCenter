package com.dev.productioncenter.controller.command.impl.go.activity;

import com.dev.productioncenter.controller.command.Command;
import com.dev.productioncenter.controller.command.PagePath;
import com.dev.productioncenter.controller.command.RequestAttribute;
import com.dev.productioncenter.controller.command.Router;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.service.ActivityService;
import com.dev.productioncenter.model.service.impl.ActivityServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GoToAddActivityCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final ActivityService activityService = new ActivityServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {
        try {
            List<String> categories = activityService.findCategories();
            request.setAttribute(RequestAttribute.CATEGORIES, categories);
            return new Router(PagePath.ADD_ACTIVITY, Router.RouterType.FORWARD);
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while redirecting to activities page: " + exception);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }
    }
}
