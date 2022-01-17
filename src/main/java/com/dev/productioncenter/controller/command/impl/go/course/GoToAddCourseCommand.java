package com.dev.productioncenter.controller.command.impl.go.course;

import com.dev.productioncenter.controller.command.Command;
import com.dev.productioncenter.controller.command.PagePath;
import com.dev.productioncenter.controller.command.RequestParameter;
import com.dev.productioncenter.controller.command.Router;
import com.dev.productioncenter.entity.Activity;
import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.service.ActivityService;
import com.dev.productioncenter.model.service.UserService;
import com.dev.productioncenter.model.service.impl.ActivityServiceImpl;
import com.dev.productioncenter.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.dev.productioncenter.controller.command.RequestAttribute.*;

public class GoToAddCourseCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ActivityService activityService = new ActivityServiceImpl();
    private static final UserService userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {
        String category = request.getParameter(RequestParameter.CATEGORY);
        try {
            if (category != null) {
                request.setAttribute(SELECTED_CATEGORY, category);
                List<Activity> activities = activityService.findActivities(category);
                request.setAttribute(ACTIVITIES, activities);
            }
            List<String> categories = activityService.findCategories();
            List<User> teachers = userService.findTeachers();
            request.setAttribute(CATEGORIES, categories);
            request.setAttribute(TEACHERS, teachers);
            return new Router(PagePath.ADD_COURSE, Router.RouterType.FORWARD);
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while redirecting to adding course: " + exception);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }
    }
}
