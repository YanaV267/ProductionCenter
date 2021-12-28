package com.dev.productioncenter.model.service;

import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.exception.ServiceException;

import java.util.Map;
import java.util.Optional;

public interface UserService {
    Optional<User> findUser(String login, String password) throws ServiceException;

    boolean registerUser(Map<String, String> userData) throws ServiceException;
}
