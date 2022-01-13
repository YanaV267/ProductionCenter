package com.dev.productioncenter.model.service.impl;

import com.dev.productioncenter.entity.*;
import com.dev.productioncenter.exception.DaoException;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.dao.*;
import com.dev.productioncenter.model.dao.impl.*;
import com.dev.productioncenter.model.service.CourseService;
import com.dev.productioncenter.validator.impl.CourseValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.dev.productioncenter.controller.command.RequestParameter.*;

public class CourseServiceImpl implements CourseService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final CourseDao courseDao = CourseDaoImpl.getInstance();
    private static final LessonDao lessonDao = LessonDaoImpl.getInstance();
    private static final AgeGroupDao ageGroupDao = AgeGroupDaoImpl.getInstance();
    private static final ActivityDao activityDao = ActivityDaoImpl.getInstance();
    private static final UserDao userDao = UserDaoImpl.getInstance();
    private static final String TEACHER_NAME_DELIMITER = " ";
    private static final String WEEKDAYS_DELIMITER = ",";
    private static final String REMOVING_SYMBOLS_REGEX = "[\\[\\]]";
    private static final String REPLACEMENT_REGEX = "";

    @Override
    public boolean addCourse(Map<String, String> courseData) throws ServiceException {
        try {
            if (CourseValidatorImpl.getInstance().checkCourse(courseData)) {
                AgeGroup ageGroup = new AgeGroup(Integer.parseInt(courseData.get(MIN_AGE)),
                        Integer.parseInt(courseData.get(MAX_AGE)));
                long ageGroupId = ageGroupDao.add(ageGroup);
                ageGroup.setId(ageGroupId);
                Activity activity = new Activity(courseData.get(CATEGORY)
                        .replaceAll(REMOVING_SYMBOLS_REGEX, REPLACEMENT_REGEX), courseData.get(TYPE));
                long activityId = activityDao.findActivityId(activity);
                activity.setId(activityId);
                Optional<User> teacher = userDao.findTeacherByName(courseData.get(TEACHER).split(TEACHER_NAME_DELIMITER)[0],
                        courseData.get(TEACHER).split(TEACHER_NAME_DELIMITER)[1]);
                if (teacher.isPresent()) {
                    Course course = new Course.CourseBuilder()
                            .setActivity(activity)
                            .setTeacher(teacher.get())
                            .setStudentAmount(Integer.parseInt(courseData.get(STUDENT_AMOUNT)))
                            .setLessonPrice(BigDecimal.valueOf(Double.parseDouble(courseData.get(LESSON_PRICE))))
                            .setAgeGroup(ageGroup)
                            .setDescription(courseData.get(DESCRIPTION))
                            .build();
                    long courseId = courseDao.add(course);
                    course.setId(courseId);
                    String[] weekdays = courseData.get(WEEKDAYS)
                            .replaceAll(REMOVING_SYMBOLS_REGEX, REPLACEMENT_REGEX)
                            .split(WEEKDAYS_DELIMITER);
                    for (String weekday : weekdays) {
                        Lesson lesson = new Lesson();
                        lesson.setCourse(course);
                        lesson.setWeekDay(weekday);
                        lesson.setStartTime(LocalTime.parse(courseData.get(TIME)));
                        lesson.setDuration(Integer.parseInt(courseData.get(DURATION)));
                        lessonDao.add(lesson);
                    }
                    return true;
                }
            }
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while adding course: " + exception);
            throw new ServiceException("Error has occurred while adding course: " + exception);
        }
        return false;
    }

    @Override
    public List<Course> findCourses() throws ServiceException {
        try {
            return courseDao.findAll();
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding all courses: " + exception);
            throw new ServiceException("Error has occurred while finding all courses: " + exception);
        }
    }

    @Override
    public List<Course> findAvailableCourses() throws ServiceException {
        try {
            List<Course> courses = courseDao.findCourseByStatus(CourseStatus.UPCOMING);
            courses.addAll(courseDao.findCourseByStatus(CourseStatus.RUNNING));
            return courses;
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding available courses: " + exception);
            throw new ServiceException("Error has occurred while finding available courses: " + exception);
        }
    }
}
