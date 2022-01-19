package com.dev.productioncenter.validator;

import java.util.Map;

public interface BankCardValidator {
    boolean checkNumber(String number);

    boolean checkOwnerName(String ownerName);

    boolean checkExpirationDate(String expirationDate);

    boolean checkCVVNumber(String cvvNumber);

    boolean checkBalance(String balance);

    boolean checkCardData(Map<String, String> cardData);
}
