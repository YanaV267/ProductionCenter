package com.dev.productioncenter.model.dao.impl;

import com.dev.productioncenter.entity.*;
import com.dev.productioncenter.exception.DaoException;
import com.dev.productioncenter.model.connection.ConnectionPool;
import com.dev.productioncenter.model.dao.CourseDao;
import com.dev.productioncenter.model.dao.mapper.impl.CourseMapper;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;

public class CourseDaoImpl implements CourseDao {
    private static final String SQL_INSERT_COURSE =
            "INSERT INTO courses(description, id_teacher, id_activity, id_age_group, lesson_price, student_amount) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_COURSE =
            "UPDATE courses SET description = ?, id_teacher = ?, id_activity = ?, id_age_group = ?, lesson_price = ?, student_amount = ?, status = ? WHERE id_course = ?";
    private static final String SQL_DELETE_COURSE = "DELETE FROM courses WHERE id_course = ?";
    private static final String SQL_SELECT_ALL_COURSES =
            "SELECT id_course, description, surname, name, category, type, min_age, max_age, lesson_price, student_amount, courses.status FROM courses " +
                    "JOIN users ON courses.id_teacher = users.id_user " +
                    "JOIN activities ON courses.id_activity = activities.id_activity " +
                    "JOIN age_group ON courses.id_age_group = age_group.id_age_group";
    private static final String SQL_SELECT_COURSES_BY_TEACHER =
            "SELECT id_course, description, surname, name, category, type, min_age, max_age, lesson_price, student_amount, courses.status FROM courses " +
                    "JOIN users ON courses.id_teacher = users.id_user " +
                    "JOIN activities ON courses.id_activity = activities.id_activity " +
                    "JOIN age_group ON courses.id_age_group = age_group.id_age_group " +
                    "WHERE id_teacher = ?";
    private static final String SQL_SELECT_COURSES_BY_ACTIVITY_CATEGORY =
            "SELECT id_course, description, surname, name, category, type, min_age, max_age, lesson_price, student_amount, courses.status FROM courses " +
                    "JOIN users ON courses.id_teacher = users.id_user " +
                    "JOIN activities ON courses.id_activity = activities.id_activity " +
                    "JOIN age_group ON courses.id_age_group = age_group.id_age_group " +
                    "WHERE category = ?";
    private static final String SQL_SELECT_COURSES_BY_ACTIVITY_TYPE =
            "SELECT id_course, description, surname, name, category, type, min_age, max_age, lesson_price, student_amount, courses.status FROM courses " +
                    "JOIN users ON courses.id_teacher = users.id_user " +
                    "JOIN activities ON courses.id_activity = activities.id_activity " +
                    "JOIN age_group ON courses.id_age_group = age_group.id_age_group " +
                    "WHERE type = ?";
    private static final String SQL_SELECT_COURSES_BY_AGE_GROUP =
            "SELECT id_course, description, surname, name, category, type, min_age, max_age, lesson_price, student_amount, courses.status FROM courses " +
                    "JOIN users ON courses.id_teacher = users.id_user " +
                    "JOIN activities ON courses.id_activity = activities.id_activity " +
                    "JOIN age_group ON courses.id_age_group = age_group.id_age_group " +
                    "WHERE min_age <= ? AND max_age >= ?";
    private static final String SQL_SELECT_COURSES_BY_LESSON_PRICE =
            "SELECT id_course, description, surname, name, category, type, min_age, max_age, lesson_price, student_amount, courses.status FROM courses " +
                    "JOIN users ON courses.id_teacher = users.id_user " +
                    "JOIN activities ON courses.id_activity = activities.id_activity " +
                    "JOIN age_group ON courses.id_age_group = age_group.id_age_group " +
                    "WHERE lesson_price = ?";
    private static final String SQL_SELECT_COURSES_BY_STUDENT_AMOUNT =
            "SELECT id_course, description, surname, name, category, type, min_age, max_age, lesson_price, student_amount, courses.status FROM courses " +
                    "JOIN users ON courses.id_teacher = users.id_user " +
                    "JOIN activities ON courses.id_activity = activities.id_activity " +
                    "JOIN age_group ON courses.id_age_group = age_group.id_age_group " +
                    "WHERE student_amount = ?";
    private static final String SQL_SELECT_COURSES_BY_STATUS =
            "SELECT id_course, description, surname, name, category, type, min_age, max_age, lesson_price, student_amount, courses.status FROM courses " +
                    "JOIN users ON courses.id_teacher = users.id_user " +
                    "JOIN activities ON courses.id_activity = activities.id_activity " +
                    "JOIN age_group ON courses.id_age_group = age_group.id_age_group " +
                    "WHERE courses.status = ?";
    private static final CourseDaoImpl INSTANCE = new CourseDaoImpl();

    private CourseDaoImpl() {
    }

    public static CourseDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean add(Course course) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_COURSE)) {
            preparedStatement.setString(1, course.getDescription());
            preparedStatement.setLong(2, course.getTeacher().getId());
            preparedStatement.setLong(3, course.getActivity().getId());
            preparedStatement.setLong(4, course.getAgeGroup().getId());
            preparedStatement.setBigDecimal(5, course.getLessonPrice());
            preparedStatement.setLong(6, course.getStudentAmount());
            preparedStatement.execute();
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while adding course: " + exception);
            throw new DaoException("Error has occurred while adding course: ", exception);
        }
    }

    @Override
    public boolean update(Course course) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_COURSE)) {
            preparedStatement.setString(1, course.getDescription());
            preparedStatement.setLong(2, course.getTeacher().getId());
            preparedStatement.setLong(3, course.getActivity().getId());
            preparedStatement.setLong(4, course.getAgeGroup().getId());
            preparedStatement.setBigDecimal(5, course.getLessonPrice());
            preparedStatement.setLong(6, course.getStudentAmount());
            preparedStatement.setString(7, course.getCourseStatus().getStatus());
            preparedStatement.setLong(8, course.getId());
            preparedStatement.execute();
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while updating course data: " + exception);
            throw new DaoException("Error has occurred while updating course data: ", exception);
        }
    }

    @Override
    public boolean delete(Course course) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_COURSE)) {
            preparedStatement.setLong(1, course.getId());
            preparedStatement.execute();
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while deleting course: " + exception);
            throw new DaoException("Error has occurred while deleting course: ", exception);
        }
    }

    @Override
    public List<Course> findAll() throws DaoException {
        List<Course> courses;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_COURSES)) {
            courses = CourseMapper.getInstance().retrieve(resultSet);
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding courses: " + exception);
            throw new DaoException("Error has occurred while finding courses: ", exception);
        }
        return courses;
    }

    @Override
    public List<Course> findCourseByTeacher(User teacher) throws DaoException {
        List<Course> courses;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_COURSES_BY_TEACHER)) {
            preparedStatement.setLong(1, teacher.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            courses = CourseMapper.getInstance().retrieve(resultSet);
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding courses by teacher: " + exception);
            throw new DaoException("Error has occurred while finding courses by teacher: ", exception);
        }
        return courses;
    }

    @Override
    public List<Course> findCourseByActivityCategory(Activity activity) throws DaoException {
        List<Course> courses;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_COURSES_BY_ACTIVITY_CATEGORY)) {
            preparedStatement.setString(1, activity.getCategory());
            ResultSet resultSet = preparedStatement.executeQuery();
            courses = CourseMapper.getInstance().retrieve(resultSet);
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding courses by activity category: " + exception);
            throw new DaoException("Error has occurred while finding courses by activity category: ", exception);
        }
        return courses;
    }

    @Override
    public List<Course> findCourseByActivityType(Activity activity) throws DaoException {
        List<Course> courses;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_COURSES_BY_ACTIVITY_TYPE)) {
            preparedStatement.setString(1, activity.getType());
            ResultSet resultSet = preparedStatement.executeQuery();
            courses = CourseMapper.getInstance().retrieve(resultSet);
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding courses by activity type: " + exception);
            throw new DaoException("Error has occurred while finding courses by activity type: ", exception);
        }
        return courses;
    }

    @Override
    public List<Course> findCourseByAgeGroup(AgeGroup ageGroup) throws DaoException {
        List<Course> courses;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_COURSES_BY_AGE_GROUP)) {
            preparedStatement.setInt(1, ageGroup.getMinAge());
            preparedStatement.setInt(2, ageGroup.getMaxAge());
            ResultSet resultSet = preparedStatement.executeQuery();
            courses = CourseMapper.getInstance().retrieve(resultSet);
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding courses by age group: " + exception);
            throw new DaoException("Error has occurred while finding courses by age group: ", exception);
        }
        return courses;
    }

    @Override
    public List<Course> findCourseByLessonPrice(BigDecimal lessonPrice) throws DaoException {
        List<Course> courses;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_COURSES_BY_LESSON_PRICE)) {
            preparedStatement.setBigDecimal(1, lessonPrice);
            ResultSet resultSet = preparedStatement.executeQuery();
            courses = CourseMapper.getInstance().retrieve(resultSet);
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding courses by lesson price: " + exception);
            throw new DaoException("Error has occurred while finding courses by lesson price: ", exception);
        }
        return courses;
    }

    @Override
    public List<Course> findCourseByStudentAmount(int studentAmount) throws DaoException {
        List<Course> courses;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_COURSES_BY_STUDENT_AMOUNT)) {
            preparedStatement.setInt(1, studentAmount);
            ResultSet resultSet = preparedStatement.executeQuery();
            courses = CourseMapper.getInstance().retrieve(resultSet);
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding courses by student amount: " + exception);
            throw new DaoException("Error has occurred while finding courses by student amount: ", exception);
        }
        return courses;
    }

    @Override
    public List<Course> findCourseByStatus(CourseStatus courseStatus) throws DaoException {
        List<Course> courses;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_COURSES_BY_STATUS)) {
            preparedStatement.setString(1, courseStatus.getStatus());
            ResultSet resultSet = preparedStatement.executeQuery();
            courses = CourseMapper.getInstance().retrieve(resultSet);
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding courses by status: " + exception);
            throw new DaoException("Error has occurred while finding courses by status: ", exception);
        }
        return courses;
    }
}
