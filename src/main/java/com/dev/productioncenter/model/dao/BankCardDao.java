package com.dev.productioncenter.model.dao;

import com.dev.productioncenter.entity.BankCard;
import com.dev.productioncenter.exception.DaoException;

import java.math.BigDecimal;

public interface BankCardDao extends BaseDao<BankCard> {
    boolean checkBankCard(BankCard bankCard) throws DaoException;

    BigDecimal findBalance(BankCard bankCard) throws DaoException;
}
