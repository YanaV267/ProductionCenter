package com.dev.productioncenter.model.service.impl;

import com.dev.productioncenter.entity.*;
import com.dev.productioncenter.exception.DaoException;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.dao.*;
import com.dev.productioncenter.model.dao.impl.*;
import com.dev.productioncenter.model.service.CourseService;
import com.dev.productioncenter.model.service.LessonService;
import com.dev.productioncenter.validator.impl.CourseValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.dev.productioncenter.controller.command.RequestParameter.*;

public class CourseServiceImpl implements CourseService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final LessonService lessonService = new LessonServiceImpl();
    private static final CourseDao courseDao = CourseDaoImpl.getInstance();
    private static final AgeGroupDao ageGroupDao = AgeGroupDaoImpl.getInstance();
    private static final ActivityDao activityDao = ActivityDaoImpl.getInstance();
    private static final UserDao userDao = UserDaoImpl.getInstance();
    private static final String SPACE_DELIMITER_REGEX = " ";
    private static final String AGE_GROUP_DELIMITER_REGEX = "-";
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
                Optional<User> teacher = userDao.findTeacherByName(courseData.get(TEACHER).split(SPACE_DELIMITER_REGEX)[0],
                        courseData.get(TEACHER).split(SPACE_DELIMITER_REGEX)[1]);
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
                    return lessonService.addLessons(courseData, courseId);
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

    @Override
    public Optional<Course> findCourse(Map<String, String> chosenCourseData) throws ServiceException {
        try {
            Activity activity = new Activity();
            activity.setType(chosenCourseData.get(CHOSEN_TYPE));
            User teacher = new User();
            teacher.setSurname(chosenCourseData.get(CHOSEN_TEACHER).split(SPACE_DELIMITER_REGEX)[0]);
            teacher.setName(chosenCourseData.get(CHOSEN_TEACHER).split(SPACE_DELIMITER_REGEX)[1]);
            String chosenAgeGroup = chosenCourseData.get(CHOSEN_AGE_GROUP).split(SPACE_DELIMITER_REGEX)[0];
            AgeGroup ageGroup = new AgeGroup(Integer.parseInt(chosenAgeGroup.split(AGE_GROUP_DELIMITER_REGEX)[0]),
                    Integer.parseInt(chosenAgeGroup.split(AGE_GROUP_DELIMITER_REGEX)[1]));
            return courseDao.findChosenCourse(activity, teacher, ageGroup);
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding available courses: " + exception);
            throw new ServiceException("Error has occurred while finding available courses: " + exception);
        }
    }
}
