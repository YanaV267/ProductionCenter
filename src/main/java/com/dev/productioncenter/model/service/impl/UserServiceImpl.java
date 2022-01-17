package com.dev.productioncenter.model.service.impl;

import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.entity.UserRole;
import com.dev.productioncenter.exception.DaoException;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.dao.UserDao;
import com.dev.productioncenter.model.dao.impl.UserDaoImpl;
import com.dev.productioncenter.model.service.UserService;
import com.dev.productioncenter.util.PasswordEncoder;
import com.dev.productioncenter.validator.UserValidator;
import com.dev.productioncenter.validator.impl.UserValidatorImpl;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.dev.productioncenter.controller.command.RequestParameter.*;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final UserDao userDao = UserDaoImpl.getInstance();
    private static final String INCORRECT_VALUE_PARAMETER = "incorrect";
    private static final String NUMBER_REMOVING_SYMBOLS_REGEX = "[+()-]";
    private static final String NUMBER_REPLACEMENT_REGEX = "";
    private static final String PICTURE_HEADER = "data:image/jpg;base64,";

    @Override
    public Optional<User> findUser(String login, String password) throws ServiceException {
        UserValidator validator = UserValidatorImpl.getInstance();
        try {
            if (validator.checkLogin(login) && validator.checkPassword(password)) {
                Optional<User> user = userDao.findUserByLogin(login);
                Optional<String> encodedPassword = PasswordEncoder.encode(password);
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
    public Optional<User> findUser(String login) throws ServiceException {
        try {
            Optional<User> user = userDao.findUserByLogin(login);
            if (user.isPresent()) {
                return user;
            }
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding user by login: " + exception);
            throw new ServiceException("Error has occurred while finding user by login: " + exception);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findUser(String surname, String name, UserRole userRole) throws ServiceException {
        try {
            if (userRole == UserRole.TEACHER) {
                return userDao.findTeacherByName(surname, name);
            }
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding user by login: " + exception);
            throw new ServiceException("Error has occurred while finding user by login: " + exception);
        }
        return Optional.empty();
    }

    @Override
    public List<User> findUsers() throws ServiceException {
        try {
            return userDao.findUsersByRole(UserRole.USER);
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding all users: " + exception);
            throw new ServiceException("Error has occurred while finding all users: " + exception);
        }
    }

    @Override
    public List<User> findTeachers() throws ServiceException {
        try {
            return userDao.findUsersByRole(UserRole.TEACHER);
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding teachers: " + exception);
            throw new ServiceException("Error has occurred while finding teachers: " + exception);
        }
    }

    @Override
    public boolean isLoginAvailable(String login) throws ServiceException {
        try {
            Optional<User> foundUser = userDao.findUserByLogin(login);
            return foundUser.isEmpty();
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while checking login availability: " + exception);
            throw new ServiceException("Error has occurred while checking login availability: " + exception);
        }
    }

    @Override
    public boolean isEmailAvailable(String email) throws ServiceException {
        try {
            Optional<User> foundUser = userDao.findUserByEmail(email);
            return foundUser.isEmpty();
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while checking email availability: " + exception);
            throw new ServiceException("Error has occurred while checking email availability: " + exception);
        }
    }

    @Override
    public boolean registerUser(Map<String, String> userData) throws ServiceException {
        try {
            if (UserValidatorImpl.getInstance().checkUserData(userData)) {
                if (!userData.get(PASSWORD).equals(userData.get(REPEATED_PASSWORD))) {
                    userData.put(REPEATED_PASSWORD, INCORRECT_VALUE_PARAMETER);
                    return false;
                }
                Optional<String> password = PasswordEncoder.encode(userData.get(PASSWORD));
                if (password.isPresent()) {
                    BigInteger phoneNumber = new BigInteger(
                            userData.get(PHONE_NUMBER).replaceAll(NUMBER_REMOVING_SYMBOLS_REGEX, NUMBER_REPLACEMENT_REGEX));
                    User user = new User.UserBuilder()
                            .setLogin(userData.get(LOGIN))
                            .setPassword(password.get())
                            .setSurname(userData.get(SURNAME))
                            .setName(userData.get(NAME))
                            .setEmail(userData.get(EMAIL))
                            .setPhoneNumber(phoneNumber)
                            .setUserRole(UserRole.USER)
                            .build();
                    userDao.add(user);
                    return true;
                }
            }
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while registering user: " + exception);
            throw new ServiceException("Error has occurred while registering user: " + exception);
        }
        return false;
    }

    @Override
    public boolean updateUserAccountData(Map<String, String> userData) throws ServiceException {
        UserValidator validator = UserValidatorImpl.getInstance();
        try {
            if (validator.checkLogin(userData.get(LOGIN)) && validator.checkUserPersonalData(userData)) {
                Optional<User> foundUser = userDao.findUserByLogin(userData.get(LOGIN));
                if (foundUser.isPresent() && !foundUser.get().getPassword().equals(userData.get(PASSWORD))) {
                    userData.put(PASSWORD, INCORRECT_VALUE_PARAMETER);
                    return false;
                }
                Optional<String> password;
                if (!userData.get(NEW_PASSWORD).isEmpty() || !userData.get(REPEATED_PASSWORD).isEmpty()
                        && UserValidatorImpl.getInstance().checkPassword(NEW_PASSWORD)) {
                    if (!userData.get(NEW_PASSWORD).equals(userData.get(REPEATED_PASSWORD))) {
                        userData.put(REPEATED_PASSWORD, INCORRECT_VALUE_PARAMETER);
                        return false;
                    }
                    password = PasswordEncoder.encode(userData.get(NEW_PASSWORD));
                } else {
                    password = Optional.of(userData.get(PASSWORD));
                }
                if (password.isPresent()) {
                    BigInteger phoneNumber = new BigInteger(
                            userData.get(PHONE_NUMBER).replaceAll(NUMBER_REMOVING_SYMBOLS_REGEX, NUMBER_REPLACEMENT_REGEX));
                    User user = new User.UserBuilder()
                            .setLogin(userData.get(LOGIN))
                            .setPassword(password.get())
                            .setSurname(userData.get(SURNAME))
                            .setName(userData.get(NAME))
                            .setEmail(userData.get(EMAIL))
                            .setPhoneNumber(phoneNumber)
                            .setUserRole(UserRole.USER)
                            .build();
                    userDao.update(user);
                    return true;
                }
            }
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while updating user account: " + exception);
            throw new ServiceException("Error has occurred while updating user account: " + exception);
        }
        return false;
    }

    @Override
    public boolean updatePicture(String login, InputStream pictureStream) throws ServiceException {
        try {
            return userDao.updatePicture(login, pictureStream);
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while updating user account picture: " + exception);
            throw new ServiceException("Error has occurred while updating user account picture: " + exception);
        }
    }

    @Override
    public Optional<String> loadPicture(String login) throws ServiceException {
        try {
            Optional<InputStream> pictureStream = userDao.loadPicture(login);
            if (pictureStream.isPresent()) {
                StringBuilder sb = new StringBuilder(PICTURE_HEADER);
                byte[] encodeImage = Base64.encodeBase64(pictureStream.get().readAllBytes(), false);
                String imageStr = StringUtils.newStringUtf8(encodeImage);
                sb.append(imageStr);
                return Optional.of(sb.toString());
            }
        } catch (DaoException | IOException exception) {
            LOGGER.error("Error has occurred while loading user account picture: " + exception);
            throw new ServiceException("Error has occurred while loading user account picture: " + exception);
        }
        return Optional.empty();
    }
}