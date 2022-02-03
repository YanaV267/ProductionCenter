package com.dev.productioncenter.model.dao;

import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.entity.UserRole;
import com.dev.productioncenter.entity.UserStatus;
import com.dev.productioncenter.exception.DaoException;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

/**
 * @project Production Center
 * @author YanaV
 * The type User dao.
 */
public abstract class UserDao extends BaseDao<Long, User> {
    /**
     * Update user password boolean.
     *
     * @param password the password
     * @param login    the login
     * @return the boolean
     * @throws DaoException the dao exception
     */
    abstract public boolean updateUserPassword(String password, String login) throws DaoException;

    /**
     * Find user by login optional.
     *
     * @param login the login
     * @return the optional
     * @throws DaoException the dao exception
     */
    abstract public Optional<User> findUserByLogin(String login) throws DaoException;

    /**
     * Find user password optional.
     *
     * @param login the login
     * @return the optional
     * @throws DaoException the dao exception
     */
    abstract public Optional<String> findUserPassword(String login) throws DaoException;

    /**
     * Find teacher by name optional.
     *
     * @param surname the surname
     * @param name    the name
     * @return the optional
     * @throws DaoException the dao exception
     */
    abstract public Optional<User> findTeacherByName(String surname, String name) throws DaoException;

    /**
     * Find user by email optional.
     *
     * @param email the email
     * @return the optional
     * @throws DaoException the dao exception
     */
    abstract public Optional<User> findUserByEmail(String email) throws DaoException;

    /**
     * Find users by full name status list.
     *
     * @param user               the user
     * @param startElementNumber the start element number
     * @return the list
     * @throws DaoException the dao exception
     */
    abstract public List<User> findUsersByFullNameStatus(User user, int startElementNumber) throws DaoException;

    /**
     * Find users by surname status list.
     *
     * @param user               the user
     * @param startElementNumber the start element number
     * @return the list
     * @throws DaoException the dao exception
     */
    abstract public List<User> findUsersBySurnameStatus(User user, int startElementNumber) throws DaoException;

    /**
     * Find users by surname list.
     *
     * @param user               the user
     * @param startElementNumber the start element number
     * @return the list
     * @throws DaoException the dao exception
     */
    abstract public List<User> findUsersBySurname(User user, int startElementNumber) throws DaoException;

    /**
     * Find users by full name list.
     *
     * @param user               the user
     * @param startElementNumber the start element number
     * @return the list
     * @throws DaoException the dao exception
     */
    abstract public List<User> findUsersByFullName(User user, int startElementNumber) throws DaoException;

    /**
     * Find users by status list.
     *
     * @param user               the user
     * @param startElementNumber the start element number
     * @return the list
     * @throws DaoException the dao exception
     */
    abstract public List<User> findUsersByStatus(User user, int startElementNumber) throws DaoException;

    /**
     * Find users by role list.
     *
     * @param role the role
     * @return the list
     * @throws DaoException the dao exception
     */
    abstract public List<User> findUsersByRole(UserRole role) throws DaoException;

    /**
     * Find users by role list.
     *
     * @param role               the role
     * @param startElementNumber the start element number
     * @return the list
     * @throws DaoException the dao exception
     */
    abstract public List<User> findUsersByRole(UserRole role, int startElementNumber) throws DaoException;

    /**
     * Find users teachers list.
     *
     * @param startElementNumber the start element number
     * @return the list
     * @throws DaoException the dao exception
     */
    abstract public List<User> findUsersTeachers(int startElementNumber) throws DaoException;

    /**
     * Find teachers holding lessons list.
     *
     * @param startElementNumber the start element number
     * @return the list
     * @throws DaoException the dao exception
     */
    abstract public List<User> findTeachersHoldingLessons(int startElementNumber) throws DaoException;

    /**
     * Find teachers holding lessons by surname list.
     *
     * @param surname            the surname
     * @param startElementNumber the start element number
     * @return the list
     * @throws DaoException the dao exception
     */
    abstract public List<User> findTeachersHoldingLessonsBySurname(String surname, int startElementNumber) throws DaoException;

    /**
     * Find teachers holding lessons by full name list.
     *
     * @param teacher            the teacher
     * @param startElementNumber the start element number
     * @return the list
     * @throws DaoException the dao exception
     */
    abstract public List<User> findTeachersHoldingLessonsByFullName(User teacher, int startElementNumber) throws DaoException;

    /**
     * Update user login boolean.
     *
     * @param currentLogin the current login
     * @param newLogin     the new login
     * @return the boolean
     * @throws DaoException the dao exception
     */
    abstract public boolean updateUserLogin(String currentLogin, String newLogin) throws DaoException;

    /**
     * Update user status boolean.
     *
     * @param login         the login
     * @param currentStatus the current status
     * @return the boolean
     * @throws DaoException the dao exception
     */
    abstract public boolean updateUserStatus(String login, UserStatus currentStatus) throws DaoException;

    /**
     * Update user role boolean.
     *
     * @param login       the login
     * @param currentRole the current role
     * @return the boolean
     * @throws DaoException the dao exception
     */
    abstract public boolean updateUserRole(String login, UserRole currentRole) throws DaoException;

    /**
     * Update picture boolean.
     *
     * @param login         the login
     * @param pictureStream the picture stream
     * @return the boolean
     * @throws DaoException the dao exception
     */
    abstract public boolean updatePicture(String login, InputStream pictureStream) throws DaoException;

    /**
     * Load picture optional.
     *
     * @param login the login
     * @return the optional
     * @throws DaoException the dao exception
     */
    abstract public Optional<InputStream> loadPicture(String login) throws DaoException;
}
