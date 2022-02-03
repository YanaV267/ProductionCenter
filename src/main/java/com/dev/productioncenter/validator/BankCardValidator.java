package com.dev.productioncenter.validator;

import java.util.Map;

/**
 * @project Production Center
 * @author YanaV
 * The interface Bank card validator.
 */
public interface BankCardValidator {
    /**
     * Check number boolean.
     *
     * @param number the number
     * @return the boolean
     */
    boolean checkNumber(String number);

    /**
     * Check owner name boolean.
     *
     * @param ownerName the owner name
     * @return the boolean
     */
    boolean checkOwnerName(String ownerName);

    /**
     * Check expiration date boolean.
     *
     * @param expirationDate the expiration date
     * @return the boolean
     */
    boolean checkExpirationDate(String expirationDate);

    /**
     * Check cvv number boolean.
     *
     * @param cvvNumber the cvv number
     * @return the boolean
     */
    boolean checkCVVNumber(String cvvNumber);

    /**
     * Check balance boolean.
     *
     * @param balance the balance
     * @return the boolean
     */
    boolean checkBalance(String balance);

    /**
     * Check card data boolean.
     *
     * @param cardData the card data
     * @return the boolean
     */
    boolean checkCardData(Map<String, String> cardData);
}
