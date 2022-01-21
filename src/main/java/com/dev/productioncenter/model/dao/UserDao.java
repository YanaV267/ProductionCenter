package com.dev.productioncenter.model.dao;

import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.exception.DaoException;
import com.dev.productioncenter.entity.UserRole;
import com.dev.productioncenter.entity.UserStatus;

import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface UserDao extends BaseDao<User> {
    Optional<User> findUserByLogin(String login) throws DaoException;

    Optional<User> findTeacherByName(String surname, String name) throws DaoException;

    Optional<User> findUserByEmail(String email) throws DaoException;

    List<User> findUsersByPhoneNumber(BigInteger phoneNumber) throws DaoException;

    List<User> findUsersByStatus(UserStatus status) throws DaoException;

    List<User> findUsersByRole(UserRole role) throws DaoException;

    boolean updateUserStatus(String login, UserStatus currentStatus) throws DaoException;

    boolean updateUserRole(String login, UserRole currentRole) throws DaoException;

    boolean updatePicture(String login, InputStream pictureStream) throws DaoException;

    Optional<InputStream> loadPicture(String login) throws DaoException;
}
