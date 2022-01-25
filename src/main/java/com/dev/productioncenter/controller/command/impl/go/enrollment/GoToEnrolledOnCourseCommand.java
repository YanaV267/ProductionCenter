package com.dev.productioncenter.controller.command.impl.go.enrollment;

import com.dev.productioncenter.controller.command.Command;
import com.dev.productioncenter.controller.command.PagePath;
import com.dev.productioncenter.controller.command.RequestParameter;
import com.dev.productioncenter.controller.command.Router;
import com.dev.productioncenter.entity.Course;
import com.dev.productioncenter.entity.Enrollment;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.service.CourseService;
import com.dev.productioncenter.model.service.EnrollmentService;
import com.dev.productioncenter.model.service.impl.CourseServiceImpl;
import com.dev.productioncenter.model.service.impl.EnrollmentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import static com.dev.productioncenter.controller.command.RequestAttribute.*;

public class GoToEnrolledOnCourseCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String DEFAULT_PAGE = "1";
    private final EnrollmentService enrollmentService = EnrollmentServiceImpl.getInstance();
    private final CourseService courseService = CourseServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        long courseId = Long.parseLong(request.getParameter(RequestParameter.CHOSEN_COURSE_ID));
        String page = request.getParameter(RequestParameter.PAGE);
        if (page == null) {
            page = DEFAULT_PAGE;
        }
        try {
            if (enrollmentService.checkEnrollmentsReservationStatus()) {
                Optional<Course> course = courseService.findCourse(courseId);
                if (course.isPresent()) {
                    Map<Enrollment, LocalDate> enrollments = enrollmentService.findEnrollments(courseId);
                    request.setAttribute(ENROLLMENTS, enrollments);
                    request.setAttribute(COURSE, course.get());
                    request.setAttribute(PAGE, page);
                    return new Router(PagePath.SHOW_ENROLLED_ON_COURSE, Router.RouterType.FORWARD);
                }
            }
        } catch (ServiceException exception) {
            LOGGER.error("Error has occurred while redirecting to enrolled on course page: " + exception);
        }
        return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
    }
}
