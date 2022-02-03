package com.dev.productioncenter.validator;

import java.util.Map;

/**
 * @project Production Center
 * @author YanaV
 * The interface User validator.
 */
public interface UserValidator {
    /**
     * Check login boolean.
     *
     * @param login the login
     * @return the boolean
     */
    boolean checkLogin(String login);

    /**
     * Check password boolean.
     *
     * @param password the password
     * @return the boolean
     */
    boolean checkPassword(String password);

    /**
     * Check surname boolean.
     *
     * @param surname the surname
     * @return the boolean
     */
    boolean checkSurname(String surname);

    /**
     * Check name boolean.
     *
     * @param name the name
     * @return the boolean
     */
    boolean checkName(String name);

    /**
     * Check email boolean.
     *
     * @param email the email
     * @return the boolean
     */
    boolean checkEmail(String email);

    /**
     * Check number boolean.
     *
     * @param number the number
     * @return the boolean
     */
    boolean checkNumber(String number);

    /**
     * Check user data boolean.
     *
     * @param userData the user data
     * @return the boolean
     */
    boolean checkUserData(Map<String, String> userData);

    /**
     * Check user personal data boolean.
     *
     * @param userData the user data
     * @return the boolean
     */
    boolean checkUserPersonalData(Map<String, String> userData);
}
