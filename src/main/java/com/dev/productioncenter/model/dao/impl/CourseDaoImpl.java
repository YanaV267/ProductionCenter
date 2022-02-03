package com.dev.productioncenter.model.dao.impl;

import com.dev.productioncenter.entity.Activity;
import com.dev.productioncenter.entity.AgeGroup;
import com.dev.productioncenter.entity.Course;
import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.exception.DaoException;
import com.dev.productioncenter.model.connection.ConnectionPool;
import com.dev.productioncenter.model.dao.CourseDao;
import com.dev.productioncenter.model.dao.mapper.impl.CourseMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.dev.productioncenter.model.dao.ColumnName.*;

/**
 * @project Production Center
 * @author YanaV
 * The type Course dao.
 */
public class CourseDaoImpl extends CourseDao {
    private static final String SQL_INSERT_COURSE =
            "INSERT INTO courses(description, id_teacher, id_activity, id_age_group, lesson_price, student_amount) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_COURSE =
            "UPDATE courses SET description = ?, id_teacher = ?, id_age_group = ?, lesson_price = ?, " +
                    "student_amount = ?, status = ? WHERE id_course = ?";
    private static final String SQL_UPDATE_STUDENT_AMOUNT =
            "UPDATE courses SET student_amount = ? WHERE id_course = ?";
    private static final String SQL_DELETE_COURSE = "DELETE FROM courses WHERE id_course = ?";
    private static final String SQL_SELECT_ALL_COURSES =
            "SELECT courses.id_course, description, login, surname, name, category, type, min_age, max_age, " +
                    "lesson_price, student_amount, courses.status FROM courses " +
                    "JOIN users ON courses.id_teacher = users.id_user " +
                    "JOIN activities ON courses.id_activity = activities.id_activity " +
                    "JOIN age_group ON courses.id_age_group = age_group.id_age_group";
    private static final String SQL_SELECT_COURSES =
            "SELECT courses.id_course, description, login, surname, name, category, type, min_age, max_age, " +
                    "lesson_price, student_amount, courses.status FROM courses " +
                    "JOIN users ON courses.id_teacher = users.id_user " +
                    "JOIN activities ON courses.id_activity = activities.id_activity " +
                    "JOIN age_group ON courses.id_age_group = age_group.id_age_group LIMIT ?, 15";
    private static final String SQL_SELECT_COURSE_BY_ID =
            "SELECT courses.id_course, description, login, surname, name, category, type, min_age, max_age, " +
                    "lesson_price, student_amount, courses.status FROM courses " +
                    "JOIN users ON courses.id_teacher = users.id_user " +
                    "JOIN activities ON courses.id_activity = activities.id_activity " +
                    "JOIN age_group ON courses.id_age_group = age_group.id_age_group " +
                    "WHERE id_course = ?";
    private static final String SQL_SELECT_COURSES_BY_TEACHER =
            "SELECT courses.id_course, description, login, surname, name, category, type, min_age, max_age, " +
                    "lesson_price, student_amount, courses.status FROM courses " +
                    "JOIN users ON courses.id_teacher = users.id_user " +
                    "JOIN activities ON courses.id_activity = activities.id_activity " +
                    "JOIN age_group ON courses.id_age_group = age_group.id_age_group " +
                    "WHERE id_teacher = ?";
    private static final String SQL_SELECT_COURSES_BY_AGE_GROUP =
            "SELECT courses.id_course, description, login, surname, name, category, type, min_age, max_age, " +
                    "lesson_price, student_amount, courses.status FROM courses " +
                    "JOIN users ON courses.id_teacher = users.id_user " +
                    "JOIN activities ON courses.id_activity = activities.id_activity " +
                    "JOIN age_group ON courses.id_age_group = age_group.id_age_group " +
                    "WHERE min_age <= ? AND max_age >= ?";
    private static final String SQL_SELECT_COURSES_BY_ACTIVITY =
            "SELECT courses.id_course, description, login, surname, name, category, type, min_age, max_age, " +
                    "lesson_price, student_amount, courses.status FROM courses " +
                    "JOIN users ON courses.id_teacher = users.id_user " +
                    "JOIN activities ON courses.id_activity = activities.id_activity " +
                    "JOIN age_group ON courses.id_age_group = age_group.id_age_group " +
                    "WHERE category = ? AND type = ? LIMIT ?, 15";
    private static final String SQL_SELECT_COURSES_BY_ACTIVITY_CATEGORY =
            "SELECT courses.id_course, description, login, surname, name, category, type, min_age, max_age, " +
                    "lesson_price, student_amount, courses.status FROM courses " +
                    "JOIN users ON courses.id_teacher = users.id_user " +
                    "JOIN activities ON courses.id_activity = activities.id_activity " +
                    "JOIN age_group ON courses.id_age_group = age_group.id_age_group " +
                    "WHERE category = ? LIMIT ?, 15";
    private static final String SQL_SELECT_COURSES_BY_ACTIVITY_TYPE =
            "SELECT courses.id_course, description, login, surname, name, category, type, min_age, max_age, " +
                    "lesson_price, student_amount, courses.status FROM courses " +
                    "JOIN users ON courses.id_teacher = users.id_user " +
                    "JOIN activities ON courses.id_activity = activities.id_activity " +
                    "JOIN age_group ON courses.id_age_group = age_group.id_age_group " +
                    "WHERE type = ? LIMIT ?, 15";
    private static final String SQL_SELECT_COURSES_BY_ACTIVITY_WEEKDAY =
            "SELECT courses.id_course, description, login, surname, name, category, type, min_age, max_age, " +
                    "lesson_price, student_amount, courses.status FROM courses " +
                    "JOIN users ON courses.id_teacher = users.id_user " +
                    "JOIN activities ON courses.id_activity = activities.id_activity " +
                    "JOIN age_group ON courses.id_age_group = age_group.id_age_group " +
                    "JOIN lessons ON courses.id_course = lessons.id_course " +
                    "WHERE category = ? AND type = ? AND lessons.week_day = ? LIMIT ?, 15";
    private static final String SQL_SELECT_COURSES_BY_WEEKDAY =
            "SELECT courses.id_course, description, login, surname, name, category, type, min_age, max_age, " +
                    "lesson_price, student_amount, courses.status FROM courses " +
                    "JOIN users ON courses.id_teacher = users.id_user " +
                    "JOIN activities ON courses.id_activity = activities.id_activity " +
                    "JOIN age_group ON courses.id_age_group = age_group.id_age_group " +
                    "JOIN lessons ON courses.id_course = lessons.id_course " +
                    "WHERE lessons.week_day = ?";
    private static final String SQL_SELECT_AVAILABLE_COURSES =
            "SELECT courses.id_course, description, login, surname, name, category, type, min_age, max_age, " +
                    "lesson_price, student_amount, courses.status FROM courses " +
                    "JOIN users ON courses.id_teacher = users.id_user " +
                    "JOIN activities ON courses.id_activity = activities.id_activity " +
                    "JOIN age_group ON courses.id_age_group = age_group.id_age_group " +
                    "WHERE courses.status = 'upcoming' OR courses.status = 'running' LIMIT ?, 15";
    private static final String SQL_SELECT_COURSES_ALL_ACTIVITIES =
            "SELECT courses.id_course, surname, name, category, type FROM courses " +
                    "JOIN users ON courses.id_teacher = users.id_user " +
                    "JOIN activities ON courses.id_activity = activities.id_activity " +
                    "JOIN age_group ON courses.id_age_group = age_group.id_age_group GROUP BY surname, name, type";
    private static final String SQL_SELECT_COURSES_AVAILABLE_ACTIVITIES =
            "SELECT courses.id_course, surname, name, category, type FROM courses " +
                    "JOIN users ON courses.id_teacher = users.id_user " +
                    "JOIN activities ON courses.id_activity = activities.id_activity " +
                    "JOIN age_group ON courses.id_age_group = age_group.id_age_group " +
                    "WHERE courses.status = 'upcoming' OR courses.status = 'running' GROUP BY surname, name, type";

    /**
     * Instantiates a new Course dao.
     */
    public CourseDaoImpl() {
    }

    /**
     * Instantiates a new Course dao.
     *
     * @param isTransaction the is transaction
     */
    public CourseDaoImpl(boolean isTransaction) {
        if (!isTransaction) {
            connection = ConnectionPool.getInstance().getConnection();
        }
    }

    @Override
    public long add(Course course) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_COURSE, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, course.getDescription());
            preparedStatement.setLong(2, course.getTeacher().getId());
            preparedStatement.setLong(3, course.getActivity().getId());
            preparedStatement.setLong(4, course.getAgeGroup().getId());
            preparedStatement.setBigDecimal(5, course.getLessonPrice());
            preparedStatement.setLong(6, course.getStudentAmount());
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getLong(1);
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while adding course: " + exception);
            throw new DaoException("Error has occurred while adding course: ", exception);
        }
    }

    @Override
    public boolean update(Course course) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_COURSE)) {
            preparedStatement.setString(1, course.getDescription());
            preparedStatement.setLong(2, course.getTeacher().getId());
            preparedStatement.setLong(3, course.getAgeGroup().getId());
            preparedStatement.setBigDecimal(4, course.getLessonPrice());
            preparedStatement.setLong(5, course.getStudentAmount());
            preparedStatement.setString(6, course.getStatus().getStatus());
            preparedStatement.setLong(7, course.getId());
            preparedStatement.execute();
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while updating course data: " + exception);
            throw new DaoException("Error has occurred while updating course data: ", exception);
        }
    }

    @Override
    public boolean updateCourseStudentAmount(long id, int studentAmount) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_STUDENT_AMOUNT)) {
            preparedStatement.setInt(1, studentAmount);
            preparedStatement.setLong(2, id);
            preparedStatement.execute();
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while updating student amount: " + exception);
            throw new DaoException("Error has occurred while updating student amount: ", exception);
        }
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_COURSE)) {
            preparedStatement.setLong(1, id);
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
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_COURSES)) {
            CourseMapper courseMapper = CourseMapper.getInstance();
            courses = courseMapper.retrieve(resultSet);
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding courses: " + exception);
            throw new DaoException("Error has occurred while finding courses: ", exception);
        }
        return courses;
    }

    @Override
    public List<Course> findAll(int startElementNumber) throws DaoException {
        List<Course> courses;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_COURSES)) {
            preparedStatement.setInt(1, startElementNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                CourseMapper courseMapper = CourseMapper.getInstance();
                courses = courseMapper.retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding courses: " + exception);
            throw new DaoException("Error has occurred while finding courses: ", exception);
        }
        return courses;
    }

    @Override
    public Optional<Course> findById(Long id) throws DaoException {
        List<Course> courses;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_COURSE_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                CourseMapper courseMapper = CourseMapper.getInstance();
                courses = courseMapper.retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding course by id: " + exception);
            throw new DaoException("Error has occurred while finding course by id: ", exception);
        }
        return courses.isEmpty() ? Optional.empty() : Optional.of(courses.get(0));
    }

    @Override
    public List<Course> findCourseByTeacher(User teacher) throws DaoException {
        List<Course> courses;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_COURSES_BY_TEACHER)) {
            preparedStatement.setLong(1, teacher.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                CourseMapper courseMapper = CourseMapper.getInstance();
                courses = courseMapper.retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding courses by teacher: " + exception);
            throw new DaoException("Error has occurred while finding courses by teacher: ", exception);
        }
        return courses;
    }

    @Override
    public List<Course> findCourseByAgeGroup(AgeGroup ageGroup) throws DaoException {
        List<Course> courses;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_COURSES_BY_AGE_GROUP)) {
            preparedStatement.setInt(1, ageGroup.getMinAge());
            preparedStatement.setInt(2, ageGroup.getMaxAge());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                CourseMapper courseMapper = CourseMapper.getInstance();
                courses = courseMapper.retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding courses by age group: " + exception);
            throw new DaoException("Error has occurred while finding courses by age group: ", exception);
        }
        return courses;
    }

    @Override
    public List<Course> findCourseByActivity(Activity activity, int startElementNumber) throws DaoException {
        List<Course> courses;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_COURSES_BY_ACTIVITY)) {
            preparedStatement.setString(1, activity.getCategory());
            preparedStatement.setString(2, activity.getType());
            preparedStatement.setInt(3, startElementNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                CourseMapper courseMapper = CourseMapper.getInstance();
                courses = courseMapper.retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding courses by activity: " + exception);
            throw new DaoException("Error has occurred while finding courses by activity: ", exception);
        }
        return courses;
    }

    @Override
    public List<Course> findCourseByActivityCategory(Activity activity, int startElementNumber) throws DaoException {
        List<Course> courses;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_COURSES_BY_ACTIVITY_CATEGORY)) {
            preparedStatement.setString(1, activity.getCategory());
            preparedStatement.setInt(2, startElementNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                CourseMapper courseMapper = CourseMapper.getInstance();
                courses = courseMapper.retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding courses by activity category: " + exception);
            throw new DaoException("Error has occurred while finding courses by activity category: ", exception);
        }
        return courses;
    }

    @Override
    public List<Course> findCourseByActivityType(Activity activity, int startElementNumber) throws DaoException {
        List<Course> courses;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_COURSES_BY_ACTIVITY_TYPE)) {
            preparedStatement.setString(1, activity.getType());
            preparedStatement.setInt(2, startElementNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                CourseMapper courseMapper = CourseMapper.getInstance();
                courses = courseMapper.retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding courses by activity type: " + exception);
            throw new DaoException("Error has occurred while finding courses by activity type: ", exception);
        }
        return courses;
    }

    @Override
    public List<Course> findCourseByActivityWeekday(Activity activity, String weekday, int startElementNumber) throws DaoException {
        List<Course> courses;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_COURSES_BY_ACTIVITY_WEEKDAY)) {
            preparedStatement.setString(1, activity.getCategory());
            preparedStatement.setString(2, activity.getType());
            preparedStatement.setString(3, weekday);
            preparedStatement.setInt(4, startElementNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                CourseMapper courseMapper = CourseMapper.getInstance();
                courses = courseMapper.retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding courses by activity & weekday: " + exception);
            throw new DaoException("Error has occurred while finding courses by activity & weekday: ", exception);
        }
        return courses;
    }

    @Override
    public List<Course> findCourseByWeekday(String weekday) throws DaoException {
        List<Course> courses;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_COURSES_BY_WEEKDAY)) {
            preparedStatement.setString(1, weekday);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                CourseMapper courseMapper = CourseMapper.getInstance();
                courses = courseMapper.retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding courses by weekday: " + exception);
            throw new DaoException("Error has occurred while finding courses by weekday: ", exception);
        }
        return courses;
    }

    @Override
    public List<Course> findAvailableCourses(int startElementNumber) throws DaoException {
        List<Course> courses;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_AVAILABLE_COURSES)) {
            preparedStatement.setInt(1, startElementNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                CourseMapper courseMapper = CourseMapper.getInstance();
                courses = courseMapper.retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding available courses: " + exception);
            throw new DaoException("Error has occurred while finding available courses: ", exception);
        }
        return courses;
    }

    @Override
    public List<Course> findCoursesAllActivities() throws DaoException {
        List<Course> courses = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_COURSES_ALL_ACTIVITIES)) {
            while (resultSet.next()) {
                Course course = new Course.CourseBuilder()
                        .setId(resultSet.getLong(COURSE_ID))
                        .setTeacher(new User.UserBuilder()
                                .setSurname(resultSet.getString(USER_SURNAME))
                                .setName(resultSet.getString(USER_NAME))
                                .build())
                        .setActivity(new Activity.ActivityBuilder()
                                .setCategory(resultSet.getString(ACTIVITY_CATEGORY))
                                .setType(resultSet.getString(ACTIVITY_TYPE))
                                .build())
                        .build();
                courses.add(course);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding all activities & appropriate teachers: " + exception);
            throw new DaoException("Error has occurred while finding all activities & appropriate teachers: ", exception);
        }
        return courses;
    }

    @Override
    public List<Course> findCoursesAvailableActivities() throws DaoException {
        List<Course> courses = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_COURSES_AVAILABLE_ACTIVITIES)) {
            while (resultSet.next()) {
                Course course = new Course.CourseBuilder()
                        .setId(resultSet.getLong(COURSE_ID))
                        .setTeacher(new User.UserBuilder()
                                .setSurname(resultSet.getString(USER_SURNAME))
                                .setName(resultSet.getString(USER_NAME))
                                .build())
                        .setActivity(new Activity.ActivityBuilder()
                                .setCategory(resultSet.getString(ACTIVITY_CATEGORY))
                                .setType(resultSet.getString(ACTIVITY_TYPE))
                                .build())
                        .build();
                courses.add(course);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding available activities & appropriate teachers: " + exception);
            throw new DaoException("Error has occurred while finding available activities & appropriate teachers: ", exception);
        }
        return courses;
    }
}
