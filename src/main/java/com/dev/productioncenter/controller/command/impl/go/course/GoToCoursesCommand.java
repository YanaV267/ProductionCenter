package com.dev.productioncenter.controller.command.impl.go.course;

import com.dev.productioncenter.controller.command.*;
import com.dev.productioncenter.entity.Activity;
import com.dev.productioncenter.entity.Course;
import com.dev.productioncenter.entity.UserRole;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.service.ActivityService;
import com.dev.productioncenter.model.service.CourseService;
import com.dev.productioncenter.model.service.impl.ActivityServiceImpl;
import com.dev.productioncenter.model.service.impl.CourseServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

import static com.dev.productioncenter.controller.command.RequestAttribute.*;

public class GoToCoursesCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int DEFAULT_PAGE = 1;
    private final CourseService courseService = CourseServiceImpl.getInstance();
    private final ActivityService activityService = ActivityServiceImpl.getInstance();
    private final List<String> weekdays = Arrays.asList("monday", "tuesday", "wednesday", "thursday", "friday", "saturday");

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute(SessionAttribute.ROLE);
        String category = request.getParameter(RequestParameter.CATEGORY);
        int page;
        if (request.getParameter(RequestParameter.PAGE) == null) {
            page = DEFAULT_PAGE;
        } else {
            page = Integer.parseInt(request.getParameter(RequestParameter.PAGE));
        }
        try {
            if (category != null) {
                List<Activity> activities = activityService.findActivities(category);
                request.setAttribute(SELECTED_CATEGORY, category);
                session.setAttribute(SessionAttribute.ACTIVITIES, activities);
            }
            List<Course> courses;
            List<Course> nextCourses;
            if (UserRole.valueOf(role.toUpperCase()) == UserRole.ADMIN
                    || UserRole.valueOf(role.toUpperCase()) == UserRole.TEACHER) {
                courses = courseService.findCourses(page);
                nextCourses = courseService.findCourses(page + 1);
            } else {
                courses = courseService.findAvailableCourses(page);
                nextCourses = courseService.findAvailableCourses(page + 1);
            }
            List<String> categories = activityService.findCategories();
            session.setAttribute(SessionAttribute.COURSES, courses);
            session.setAttribute(SessionAttribute.CATEGORIES, categories);
            session.setAttribute(SessionAttribute.WEEKDAYS, weekdays);
            request.setAttribute(PAGE, page);
            request.setAttribute(LAST, nextCourses.isEmpty());
            return new Router(PagePath.SHOW_COURSES, Router.RouterType.FORWARD);
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while redirecting to courses page: " + exception);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }
    }
}
