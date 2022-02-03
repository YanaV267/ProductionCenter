package com.dev.productioncenter.model.dao;

import com.dev.productioncenter.entity.BankCard;
import com.dev.productioncenter.exception.DaoException;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @project Production Center
 * @author YanaV
 * The type Bank card dao.
 */
public abstract class BankCardDao extends BaseDao<Long, BankCard> {
    /**
     * Find bank card optional.
     *
     * @param bankCard the bank card
     * @return the optional
     * @throws DaoException the dao exception
     */
    abstract public Optional<BankCard> findBankCard(BankCard bankCard) throws DaoException;

    /**
     * Find balance big decimal.
     *
     * @param bankCard the bank card
     * @return the big decimal
     * @throws DaoException the dao exception
     */
    abstract public BigDecimal findBalance(BankCard bankCard) throws DaoException;
}
