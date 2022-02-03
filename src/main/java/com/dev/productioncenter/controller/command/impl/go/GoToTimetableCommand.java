package com.dev.productioncenter.controller.command.impl.go;

import com.dev.productioncenter.controller.command.Command;
import com.dev.productioncenter.controller.command.PagePath;
import com.dev.productioncenter.controller.command.Router;
import com.dev.productioncenter.controller.command.SessionAttribute;
import com.dev.productioncenter.entity.Course;
import com.dev.productioncenter.entity.Enrollment;
import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.entity.UserRole;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.service.CourseService;
import com.dev.productioncenter.model.service.EnrollmentService;
import com.dev.productioncenter.model.service.impl.CourseServiceImpl;
import com.dev.productioncenter.model.service.impl.EnrollmentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.dev.productioncenter.controller.command.RequestAttribute.*;

/**
 * @project Production Center
 * @author YanaV
 * The type Go to timetable command.
 */
public class GoToTimetableCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final EnrollmentService enrollmentService = EnrollmentServiceImpl.getInstance();
    private final CourseService courseService = CourseServiceImpl.getInstance();
    private final List<String> weekdays = Arrays.asList("monday", "tuesday", "wednesday", "thursday", "friday", "saturday");

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        try {
            if (enrollmentService.checkEnrollmentsReservationStatus()) {
                if (user.getUserRole() == UserRole.USER) {
                    Map<Enrollment, LocalDate> enrollments = enrollmentService.findEnrollments(user);
                    request.setAttribute(ENROLLMENTS, enrollments);
                } else {
                    List<Course> courses = courseService.findCourses(user);
                    request.setAttribute(COURSES, courses);
                }
                request.setAttribute(WEEKDAYS, weekdays);
                return new Router(PagePath.TIMETABLE, Router.RouterType.FORWARD);
            }
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while redirecting to timetable page: " + exception);
        }
        return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
    }
}
