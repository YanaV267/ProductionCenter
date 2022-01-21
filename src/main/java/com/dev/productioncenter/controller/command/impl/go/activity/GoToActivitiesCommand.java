package com.dev.productioncenter.controller.command.impl.go.activity;

import com.dev.productioncenter.controller.command.Command;
import com.dev.productioncenter.controller.command.PagePath;
import com.dev.productioncenter.controller.command.Router;
import com.dev.productioncenter.controller.command.SessionAttribute;
import com.dev.productioncenter.entity.Activity;
import com.dev.productioncenter.entity.Course;
import com.dev.productioncenter.entity.UserRole;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.service.ActivityService;
import com.dev.productioncenter.model.service.CourseService;
import com.dev.productioncenter.model.service.UserService;
import com.dev.productioncenter.model.service.impl.ActivityServiceImpl;
import com.dev.productioncenter.model.service.impl.CourseServiceImpl;
import com.dev.productioncenter.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

import static com.dev.productioncenter.controller.command.RequestAttribute.*;

public class GoToActivitiesCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final ActivityService activityService = new ActivityServiceImpl();
    private final CourseService courseService = new CourseServiceImpl();
    private final UserService userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute(SessionAttribute.ROLE);
        try {
            List<String> categories = activityService.findCategories();
            List<Activity> activities = activityService.findActivities();
            List<Course> allCourses;
            if (UserRole.valueOf(role.toUpperCase()) == UserRole.ADMIN
                    || UserRole.valueOf(role.toUpperCase()) == UserRole.TEACHER) {
                allCourses = courseService.findCourses();
            } else {
                allCourses = courseService.findAvailableCourses();
            }
            Map<Course, String> courses = userService.loadTeachersPictures(allCourses);
            session.setAttribute(CATEGORIES, categories);
            session.setAttribute(ACTIVITIES, activities);
            session.setAttribute(COURSES, courses);
            return new Router(PagePath.SHOW_ACTIVITIES, Router.RouterType.FORWARD);
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while redirecting to activities page: " + exception);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }
    }
}
