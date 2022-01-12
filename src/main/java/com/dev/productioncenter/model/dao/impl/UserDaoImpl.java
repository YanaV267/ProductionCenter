package com.dev.productioncenter.model.dao.impl;

import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.exception.DaoException;
import com.dev.productioncenter.model.connection.ConnectionPool;
import com.dev.productioncenter.model.dao.ColumnName;
import com.dev.productioncenter.model.dao.UserDao;
import com.dev.productioncenter.model.dao.mapper.impl.UserMapper;
import com.dev.productioncenter.entity.UserRole;
import com.dev.productioncenter.entity.UserStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.math.BigInteger;
import java.sql.*;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String SQL_INSERT_USER =
            "INSERT INTO users(login, password, surname, name, email, phone_number, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_USER =
            "UPDATE users SET password = ?, surname = ?, name = ?, email = ?, phone_number = ? WHERE login = ?";
    private static final String SQL_UPDATE_PROFILE_PICTURE = "UPDATE users SET profile_picture = ? WHERE login = ?";
    private static final String SQL_DELETE_USER = "DELETE FROM users WHERE login = ?";
    private static final String SQL_SELECT_ALL_USERS =
            "SELECT login, password, surname, name, email, phone_number, role FROM users";
    private static final String SQL_SELECT_USERS_BY_LOGIN =
            "SELECT login, password, surname, name, email, phone_number, role, profile_picture FROM users WHERE login = ?";
    private static final String SQL_SELECT_USERS_BY_SURNAME =
            "SELECT login, password, surname, name, email, phone_number, role FROM users WHERE surname = ?";
    private static final String SQL_SELECT_USERS_BY_EMAIL =
            "SELECT login, password, surname, name, email, phone_number, role FROM users WHERE email = ?";
    private static final String SQL_SELECT_USERS_BY_PHONE_NUMBER =
            "SELECT login, password, surname, name, email, phone_number, role FROM users WHERE phone_number = ?";
    private static final String SQL_SELECT_USERS_BY_STATUS =
            "SELECT login, password, surname, name, email, phone_number, role FROM users WHERE status = ?";
    private static final String SQL_SELECT_USERS_BY_ROLE =
            "SELECT login, password, surname, name, email, phone_number, role FROM users WHERE role = ?";
    private static final UserDaoImpl INSTANCE = new UserDaoImpl();

    private UserDaoImpl() {
    }

    public static UserDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean add(User user) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_USER)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getSurname());
            preparedStatement.setString(4, user.getName());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setLong(6, user.getPhoneNumber().longValue());
            preparedStatement.setString(7, user.getUserRole().getRole());
            preparedStatement.execute();
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while adding user: " + exception);
            throw new DaoException("Error has occurred while adding user: ", exception);
        }
    }

    @Override
    public boolean update(User user) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER)) {
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setLong(5, user.getPhoneNumber().longValue());
            preparedStatement.setString(6, user.getLogin());
            preparedStatement.execute();
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while updating user's data: " + exception);
            throw new DaoException("Error has occurred while updating user's data: ", exception);
        }
    }

    @Override
    public boolean updatePicture(String login, InputStream pictureStream) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_PROFILE_PICTURE)) {
            preparedStatement.setBlob(1, pictureStream);
            preparedStatement.setString(2, login);
            preparedStatement.execute();
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while updating user's picture: " + exception);
            throw new DaoException("Error has occurred while updating user's picture: ", exception);
        }
    }

    @Override
    public boolean delete(User user) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_USER)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.execute();
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while deleting user: " + exception);
            throw new DaoException("Error has occurred while deleting user: ", exception);
        }
    }

    public Optional<InputStream> loadPicture(String login) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USERS_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(resultSet.getBlob(ColumnName.USER_PROFILE_PICTURE).getBinaryStream());
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
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_USERS)) {
            users = UserMapper.getInstance().retrieve(resultSet);
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding users: " + exception);
            throw new DaoException("Error has occurred while finding users: ", exception);
        }
        return users;
    }

    @Override
    public Optional<User> findUserByLogin(String login) throws DaoException {
        List<User> users;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USERS_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                users = UserMapper.getInstance().retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding user by login: " + exception);
            throw new DaoException("Error has occurred while finding user by login: ", exception);
        }
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    @Override
    public List<User> findUsersBySurname(String surname) throws DaoException {
        List<User> users;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USERS_BY_SURNAME)) {
            preparedStatement.setString(1, surname);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                users = UserMapper.getInstance().retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding users by surname: " + exception);
            throw new DaoException("Error has occurred while finding users by surname: ", exception);
        }
        return users;
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws DaoException {
        List<User> users;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USERS_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                users = UserMapper.getInstance().retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding user by email: " + exception);
            throw new DaoException("Error has occurred while finding user by email: ", exception);
        }
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    @Override
    public List<User> findUsersByPhoneNumber(BigInteger phoneNumber) throws DaoException {
        List<User> users;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USERS_BY_PHONE_NUMBER)) {
            preparedStatement.setInt(1, phoneNumber.intValue());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                users = UserMapper.getInstance().retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding users by phone number: " + exception);
            throw new DaoException("Error has occurred while finding users by phone number: ", exception);
        }
        return users;
    }

    @Override
    public List<User> findUsersByStatus(UserStatus userStatus) throws DaoException {
        List<User> users;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USERS_BY_STATUS)) {
            preparedStatement.setString(1, userStatus.getStatus());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                users = UserMapper.getInstance().retrieve(resultSet);
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
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USERS_BY_ROLE)) {
            preparedStatement.setString(1, userRole.getRole());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                users = UserMapper.getInstance().retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding users by role: " + exception);
            throw new DaoException("Error has occurred while finding users by role: ", exception);
        }
        return users;
    }
}
