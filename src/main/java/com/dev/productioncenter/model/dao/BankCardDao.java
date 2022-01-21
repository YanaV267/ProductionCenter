package com.dev.productioncenter.model.dao;

import com.dev.productioncenter.entity.BankCard;
import com.dev.productioncenter.exception.DaoException;

import java.math.BigDecimal;
import java.util.Optional;

public interface BankCardDao extends BaseDao<Long, BankCard> {
    Optional<BankCard> findBankCard(BankCard bankCard) throws DaoException;

    BigDecimal findBalance(BankCard bankCard) throws DaoException;
}
