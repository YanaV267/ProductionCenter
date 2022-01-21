package com.dev.productioncenter.model.dao.impl;

import com.dev.productioncenter.entity.BankCard;
import com.dev.productioncenter.exception.DaoException;
import com.dev.productioncenter.model.connection.ConnectionPool;
import com.dev.productioncenter.model.dao.ColumnName;
import com.dev.productioncenter.model.dao.BankCardDao;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Optional;

import static com.dev.productioncenter.model.dao.ColumnName.*;

public class BankCardDaoImpl implements BankCardDao {
    private static final String SQL_UPDATE_BANK_CARD_BALANCE =
            "UPDATE bank_cards SET balance = ? WHERE card_number = ?";
    private static final String SQL_SELECT_BANK_CARD =
            "SELECT card_number, expiration_date, owner_name, cvv_number, balance FROM bank_cards " +
                    "WHERE card_number = ? AND expiration_date = ? AND owner_name = ? AND cvv_number = ?";
    private static final String SQL_SELECT_BANK_CARD_BALANCE =
            "SELECT balance FROM bank_cards WHERE card_number = ? AND expiration_date = ? AND owner_name = ? AND cvv_number = ?";
    private static final BankCardDaoImpl instance = new BankCardDaoImpl();

    private BankCardDaoImpl() {
    }

    public static BankCardDaoImpl getInstance() {
        return instance;
    }

    @Override
    public long add(BankCard bankCard) {
        throw new UnsupportedOperationException("Adding of a bank card is unsupported");
    }

    @Override
    public boolean update(BankCard bankCard) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_BANK_CARD_BALANCE)) {
            preparedStatement.setBigDecimal(1, bankCard.getBalance());
            preparedStatement.setLong(2, bankCard.getCardNumber());
            preparedStatement.execute();
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while updating bank card balance: " + exception);
            throw new DaoException("Error has occurred while updating bank card balance: ", exception);
        }
    }

    @Override
    public boolean delete(Long id) {
        throw new UnsupportedOperationException("Deleting of a bank card is unsupported");
    }

    @Override
    public List<BankCard> findAll() {
        throw new UnsupportedOperationException("Finding all bank cards is unsupported");
    }

    @Override
    public Optional<BankCard> findById(Long id) {
        throw new UnsupportedOperationException("Finding bank card by id is unsupported");
    }

    @Override
    public Optional<BankCard> findBankCard(BankCard bankCard) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BANK_CARD)) {
            preparedStatement.setLong(1, bankCard.getCardNumber());
            preparedStatement.setDate(2, Date.valueOf(bankCard.getExpirationDate()));
            preparedStatement.setString(3, bankCard.getOwnerName());
            preparedStatement.setInt(4, bankCard.getCvvNumber());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    bankCard.setBalance(resultSet.getBigDecimal(BANK_CARD_BALANCE));
                    return Optional.of(bankCard);
                }
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding bank card: " + exception);
            throw new DaoException("Error has occurred while finding bank card: ", exception);
        }
        return Optional.empty();
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
                resultSet.next();
                balance = resultSet.getBigDecimal(ColumnName.BANK_CARD_BALANCE);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding bank card balance: " + exception);
            throw new DaoException("Error has occurred while finding bank card balance: ", exception);
        }
        return balance;
    }
}
