package com.dev.productioncenter.model.service.impl;

import com.dev.productioncenter.entity.Course;
import com.dev.productioncenter.entity.Lesson;
import com.dev.productioncenter.exception.DaoException;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.dao.LessonDao;
import com.dev.productioncenter.model.dao.impl.LessonDaoImpl;
import com.dev.productioncenter.model.service.LessonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static com.dev.productioncenter.controller.command.RequestParameter.*;

public class LessonServiceImpl implements LessonService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String DELIMITER_REGEX = " ";
    private static final String REMOVING_SYMBOLS_REGEX = "[\\[\\],]";
    private static final String REPLACEMENT_REGEX = "";
    private final LessonDao lessonDao = LessonDaoImpl.getInstance();

    @Override
    public boolean addLessons(Map<String, String> lessonData, long courseId) throws ServiceException {
        String[] weekdays = lessonData.get(WEEKDAYS)
                .replaceAll(REMOVING_SYMBOLS_REGEX, REPLACEMENT_REGEX)
                .split(DELIMITER_REGEX);
        try {
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
            LOGGER.error("Error has occurred while adding course's lessons: " + exception);
            throw new ServiceException("Error has occurred while adding course's lessons: " + exception);
        }
    }

    @Override
    public boolean updateLessons(Map<String, String> lessonData, long courseId) throws ServiceException {
        String[] weekdays = lessonData.get(WEEKDAYS)
                .replaceAll(REMOVING_SYMBOLS_REGEX, REPLACEMENT_REGEX)
                .split(DELIMITER_REGEX);
        String[] durations = lessonData.get(DURATION)
                .replaceAll(REMOVING_SYMBOLS_REGEX, REPLACEMENT_REGEX).split(DELIMITER_REGEX);
        String[] times = lessonData.get(TIME)
                .replaceAll(REMOVING_SYMBOLS_REGEX, REPLACEMENT_REGEX).split(DELIMITER_REGEX);
        try {
            for (int i = 0; i < weekdays.length; i++) {
                Lesson lesson = new Lesson();
                lesson.setCourse(new Course(courseId));
                lesson.setWeekDay(weekdays[i]);
                lesson.setStartTime(LocalTime.parse(times[i]));
                lesson.setDuration(Integer.parseInt(durations[i]));
                lessonDao.update(lesson);
            }
            return true;
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while updating course's lessons: " + exception);
            throw new ServiceException("Error has occurred while updating course's lessons: " + exception);
        }
    }

    @Override
    public List<Lesson> findLessons(long courseId) throws ServiceException {
        try {
            return lessonDao.findLessonsByCourse(courseId);
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding user's lessons: " + exception);
            throw new ServiceException("Error has occurred while finding user's lessons: " + exception);
        }
    }
}
