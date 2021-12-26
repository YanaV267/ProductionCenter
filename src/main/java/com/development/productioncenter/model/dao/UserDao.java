package com.development.productioncenter.model.dao;

import com.development.productioncenter.entity.User;
import com.development.productioncenter.entity.UserRole;
import com.development.productioncenter.entity.UserStatus;
import com.development.productioncenter.exception.DaoException;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface UserDao extends BaseDao<User> {
    Optional<User> findUserByLogin(String login) throws DaoException;

    List<User> findUsersBySurname(String surname) throws DaoException;

    Optional<User> findUserByEmail(String email) throws DaoException;

    List<User> findUsersByPhoneNumber(BigInteger phoneNumber) throws DaoException;

    List<User> findUsersByStatus(UserStatus status) throws DaoException;

    List<User> findUsersByRole(UserRole role) throws DaoException;

}
