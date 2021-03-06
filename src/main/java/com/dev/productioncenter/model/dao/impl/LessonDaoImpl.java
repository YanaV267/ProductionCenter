package com.dev.productioncenter.model.dao.impl;

import com.dev.productioncenter.entity.Lesson;
import com.dev.productioncenter.exception.DaoException;
import com.dev.productioncenter.model.connection.ConnectionPool;
import com.dev.productioncenter.model.dao.LessonDao;
import com.dev.productioncenter.model.dao.mapper.impl.LessonMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

/**
 * @project Production Center
 * @author YanaV
 * The type Lesson dao.
 */
public class LessonDaoImpl extends LessonDao {
    private static final String SQL_INSERT_LESSON =
            "INSERT INTO lessons(id_course, week_day, start_time, duration) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE_LESSON =
            "UPDATE lessons SET week_day = ?, start_time = ?, duration = ? WHERE id_lesson = ?";
    private static final String SQL_DELETE_LESSON = "DELETE FROM lessons WHERE id_lesson = ?";
    private static final String SQL_SELECT_ALL_LESSONS = "SELECT id_lesson, week_day, start_time, duration FROM lessons";
    private static final String SQL_SELECT_LESSONS_BY_COURSE =
            "SELECT id_lesson, week_day, start_time, duration FROM lessons WHERE id_course = ?";
    private static final String SQL_SELECT_LESSON_BY_COURSE_WEEK_DAY =
            "SELECT id_lesson, week_day, start_time, duration FROM lessons WHERE week_day = ? AND id_course = ?";

    /**
     * Instantiates a new Lesson dao.
     */
    public LessonDaoImpl() {
    }

    /**
     * Instantiates a new Lesson dao.
     *
     * @param isTransaction the is transaction
     */
    public LessonDaoImpl(boolean isTransaction) {
        if (!isTransaction) {
            connection = ConnectionPool.getInstance().getConnection();
        }
    }

    @Override
    public long add(Lesson lesson) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_LESSON,
                Statement.RETURN_GENERATED_KEYS)) {
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
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_LESSON)) {
            preparedStatement.setString(1, lesson.getWeekDay());
            preparedStatement.setString(2, lesson.getStartTime().toString());
            preparedStatement.setInt(3, lesson.getDuration());
            preparedStatement.setLong(4, lesson.getId());
            preparedStatement.execute();
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while updating lesson: " + exception);
            throw new DaoException("Error has occurred while updating lesson: ", exception);
        }
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_LESSON)) {
            preparedStatement.setLong(1, id);
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
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_LESSONS)) {
            LessonMapper lessonMapper = LessonMapper.getInstance();
            lessons = lessonMapper.retrieve(resultSet);
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding lessons: " + exception);
            throw new DaoException("Error has occurred while finding lessons: ", exception);
        }
        return lessons;
    }

    @Override
    public Optional<Lesson> findById(Long id) {
        throw new UnsupportedOperationException("Finding lesson by id is unsupported");
    }

    @Override
    public List<Lesson> findLessonsByCourse(long courseId) throws DaoException {
        List<Lesson> lessons;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_LESSONS_BY_COURSE)) {
            preparedStatement.setLong(1, courseId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                LessonMapper lessonMapper = LessonMapper.getInstance();
                lessons = lessonMapper.retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding lessons by course: " + exception);
            throw new DaoException("Error has occurred while finding lessons by course: ", exception);
        }
        return lessons;
    }

    @Override
    public Optional<Lesson> findLessonsByCourseWeekDay(String weekDay, long courseId) throws DaoException {
        List<Lesson> lessons;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_LESSON_BY_COURSE_WEEK_DAY)) {
            preparedStatement.setString(1, weekDay);
            preparedStatement.setLong(2, courseId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                LessonMapper lessonMapper = LessonMapper.getInstance();
                lessons = lessonMapper.retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding lessons by course & week day: " + exception);
            throw new DaoException("Error has occurred while finding lessons by course & week day: ", exception);
        }
        return lessons.isEmpty() ? Optional.empty() : Optional.of(lessons.get(0));
    }
}
