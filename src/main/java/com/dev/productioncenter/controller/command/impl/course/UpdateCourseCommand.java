package com.dev.productioncenter.controller.command.impl.course;

import com.dev.productioncenter.controller.command.*;
import com.dev.productioncenter.entity.Course;
import com.dev.productioncenter.entity.UserRole;
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

public class UpdateCourseCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String EMPTY_STRING_REGEX = "";
    private static final String UPDATE_COURSE_ERROR_MESSAGE_KEY = "error.update_course";
    private static final String UPDATE_COURSE_CONFIRM_MESSAGE_KEY = "confirm.update_course";
    private final CourseService courseService = new CourseServiceImpl();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute(SessionAttribute.ROLE);
        Map<String, String> courseData = new HashMap<>();
        courseData.put(ID, request.getParameter(CHOSEN_COURSE_ID));
        courseData.put(TYPE, request.getParameter(TYPE));
        courseData.put(TEACHER, request.getParameter(TEACHER));
        String[] weekdays = request.getParameterValues(WEEKDAYS);
        courseData.put(WEEKDAYS, weekdays != null ? Arrays.toString(weekdays) : EMPTY_STRING_REGEX);
        courseData.put(TIME, Arrays.toString(request.getParameterValues(TIME)));
        courseData.put(DURATION, Arrays.toString(request.getParameterValues(DURATION)));
        courseData.put(MIN_AGE, request.getParameter(MIN_AGE));
        courseData.put(MAX_AGE, request.getParameter(MAX_AGE));
        courseData.put(LESSON_PRICE, request.getParameter(LESSON_PRICE));
        courseData.put(STUDENT_AMOUNT, request.getParameter(STUDENT_AMOUNT));
        courseData.put(DESCRIPTION, request.getParameter(DESCRIPTION));
        courseData.put(STATUS, request.getParameter(STATUS));
        try {
            if (courseService.updateCourse(courseData)) {
                List<Course> courses;
                if (UserRole.valueOf(role.toUpperCase()) == UserRole.ADMIN
                        || UserRole.valueOf(role.toUpperCase()) == UserRole.TEACHER) {
                    courses = courseService.findCourses();
                } else {
                    courses = courseService.findAvailableCourses();
                }
                request.setAttribute(RequestAttribute.COURSES, courses);
                session.setAttribute(SessionAttribute.MESSAGE, UPDATE_COURSE_CONFIRM_MESSAGE_KEY);
                return new Router(PagePath.SHOW_COURSES, Router.RouterType.REDIRECT);
            } else {
                request.setAttribute(RequestAttribute.MESSAGE, UPDATE_COURSE_ERROR_MESSAGE_KEY);
                return new Router(PagePath.UPDATE_COURSE, Router.RouterType.FORWARD);
            }
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while updating course: " + exception);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }
    }
}
