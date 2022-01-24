package com.dev.productioncenter.model.service;

import com.dev.productioncenter.entity.BankCard;
import com.dev.productioncenter.exception.ServiceException;

import java.util.Map;
import java.util.Optional;

public interface BankCardService {
    Optional<BankCard> findCard(Map<String, String> bankCardData) throws ServiceException;

    boolean replenishBalance(BankCard bankCard, String replenishmentValue) throws ServiceException;

    boolean withdrawMoneyForEnrollment(BankCard bankCard, long enrollmentId) throws ServiceException;

}
