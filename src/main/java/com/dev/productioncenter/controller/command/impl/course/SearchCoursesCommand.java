package com.dev.productioncenter.controller.command.impl.course;

import com.dev.productioncenter.controller.command.Command;
import com.dev.productioncenter.controller.command.PagePath;
import com.dev.productioncenter.controller.command.RequestParameter;
import com.dev.productioncenter.controller.command.Router;
import com.dev.productioncenter.entity.Activity;
import com.dev.productioncenter.entity.Course;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.service.CourseService;
import com.dev.productioncenter.model.service.impl.CourseServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.dev.productioncenter.controller.command.RequestAttribute.*;
import static com.dev.productioncenter.controller.command.RequestParameter.CATEGORY;
import static com.dev.productioncenter.controller.command.RequestParameter.TYPE;

public class SearchCoursesCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String WEEKDAYS_CHOICE_ERROR_MESSAGE_KEY = "error.invalid_weekdays_amount";
    private final CourseService courseService = new CourseServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {
        String category = request.getParameter(CATEGORY);
        String type = request.getParameter(TYPE);
        String[] weekdays = request.getParameterValues(RequestParameter.WEEKDAYS);
        try {
            if (weekdays != null && weekdays.length > 3) {
                request.setAttribute(MESSAGE, WEEKDAYS_CHOICE_ERROR_MESSAGE_KEY);
                return new Router(PagePath.SHOW_COURSES, Router.RouterType.FORWARD);
            }
            Activity activity = new Activity(category, type);
            List<Course> courses = courseService.findCourses(activity, weekdays);
            request.setAttribute(COURSES, courses);
            request.setAttribute(SELECTED_CATEGORY, category);
            request.setAttribute(SELECTED_TYPE, type);
            request.setAttribute(SELECTED_WEEKDAYS, weekdays);
            return new Router(PagePath.SHOW_COURSES, Router.RouterType.FORWARD);
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while searching courses: " + exception);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }
    }
}
