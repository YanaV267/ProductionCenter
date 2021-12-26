package com.development.productioncenter.model.service.impl;

import com.development.productioncenter.entity.User;
import com.development.productioncenter.entity.UserRole;
import com.development.productioncenter.exception.DaoException;
import com.development.productioncenter.exception.ServiceException;
import com.development.productioncenter.model.dao.UserDao;
import com.development.productioncenter.model.dao.impl.UserDaoImpl;
import com.development.productioncenter.model.service.UserService;
import com.development.productioncenter.util.PasswordEncoder;
import com.development.productioncenter.validator.Validator;
import com.development.productioncenter.validator.impl.ValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.util.Map;
import java.util.Optional;

import static com.development.productioncenter.controller.command.RequestParameter.*;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final UserDao userDao = new UserDaoImpl();
    private static final String NUMBER_REMOVING_SYMBOLS_REGEX = "[+()-]";
    private static final String NUMBER_REPLACEMENT_REGEX = "";

    @Override
    public Optional<User> findUser(String login, String password) throws ServiceException {
        Validator validator = ValidatorImpl.getInstance();
        try {
            if (validator.checkLogin(login) && validator.checkPassword(password)) {
                Optional<User> user = userDao.findUserByLogin(login);
                Optional<String> encodedPassword = PasswordEncoder.getInstance().encode(password);
                if (user.isPresent() && encodedPassword.isPresent() && user.get().getPassword().equals(encodedPassword.get())) {
                    return user;
                }
            }
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while searching for user with login \"{}\": {}", login, exception);
            throw new ServiceException("Error has occurred while searching for user with login \"" + login + "\": ", exception);
        }
        return Optional.empty();
    }

    @Override
    public boolean registerUser(Map<String, String> userData) throws ServiceException {
        try {
            if (ValidatorImpl.getInstance().checkUserData(userData)) {
                Optional<User> foundUser = userDao.findUserByLogin(userData.get(LOGIN_PARAMETER));
                if (foundUser.isPresent() || !userData.get(PASSWORD_PARAMETER).equals(userData.get(REPEATED_PASSWORD_PARAMETER))) {
                    return false;
                }
                Optional<String> password = PasswordEncoder.getInstance().encode(userData.get(PASSWORD_PARAMETER));
                if (password.isPresent()) {
                    BigInteger phoneNumber = new BigInteger(
                            userData.get(PHONE_NUMBER_PARAMETER).replaceAll(NUMBER_REMOVING_SYMBOLS_REGEX, NUMBER_REPLACEMENT_REGEX));
                    User user = new User.UserBuilder()
                            .setLogin(userData.get(LOGIN_PARAMETER))
                            .setPassword(password.get())
                            .setSurname(userData.get(SURNAME_PARAMETER))
                            .setName(userData.get(NAME_PARAMETER))
                            .setEmail(userData.get(EMAIL_PARAMETER))
                            .setPhoneNumber(phoneNumber)
                            .setUserRole(UserRole.USER)
                            .build();
                    userDao.add(user);
                } else {
                    return false;
                }
            }
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while registering user: " + exception);
            throw new ServiceException("Error has occurred while registering user: " + exception);
        }
        return true;
    }
}
