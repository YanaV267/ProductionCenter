package com.dev.productioncenter.tag;

import com.dev.productioncenter.entity.Course;
import com.dev.productioncenter.entity.Enrollment;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspTagException;
import jakarta.servlet.jsp.tagext.BodyTagSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

/**
 * @author YanaV
 * The type Timetable tag.
 * @project Production Center
 */
public class TimetableTag extends BodyTagSupport {
    private static final Logger LOGGER = LogManager.getLogger();
    private String weekday;
    private Map<Enrollment, LocalDate> enrollments;
    private List<Course> courses;
    private StringBuilder pageTag;

    /**
     * Sets weekday.
     *
     * @param weekday the weekday
     */
    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    /**
     * Sets enrollments.
     *
     * @param enrollments the enrollments
     */
    public void setEnrollments(Map<Enrollment, LocalDate> enrollments) {
        this.enrollments = enrollments;
    }

    /**
     * Sets courses.
     *
     * @param courses the courses
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public int doStartTag() throws JspException {
        pageTag = new StringBuilder();
        if (enrollments != null) {
            Map<Enrollment, LocalDate> currentEnrollments = new HashMap<>();
            enrollments.forEach((key, value) -> {
                try {
                    currentEnrollments.put(key.clone(), value);
                } catch (CloneNotSupportedException exception) {
                    LOGGER.error("Error has occurred while cloning enrollment: " + exception);
                }
            });
            currentEnrollments.keySet().forEach(e -> e.getCourse().setLessons(e.getCourse().getLessons()
                    .stream()
                    .filter(l -> l.getWeekDay().equals(weekday))
                    .toList()));
            currentEnrollments.keySet().stream()
                    .filter(e -> !e.getCourse().getLessons().isEmpty())
                    .sorted(Comparator.comparing(c -> c.getCourse().getLessons().get(0).getStartTime()))
                    .forEach(e -> {
                        pageTag.append("<div>");
                        pageTag.append("<div>").append(e.getCourse().getLessons().get(0).getStartTime()).append("</div>");
                        pageTag.append("<div>").append(e.getCourse().getActivity().getType()).append("</div>");
                        pageTag.append("</div>");
                    });
        } else {
            List<Course> currentCourses = new ArrayList<>();
            courses.forEach(course -> {
                try {
                    currentCourses.add(course.clone());
                } catch (CloneNotSupportedException exception) {
                    LOGGER.error("Error has occurred while cloning course: " + exception);
                }
            });
            currentCourses.forEach(c -> c.setLessons(c.getLessons()
                    .stream()
                    .filter(l -> l.getWeekDay().equals(weekday))
                    .toList()));
            currentCourses.stream()
                    .filter(c -> !c.getLessons().isEmpty())
                    .sorted(Comparator.comparing(c -> c.getLessons().get(0).getStartTime()))
                    .forEach(c -> {
                        pageTag.append("<div>");
                        pageTag.append("<div>").append(c.getLessons().get(0).getStartTime()).append("</div>");
                        pageTag.append("<div>").append(c.getActivity().getType()).append("</div>");
                        pageTag.append("</div>");
                    });
        }
        try {
            pageContext.getOut().write(pageTag.toString());
            return EVAL_BODY_BUFFERED;
        } catch (IOException exception) {
            LOGGER.error("Error has occurred while writing tag into stream: " + exception);
            throw new JspTagException("Error has occurred while writing tag into stream: ", exception);
        }
    }

    @Override
    public int doEndTag() throws JspException {
        if (pageTag.isEmpty()) {
            try {
                pageContext.getOut().write(bodyContent.getString());
            } catch (IOException exception) {
                LOGGER.error("Error has occurred while writing tag into stream: " + exception);
                throw new JspTagException("Error has occurred while writing tag into stream: ", exception);
            }
        }
        return EVAL_BODY_INCLUDE;
    }
}
