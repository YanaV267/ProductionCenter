package com.dev.productioncenter.model.dao.impl;

import com.dev.productioncenter.model.dao.EnrollmentDao;
import com.dev.productioncenter.entity.Course;
import com.dev.productioncenter.entity.Enrollment;
import com.dev.productioncenter.entity.EnrollmentStatus;
import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.model.connection.ConnectionPool;

import com.dev.productioncenter.exception.DaoException;
import com.dev.productioncenter.model.dao.mapper.impl.EnrollmentMapper;

import java.sql.*;
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
    private static final EnrollmentDaoImpl INSTANCE = new EnrollmentDaoImpl();

    private EnrollmentDaoImpl() {
    }

    public static EnrollmentDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public long add(Enrollment enrollment) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_ENROLLMENT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, enrollment.getUser().getId());
            preparedStatement.setLong(2, enrollment.getCourse().getId());
            preparedStatement.setInt(3, enrollment.getLessonAmount());
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getLong(1);
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while adding enrollment: " + exception);
            throw new DaoException("Error has occurred while adding enrollment: ", exception);
        }
    }

    @Override
    public boolean update(Enrollment enrollment) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_ENROLLMENT)) {
            preparedStatement.setLong(1, enrollment.getUser().getId());
            preparedStatement.setLong(2, enrollment.getCourse().getId());
            preparedStatement.setInt(3, enrollment.getLessonAmount());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(enrollment.getReservationDateTime()));
            preparedStatement.setString(5, enrollment.getEnrollmentStatus().getStatus());
            preparedStatement.setLong(6, enrollment.getId());
            preparedStatement.execute();
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while updating enrollment data: " + exception);
            throw new DaoException("Error has occurred while updating enrollment data: ", exception);
        }
    }

    @Override
    public boolean updateEnrollmentStatus(Enrollment enrollment) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_ENROLLMENT_STATUS)) {
            preparedStatement.setString(1, enrollment.getEnrollmentStatus().getStatus());
            preparedStatement.setLong(2, enrollment.getUser().getId());
            preparedStatement.execute();
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while updating enrollment status: " + exception);
            throw new DaoException("Error has occurred while updating enrollment status: ", exception);
        }
    }

    public boolean checkEnrollmentReservationStatus() throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(SQL_CHECK_ENROLLMENT_RESERVATION_STATUS);
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while checking enrollment reservation status: " + exception);
            throw new DaoException("Error has occurred while checking enrollment reservation status: ", exception);
        }
    }

    @Override
    public boolean delete(Enrollment enrollment) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_ENROLLMENT)) {
            preparedStatement.setLong(1, enrollment.getId());
            preparedStatement.execute();
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while deleting enrollment: " + exception);
            throw new DaoException("Error has occurred while deleting enrollment: ", exception);
        }
    }

    @Override
    public List<Enrollment> findAll() throws DaoException {
        List<Enrollment> enrollments;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_ENROLLMENTS)) {
            enrollments = EnrollmentMapper.getInstance().retrieve(resultSet);
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while adding enrollment: " + exception);
            throw new DaoException("Error has occurred while adding enrollment: ", exception);
        }
        return enrollments;
    }

    @Override
    public List<Enrollment> findEnrollmentsByUser(User user) throws DaoException {
        List<Enrollment> enrollments;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ENROLLMENTS_BY_USER)) {
            preparedStatement.setString(1, user.getSurname());
            preparedStatement.setString(2, user.getName());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                enrollments = EnrollmentMapper.getInstance().retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding enrollments: " + exception);
            throw new DaoException("Error has occurred while finding enrollments: ", exception);
        }
        return enrollments;
    }

    @Override
    public List<Enrollment> findEnrollmentsByCourse(Course course) throws DaoException {
        List<Enrollment> enrollments;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ENROLLMENTS_BY_COURSE)) {
            preparedStatement.setString(1, course.getActivity().getCategory());
            preparedStatement.setString(2, course.getActivity().getType());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                enrollments = EnrollmentMapper.getInstance().retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding enrollments by course: " + exception);
            throw new DaoException("Error has occurred while finding enrollments by course: ", exception);
        }
        return enrollments;
    }

    @Override
    public List<Enrollment> findEnrollmentsByLessonAmount(int minAmount, int maxAmount) throws DaoException {
        List<Enrollment> enrollments;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ENROLLMENTS_BY_LESSON_AMOUNT)) {
            preparedStatement.setLong(1, minAmount);
            preparedStatement.setLong(2, maxAmount);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                enrollments = EnrollmentMapper.getInstance().retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding enrollments by lesson amount: " + exception);
            throw new DaoException("Error has occurred while finding enrollments by lesson amount: ", exception);
        }
        return enrollments;
    }

    @Override
    public List<Enrollment> findEnrollmentsByReservationDatetime(Date dateTime) throws DaoException {
        List<Enrollment> enrollments;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ENROLLMENTS_BY_RESERVATION_DATETIME)) {
            preparedStatement.setDate(1, dateTime);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                enrollments = EnrollmentMapper.getInstance().retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding enrollments by reservation datetime: " + exception);
            throw new DaoException("Error has occurred while finding enrollments by reservation datetime: ", exception);
        }
        return enrollments;
    }

    @Override
    public List<Enrollment> findEnrollmentsByStatus(EnrollmentStatus status) throws DaoException {
        List<Enrollment> enrollments;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ENROLLMENTS_BY_STATUS)) {
            preparedStatement.setString(1, status.getStatus());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                enrollments = EnrollmentMapper.getInstance().retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding enrollments by status: " + exception);
            throw new DaoException("Error has occurred while finding enrollments by status: ", exception);
        }
        return enrollments;
    }
}
