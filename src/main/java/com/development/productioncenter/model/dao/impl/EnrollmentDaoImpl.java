package com.development.productioncenter.model.dao.impl;

import com.development.productioncenter.entity.Course;
import com.development.productioncenter.entity.Enrollment;
import com.development.productioncenter.entity.EnrollmentStatus;
import com.development.productioncenter.entity.User;
import com.development.productioncenter.model.connection.ConnectionPool;
import com.development.productioncenter.model.dao.ColumnName;
import com.development.productioncenter.model.dao.EnrollmentDao;

import com.development.productioncenter.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDaoImpl implements EnrollmentDao {
    private static final String SQL_INSERT_ENROLLMENT =
            "INSERT INTO enrollments(id_user, id_course, lesson_amount) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE_ENROLLMENT =
            "UPDATE enrollments SET id_user = ?, id_course = ?, lesson_amount = ?, status = ? WHERE id_enrollment = ?";
    private static final String SQL_CHECK_ENROLLMENT_RESERVATION_STATUS =
            "UPDATE enrollments SET status = 'expired' WHERE datediff(reservation_datetime, now()) < -3";
    private static final String SQL_UPDATE_ENROLLMENT_STATUS = "UPDATE enrollments SET status = ? WHERE id_enrollment = ?";
    private static final String SQL_DELETE_ENROLLMENT = "DELETE FROM enrollments WHERE id_enrollment = ?";
    private static final String SQL_SELECT_ALL_ENROLLMENTS =
            "SELECT id_enrollment, surname, name, category, type, lesson_amount, reservation_datetime, enrollments.status FROM enrollments " +
                    "JOIN users ON enrollments.id_user = users.id_user " +
                    "JOIN courses ON enrollments.id_course = courses.id_course " +
                    "JOIN activities ON activities.id_activity = courses.id_activity";
    private static final String SQL_SELECT_ENROLLMENTS_BY_USER =
            "SELECT id_enrollment, surname, name, category, type, lesson_amount, reservation_datetime, enrollments.status FROM enrollments " +
                    "JOIN users ON enrollments.id_user = users.id_user " +
                    "JOIN courses ON enrollments.id_course = courses.id_course " +
                    "JOIN activities ON activities.id_activity = courses.id_activity " +
                    "WHERE surname = ? AND name = ?";
    private static final String SQL_SELECT_ENROLLMENTS_BY_COURSE =
            "SELECT id_enrollment, surname, name, category, type, lesson_amount, reservation_datetime, enrollments.status FROM enrollments " +
                    "JOIN users ON enrollments.id_user = users.id_user " +
                    "JOIN courses ON enrollments.id_course = courses.id_course " +
                    "JOIN activities ON activities.id_activity = courses.id_activity " +
                    "WHERE category = ? AND type = ?";
    private static final String SQL_SELECT_ENROLLMENTS_BY_LESSON_AMOUNT =
            "SELECT id_enrollment, surname, name, category, type, lesson_amount, reservation_datetime, enrollments.status FROM enrollments " +
                    "JOIN users ON enrollments.id_user = users.id_user " +
                    "JOIN courses ON enrollments.id_course = courses.id_course " +
                    "JOIN activities ON activities.id_activity = courses.id_activity " +
                    "WHERE lesson_amount >= ? && lesson_amount <= ?";
    private static final String SQL_SELECT_ENROLLMENTS_BY_RESERVATION_DATETIME =
            "SELECT id_enrollment, surname, name, category, type, lesson_amount, reservation_datetime, enrollments.status FROM enrollments " +
                    "JOIN users ON enrollments.id_user = users.id_user " +
                    "JOIN courses ON enrollments.id_course = courses.id_course " +
                    "JOIN activities ON activities.id_activity = courses.id_activity " +
                    "WHERE reservation_datetime = ?";
    private static final String SQL_SELECT_ENROLLMENTS_BY_STATUS =
            "SELECT id_enrollment, surname, name, category, type, lesson_amount, reservation_datetime, enrollments.status FROM enrollments " +
                    "JOIN users ON enrollments.id_user = users.id_user " +
                    "JOIN courses ON enrollments.id_course = courses.id_course " +
                    "JOIN activities ON activities.id_activity = courses.id_activity " +
                    "WHERE enrollments.status = ?";

    @Override
    public boolean add(Enrollment enrollment) throws DaoException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_ENROLLMENT)) {
            preparedStatement.setLong(1, enrollment.getUser().getId());
            preparedStatement.setLong(2, enrollment.getCourse().getId());
            preparedStatement.setInt(3, enrollment.getLessonAmount());
            preparedStatement.execute();
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while adding enrollment: " + exception);
            throw new DaoException("Error has occurred while adding enrollment: ", exception);
        } finally {
            close(connection);
        }
    }

    @Override
    public boolean update(Enrollment enrollment) throws DaoException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_ENROLLMENT)) {
            preparedStatement.setLong(1, enrollment.getUser().getId());
            preparedStatement.setLong(2, enrollment.getCourse().getId());
            preparedStatement.setInt(3, enrollment.getLessonAmount());
            preparedStatement.setDate(4, enrollment.getReservationDateTime());
            preparedStatement.setString(5, enrollment.getEnrollmentStatus().getStatus());
            preparedStatement.setLong(6, enrollment.getId());
            preparedStatement.execute();
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while updating enrollment data: " + exception);
            throw new DaoException("Error has occurred while updating enrollment data: ", exception);
        } finally {
            close(connection);
        }
    }

    @Override
    public boolean updateEnrollmentStatus(Enrollment enrollment) throws DaoException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_ENROLLMENT_STATUS)) {
            preparedStatement.setString(1, enrollment.getEnrollmentStatus().getStatus());
            preparedStatement.setLong(2, enrollment.getUser().getId());
            preparedStatement.execute();
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while updating enrollment status: " + exception);
            throw new DaoException("Error has occurred while updating enrollment status: ", exception);
        } finally {
            close(connection);
        }
    }

    public boolean checkEnrollmentReservationStatus() throws DaoException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHECK_ENROLLMENT_RESERVATION_STATUS)) {
            preparedStatement.execute();
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while checking enrollment reservation status: " + exception);
            throw new DaoException("Error has occurred while checking enrollment reservation status: ", exception);
        } finally {
            close(connection);
        }
    }

    @Override
    public boolean delete(Enrollment enrollment) throws DaoException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_ENROLLMENT)) {
            preparedStatement.setLong(1, enrollment.getId());
            preparedStatement.execute();
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while deleting enrollment: " + exception);
            throw new DaoException("Error has occurred while deleting enrollment: ", exception);
        } finally {
            close(connection);
        }
    }

    @Override
    public List<Enrollment> findAll() throws DaoException {
        List<Enrollment> enrollments;
        Connection connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_ENROLLMENTS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            enrollments = retrieveLessons(resultSet);
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while adding enrollment: " + exception);
            throw new DaoException("Error has occurred while adding enrollment: ", exception);
        } finally {
            close(connection);
        }
        return enrollments;
    }

    @Override
    public List<Enrollment> findEnrollmentsByUser(User user) throws DaoException {
        List<Enrollment> enrollments;
        Connection connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ENROLLMENTS_BY_USER)) {
            preparedStatement.setString(1, user.getSurname());
            preparedStatement.setString(2, user.getName());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                enrollments = retrieveLessons(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding enrollments: " + exception);
            throw new DaoException("Error has occurred while finding enrollments: ", exception);
        } finally {
            close(connection);
        }
        return enrollments;
    }

    @Override
    public List<Enrollment> findEnrollmentsByCourse(Course course) throws DaoException {
        List<Enrollment> enrollments;
        Connection connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ENROLLMENTS_BY_COURSE)) {
            preparedStatement.setString(1, course.getActivity().getCategory());
            preparedStatement.setString(2, course.getActivity().getType());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                enrollments = retrieveLessons(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding enrollments by course: " + exception);
            throw new DaoException("Error has occurred while finding enrollments by course: ", exception);
        } finally {
            close(connection);
        }
        return enrollments;
    }

    @Override
    public List<Enrollment> findEnrollmentsByLessonAmount(int minAmount, int maxAmount) throws DaoException {
        List<Enrollment> enrollments;
        Connection connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ENROLLMENTS_BY_LESSON_AMOUNT)) {
            preparedStatement.setLong(1, minAmount);
            preparedStatement.setLong(2, maxAmount);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                enrollments = retrieveLessons(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding enrollments by lesson amount: " + exception);
            throw new DaoException("Error has occurred while finding enrollments by lesson amount: ", exception);
        } finally {
            close(connection);
        }
        return enrollments;
    }

    @Override
    public List<Enrollment> findEnrollmentsByReservationDatetime(Date dateTime) throws DaoException {
        List<Enrollment> enrollments;
        Connection connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ENROLLMENTS_BY_RESERVATION_DATETIME)) {
            preparedStatement.setDate(1, dateTime);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                enrollments = retrieveLessons(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding enrollments by reservation datetime: " + exception);
            throw new DaoException("Error has occurred while finding enrollments by reservation datetime: ", exception);
        } finally {
            close(connection);
        }
        return enrollments;
    }

    @Override
    public List<Enrollment> findEnrollmentsByStatus(EnrollmentStatus status) throws DaoException {
        List<Enrollment> enrollments;
        Connection connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ENROLLMENTS_BY_STATUS)) {
            preparedStatement.setString(1, status.getStatus());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                enrollments = retrieveLessons(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding enrollments by status: " + exception);
            throw new DaoException("Error has occurred while finding enrollments by status: ", exception);
        } finally {
            close(connection);
        }
        return enrollments;
    }

    public List<Enrollment> retrieveLessons(ResultSet resultSet) throws DaoException {
        List<Enrollment> enrollments = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Enrollment enrollment = new Enrollment();
                enrollment.setId(resultSet.getLong(ColumnName.ENROLLMENT_ID));
                enrollment.getUser().setSurname(resultSet.getString(ColumnName.USER_SURNAME));
                enrollment.getUser().setName(resultSet.getString(ColumnName.USER_NAME));
                enrollment.getCourse().getActivity().setCategory(resultSet.getString(ColumnName.ACTIVITY_CATEGORY));
                enrollment.getCourse().getActivity().setCategory(resultSet.getString(ColumnName.ACTIVITY_TYPE));
                enrollment.setLessonAmount(resultSet.getInt(ColumnName.ENROLLMENT_LESSON_AMOUNT));
                enrollment.setReservationDateTime(resultSet.getDate(ColumnName.ENROLLMENT_RESERVATION_DATETIME));
                enrollment.setEnrollmentStatus(EnrollmentStatus.valueOf(resultSet.getString(ColumnName.ENROLLMENT_STATUS).toUpperCase()));
                enrollments.add(enrollment);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while retrieving enrollments' data from database: " + exception);
            throw new DaoException("Error has occurred while retrieving enrollments' data from database: ", exception);
        }
        return enrollments;
    }
}
