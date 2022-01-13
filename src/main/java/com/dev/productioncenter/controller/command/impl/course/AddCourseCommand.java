package com.dev.productioncenter.controller.command.impl.course;

import com.dev.productioncenter.controller.command.Command;
import com.dev.productioncenter.controller.command.PagePath;
import com.dev.productioncenter.controller.command.RequestAttribute;
import com.dev.productioncenter.controller.command.Router;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.service.CourseService;
import com.dev.productioncenter.model.service.impl.CourseServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.dev.productioncenter.controller.command.RequestParameter.*;

public class AddCourseCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final CourseService courseService = new CourseServiceImpl();
    private static final String ADD_COURSE_ERROR_MESSAGE_KEY = "error.add_course";
    private static final String ADD_COURSE_CONFIRM_MESSAGE_KEY = "confirm.add_course";

    @Override
    public Router execute(HttpServletRequest request) {
        Map<String, String> courseData = new HashMap<>();
        courseData.put(CATEGORY, Arrays.toString(request.getParameterValues(CATEGORY)));
        courseData.put(TYPE, request.getParameter(TYPE));
        courseData.put(TEACHER, request.getParameter(TEACHER));
        courseData.put(WEEKDAYS, Arrays.toString(request.getParameterValues(WEEKDAYS)));
        courseData.put(TIME, request.getParameter(TIME));
        courseData.put(DURATION, request.getParameter(DURATION));
        courseData.put(MIN_AGE, request.getParameter(MIN_AGE));
        courseData.put(MAX_AGE, request.getParameter(MAX_AGE));
        courseData.put(LESSON_PRICE, request.getParameter(LESSON_PRICE));
        courseData.put(STUDENT_AMOUNT, request.getParameter(STUDENT_AMOUNT));
        courseData.put(DESCRIPTION, request.getParameter(DESCRIPTION));
        try {
            if (courseService.addCourse(courseData)) {
                request.setAttribute(RequestAttribute.MESSAGE, ADD_COURSE_CONFIRM_MESSAGE_KEY);
                return new Router(PagePath.SHOW_COURSES, Router.RouterType.FORWARD);
            } else {
                request.setAttribute(RequestAttribute.COURSE_DATA, courseData);
                request.setAttribute(RequestAttribute.MESSAGE, ADD_COURSE_ERROR_MESSAGE_KEY);
                return new Router(PagePath.ADD_COURSE, Router.RouterType.FORWARD);
            }
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while adding course: " + exception);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }
    }
}
