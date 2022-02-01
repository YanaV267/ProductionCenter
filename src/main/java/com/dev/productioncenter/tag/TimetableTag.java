package com.dev.productioncenter.tag;

import com.dev.productioncenter.entity.Course;
import com.dev.productioncenter.entity.Enrollment;
import com.dev.productioncenter.entity.Lesson;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspTagException;
import jakarta.servlet.jsp.tagext.BodyTagSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class TimetableTag extends BodyTagSupport {
    private static final Logger LOGGER = LogManager.getLogger();
    private String weekday;
    private Map<Enrollment, LocalDate> enrollments;
    private List<Course> courses;
    private StringBuilder pageTag;

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public void setEnrollments(Map<Enrollment, LocalDate> enrollments) {
        this.enrollments = enrollments;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public int doStartTag() throws JspException {
        pageTag = new StringBuilder();
        if (enrollments != null) {
            for (Map.Entry<Enrollment, LocalDate> enrollment : enrollments.entrySet()) {
                for (Lesson lesson : enrollment.getKey().getCourse().getLessons()) {
                    if (lesson.getWeekDay().equals(weekday)) {
                        pageTag.append("<div>");
                        pageTag.append("<div>").append(lesson.getStartTime()).append("</div>");
                        pageTag.append("<div>").append(enrollment.getKey().getCourse().getActivity().getType()).append("</div>");
                        pageTag.append("</div>");
                    }
                }
            }
        } else {
            for (Course course : courses) {
                for (Lesson lesson : course.getLessons()) {
                    if (lesson.getWeekDay().equals(weekday)) {
                        pageTag.append("<div>");
                        pageTag.append("<div>").append(lesson.getStartTime()).append("</div>");
                        pageTag.append("<div>").append(course.getActivity().getType()).append("</div>");
                        pageTag.append("</div>");
                    }
                }
            }
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
