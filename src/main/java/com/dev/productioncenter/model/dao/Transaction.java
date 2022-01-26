package com.dev.productioncenter.model.dao;

import com.dev.productioncenter.model.connection.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class Transaction {//TODO: connection into dao
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Transaction instance = new Transaction();
    private Connection connection;

    private Transaction() {
    }

    public static Transaction getInstance() {
        return instance;
    }

    public void begin() {
        if (connection == null) {
            connection = ConnectionPool.getInstance().getConnection();
        }
        try {
            connection.setAutoCommit(false);
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while beginning a transaction: " + exception);
        }
        //set connection to dao
    }

    public void commit() {
        try {
            connection.commit();
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while doing transaction commit: " + exception);
        }
    }

    public void rollback() {
        try {
            connection.commit();
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while doing transaction rollback: " + exception);
        }
    }

    public void end() {
        if (connection != null) {
            try {
                connection.close();
                connection.setAutoCommit(true);
            } catch (SQLException exception) {
                LOGGER.error("Error has occurred while ending transaction: " + exception);
            }
            connection = null;
        }
    }
}
