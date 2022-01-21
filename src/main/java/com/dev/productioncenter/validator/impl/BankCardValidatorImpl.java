package com.dev.productioncenter.validator.impl;

import com.dev.productioncenter.validator.BankCardValidator;

import java.util.Map;

import static com.dev.productioncenter.controller.command.RequestParameter.*;

public class BankCardValidatorImpl implements BankCardValidator {
    private static final String INCORRECT_VALUE_PARAMETER = "incorrect";
    private static final String CARD_NUMBER_REGEX = "\\d{4} \\d{4} \\d{4} \\d{4}";
    private static final String OWNER_NAME_REGEX = "[A-Z ]{3,30}";
    private static final String EXPIRATION_DATE_REGEX = "(0[1-9]|1[0-2])/(2[2-6])";
    private static final String CVV_NUMBER_REGEX = "\\d{3}";
    private static final String BALANCE_REGEX = "^((\\d{2,4}\\.\\d{2})|(\\d{2,4}))$";
    private static final BankCardValidatorImpl instance = new BankCardValidatorImpl();

    private BankCardValidatorImpl() {

    }

    public static BankCardValidatorImpl getInstance() {
        return instance;
    }

    @Override
    public boolean checkNumber(String number) {
        return number != null && number.matches(CARD_NUMBER_REGEX);
    }

    @Override
    public boolean checkOwnerName(String ownerName) {
        return ownerName != null && ownerName.matches(OWNER_NAME_REGEX);
    }

    @Override
    public boolean checkExpirationDate(String expirationDate) {
        return expirationDate != null && expirationDate.matches(EXPIRATION_DATE_REGEX);
    }

    @Override
    public boolean checkCVVNumber(String cvvNumber) {
        return cvvNumber != null && cvvNumber.matches(CVV_NUMBER_REGEX);
    }

    @Override
    public boolean checkBalance(String balance) {
        return balance != null && balance.matches(BALANCE_REGEX);
    }

    @Override
    public boolean checkCardData(Map<String, String> cardData) {
        boolean isValid = true;
        if (!checkNumber(cardData.get(CARD_NUMBER))) {
            cardData.put(CARD_NUMBER, INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        if (!checkOwnerName(cardData.get(OWNER_NAME))) {
            cardData.put(OWNER_NAME, INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        if (!checkExpirationDate(cardData.get(EXPIRATION_DATE))) {
            cardData.put(EXPIRATION_DATE, INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        if (!checkCVVNumber(cardData.get(CVV_NUMBER))) {
            cardData.put(CVV_NUMBER, INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        return isValid;
    }
}
