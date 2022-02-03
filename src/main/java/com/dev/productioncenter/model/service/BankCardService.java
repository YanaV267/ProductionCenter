package com.dev.productioncenter.model.service;

import com.dev.productioncenter.entity.BankCard;
import com.dev.productioncenter.exception.ServiceException;

import java.util.Map;
import java.util.Optional;

/**
 * @project Production Center
 * @author YanaV
 * The interface Bank card service.
 */
public interface BankCardService {
    /**
     * Find card optional.
     *
     * @param bankCardData the bank card data
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<BankCard> findCard(Map<String, String> bankCardData) throws ServiceException;

    /**
     * Replenish balance boolean.
     *
     * @param bankCard           the bank card
     * @param replenishmentValue the replenishment value
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean replenishBalance(BankCard bankCard, String replenishmentValue) throws ServiceException;

    /**
     * Withdraw money for enrollment boolean.
     *
     * @param bankCard     the bank card
     * @param enrollmentId the enrollment id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean withdrawMoneyForEnrollment(BankCard bankCard, long enrollmentId) throws ServiceException;

}
