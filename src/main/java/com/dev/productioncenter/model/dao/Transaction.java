package com.dev.productioncenter.model.dao;

import com.dev.productioncenter.exception.DaoException;
import com.dev.productioncenter.model.connection.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @project Production Center
 * @author YanaV
 * The type Transaction.
 */
public class Transaction {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Transaction instance = new Transaction();
    private Connection connection;

    private Transaction() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static Transaction getInstance() {
        return instance;
    }

    /**
     * Begin.
     *
     * @param daos the daos
     * @throws DaoException the dao exception
     */
    public void begin(BaseDao... daos) throws DaoException {
        if (connection == null) {
            connection = ConnectionPool.getInstance().getConnection();
        }
        try {
            connection.setAutoCommit(false);
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while beginning a transaction: " + exception);
            throw new DaoException("Error has occurred while beginning a transaction: ", exception);
        }
        for (BaseDao dao : daos) {
            dao.setConnection(connection);
        }
    }

    /**
     * Commit.
     *
     * @throws DaoException the dao exception
     */
    public void commit() throws DaoException {
        try {
            connection.commit();
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while doing transaction commit: " + exception);
            throw new DaoException("Error has occurred while doing transaction commit: ", exception);
        }
    }

    /**
     * Rollback.
     *
     * @throws DaoException the dao exception
     */
    public void rollback() throws DaoException {
        try {
            connection.rollback();
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while doing transaction rollback: " + exception);
            throw new DaoException("Error has occurred while doing transaction rollback: ", exception);
        }
    }

    /**
     * End.
     *
     * @throws DaoException the dao exception
     */
    public void end() throws DaoException {
        if (connection != null) {
            try {
                connection.close();
                connection.setAutoCommit(true);
            } catch (SQLException exception) {
                LOGGER.error("Error has occurred while ending transaction: " + exception);
                throw new DaoException("Error has occurred while ending transaction: ", exception);
            }
            connection = null;
        }
    }
}
