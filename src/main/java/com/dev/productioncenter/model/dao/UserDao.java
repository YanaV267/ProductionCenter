package com.dev.productioncenter.model.dao;

import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.entity.UserRole;
import com.dev.productioncenter.entity.UserStatus;
import com.dev.productioncenter.exception.DaoException;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface UserDao extends BaseDao<Long, User> {
    Optional<User> findUserByLogin(String login) throws DaoException;

    Optional<User> findTeacherByName(String surname, String name) throws DaoException;

    Optional<User> findUserByEmail(String email) throws DaoException;

    List<User> findUsersByNameStatus(User user) throws DaoException;

    List<User> findUsersByFullName(User user) throws DaoException;

    List<User> findUsersByStatus(UserStatus userStatus) throws DaoException;

    List<User> findUsersByStatus(User user) throws DaoException;

    List<User> findUsersByRole(UserRole role) throws DaoException;

    List<User> findTeachersHoldingLessons() throws DaoException;

    boolean updateUserStatus(String login, UserStatus currentStatus) throws DaoException;

    boolean updateUserRole(String login, UserRole currentRole) throws DaoException;

    boolean updatePicture(String login, InputStream pictureStream) throws DaoException;

    Optional<InputStream> loadPicture(String login) throws DaoException;
}
