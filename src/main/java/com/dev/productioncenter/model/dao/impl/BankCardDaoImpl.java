package com.dev.productioncenter.model.dao.impl;

import com.dev.productioncenter.entity.BankCard;
import com.dev.productioncenter.exception.DaoException;
import com.dev.productioncenter.model.connection.ConnectionPool;
import com.dev.productioncenter.model.dao.ColumnName;
import com.dev.productioncenter.model.dao.BankCardDao;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;

public class BankCardDaoImpl implements BankCardDao {
    private static final String SQL_UPDATE_BANK_CARD_BALANCE =
            "UPDATE bank_cards SET balance = ? WHERE card_number = ?";
    private static final String SQL_SELECT_BANK_CARD =
            "SELECT card_number, expiration_date, owner_name, cvv_number FROM bank_cards WHERE card_number = ? AND expiration_date = ? AND owner_name = ? AND cvv_number = ?";
    private static final String SQL_SELECT_BANK_CARD_BALANCE =
            "SELECT balance FROM bank_cards WHERE card_number = ? AND expiration_date = ? AND owner_name = ? AND cvv_number = ?";
    private static final BankCardDaoImpl INSTANCE = new BankCardDaoImpl();

    private BankCardDaoImpl() {
    }

    public static BankCardDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean add(BankCard bankCard) {
        throw new UnsupportedOperationException("Adding of a bank card is unsupported");
    }

    @Override
    public boolean update(BankCard bankCard) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_BANK_CARD_BALANCE)) {
            preparedStatement.setLong(1, bankCard.getCardNumber());
            preparedStatement.setBigDecimal(2, bankCard.getBalance());
            preparedStatement.execute();
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while checking bank card: " + exception);
            throw new DaoException("Error has occurred while checking bank card: ", exception);
        }
    }

    @Override
    public boolean delete(BankCard bankCard) {
        throw new UnsupportedOperationException("Deleting of a bank card is unsupported");
    }

    @Override
    public List<BankCard> findAll() {
        throw new UnsupportedOperationException("Finding all bank cards is unsupported");
    }

    @Override
    public boolean checkBankCard(BankCard bankCard) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BANK_CARD)) {
            preparedStatement.setLong(1, bankCard.getCardNumber());
            preparedStatement.setDate(2, Date.valueOf(bankCard.getExpirationDate()));
            preparedStatement.setString(3, bankCard.getOwnerName());
            preparedStatement.setInt(4, bankCard.getCvvNumber());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while checking bank card: " + exception);
            throw new DaoException("Error has occurred while checking bank card: ", exception);
        }
        return false;
    }

    @Override
    public BigDecimal findBalance(BankCard bankCard) throws DaoException {
        BigDecimal balance;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BANK_CARD_BALANCE)) {
            preparedStatement.setLong(1, bankCard.getCardNumber());
            preparedStatement.setDate(2, Date.valueOf(bankCard.getExpirationDate()));
            preparedStatement.setString(3, bankCard.getOwnerName());
            preparedStatement.setInt(4, bankCard.getCvvNumber());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                balance = resultSet.getBigDecimal(ColumnName.BANK_CARD_BALANCE);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while retrieving bank card balance: " + exception);
            throw new DaoException("Error has occurred while retrieving bank card balance: ", exception);
        }
        return balance;
    }
}
