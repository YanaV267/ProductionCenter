package com.dev.productioncenter.model.dao;

import com.dev.productioncenter.entity.AbstractEntity;
import com.dev.productioncenter.exception.DaoException;
import com.dev.productioncenter.model.connection.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public interface BaseDao<K, T extends AbstractEntity> {
    Logger LOGGER = LogManager.getLogger();

    long add(T t) throws DaoException;

    boolean update(T t) throws DaoException;

    boolean delete(K k) throws DaoException;

    List<T> findAll() throws DaoException;

    Optional<T> findById(K k) throws DaoException;

    default void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while closing statement: " + exception);
        }
    }

    default void close(Connection connection) {
        if (connection != null) {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }
}
