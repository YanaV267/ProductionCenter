package com.dev.productioncenter.model.dao;

import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.exception.DaoException;
import com.dev.productioncenter.entity.UserRole;
import com.dev.productioncenter.entity.UserStatus;

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
