package com.development.productioncenter.model.dao.impl;

import com.development.productioncenter.model.connection.ConnectionPool;
import com.development.productioncenter.model.dao.ColumnName;
import com.development.productioncenter.model.dao.UserDao;
import com.development.productioncenter.entity.User;
import com.development.productioncenter.entity.UserRole;
import com.development.productioncenter.entity.UserStatus;
import com.development.productioncenter.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String SQL_INSERT_USER =
            "INSERT INTO users(login, password, surname, name, email, phone_number, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_USER =
            "UPDATE users SET login = ?, password = ?, surname = ?, name = ?, email = ?, phone_number = ?, status = ?, role = ? WHERE login = ?";
    private static final String SQL_DELETE_USER = "DELETE FROM users WHERE login = ?";
    private static final String SQL_SELECT_ALL_USERS =
            "SELECT login, password, surname, name, email, phone_number FROM users";
    private static final String SQL_SELECT_USERS_BY_LOGIN =
            "SELECT login, password, surname, name, email, phone_number FROM users WHERE login = ?";
    private static final String SQL_SELECT_USERS_BY_SURNAME =
            "SELECT login, password, surname, name, email, phone_number FROM users WHERE surname = ?";
    private static final String SQL_SELECT_USERS_BY_PHONE_NUMBER =
            "SELECT login, password, surname, name, email, phone_number FROM users WHERE phone_number = ?";
    private static final String SQL_SELECT_USERS_BY_STATUS =
            "SELECT login, password, surname, name, email, phone_number FROM users WHERE status = ?";
    private static final String SQL_SELECT_USERS_BY_ROLE =
            "SELECT login, password, surname, name, email, phone_number FROM users WHERE role = ?";

    @Override
    public boolean add(User user) throws DaoException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_USER)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getSurname());
            preparedStatement.setString(4, user.getName());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setInt(6, user.getPhoneNumber().intValue());
            preparedStatement.setString(7, user.getUserRole().getRole());
            preparedStatement.execute();
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while adding user: " + exception);
            throw new DaoException("Error has occurred while adding user: ", exception);
        } finally {
            close(connection);
        }
    }

    @Override
    public boolean update(User user) throws DaoException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER)) {
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setInt(5, user.getPhoneNumber().intValue());
            preparedStatement.setString(6, user.getUserStatus().getStatus());
            preparedStatement.setString(7, user.getUserRole().getRole());
            preparedStatement.setString(8, user.getLogin());
            preparedStatement.execute();
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while updating user's data: " + exception);
            throw new DaoException("Error has occurred while updating user's data: ", exception);
        } finally {
            close(connection);
        }
    }

    @Override
    public boolean delete(User user) throws DaoException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_USER)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.execute();
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while deleting user: " + exception);
            throw new DaoException("Error has occurred while deleting user: ", exception);
        } finally {
            close(connection);
        }
    }

    @Override
    public List<User> findAll() throws DaoException {
        List<User> users;
        Connection connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_USERS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            users = retrieveUsers(resultSet);
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding users: " + exception);
            throw new DaoException("Error has occurred while finding users: ", exception);
        } finally {
            close(connection);
        }
        return users;
    }

    @Override
    public List<User> findUsersByLogin(String login) throws DaoException {
        List<User> users;
        Connection connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USERS_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                users = retrieveUsers(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding users by login: " + exception);
            throw new DaoException("Error has occurred while finding users by login: ", exception);
        } finally {
            close(connection);
        }
        return users;
    }

    @Override
    public List<User> findUsersBySurname(String surname) throws DaoException {
        List<User> users;
        Connection connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USERS_BY_SURNAME)) {
            preparedStatement.setString(1, surname);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                users = retrieveUsers(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding users by surname: " + exception);
            throw new DaoException("Error has occurred while finding users by surname: ", exception);
        } finally {
            close(connection);
        }
        return users;
    }

    @Override
    public List<User> findUsersByPhoneNumber(BigInteger phoneNumber) throws DaoException {
        List<User> users;
        Connection connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USERS_BY_PHONE_NUMBER)) {
            preparedStatement.setInt(1, phoneNumber.intValue());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                users = retrieveUsers(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding users by phone number: " + exception);
            throw new DaoException("Error has occurred while finding users by phone number: ", exception);
        } finally {
            close(connection);
        }
        return users;
    }

    @Override
    public List<User> findUsersByStatus(UserStatus userStatus) throws DaoException {
        List<User> users;
        Connection connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USERS_BY_STATUS)) {
            preparedStatement.setString(1, userStatus.getStatus());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                users = retrieveUsers(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding users by status: " + exception);
            throw new DaoException("Error has occurred while finding users by status: ", exception);
        } finally {
            close(connection);
        }
        return users;
    }

    @Override
    public List<User> findUsersByRole(UserRole userRole) throws DaoException {
        List<User> users;
        Connection connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USERS_BY_ROLE)) {
            preparedStatement.setString(1, userRole.getRole());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                users = retrieveUsers(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding users by role: " + exception);
            throw new DaoException("Error has occurred while finding users by role: ", exception);
        } finally {
            close(connection);
        }
        return users;
    }

    public List<User> retrieveUsers(ResultSet resultSet) throws DaoException {
        List<User> users = new ArrayList<>();
        try {
            while (resultSet.next()) {
                User user = new User();
                user.setLogin(resultSet.getString(ColumnName.USER_LOGIN));
                user.setPassword(resultSet.getString(ColumnName.USER_PASSWORD));
                user.setSurname(resultSet.getString(ColumnName.USER_SURNAME));
                user.setName(resultSet.getString(ColumnName.USER_NAME));
                user.setEmail(resultSet.getString(ColumnName.USER_EMAIL));
                user.setPhoneNumber(BigInteger.valueOf(resultSet.getInt(ColumnName.USER_PHONE_NUMBER)));
                users.add(user);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while retrieving users' data from database: " + exception);
            throw new DaoException("Error has occurred while retrieving users' data from database: ", exception);
        }
        return users;
    }
}
