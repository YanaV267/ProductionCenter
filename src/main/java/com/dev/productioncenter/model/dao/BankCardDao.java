package com.dev.productioncenter.model.dao;

import com.dev.productioncenter.entity.BankCard;
import com.dev.productioncenter.exception.DaoException;

import java.math.BigDecimal;
import java.util.Optional;

public abstract class BankCardDao extends BaseDao<Long, BankCard> {
    abstract public Optional<BankCard> findBankCard(BankCard bankCard) throws DaoException;

    abstract public BigDecimal findBalance(BankCard bankCard) throws DaoException;
}
