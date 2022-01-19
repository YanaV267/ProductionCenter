package com.dev.productioncenter.controller.command.impl.go;

import com.dev.productioncenter.controller.command.Command;
import com.dev.productioncenter.controller.command.PagePath;
import com.dev.productioncenter.controller.command.Router;
import com.dev.productioncenter.entity.Course;
import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.entity.UserRole;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.service.CourseService;
import com.dev.productioncenter.model.service.UserService;
import com.dev.productioncenter.model.service.impl.CourseServiceImpl;
import com.dev.productioncenter.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

import static com.dev.productioncenter.controller.command.RequestAttribute.*;

public class GoToContactsCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final UserService userService = new UserServiceImpl();
    private final CourseService courseService = new CourseServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {
        try {
            List<Course> courses = courseService.findCourses();
            Map<User, String> employers = userService.findUsers(UserRole.ADMIN);
            Map<User, String> teachers = userService.findUsers(UserRole.TEACHER);
            request.setAttribute(COURSES, courses);
            request.setAttribute(EMPLOYERS, employers);
            request.setAttribute(TEACHERS, teachers);
            return new Router(PagePath.CONTACTS, Router.RouterType.FORWARD);
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while redirecting to contacts page: " + exception);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }
    }
}
