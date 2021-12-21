package com.development.productioncenter.validator.impl;

import com.development.productioncenter.validator.Validator;

public class ValidatorImpl implements Validator {
    private static final ValidatorImpl INSTANCE = new ValidatorImpl();
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
}
