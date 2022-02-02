package com.dev.productioncenter.model.service.impl;

import com.dev.productioncenter.entity.Course;
import com.dev.productioncenter.entity.Lesson;
import com.dev.productioncenter.exception.DaoException;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.dao.DaoProvider;
import com.dev.productioncenter.model.dao.LessonDao;
import com.dev.productioncenter.model.dao.Transaction;
import com.dev.productioncenter.model.service.LessonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.dev.productioncenter.controller.command.RequestParameter.*;

public class LessonServiceImpl implements LessonService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String DELIMITER_REGEX = " ";
    private static final String REMOVING_SYMBOLS_REGEX = "[\\[\\],]";
    private static final String REPLACEMENT_REGEX = "";
    private static final LessonService instance = new LessonServiceImpl();

    private LessonServiceImpl() {
    }

    public static LessonService getInstance() {
        return instance;
    }

    @Override
    public boolean addLessons(Map<String, String> lessonData, long courseId) throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        LessonDao lessonDao = daoProvider.getLessonDao(true);
        String[] weekdays = lessonData.get(WEEKDAYS)
                .replaceAll(REMOVING_SYMBOLS_REGEX, REPLACEMENT_REGEX)
                .split(DELIMITER_REGEX);
        Transaction transaction = Transaction.getInstance();
        try {
            transaction.begin(lessonDao);
            for (String weekday : weekdays) {
                Lesson lesson = new Lesson();
                lesson.setCourse(new Course(courseId));
                lesson.setWeekDay(weekday);
                lesson.setStartTime(LocalTime.parse(lessonData.get(TIME)));
                lesson.setDuration(Integer.parseInt(lessonData.get(DURATION)));
                lessonDao.add(lesson);
            }
            return true;
        } catch (DaoException exception) {
            try {
                transaction.rollback();
            } catch (DaoException daoException) {
                LOGGER.error("Error has occurred while doing transaction rollback for adding course's lessons: " + daoException);
            }
            LOGGER.error("Error has occurred while adding course's lessons: " + exception);
            throw new ServiceException("Error has occurred while adding course's lessons: ", exception);
        }
    }

    @Override
    public boolean updateLessons(Map<String, String> lessonData, long courseId) throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        LessonDao lessonDao = daoProvider.getLessonDao(true);
        String[] weekdays = lessonData.get(WEEKDAYS)
                .replaceAll(REMOVING_SYMBOLS_REGEX, REPLACEMENT_REGEX)
                .trim()
                .toLowerCase()
                .split(DELIMITER_REGEX);
        String[] durations = lessonData.get(DURATION)
                .replaceAll(REMOVING_SYMBOLS_REGEX, REPLACEMENT_REGEX)
                .trim()
                .split(DELIMITER_REGEX);
        String[] times = lessonData.get(TIME)
                .replaceAll(REMOVING_SYMBOLS_REGEX, REPLACEMENT_REGEX)
                .trim()
                .split(DELIMITER_REGEX);
        Transaction transaction = Transaction.getInstance();
        try {
            transaction.begin(lessonDao);
            for (int i = 0; i < weekdays.length; i++) {
                Lesson lesson = new Lesson();
                lesson.setCourse(new Course(courseId));
                lesson.setWeekDay(weekdays[i]);
                lesson.setStartTime(LocalTime.parse(times[i]));
                lesson.setDuration(Integer.parseInt(durations[i]));
                Optional<Lesson> foundLesson = lessonDao.findLessonsByCourseWeekDay(lesson.getWeekDay(), courseId);
                if (foundLesson.isPresent()) {
                    lesson.setId(foundLesson.get().getId());
                    lessonDao.update(lesson);
                } else {
                    lessonDao.add(lesson);
                }
            }
            List<Lesson> lessons = lessonDao.findLessonsByCourse(courseId);
            for (Lesson lesson : lessons) {
                Optional<String> weekday = Arrays.stream(weekdays)
                        .filter(d -> d.equals(lesson.getWeekDay()))
                        .findAny();
                if (weekday.isEmpty()) {
                    lessonDao.delete(lesson.getId());
                }
            }
            return true;
        } catch (DaoException exception) {
            try {
                transaction.rollback();
            } catch (DaoException daoException) {
                LOGGER.error("Error has occurred while doing transaction rollback for updating course's lessons: " + daoException);
            }
            LOGGER.error("Error has occurred while updating course's lessons: " + exception);
            throw new ServiceException("Error has occurred while updating course's lessons: ", exception);
        }
    }

    @Override
    public List<Lesson> findLessons(long courseId) throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        LessonDao lessonDao = daoProvider.getLessonDao(false);
        try {
            return lessonDao.findLessonsByCourse(courseId);
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding user's lessons: " + exception);
            throw new ServiceException("Error has occurred while finding user's lessons: ", exception);
        } finally {
            lessonDao.closeConnection();
        }
    }
}
