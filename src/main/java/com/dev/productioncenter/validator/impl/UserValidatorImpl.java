package com.dev.productioncenter.validator.impl;

import com.dev.productioncenter.validator.UserValidator;

import java.util.Map;

import static com.dev.productioncenter.controller.command.RequestParameter.*;

public class UserValidatorImpl implements UserValidator {
    private static final UserValidatorImpl instance = new UserValidatorImpl();
    private static final String INCORRECT_VALUE_PARAMETER = "incorrect";
    private static final String LOGIN_REGEX = "[a-zA-Z][A-Za-z\\d]{4,29}";
    private static final String PASSWORD_REGEX = "[a-zA-Z][A-Za-z\\d]{7,29}";
    private static final String SURNAME_REGEX = "[А-ЯA-Z][а-яa-z]{1,20}";
    private static final String NAME_REGEX = "[А-ЯA-Z][а-яa-z]{1,15}";
    private static final String EMAIL_REGEX = "(([A-Za-z\\d._]+){5,25}@([a-z]+){3,7}\\.([a-z]+){2,3})";
    private static final String NUMBER_REGEX = "\\+375\\(\\d{2}\\)\\d{3}-\\d{2}-\\d{2}";

    private UserValidatorImpl() {
    }

    public static UserValidatorImpl getInstance() {
        return instance;
    }

    @Override
    public boolean checkLogin(String login) {
        return login != null && login.matches(LOGIN_REGEX);
    }

    @Override
    public boolean checkPassword(String password) {
        return password != null && password.matches(PASSWORD_REGEX);
    }

    @Override
    public boolean checkSurname(String surname) {
        return surname != null && surname.matches(SURNAME_REGEX);
    }

    @Override
    public boolean checkName(String name) {
        return name != null && name.matches(NAME_REGEX);
    }

    @Override
    public boolean checkEmail(String email) {
        return email != null && email.matches(EMAIL_REGEX);
    }

    @Override
    public boolean checkNumber(String number) {
        return number != null && number.matches(NUMBER_REGEX);
    }

    @Override
    public boolean checkUserData(Map<String, String> userData) {
        boolean isValid = true;
        if (!checkLogin(userData.get(LOGIN))) {
            userData.put(LOGIN, INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        if (!checkPassword(userData.get(PASSWORD))) {
            userData.put(PASSWORD, INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        isValid = checkUserPersonalData(userData) && isValid;
        return isValid;
    }

    @Override
    public boolean checkUserPersonalData(Map<String, String> userData) {
        boolean isValid = true;
        if (!checkSurname(userData.get(SURNAME))) {
            userData.put(SURNAME, INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        if (!checkName(userData.get(NAME))) {
            userData.put(NAME, INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        if (!checkEmail(userData.get(EMAIL))) {
            userData.put(EMAIL, INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        if (!checkNumber(userData.get(PHONE_NUMBER))) {
            userData.put(PHONE_NUMBER, INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        return isValid;
    }
}
