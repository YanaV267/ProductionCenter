package com.dev.productioncenter.model.service.impl;

import com.dev.productioncenter.entity.*;
import com.dev.productioncenter.exception.DaoException;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.dao.*;
import com.dev.productioncenter.model.dao.impl.*;
import com.dev.productioncenter.model.service.CourseService;
import com.dev.productioncenter.model.service.LessonService;
import com.dev.productioncenter.validator.CourseValidator;
import com.dev.productioncenter.validator.impl.CourseValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.*;

import static com.dev.productioncenter.controller.command.RequestParameter.*;

public class CourseServiceImpl implements CourseService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String SPACE_DELIMITER_REGEX = " ";
    private static final String REMOVING_SYMBOLS_REGEX = "[\\[\\],]";
    private static final String REPLACEMENT_REGEX = "";
    private static final CourseService instance = new CourseServiceImpl();

    private CourseServiceImpl() {
    }

    public static CourseService getInstance() {
        return instance;
    }

    @Override
    public boolean addCourse(Map<String, String> courseData) throws ServiceException {
        CourseValidator validator = CourseValidatorImpl.getInstance();
        LessonService lessonService = LessonServiceImpl.getInstance();
        AgeGroupDao ageGroupDao = new AgeGroupDaoImpl(true);
        ActivityDao activityDao = new ActivityDaoImpl(true);
        UserDao userDao = new UserDaoImpl(true);
        CourseDao courseDao = new CourseDaoImpl(true);
        Transaction transaction = Transaction.getInstance();
        try {
            if (validator.checkCourse(courseData) && validator.checkActivity(courseData)) {
                transaction.begin(ageGroupDao, activityDao, userDao, courseDao);
                AgeGroup ageGroup = new AgeGroup(Integer.parseInt(courseData.get(MIN_AGE)),
                        Integer.parseInt(courseData.get(MAX_AGE)));
                Optional<Long> ageGroupId = ageGroupDao.findByMinMaxAge(ageGroup);
                if (ageGroupId.isPresent()) {
                    ageGroup.setId(ageGroupId.get());
                } else {
                    long newAgeGroupId = ageGroupDao.add(ageGroup);
                    ageGroup.setId(newAgeGroupId);
                }
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
                    if (lessonService.addLessons(courseData, courseId)) {
                        transaction.commit();
                        return true;
                    }
                }
            }
            transaction.rollback();
            return false;
        } catch (DaoException exception) {
            try {
                transaction.rollback();
            } catch (DaoException daoException) {
                LOGGER.error("Error has occurred while doing transaction rollback for adding course: " + daoException);
            }
            LOGGER.error("Error has occurred while adding course: " + exception);
            throw new ServiceException("Error has occurred while adding course: ", exception);
        } finally {
            try {
                transaction.end();
            } catch (DaoException exception) {
                LOGGER.error("Error has occurred while ending transaction for adding course: " + exception);
            }
        }
    }

    @Override
    public boolean updateCourse(Map<String, String> courseData) throws ServiceException {
        LessonService lessonService = LessonServiceImpl.getInstance();
        AgeGroupDao ageGroupDao = new AgeGroupDaoImpl(true);
        UserDao userDao = new UserDaoImpl(true);
        CourseDao courseDao = new CourseDaoImpl(true);
        Transaction transaction = Transaction.getInstance();
        try {
            if (CourseValidatorImpl.getInstance().checkCourse(courseData)) {
                transaction.begin(ageGroupDao, userDao, courseDao);
                AgeGroup ageGroup = new AgeGroup(Integer.parseInt(courseData.get(MIN_AGE)),
                        Integer.parseInt(courseData.get(MAX_AGE)));
                Optional<Long> ageGroupId = ageGroupDao.findByMinMaxAge(ageGroup);
                if (ageGroupId.isPresent()) {
                    ageGroup.setId(ageGroupId.get());
                } else {
                    long newAgeGroupId = ageGroupDao.add(ageGroup);
                    ageGroup.setId(newAgeGroupId);
                }
                Optional<User> teacher = userDao.findTeacherByName(courseData.get(TEACHER).split(SPACE_DELIMITER_REGEX)[0],
                        courseData.get(TEACHER).split(SPACE_DELIMITER_REGEX)[1]);
                if (teacher.isPresent()) {
                    Course course = new Course.CourseBuilder()
                            .setId(Long.parseLong(courseData.get(ID)))
                            .setTeacher(teacher.get())
                            .setStudentAmount(Integer.parseInt(courseData.get(STUDENT_AMOUNT)))
                            .setLessonPrice(BigDecimal.valueOf(Double.parseDouble(courseData.get(LESSON_PRICE))))
                            .setAgeGroup(ageGroup)
                            .setDescription(courseData.get(DESCRIPTION))
                            .setCourseStatus(CourseStatus.valueOf(courseData.get(STATUS).toUpperCase()))
                            .build();
                    if (courseDao.update(course)
                            && lessonService.updateLessons(courseData, course.getId())) {
                        transaction.commit();
                        return true;
                    }
                }
            }
            transaction.rollback();
            return false;
        } catch (DaoException exception) {
            try {
                transaction.rollback();
            } catch (DaoException daoException) {
                LOGGER.error("Error has occurred while doing transaction rollback for updating course: " + daoException);
            }
            LOGGER.error("Error has occurred while adding course: " + exception);
            throw new ServiceException("Error has occurred while updating course: ", exception);
        } finally {
            try {
                transaction.end();
            } catch (DaoException exception) {
                LOGGER.error("Error has occurred while ending transaction for updating course: " + exception);
            }
        }
    }

    @Override
    public List<Course> findCourses() throws ServiceException {
        CourseDao courseDao = new CourseDaoImpl(false);
        try {
            return courseDao.findAll();
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding all courses: " + exception);
            throw new ServiceException("Error has occurred while finding all courses: ", exception);
        } finally {
            courseDao.closeConnection();
        }
    }

    @Override
    public List<Course> findCourses(Activity activity, String[] weekdays) throws ServiceException {
        CourseDao courseDao = new CourseDaoImpl(false);
        try {
            if (activity.getCategory() != null) {
                if (activity.getType() != null) {
                    if (weekdays != null) {
                        Set<Course> courses = new HashSet<>();
                        for (String weekday : weekdays) {
                            courses.addAll(courseDao.findCourseByActivityWeekday(activity, weekday));
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
            throw new ServiceException("Error has occurred while finding courses: ", exception);
        } finally {
            courseDao.closeConnection();
        }
        return Collections.emptyList();
    }

    @Override
    public List<Course> findAvailableCourses() throws ServiceException {
        CourseDao courseDao = new CourseDaoImpl(false);
        try {
            return courseDao.findAvailableCourses();
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding available courses: " + exception);
            throw new ServiceException("Error has occurred while finding available courses: ", exception);
        } finally {
            courseDao.closeConnection();
        }
    }

    @Override
    public List<Course> findCoursesAvailableActivities() throws ServiceException {
        CourseDao courseDao = new CourseDaoImpl(false);
        try {
            return courseDao.findCoursesAvailableActivities();
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding available activities & appropriate teachers: " + exception);
            throw new ServiceException("Error has occurred while finding available activities & appropriate teachers: ", exception);
        } finally {
            courseDao.closeConnection();
        }
    }

    @Override
    public List<Course> findCoursesAllActivities() throws ServiceException {
        CourseDao courseDao = new CourseDaoImpl(false);
        try {
            return courseDao.findCoursesAllActivities();
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding all activities & appropriate teachers: " + exception);
            throw new ServiceException("Error has occurred while finding all activities & appropriate teachers: ", exception);
        } finally {
            courseDao.closeConnection();
        }
    }

    @Override
    public Optional<Course> findCourse(long id) throws ServiceException {
        LessonService lessonService = LessonServiceImpl.getInstance();
        CourseDao courseDao = new CourseDaoImpl(false);
        try {
            Optional<Course> course = courseDao.findById(id);
            if (course.isPresent()) {
                List<Lesson> lessons = lessonService.findLessons(course.get().getId());
                course.get().setLessons(lessons);
                return course;
            }
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding available courses: " + exception);
            throw new ServiceException("Error has occurred while finding available courses: ", exception);
        } finally {
            courseDao.closeConnection();
        }
        return Optional.empty();
    }

    @Override
    public boolean reservePlaceAtCourse(long id) throws ServiceException {
        CourseDao courseDao = new CourseDaoImpl(false);
        try {
            Optional<Course> course = courseDao.findById(id);
            if (course.isPresent() && course.get().getStudentAmount() != 0) {
                return courseDao.updateCourseStudentAmount(id, course.get().getStudentAmount() - 1);
            }
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while reserving place at course: " + exception);
            throw new ServiceException("Error has occurred while reserving place at course: ", exception);
        } finally {
            courseDao.closeConnection();
        }
        return false;
    }

    @Override
    public boolean releasePlaceAtCourse(long id) throws ServiceException {
        CourseDao courseDao = new CourseDaoImpl(false);
        try {
            Optional<Course> course = courseDao.findById(id);
            if (course.isPresent()) {
                return courseDao.updateCourseStudentAmount(id, course.get().getStudentAmount() + 1);
            }
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while releasing place at course: " + exception);
            throw new ServiceException("Error has occurred while releasing place at course: ", exception);
        } finally {
            courseDao.closeConnection();
        }
        return false;
    }
}
