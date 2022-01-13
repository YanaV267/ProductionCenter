package com.dev.productioncenter.model.dao.impl;

import com.dev.productioncenter.model.dao.LessonDao;
import com.dev.productioncenter.model.connection.ConnectionPool;
import com.dev.productioncenter.entity.Lesson;
import com.dev.productioncenter.exception.DaoException;
import com.dev.productioncenter.model.dao.mapper.impl.LessonMapper;

import java.sql.*;
import java.util.List;

public class LessonDaoImpl implements LessonDao {
    private static final String SQL_INSERT_LESSON =
            "INSERT INTO lessons(id_course, week_day, start_time, duration) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE_LESSON =
            "UPDATE lessons SET week_day = ?, start_time = ?, duration = ? WHERE id_lesson = ?";
    private static final String SQL_DELETE_LESSON = "DELETE FROM lessons WHERE id_lesson = ?";
    private static final String SQL_SELECT_ALL_LESSONS = "SELECT id_course, week_day, start_time, duration FROM lessons";
    private static final String SQL_SELECT_LESSON_BY_COURSE =
            "SELECT id_course, week_day, start_time, duration FROM lessons WHERE id_course = ?";
    private static final String SQL_SELECT_LESSON_BY_WEEK_DAY =
            "SELECT id_course, week_day, start_time, duration FROM lessons WHERE week_day = ?";
    private static final String SQL_SELECT_LESSON_BY_START_TIME =
            "SELECT id_course, week_day, start_time, duration FROM lessons WHERE start_time = ?";
    private static final String SQL_SELECT_LESSON_BY_DURATION =
            "SELECT id_course, week_day, start_time, duration FROM lessons WHERE duration >= ? AND duration <= ?";
    private static final LessonDaoImpl INSTANCE = new LessonDaoImpl();

    private LessonDaoImpl() {
    }

    public static LessonDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public long add(Lesson lesson) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_LESSON, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, lesson.getCourse().getId());
            preparedStatement.setString(2, lesson.getWeekDay());
            preparedStatement.setString(3, lesson.getStartTime().toString());
            preparedStatement.setInt(4, lesson.getDuration());
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getLong(1);
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while adding lesson: " + exception);
            throw new DaoException("Error has occurred while adding lesson: ", exception);
        }
    }

    @Override
    public boolean update(Lesson lesson) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_LESSON)) {
            preparedStatement.setString(1, lesson.getWeekDay());
            preparedStatement.setString(2, lesson.getStartTime().toString());
            preparedStatement.setInt(3, lesson.getDuration());
            preparedStatement.setLong(4, lesson.getCourse().getId());
            preparedStatement.execute();
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while updating lesson: " + exception);
            throw new DaoException("Error has occurred while updating lesson: ", exception);
        }
    }

    @Override
    public boolean delete(Lesson lesson) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_LESSON)) {
            preparedStatement.setLong(1, lesson.getId());
            preparedStatement.execute();
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while deleting lesson: " + exception);
            throw new DaoException("Error has occurred while deleting lesson: ", exception);
        }
    }

    @Override
    public List<Lesson> findAll() throws DaoException {
        List<Lesson> lessons;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_LESSONS)) {
            lessons = LessonMapper.getInstance().retrieve(resultSet);
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding lessons: " + exception);
            throw new DaoException("Error has occurred while finding lessons: ", exception);
        }
        return lessons;
    }

    @Override
    public List<Lesson> findLessonsByCourse(long courseId) throws DaoException {
        List<Lesson> lessons;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_LESSON_BY_COURSE)) {
            preparedStatement.setLong(1, courseId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                lessons = LessonMapper.getInstance().retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding lessons by course: " + exception);
            throw new DaoException("Error has occurred while finding lessons by course: ", exception);
        }
        return lessons;
    }

    @Override
    public List<Lesson> findLessonsByWeekDay(String weekDay) throws DaoException {
        List<Lesson> lessons;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_LESSON_BY_WEEK_DAY)) {
            preparedStatement.setString(1, weekDay);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                lessons = LessonMapper.getInstance().retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding lessons by week day: " + exception);
            throw new DaoException("Error has occurred while finding lessons by week day: ", exception);
        }
        return lessons;
    }

    @Override
    public List<Lesson> findLessonsByStartTime(Time startTime) throws DaoException {
        List<Lesson> lessons;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_LESSON_BY_START_TIME)) {
            preparedStatement.setTime(1, startTime);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                lessons = LessonMapper.getInstance().retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding lessons by start time: " + exception);
            throw new DaoException("Error has occurred while finding lessons by start time: ", exception);
        }
        return lessons;
    }

    @Override
    public List<Lesson> findLessonsByDuration(int minDuration, int maxDuration) throws DaoException {
        List<Lesson> lessons;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_LESSON_BY_DURATION)) {
            preparedStatement.setInt(1, minDuration);
            preparedStatement.setInt(2, maxDuration);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                lessons = LessonMapper.getInstance().retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding lessons by duration: " + exception);
            throw new DaoException("Error has occurred while finding lessons by duration: ", exception);
        }
        return lessons;
    }
}
