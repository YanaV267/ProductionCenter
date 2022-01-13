package com.dev.productioncenter.controller.command.impl.go.course;

import com.dev.productioncenter.controller.command.*;
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

import java.util.List;

public class GoToCoursesCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final CourseService courseService = new CourseServiceImpl();
    private static final ActivityService activityService = new ActivityServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute(SessionAttribute.ROLE);
        try {
            List<Course> courses;
            if (UserRole.valueOf(role.toUpperCase()) == UserRole.ADMIN
                    || UserRole.valueOf(role.toUpperCase()) == UserRole.TEACHER) {
                courses = courseService.findCourses();
            } else {
                courses = courseService.findAvailableCourses();
            }
            request.setAttribute(RequestAttribute.COURSES, courses);
            List<String> categories = activityService.findCategories();
            request.setAttribute(RequestAttribute.CATEGORIES, categories);
            return new Router(PagePath.SHOW_COURSES, Router.RouterType.FORWARD);
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while redirecting to courses page: " + exception);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }
    }
}
