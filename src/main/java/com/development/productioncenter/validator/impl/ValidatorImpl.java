package com.development.productioncenter.validator.impl;

import com.development.productioncenter.validator.Validator;

import java.util.Map;

import static com.development.productioncenter.controller.command.RequestParameter.*;

public class ValidatorImpl implements Validator {
    private static final ValidatorImpl INSTANCE = new ValidatorImpl();
    private static final String INCORRECT_VALUE_PARAMETER = "incorrect";
    private static final String LOGIN_REGEX = "[a-zA-Z][A-Za-z0-9]{4,29}";
    private static final String PASSWORD_REGEX = "[a-zA-Z][A-Za-z0-9]{7,29}";
    private static final String SURNAME_REGEX = "[А-ЯA-Z][а-яa-z]{1,20}";
    private static final String NAME_REGEX = "[А-ЯA-Z][а-яa-z]{1,15}";
    private static final String EMAIL_REGEX = "(([A-Za-z\\d._]+){5,25}@([a-z]+){3,7}\\.([a-z]+){2,3})";
    private static final String NUMBER_REGEX = "\\+375\\(\\d{2}\\)\\d{3}-\\d{2}-\\d{2}";

    private ValidatorImpl() {
    }

    public static ValidatorImpl getInstance() {
        return INSTANCE;
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
        if (!checkLogin(userData.get(LOGIN_PARAMETER))) {
            userData.put(LOGIN_PARAMETER, INCORRECT_VALUE_PARAMETER);
            return false;
        }
        if (!checkPassword(userData.get(PASSWORD_PARAMETER))) {
            userData.put(PASSWORD_PARAMETER, INCORRECT_VALUE_PARAMETER);
            return false;
        }
        if (!checkSurname(userData.get(SURNAME_PARAMETER))) {
            userData.put(SURNAME_PARAMETER, INCORRECT_VALUE_PARAMETER);
            return false;
        }
        if (!checkName(userData.get(NAME_PARAMETER))) {
            userData.put(NAME_PARAMETER, INCORRECT_VALUE_PARAMETER);
            return false;
        }
        if (!checkEmail(userData.get(EMAIL_PARAMETER))) {
            userData.put(EMAIL_PARAMETER, INCORRECT_VALUE_PARAMETER);
            return false;
        }
        if (!checkNumber(userData.get(PHONE_NUMBER_PARAMETER))) {
            userData.put(PHONE_NUMBER_PARAMETER, INCORRECT_VALUE_PARAMETER);
            return false;
        }
        return true;
    }
}
