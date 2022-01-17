package com.dev.productioncenter.controller.command.impl.go.course;

import com.dev.productioncenter.controller.command.Command;
import com.dev.productioncenter.controller.command.PagePath;
import com.dev.productioncenter.controller.command.Router;
import com.dev.productioncenter.entity.Course;
import com.dev.productioncenter.entity.Lesson;
import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.entity.UserRole;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.service.CourseService;
import com.dev.productioncenter.model.service.LessonService;
import com.dev.productioncenter.model.service.UserService;
import com.dev.productioncenter.model.service.impl.CourseServiceImpl;
import com.dev.productioncenter.model.service.impl.LessonServiceImpl;
import com.dev.productioncenter.model.service.impl.UserServiceImpl;
import com.dev.productioncenter.util.PhoneNumberFormatter;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.dev.productioncenter.controller.command.RequestAttribute.TEACHER;
import static com.dev.productioncenter.controller.command.RequestAttribute.*;
import static com.dev.productioncenter.controller.command.RequestParameter.*;

public class GoToCourseInfoCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final CourseService courseService = new CourseServiceImpl();
    private static final LessonService lessonService = new LessonServiceImpl();
    private static final UserService userService = new UserServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {
        Map<String, String> chosenCourseData = new HashMap<>();
        chosenCourseData.put(CHOSEN_TYPE, request.getParameter(CHOSEN_TYPE));
        chosenCourseData.put(CHOSEN_AGE_GROUP, request.getParameter(CHOSEN_AGE_GROUP));
        chosenCourseData.put(CHOSEN_TEACHER, request.getParameter(CHOSEN_TEACHER));
        try {
            Optional<Course> course = courseService.findCourse(chosenCourseData);
            if (course.isPresent()) {
                List<Lesson> lessons = lessonService.findLessons(course.get().getId());
                Optional<User> teacher = userService.findUser(course.get().getTeacher().getSurname(),
                        course.get().getTeacher().getName(), UserRole.TEACHER);
                if (teacher.isPresent()) {
                    request.setAttribute(COURSE, course.get());
                    request.setAttribute(LESSONS, lessons);
                    request.setAttribute(TEACHER, teacher.get());
                    String phoneNumber = PhoneNumberFormatter.format(teacher.get().getPhoneNumber());
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
