package com.development.productioncenter.model.dao;

import com.development.productioncenter.entity.AbstractEntity;
import com.development.productioncenter.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface BaseDao<T extends AbstractEntity> {
    Logger LOGGER = LogManager.getLogger();

    boolean add(T t) throws DaoException;

    boolean update(T t) throws DaoException;

    boolean delete(T t) throws DaoException;

    List<T> findAll() throws DaoException;

    default void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while closing statement: " + exception);
            throw new RuntimeException("Error has occurred while closing statement: ", exception);
        }
    }

    default void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while closing connection: " + exception);
            throw new RuntimeException("Error has occurred while closing connection: ", exception);
        }
    }
}
