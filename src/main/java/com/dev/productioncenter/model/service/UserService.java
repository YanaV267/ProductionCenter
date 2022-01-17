package com.dev.productioncenter.model.service;

import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.entity.UserRole;
import com.dev.productioncenter.exception.ServiceException;

import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    boolean updatePicture(String login, InputStream pictureStream) throws ServiceException;

    Optional<String> loadPicture(String login) throws ServiceException;

    Optional<User> findUser(String login, String password) throws ServiceException;

    Optional<User> findUser(String surname, String name, UserRole userRole) throws ServiceException;

    Optional<User> findUser(String login) throws ServiceException;

    List<User> findUsers() throws ServiceException;

    List<User> findTeachers() throws ServiceException;

    boolean isLoginAvailable(String login) throws ServiceException;

    boolean isEmailAvailable(String email) throws ServiceException;

    boolean registerUser(Map<String, String> userData) throws ServiceException;

    boolean updateUserAccountData(Map<String, String> userData) throws ServiceException;
}