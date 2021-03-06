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
import com.dev.productioncenter.util.PhoneNumberFormatter;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.dev.productioncenter.controller.command.RequestAttribute.COURSE;
import static com.dev.productioncenter.controller.command.RequestAttribute.NUMBER;
import static com.dev.productioncenter.controller.command.RequestParameter.CHOSEN_COURSE_ID;

/**
 * @project Production Center
 * @author YanaV
 * The type Go to course info command.
 */
public class GoToCourseInfoCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final CourseService courseService = CourseServiceImpl.getInstance();
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        long chosenId = Long.parseLong(request.getParameter(CHOSEN_COURSE_ID));
        try {
            Optional<Course> course = courseService.findCourse(chosenId);
            if (course.isPresent()) {
                Optional<User> teacher = userService.findUser(course.get().getTeacher().getSurname(),
                        course.get().getTeacher().getName(), UserRole.TEACHER);
                if (teacher.isPresent()) {
                    course.get().setTeacher(teacher.get());
                    String phoneNumber = PhoneNumberFormatter.format(teacher.get().getPhoneNumber());
                    request.setAttribute(COURSE, course.get());
                    request.setAttribute(NUMBER, phoneNumber);
                    return new Router(PagePath.COURSE_INFO, Router.RouterType.FORWARD);
                }
            }
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while redirecting to courses page: " + exception);
        }
        return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
    }
}
