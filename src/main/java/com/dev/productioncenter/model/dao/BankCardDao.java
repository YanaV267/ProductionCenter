package com.dev.productioncenter.model.dao;

import com.dev.productioncenter.entity.BankCard;
import com.dev.productioncenter.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

public interface BankCardDao extends BaseDao<BankCard> {
    Logger LOGGER = LogManager.getLogger();

    boolean checkBankCard(BankCard bankCard) throws DaoException;

    BigDecimal findBalance(BankCard bankCard) throws DaoException;

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
