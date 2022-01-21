package com.dev.productioncenter.model.service.impl;

import com.dev.productioncenter.entity.*;
import com.dev.productioncenter.exception.DaoException;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.dao.ActivityDao;
import com.dev.productioncenter.model.dao.AgeGroupDao;
import com.dev.productioncenter.model.dao.CourseDao;
import com.dev.productioncenter.model.dao.UserDao;
import com.dev.productioncenter.model.dao.impl.ActivityDaoImpl;
import com.dev.productioncenter.model.dao.impl.AgeGroupDaoImpl;
import com.dev.productioncenter.model.dao.impl.CourseDaoImpl;
import com.dev.productioncenter.model.dao.impl.UserDaoImpl;
import com.dev.productioncenter.model.service.CourseService;
import com.dev.productioncenter.model.service.LessonService;
import com.dev.productioncenter.validator.impl.CourseValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.*;

import static com.dev.productioncenter.controller.command.RequestParameter.*;

public class CourseServiceImpl implements CourseService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final CourseDao courseDao = CourseDaoImpl.getInstance();
    private static final AgeGroupDao ageGroupDao = AgeGroupDaoImpl.getInstance();
    private static final ActivityDao activityDao = ActivityDaoImpl.getInstance();
    private static final UserDao userDao = UserDaoImpl.getInstance();
    private static final String SPACE_DELIMITER_REGEX = " ";
    private static final String REMOVING_SYMBOLS_REGEX = "[\\[\\]]";
    private static final String REPLACEMENT_REGEX = "";
    private final LessonService lessonService = new LessonServiceImpl();

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
    public List<Course> findCourses(Activity activity, String[] weekdays) throws ServiceException {
        try {
            if (activity.getCategory() != null) {
                if (activity.getType() != null) {
                    if (weekdays != null) {
                        Set<Course> courses = new HashSet<>();
                        for (String weekday : weekdays) {
                            courses.addAll(courseDao.findCourseByWeekday(weekday));
                        }
                        return List.copyOf(courses);
                    } else {
                        return courseDao.findCourseByActivity(activity);
                    }
                } else {
                    return courseDao.findCourseByActivityCategory(activity);
                }
            } else {
                if (activity.getType() != null) {
                    return courseDao.findCourseByActivityType(activity);
                } else {
                    if (weekdays != null) {
                        Set<Course> courses = new HashSet<>();
                        for (String weekday : weekdays) {
                            courses.addAll(courseDao.findCourseByWeekday(weekday));
                        }
                        return List.copyOf(courses);
                    }
                }
            }
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding courses: " + exception);
            throw new ServiceException("Error has occurred while finding courses: " + exception);
        }
        return new ArrayList<>();
    }

    @Override
    public List<Course> findAvailableCourses() throws ServiceException {
        try {
            return courseDao.findAvailableCourses();
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding available courses: " + exception);
            throw new ServiceException("Error has occurred while finding available courses: " + exception);
        }
    }

    @Override
    public Optional<Course> findCourse(long id) throws ServiceException {
        try {
            return courseDao.findCourseById(id);
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding available courses: " + exception);
            throw new ServiceException("Error has occurred while finding available courses: " + exception);
        }
    }
}
