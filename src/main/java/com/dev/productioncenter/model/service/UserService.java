package com.dev.productioncenter.model.service;

import com.dev.productioncenter.entity.Course;
import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.entity.UserRole;
import com.dev.productioncenter.entity.UserStatus;
import com.dev.productioncenter.exception.ServiceException;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @project Production Center
 * @author YanaV
 * The interface User service.
 */
public interface UserService {
    /**
     * Find user optional.
     *
     * @param login    the login
     * @param password the password
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<User> findUser(String login, String password) throws ServiceException;

    /**
     * Find user optional.
     *
     * @param surname  the surname
     * @param name     the name
     * @param userRole the user role
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<User> findUser(String surname, String name, UserRole userRole) throws ServiceException;

    /**
     * Find user optional.
     *
     * @param login the login
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<User> findUser(String login) throws ServiceException;

    /**
     * Find users map.
     *
     * @param role the role
     * @return the map
     * @throws ServiceException the service exception
     */
    Map<User, String> findUsers(UserRole role) throws ServiceException;

    /**
     * Find users map.
     *
     * @param role the role
     * @param page the page
     * @return the map
     * @throws ServiceException the service exception
     */
    Map<User, String> findUsers(UserRole role, int page) throws ServiceException;

    /**
     * Find users map.
     *
     * @param userData the user data
     * @param page     the page
     * @return the map
     * @throws ServiceException the service exception
     */
    Map<User, String> findUsers(Map<String, String> userData, int page) throws ServiceException;

    /**
     * Find users teachers map.
     *
     * @param page the page
     * @return the map
     * @throws ServiceException the service exception
     */
    Map<User, String> findUsersTeachers(int page) throws ServiceException;

    /**
     * Find teachers map.
     *
     * @param teacherData the teacher data
     * @param page        the page
     * @return the map
     * @throws ServiceException the service exception
     */
    Map<User, String> findTeachers(Map<String, String> teacherData, int page) throws ServiceException;

    /**
     * Is login occupied boolean.
     *
     * @param login the login
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean isLoginOccupied(String login) throws ServiceException;

    /**
     * Is email occupied boolean.
     *
     * @param email the email
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean isEmailOccupied(String email) throws ServiceException;

    /**
     * Register user boolean.
     *
     * @param userData the user data
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean registerUser(Map<String, String> userData) throws ServiceException;

    /**
     * Check roles boolean.
     *
     * @param usersRoles the users roles
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean checkRoles(Map<String, UserRole> usersRoles) throws ServiceException;

    /**
     * Update statuses boolean.
     *
     * @param usersStatuses the users statuses
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateStatuses(Map<String, UserStatus> usersStatuses) throws ServiceException;

    /**
     * Update roles boolean.
     *
     * @param usersRoles the users roles
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateRoles(Map<String, UserRole> usersRoles) throws ServiceException;

    /**
     * Update user login boolean.
     *
     * @param userData the user data
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateUserLogin(Map<String, String> userData) throws ServiceException;

    /**
     * Update user account data boolean.
     *
     * @param userData the user data
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateUserAccountData(Map<String, String> userData) throws ServiceException;

    /**
     * Update picture boolean.
     *
     * @param login         the login
     * @param pictureStream the picture stream
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updatePicture(String login, InputStream pictureStream) throws ServiceException;

    /**
     * Load picture optional.
     *
     * @param login the login
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<String> loadPicture(String login) throws ServiceException;

    /**
     * Load teachers pictures map.
     *
     * @param courses the courses
     * @return the map
     * @throws ServiceException the service exception
     */
    Map<Course, String> loadTeachersPictures(List<Course> courses) throws ServiceException;
}
