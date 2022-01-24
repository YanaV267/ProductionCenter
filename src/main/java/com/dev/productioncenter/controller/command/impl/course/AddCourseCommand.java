package com.dev.productioncenter.controller.command.impl.course;

import com.dev.productioncenter.controller.command.*;
import com.dev.productioncenter.entity.Course;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.service.CourseService;
import com.dev.productioncenter.model.service.impl.CourseServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dev.productioncenter.controller.command.RequestParameter.*;

public class AddCourseCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String EMPTY_STRING_REGEX = "";
    private static final String ADD_COURSE_ERROR_MESSAGE_KEY = "error.add_course";
    private static final String ADD_COURSE_CONFIRM_MESSAGE_KEY = "confirm.add_course";
    private final CourseService courseService = new CourseServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map<String, String> courseData = new HashMap<>();
        courseData.put(CATEGORY, Arrays.toString(request.getParameterValues(CATEGORY)));
        courseData.put(TYPE, request.getParameter(TYPE));
        courseData.put(TEACHER, request.getParameter(TEACHER));
        String weekdays = request.getParameterValues(WEEKDAYS) != null ? Arrays.toString(request.getParameterValues(WEEKDAYS)) : EMPTY_STRING_REGEX;
        courseData.put(WEEKDAYS, weekdays);
        courseData.put(TIME, request.getParameter(TIME));
        courseData.put(DURATION, request.getParameter(DURATION));
        courseData.put(MIN_AGE, request.getParameter(MIN_AGE));
        courseData.put(MAX_AGE, request.getParameter(MAX_AGE));
        courseData.put(LESSON_PRICE, request.getParameter(LESSON_PRICE));
        courseData.put(STUDENT_AMOUNT, request.getParameter(STUDENT_AMOUNT));
        courseData.put(DESCRIPTION, request.getParameter(DESCRIPTION));
        try {
            if (courseService.addCourse(courseData)) {
                List<Course> courses = courseService.findCourses();
                session.setAttribute(SessionAttribute.COURSES, courses);
                session.setAttribute(SessionAttribute.MESSAGE, ADD_COURSE_CONFIRM_MESSAGE_KEY);
                return new Router(PagePath.SHOW_COURSES, Router.RouterType.REDIRECT);
            } else {
                request.setAttribute(RequestAttribute.COURSE, courseData);
                request.setAttribute(RequestAttribute.MESSAGE, ADD_COURSE_ERROR_MESSAGE_KEY);
                return new Router(PagePath.ADD_COURSE, Router.RouterType.FORWARD);
            }
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while adding course: " + exception);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }
    }
}
