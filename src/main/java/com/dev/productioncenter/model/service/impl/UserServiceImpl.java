package com.dev.productioncenter.model.service.impl;

import com.dev.productioncenter.entity.Course;
import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.entity.UserRole;
import com.dev.productioncenter.entity.UserStatus;
import com.dev.productioncenter.exception.DaoException;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.dao.Transaction;
import com.dev.productioncenter.model.dao.UserDao;
import com.dev.productioncenter.model.dao.impl.UserDaoImpl;
import com.dev.productioncenter.model.service.UserService;
import com.dev.productioncenter.util.PasswordEncoder;
import com.dev.productioncenter.util.PhoneNumberFormatter;
import com.dev.productioncenter.validator.UserValidator;
import com.dev.productioncenter.validator.impl.UserValidatorImpl;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.*;

import static com.dev.productioncenter.controller.command.RequestParameter.*;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String INCORRECT_VALUE_PARAMETER = "incorrect";
    private static final String NUMBER_REMOVING_SYMBOLS_REGEX = "[+()-]";
    private static final String NUMBER_REPLACEMENT_REGEX = "";
    private static final String PICTURE_HEADER = "data:image/jpg;base64,";
    private static final String EMPTY_VALUE_PARAMETER = "";
    private static final UserService instance = new UserServiceImpl();

    private UserServiceImpl() {
    }

    public static UserService getInstance() {
        return instance;
    }

    @Override
    public Optional<User> findUser(String login, String password) throws ServiceException {
        UserDao userDao = new UserDaoImpl(false);
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
        } finally {
            userDao.closeConnection();
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findUser(String login) throws ServiceException {
        UserDao userDao = new UserDaoImpl(false);
        try {
            Optional<User> user = userDao.findUserByLogin(login);
            if (user.isPresent()) {
                return user;
            }
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding user by login: " + exception);
            throw new ServiceException("Error has occurred while finding user by login: ", exception);
        } finally {
            userDao.closeConnection();
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findUser(String surname, String name, UserRole userRole) throws ServiceException {
        UserDao userDao = new UserDaoImpl(false);
        try {
            if (userRole == UserRole.TEACHER) {
                return userDao.findTeacherByName(surname, name);
            }
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding user by name: " + exception);
            throw new ServiceException("Error has occurred while finding user by name: ", exception);
        } finally {
            userDao.closeConnection();
        }
        return Optional.empty();
    }

    @Override
    public Map<User, String> findUsers(UserRole role) throws ServiceException {
        UserDao userDao = new UserDaoImpl(false);
        try {
            List<User> allUsers = userDao.findUsersByRole(role);
            Map<User, String> users = new HashMap<>();
            for (User user : allUsers) {
                users.put(user, PhoneNumberFormatter.format(user.getPhoneNumber()));
            }
            return users;
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding users: " + exception);
            throw new ServiceException("Error has occurred while finding users: ", exception);
        } finally {
            userDao.closeConnection();
        }
    }

    @Override
    public Map<User, String> findUsers(UserRole role, int page) throws ServiceException {
        UserDao userDao = new UserDaoImpl(false);
        try {
            int startElementNumber = page * 15 - 15;
            List<User> allUsers = userDao.findUsersByRole(role, startElementNumber);
            Map<User, String> users = new HashMap<>();
            for (User user : allUsers) {
                users.put(user, PhoneNumberFormatter.format(user.getPhoneNumber()));
            }
            return users;
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding users: " + exception);
            throw new ServiceException("Error has occurred while finding users: ", exception);
        } finally {
            userDao.closeConnection();
        }
    }

    @Override
    public Map<User, String> findUsers(Map<String, String> userData, int page) throws ServiceException {
        UserDao userDao = new UserDaoImpl(false);
        try {
            User user = new User();
            user.setSurname(userData.get(SURNAME));
            user.setName(userData.get(NAME));
            if (userData.get(STATUS) != null) {
                user.setUserStatus(UserStatus.valueOf(userData.get(STATUS).toUpperCase()));
            }
            user.setUserRole(UserRole.USER);
            int startElementNumber = page * 15 - 15;
            List<User> foundUsers = new ArrayList<>();
            if (user.getSurname() != null && !user.getSurname().isEmpty()) {
                if (user.getName() != null && !user.getName().isEmpty()) {
                    if (userData.get(STATUS) != null) {
                        foundUsers = userDao.findUsersByNameStatus(user, startElementNumber);
                    } else {
                        foundUsers = userDao.findUsersByFullName(user, startElementNumber);
                    }
                } else {
                    if (userData.get(STATUS) != null) {
                        foundUsers = userDao.findUsersBySurnameStatus(user, startElementNumber);
                    } else {
                        foundUsers = userDao.findUsersBySurname(user, startElementNumber);
                    }
                }
            } else {
                if (userData.get(STATUS) != null) {
                    foundUsers = userDao.findUsersByStatus(user, startElementNumber);
                }
            }
            Map<User, String> users = new HashMap<>();
            for (User foundUser : foundUsers) {
                users.put(foundUser, PhoneNumberFormatter.format(foundUser.getPhoneNumber()));
            }
            return users;
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding users: " + exception);
            throw new ServiceException("Error has occurred while finding users: ", exception);
        } finally {
            userDao.closeConnection();
        }
    }

    @Override
    public Map<User, String> findUsersTeachers(int page) throws ServiceException {
        UserDao userDao = new UserDaoImpl(false);
        try {
            int startElementNumber = page * 15 - 15;
            List<User> allUsers = userDao.findUsersTeachers(startElementNumber);
            Map<User, String> users = new LinkedHashMap<>();
            for (User user : allUsers) {
                users.put(user, PhoneNumberFormatter.format(user.getPhoneNumber()));
            }
            return users;
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding users & teachers: " + exception);
            throw new ServiceException("Error has occurred while finding users & teachers: ", exception);
        } finally {
            userDao.closeConnection();
        }
    }

    @Override
    public Map<User, String> findTeachers(Map<String, String> teacherData, int page) throws ServiceException {
        UserDao userDao = new UserDaoImpl(false);
        try {
            User teacher = new User();
            teacher.setSurname(teacherData.get(SURNAME));
            teacher.setName(teacherData.get(NAME));
            teacher.setUserRole(UserRole.TEACHER);
            int startElementNumber = page * 15 - 15;
            List<User> foundTeachers = new ArrayList<>();
            if (teacher.getSurname() != null && !teacher.getSurname().isEmpty()) {
                if (teacher.getName() != null && !teacher.getName().isEmpty()) {
                    if (teacherData.get(STATUS) != null) {
                        foundTeachers = userDao.findTeachersHoldingLessons(startElementNumber);
                    } else {
                        foundTeachers = userDao.findUsersByFullName(teacher, startElementNumber);
                    }
                } else {
                    if (teacherData.get(STATUS) != null) {
                        foundTeachers = userDao.findTeachersHoldingLessonsBySurname(teacher.getSurname(), startElementNumber);
                    } else {
                        foundTeachers = userDao.findUsersBySurname(teacher, startElementNumber);
                    }
                }
            } else {
                if (teacherData.get(STATUS) != null) {
                    foundTeachers = userDao.findTeachersHoldingLessons(startElementNumber);
                }
            }
            Map<User, String> teachers = new HashMap<>();
            for (User foundTeacher : foundTeachers) {
                teachers.put(foundTeacher, PhoneNumberFormatter.format(foundTeacher.getPhoneNumber()));
            }
            return teachers;
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding teachers: " + exception);
            throw new ServiceException("Error has occurred while finding teachers: ", exception);
        } finally {
            userDao.closeConnection();
        }
    }

    @Override
    public boolean isLoginOccupied(String login) throws ServiceException {
        UserDao userDao = new UserDaoImpl(false);
        try {
            Optional<User> foundUser = userDao.findUserByLogin(login);
            return foundUser.isPresent();
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while checking login availability: " + exception);
            throw new ServiceException("Error has occurred while checking login availability: ", exception);
        } finally {
            userDao.closeConnection();
        }
    }

    @Override
    public boolean isEmailOccupied(String email) throws ServiceException {
        UserDao userDao = new UserDaoImpl(false);
        try {
            Optional<User> foundUser = userDao.findUserByEmail(email);
            return foundUser.isPresent();
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while checking email availability: " + exception);
            throw new ServiceException("Error has occurred while checking email availability: ", exception);
        } finally {
            userDao.closeConnection();
        }
    }

    @Override
    public boolean registerUser(Map<String, String> userData) throws ServiceException {
        UserDao userDao = new UserDaoImpl(false);
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
            throw new ServiceException("Error has occurred while registering user: ", exception);
        } finally {
            userDao.closeConnection();
        }
        return false;
    }

    @Override
    public boolean updateStatuses(Map<String, UserStatus> usersStatuses) throws ServiceException {
        UserDao userDao = new UserDaoImpl(false);
        try {
            for (Map.Entry<String, UserStatus> userStatus : usersStatuses.entrySet()) {
                if (!userDao.updateUserStatus(userStatus.getKey(), userStatus.getValue())) {
                    return false;
                }
            }
            return true;
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while changing users' statuses: " + exception);
            throw new ServiceException("Error has occurred while changing users' statuses: ", exception);
        } finally {
            userDao.closeConnection();
        }
    }

    @Override
    public boolean checkRoles(Map<String, UserRole> usersRoles) throws ServiceException {
        UserDao userDao = new UserDaoImpl(false);
        try {
            for (Map.Entry<String, UserRole> userRole : usersRoles.entrySet()) {
                Optional<User> user = userDao.findUserByLogin(userRole.getKey());
                if (user.isPresent()) {
                    if (user.get().getUserRole() == UserRole.TEACHER && userRole.getValue() != UserRole.TEACHER) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
            return true;
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while checking users' roles: " + exception);
            throw new ServiceException("Error has occurred while changing users' roles: ", exception);
        } finally {
            userDao.closeConnection();
        }
    }

    @Override
    public boolean updateRoles(Map<String, UserRole> usersRoles) throws ServiceException {
        UserDao userDao = new UserDaoImpl(false);
        try {
            for (Map.Entry<String, UserRole> userRole : usersRoles.entrySet()) {
                if (!userDao.updateUserRole(userRole.getKey(), userRole.getValue())) {
                    return false;
                }
            }
            return true;
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while changing users' roles: " + exception);
            throw new ServiceException("Error has occurred while changing users' roles: ", exception);
        } finally {
            userDao.closeConnection();
        }
    }

    @Override
    public boolean updateUserLogin(Map<String, String> userData) throws ServiceException {
        UserDao userDao = new UserDaoImpl(true);
        UserValidator validator = UserValidatorImpl.getInstance();
        Transaction transaction = Transaction.getInstance();
        try {
            if (validator.checkLogin(userData.get(LOGIN))) {
                if (!userData.get(LOGIN).equals(userData.get(CURRENT_LOGIN))) {
                    transaction.begin(userDao);
                    Optional<User> foundUser = userDao.findUserByLogin(userData.get(CURRENT_LOGIN));
                    Optional<String> password = PasswordEncoder.encode(userData.get(PASSWORD));
                    if (foundUser.isPresent() && password.isPresent()) {
                        if (foundUser.get().getPassword().equals(password.get())) {
                            if (userDao.updateUserLogin(userData.get(CURRENT_LOGIN), userData.get(LOGIN))) {
                                return true;
                            }
                        } else {
                            userData.put(PASSWORD, INCORRECT_VALUE_PARAMETER);
                        }
                    }
                    transaction.rollback();
                    return false;
                } else {
                    return true;
                }
            }
            return false;
        } catch (DaoException exception) {
            try {
                transaction.rollback();
            } catch (DaoException daoException) {
                LOGGER.error("Error has occurred while doing transaction rollback for updating user login: " + daoException);
            }
            LOGGER.error("Error has occurred while updating user login: " + exception);
            throw new ServiceException("Error has occurred while updating user login: ", exception);
        }
    }

    @Override
    public boolean updateUserAccountData(Map<String, String> userData) throws ServiceException {
        UserDao userDao = new UserDaoImpl(true);
        UserValidator validator = UserValidatorImpl.getInstance();
        Transaction transaction = Transaction.getInstance();
        try {
            if (validator.checkLogin(userData.get(LOGIN)) && validator.checkUserPersonalData(userData)) {
                Optional<String> password;
                if (!userData.get(NEW_PASSWORD).isEmpty() || !userData.get(REPEATED_PASSWORD).isEmpty()
                        && UserValidatorImpl.getInstance().checkPassword(NEW_PASSWORD)) {
                    if (!userData.get(NEW_PASSWORD).equals(userData.get(REPEATED_PASSWORD))) {
                        userData.put(REPEATED_PASSWORD, INCORRECT_VALUE_PARAMETER);
                        transaction.rollback();
                        return false;
                    }
                    password = PasswordEncoder.encode(userData.get(NEW_PASSWORD));
                } else {
                    password = PasswordEncoder.encode(userData.get(PASSWORD));
                }
                if (password.isPresent()) {
                    transaction.begin(userDao);
                    BigInteger phoneNumber = new BigInteger(userData.get(PHONE_NUMBER)
                            .replaceAll(NUMBER_REMOVING_SYMBOLS_REGEX, NUMBER_REPLACEMENT_REGEX));
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
                    transaction.commit();
                    return true;
                }
            }
            transaction.rollback();
            return false;
        } catch (DaoException exception) {
            try {
                transaction.rollback();
            } catch (DaoException daoException) {
                LOGGER.error("Error has occurred while doing transaction rollback for updating user account: " + daoException);
            }
            LOGGER.error("Error has occurred while updating user account: " + exception);
            throw new ServiceException("Error has occurred while updating user account: ", exception);
        } finally {
            try {
                transaction.end();
            } catch (DaoException exception) {
                LOGGER.error("Error has occurred while ending transaction for updating user account: " + exception);
            }
        }
    }

    @Override
    public boolean updatePicture(String login, InputStream pictureStream) throws ServiceException {
        UserDao userDao = new UserDaoImpl(false);
        try {
            return userDao.updatePicture(login, pictureStream);
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while updating user account picture: " + exception);
            throw new ServiceException("Error has occurred while updating user account picture: ", exception);
        } finally {
            userDao.closeConnection();
        }
    }

    @Override
    public Optional<String> loadPicture(String login) throws ServiceException {
        UserDao userDao = new UserDaoImpl(false);
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
            throw new ServiceException("Error has occurred while loading user account picture: ", exception);
        } finally {
            userDao.closeConnection();
        }
        return Optional.empty();
    }

    @Override
    public Map<Course, String> loadTeachersPictures(List<Course> courses) throws ServiceException {
        Map<Course, String> teachersPictures = new HashMap<>();
        for (Course course : courses) {
            String login = course.getTeacher().getLogin();
            Optional<String> picture = loadPicture(login);
            if (picture.isPresent()) {
                teachersPictures.put(course, picture.get());
            } else {
                teachersPictures.put(course, EMPTY_VALUE_PARAMETER);
            }
        }
        return teachersPictures;
    }
}
