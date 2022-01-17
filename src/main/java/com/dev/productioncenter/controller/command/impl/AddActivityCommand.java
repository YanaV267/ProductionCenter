package com.dev.productioncenter.controller.command.impl;

import com.dev.productioncenter.controller.command.Command;
import com.dev.productioncenter.controller.command.PagePath;
import com.dev.productioncenter.controller.command.RequestAttribute;
import com.dev.productioncenter.controller.command.Router;
import com.dev.productioncenter.entity.Activity;
import com.dev.productioncenter.entity.Course;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.service.ActivityService;
import com.dev.productioncenter.model.service.CourseService;
import com.dev.productioncenter.model.service.impl.ActivityServiceImpl;
import com.dev.productioncenter.model.service.impl.CourseServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dev.productioncenter.controller.command.RequestAttribute.*;
import static com.dev.productioncenter.controller.command.RequestAttribute.COURSES;
import static com.dev.productioncenter.controller.command.RequestParameter.*;

public class AddActivityCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ActivityService activityService = new ActivityServiceImpl();
    private static final CourseService courseService = new CourseServiceImpl();
    private static final String ADD_ACTIVITY_ERROR_MESSAGE_KEY = "error.add_activity";
    private static final String ADD_ACTIVITY_CONFIRM_MESSAGE_KEY = "confirm.add_activity";

    @Override
    public Router execute(HttpServletRequest request) {
        String category = request.getParameter(NEW_CATEGORY);
        if (category == null) {
            category = request.getParameter(CATEGORY);
        }
        String type = request.getParameter(TYPE);
        Map<String, String> activityData = new HashMap<>();
        activityData.put(CATEGORY, category);
        activityData.put(TYPE, type);
        try {
            List<String> categories = activityService.findCategories();
            request.setAttribute(RequestAttribute.CATEGORIES, categories);
            if (activityService.addActivity(activityData)) {
                request.setAttribute(MESSAGE, ADD_ACTIVITY_CONFIRM_MESSAGE_KEY);
                List<Activity> activities = activityService.findActivities();
                request.setAttribute(ACTIVITIES, activities);
                List<Course> courses = courseService.findAvailableCourses();
                request.setAttribute(COURSES, courses);
                return new Router(PagePath.SHOW_ACTIVITIES, Router.RouterType.FORWARD);
            } else {
                request.setAttribute(ACTIVITY, activityData);
                request.setAttribute(MESSAGE, ADD_ACTIVITY_ERROR_MESSAGE_KEY);
                return new Router(PagePath.ADD_ACTIVITY, Router.RouterType.FORWARD);
            }
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while adding activity: " + exception);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }
    }
}
