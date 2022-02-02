package com.dev.productioncenter.model.dao.impl;

import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.entity.UserRole;
import com.dev.productioncenter.entity.UserStatus;
import com.dev.productioncenter.exception.DaoException;
import com.dev.productioncenter.model.connection.ConnectionPool;
import com.dev.productioncenter.model.dao.UserDao;
import com.dev.productioncenter.model.dao.mapper.impl.UserMapper;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static com.dev.productioncenter.model.dao.ColumnName.USER_PASSWORD;
import static com.dev.productioncenter.model.dao.ColumnName.USER_PROFILE_PICTURE;

public class UserDaoImpl extends UserDao {
    private static final String SQL_INSERT_USER =
            "INSERT INTO users(login, surname, name, email, phone_number, role) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_USER =
            "UPDATE users SET surname = ?, name = ?, email = ?, phone_number = ? WHERE login = ?";
    private static final String SQL_UPDATE_USER_PASSWORD = "UPDATE users SET password = ? WHERE login = ?";
    private static final String SQL_UPDATE_PROFILE_PICTURE = "UPDATE users SET profile_picture = ? WHERE login = ?";
    private static final String SQL_UPDATE_USER_LOGIN = "UPDATE users SET login = ? WHERE login = ?";
    private static final String SQL_UPDATE_USER_STATUS = "UPDATE users SET status = ? WHERE login = ?";
    private static final String SQL_UPDATE_USER_ROLE = "UPDATE users SET role = ? WHERE login = ?";
    private static final String SQL_DELETE_USER = "DELETE FROM users WHERE login = ?";
    private static final String SQL_SELECT_ALL_USERS =
            "SELECT id_user, login, password, surname, name, email, phone_number, role, status FROM users";
    private static final String SQL_SELECT_USERS_BY_LOGIN =
            "SELECT id_user, login, password, surname, name, email, phone_number, role, status, profile_picture FROM users WHERE login = ?";
    private static final String SQL_SELECT_USER_PASSWORD = "SELECT password FROM users WHERE login = ?";
    private static final String SQL_SELECT_TEACHERS_BY_NAME =
            "SELECT id_user, login, password, surname, name, email, phone_number, role, status FROM users " +
                    "WHERE surname = ? AND name = ? AND role = 'teacher'";
    private static final String SQL_SELECT_USERS_BY_EMAIL =
            "SELECT id_user, login, password, surname, name, email, phone_number, role, status FROM users WHERE email = ?";
    private static final String SQL_SELECT_USERS_BY_SURNAME =
            "SELECT id_user, login, password, surname, name, email, phone_number, role, status FROM users " +
                    "WHERE surname LIKE ? AND role = ? LIMIT ?, 15";
    private static final String SQL_SELECT_USERS_BY_FULL_NAME =
            "SELECT id_user, login, password, surname, name, email, phone_number, role, status FROM users " +
                    "WHERE surname LIKE ? AND name LIKE ? AND role = ? LIMIT ?, 15";
    private static final String SQL_SELECT_USERS_BY_SURNAME_STATUS =
            "SELECT id_user, login, password, surname, name, email, phone_number, role, status FROM users " +
                    "WHERE surname LIKE ? AND status = ? AND role = ? LIMIT ?, 15";
    private static final String SQL_SELECT_USERS_BY_FULL_NAME_STATUS =
            "SELECT id_user, login, password, surname, name, email, phone_number, role, status FROM users " +
                    "WHERE surname LIKE ? AND name LIKE ? AND status = ? AND role = ? LIMIT ?, 15";
    private static final String SQL_SELECT_USERS_BY_STATUS =
            "SELECT id_user, login, password, surname, name, email, phone_number, role, status FROM users " +
                    "WHERE status = ? AND role = ? LIMIT ?, 15";
    private static final String SQL_SELECT_USERS_AND_TEACHERS =
            "SELECT id_user, login, password, surname, name, email, phone_number, role, status FROM users " +
                    "WHERE role = 'teacher' OR role = 'user' ORDER BY role LIMIT ?, 15";
    private static final String SQL_SELECT_TEACHERS_HOLDING_COURSES_BY_FULL_NAME =
            "SELECT id_user, login, password, surname, name, email, phone_number, role, users.status FROM users " +
                    "JOIN courses ON users.id_user = courses.id_teacher " +
                    "WHERE role = 'teacher' AND surname LIKE ? AND name LIKE ? LIMIT ?, 15";
    private static final String SQL_SELECT_TEACHERS_HOLDING_COURSES_BY_SURNAME =
            "SELECT id_user, login, password, surname, name, email, phone_number, role, users.status FROM users " +
                    "JOIN courses ON users.id_user = courses.id_teacher " +
                    "WHERE role = 'teacher' AND surname LIKE ? LIMIT ?, 15";
    private static final String SQL_SELECT_TEACHERS_HOLDING_COURSES =
            "SELECT id_user, login, password, surname, name, email, phone_number, role, users.status FROM users " +
                    "JOIN courses ON users.id_user = courses.id_teacher " +
                    "WHERE role = 'teacher' LIMIT ?, 15";
    private static final String SQL_SELECT_ALL_USERS_BY_ROLE =
            "SELECT id_user, login, password, surname, name, email, phone_number, role, status FROM users WHERE role = ?";
    private static final String SQL_SELECT_USERS_BY_ROLE =
            "SELECT id_user, login, password, surname, name, email, phone_number, role, status FROM users " +
                    "WHERE role = ? LIMIT ?, 15";
    private static final String QUERY_LIKE_WILDCARD = "%";

    public UserDaoImpl() {
    }

    public UserDaoImpl(boolean isTransaction) {
        if (!isTransaction) {
            connection = ConnectionPool.getInstance().getConnection();
        }
    }

    @Override
    public long add(User user) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setLong(5, user.getPhoneNumber().longValue());
            preparedStatement.setString(6, user.getUserRole().getRole());
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getLong(1);
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while adding user: " + exception);
            throw new DaoException("Error has occurred while adding user: ", exception);
        }
    }

    @Override
    public boolean updateUserPassword(String password, String login) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER_PASSWORD)) {
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, login);
            preparedStatement.execute();
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while updating user password: " + exception);
            throw new DaoException("Error has occurred while updating user password: ", exception);
        }
    }

    @Override
    public boolean update(User user) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER)) {
            preparedStatement.setString(1, user.getSurname());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setLong(4, user.getPhoneNumber().longValue());
            preparedStatement.setString(5, user.getLogin());
            preparedStatement.execute();
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while updating user's data: " + exception);
            throw new DaoException("Error has occurred while updating user's data: ", exception);
        }
    }

    @Override
    public boolean updatePicture(String login, InputStream pictureStream) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_PROFILE_PICTURE)) {
            preparedStatement.setBlob(1, pictureStream);
            preparedStatement.setString(2, login);
            preparedStatement.execute();
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while updating user's profile picture: " + exception);
            throw new DaoException("Error has occurred while updating user's profile picture: ", exception);
        }
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_USER)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while deleting user: " + exception);
            throw new DaoException("Error has occurred while deleting user: ", exception);
        }
    }

    @Override
    public Optional<InputStream> loadPicture(String login) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USERS_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next() && resultSet.getBlob(USER_PROFILE_PICTURE) != null) {
                    return Optional.of(resultSet.getBlob(USER_PROFILE_PICTURE).getBinaryStream());
                }
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while loading user profile picture: " + exception);
            throw new DaoException("Error has occurred while loading user profile picture: ", exception);
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() throws DaoException {
        List<User> users;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_USERS)) {
            UserMapper userMapper = UserMapper.getInstance();
            users = userMapper.retrieve(resultSet);
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding users: " + exception);
            throw new DaoException("Error has occurred while finding users: ", exception);
        }
        return users;
    }

    @Override
    public Optional<User> findById(Long id) {
        throw new UnsupportedOperationException("Finding user by id is unsupported");
    }

    @Override
    public Optional<User> findUserByLogin(String login) throws DaoException {
        List<User> users;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USERS_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                UserMapper userMapper = UserMapper.getInstance();
                users = userMapper.retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding user by login: " + exception);
            throw new DaoException("Error has occurred while finding user by login: ", exception);
        }
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    @Override
    public Optional<String> findUserPassword(String login) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER_PASSWORD)) {
            preparedStatement.setString(1, login);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(resultSet.getString(USER_PASSWORD));
                }
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding user pasword: " + exception);
            throw new DaoException("Error has occurred while finding user pasword: ", exception);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findTeacherByName(String surname, String name) throws DaoException {
        List<User> teachers;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_TEACHERS_BY_NAME)) {
            preparedStatement.setString(1, surname);
            preparedStatement.setString(2, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                UserMapper userMapper = UserMapper.getInstance();
                teachers = userMapper.retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding teacher by full name: " + exception);
            throw new DaoException("Error has occurred while finding teacher by full name: ", exception);
        }
        return teachers.isEmpty() ? Optional.empty() : Optional.of(teachers.get(0));
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws DaoException {
        List<User> users;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USERS_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                UserMapper userMapper = UserMapper.getInstance();
                users = userMapper.retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding user by email: " + exception);
            throw new DaoException("Error has occurred while finding user by email: ", exception);
        }
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    @Override
    public List<User> findUsersByFullNameStatus(User user, int startElementNumber) throws DaoException {
        List<User> users;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USERS_BY_FULL_NAME_STATUS)) {
            preparedStatement.setString(1, QUERY_LIKE_WILDCARD + user.getSurname() + QUERY_LIKE_WILDCARD);
            preparedStatement.setString(2, QUERY_LIKE_WILDCARD + user.getName() + QUERY_LIKE_WILDCARD);
            preparedStatement.setString(3, user.getUserStatus().getStatus());
            preparedStatement.setString(4, user.getUserRole().getRole());
            preparedStatement.setInt(5, startElementNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                UserMapper userMapper = UserMapper.getInstance();
                users = userMapper.retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding users by full name & status: " + exception);
            throw new DaoException("Error has occurred while finding users by full name & status: ", exception);
        }
        return users;
    }

    @Override
    public List<User> findUsersBySurnameStatus(User user, int startElementNumber) throws DaoException {
        List<User> users;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USERS_BY_SURNAME_STATUS)) {
            preparedStatement.setString(1, QUERY_LIKE_WILDCARD + user.getSurname() + QUERY_LIKE_WILDCARD);
            preparedStatement.setString(2, user.getUserStatus().getStatus());
            preparedStatement.setString(3, user.getUserRole().getRole());
            preparedStatement.setInt(4, startElementNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                UserMapper userMapper = UserMapper.getInstance();
                users = userMapper.retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding users by full name & status: " + exception);
            throw new DaoException("Error has occurred while finding users by full name & status: ", exception);
        }
        return users;
    }

    @Override
    public List<User> findUsersBySurname(User user, int startElementNumber) throws DaoException {
        List<User> users;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USERS_BY_SURNAME)) {
            preparedStatement.setString(1, QUERY_LIKE_WILDCARD + user.getSurname() + QUERY_LIKE_WILDCARD);
            preparedStatement.setString(2, user.getUserRole().getRole());
            preparedStatement.setInt(3, startElementNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                UserMapper userMapper = UserMapper.getInstance();
                users = userMapper.retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding users by surname: " + exception);
            throw new DaoException("Error has occurred while finding users by surname: ", exception);
        }
        return users;
    }

    @Override
    public List<User> findUsersByFullName(User user, int startElementNumber) throws DaoException {
        List<User> users;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USERS_BY_FULL_NAME)) {
            preparedStatement.setString(1, QUERY_LIKE_WILDCARD + user.getSurname() + QUERY_LIKE_WILDCARD);
            preparedStatement.setString(2, QUERY_LIKE_WILDCARD + user.getName() + QUERY_LIKE_WILDCARD);
            preparedStatement.setString(3, user.getUserRole().getRole());
            preparedStatement.setInt(4, startElementNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                UserMapper userMapper = UserMapper.getInstance();
                users = userMapper.retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding users by full name: " + exception);
            throw new DaoException("Error has occurred while finding users by full name: ", exception);
        }
        return users;
    }

    @Override
    public List<User> findUsersByStatus(User user, int startElementNumber) throws DaoException {
        List<User> users;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USERS_BY_STATUS)) {
            preparedStatement.setString(1, user.getUserStatus().getStatus());
            preparedStatement.setString(2, user.getUserRole().getRole());
            preparedStatement.setInt(3, startElementNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                UserMapper userMapper = UserMapper.getInstance();
                users = userMapper.retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding users by status: " + exception);
            throw new DaoException("Error has occurred while finding users by status: ", exception);
        }
        return users;
    }

    @Override
    public List<User> findUsersByRole(UserRole userRole) throws DaoException {
        List<User> users;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_USERS_BY_ROLE)) {
            preparedStatement.setString(1, userRole.getRole());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                UserMapper userMapper = UserMapper.getInstance();
                users = userMapper.retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding users by role: " + exception);
            throw new DaoException("Error has occurred while finding users by role: ", exception);
        }
        return users;
    }

    @Override
    public List<User> findUsersByRole(UserRole userRole, int startElementNumber) throws DaoException {
        List<User> users;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USERS_BY_ROLE)) {
            preparedStatement.setString(1, userRole.getRole());
            preparedStatement.setInt(2, startElementNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                UserMapper userMapper = UserMapper.getInstance();
                users = userMapper.retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding users by role: " + exception);
            throw new DaoException("Error has occurred while finding users by role: ", exception);
        }
        return users;
    }

    @Override
    public List<User> findUsersTeachers(int startElementNumber) throws DaoException {
        List<User> users;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USERS_AND_TEACHERS)) {
            preparedStatement.setInt(1, startElementNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                UserMapper userMapper = UserMapper.getInstance();
                users = userMapper.retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding users & teachers: " + exception);
            throw new DaoException("Error has occurred while finding users & teachers: ", exception);
        }
        return users;
    }

    @Override
    public List<User> findTeachersHoldingLessons(int startElementNumber) throws DaoException {
        List<User> users;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_TEACHERS_HOLDING_COURSES)) {
            preparedStatement.setInt(1, startElementNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                UserMapper userMapper = UserMapper.getInstance();
                users = userMapper.retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding holding lessons teachers: " + exception);
            throw new DaoException("Error has occurred while finding holding lessons teachers: ", exception);
        }
        return users;
    }

    @Override
    public List<User> findTeachersHoldingLessonsBySurname(String surname, int startElementNumber) throws DaoException {
        List<User> users;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_TEACHERS_HOLDING_COURSES_BY_SURNAME)) {
            preparedStatement.setString(1, QUERY_LIKE_WILDCARD + surname + QUERY_LIKE_WILDCARD);
            preparedStatement.setInt(2, startElementNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                UserMapper userMapper = UserMapper.getInstance();
                users = userMapper.retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding holding lessons teachers by surname: " + exception);
            throw new DaoException("Error has occurred while finding holding lessons teachers by surname: ", exception);
        }
        return users;
    }

    @Override
    public List<User> findTeachersHoldingLessonsByFullName(User teacher, int startElementNumber) throws DaoException {
        List<User> users;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_TEACHERS_HOLDING_COURSES_BY_FULL_NAME)) {
            preparedStatement.setString(1, QUERY_LIKE_WILDCARD + teacher.getSurname() + QUERY_LIKE_WILDCARD);
            preparedStatement.setString(2, QUERY_LIKE_WILDCARD + teacher.getName() + QUERY_LIKE_WILDCARD);
            preparedStatement.setInt(3, startElementNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                UserMapper userMapper = UserMapper.getInstance();
                users = userMapper.retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding holding lessons teachers by surname: " + exception);
            throw new DaoException("Error has occurred while finding holding lessons teachers by surname: ", exception);
        }
        return users;
    }

    @Override
    public boolean updateUserLogin(String currentLogin, String newLogin) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER_LOGIN)) {
            preparedStatement.setString(1, newLogin);
            preparedStatement.setString(2, currentLogin);
            preparedStatement.execute();
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while updating user's login: " + exception);
            throw new DaoException("Error has occurred while updating user's login: ", exception);
        }
    }

    @Override
    public boolean updateUserStatus(String login, UserStatus currentStatus) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER_STATUS)) {
            preparedStatement.setString(1, currentStatus.getStatus());
            preparedStatement.setString(2, login);
            preparedStatement.execute();
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while updating user's status: " + exception);
            throw new DaoException("Error has occurred while updating user's status: ", exception);
        }
    }

    @Override
    public boolean updateUserRole(String login, UserRole currentRole) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER_ROLE)) {
            preparedStatement.setString(1, currentRole.getRole());
            preparedStatement.setString(2, login);
            preparedStatement.execute();
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while updating user's role: " + exception);
            throw new DaoException("Error has occurred while updating user's role: ", exception);
        }
    }
}
