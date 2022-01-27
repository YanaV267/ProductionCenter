package com.dev.productioncenter.model.dao;

import com.dev.productioncenter.entity.AbstractEntity;
import com.dev.productioncenter.exception.DaoException;
import com.dev.productioncenter.model.connection.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public abstract class BaseDao<K, T extends AbstractEntity> {
    protected Logger LOGGER = LogManager.getLogger();
    protected Connection connection;

    abstract public long add(T t) throws DaoException;

    abstract public boolean update(T t) throws DaoException;

    abstract public boolean delete(K k) throws DaoException;

    abstract public List<T> findAll() throws DaoException;

    abstract public Optional<T> findById(K k) throws DaoException;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void closeConnection() {
        if (connection != null) {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }
}
