package com.dev.productioncenter.controller.command.impl.go.activity;

import com.dev.productioncenter.controller.command.Command;
import com.dev.productioncenter.controller.command.PagePath;
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

import java.util.List;

import static com.dev.productioncenter.controller.command.RequestAttribute.*;

public class GoToActivitiesCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ActivityService activityService = new ActivityServiceImpl();
    private static final CourseService courseService = new CourseServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {
        try {
            List<String> categories = activityService.findCategories();
            request.setAttribute(CATEGORIES, categories);
            List<Activity> activities = activityService.findActivities();
            request.setAttribute(ACTIVITIES, activities);
            List<Course> courses = courseService.findAvailableCourses();
            request.setAttribute(COURSES, courses);
            return new Router(PagePath.SHOW_ACTIVITIES, Router.RouterType.FORWARD);
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while redirecting to activities page: " + exception);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }
    }
}
