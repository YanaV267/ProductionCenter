package com.dev.productioncenter.validator;

import java.util.Map;

public interface Validator {
    boolean checkLogin(String login);

    boolean checkPassword(String password);

    boolean checkSurname(String surname);

    boolean checkName(String name);

    boolean checkEmail(String email);

    boolean checkNumber(String number);

    boolean checkUserData(Map<String, String> userData);
}
