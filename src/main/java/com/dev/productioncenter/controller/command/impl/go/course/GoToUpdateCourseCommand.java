package com.dev.productioncenter.controller.command.impl.go.course;

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

import java.util.Map;
import java.util.Optional;

import static com.dev.productioncenter.controller.command.RequestAttribute.COURSE;
import static com.dev.productioncenter.controller.command.RequestAttribute.TEACHERS;
import static com.dev.productioncenter.controller.command.RequestParameter.CHOSEN_COURSE_ID;

public class GoToUpdateCourseCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final CourseService courseService = new CourseServiceImpl();
    private final UserService userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {
        long chosenId = Long.parseLong(request.getParameter(CHOSEN_COURSE_ID));
        try {
            Map<User, String> teachers = userService.findUsers(UserRole.TEACHER);
            Optional<Course> course = courseService.findCourse(chosenId);
            if (course.isPresent()) {
                request.setAttribute(TEACHERS, teachers);
                request.setAttribute(COURSE, course.get());
                return new Router(PagePath.UPDATE_COURSE, Router.RouterType.FORWARD);
            }
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while redirecting to updating course page: " + exception);
        }
        return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
    }
}
