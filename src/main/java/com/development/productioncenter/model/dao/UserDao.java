package com.development.productioncenter.model.dao;

import com.development.productioncenter.entity.User;
import com.development.productioncenter.entity.UserRole;
import com.development.productioncenter.entity.UserStatus;
import com.development.productioncenter.exception.DaoException;

import java.math.BigInteger;
import java.util.List;

public interface UserDao extends BaseDao<User> {
    List<User> findUsersByLogin(String login) throws DaoException;

    List<User> findUsersBySurname(String surname) throws DaoException;

    List<User> findUsersByPhoneNumber(BigInteger phoneNumber) throws DaoException;

    List<User> findUsersByStatus(UserStatus status) throws DaoException;

    List<User> findUsersByRole(UserRole role) throws DaoException;

}
