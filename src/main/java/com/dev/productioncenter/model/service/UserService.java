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

public interface UserService {
    Optional<User> findUser(String login, String password) throws ServiceException;

    Optional<User> findUser(String surname, String name, UserRole userRole) throws ServiceException;

    Optional<User> findUser(String login) throws ServiceException;

    Map<User, String> findUsers(UserRole role) throws ServiceException;

    Map<User, String> findUsers(UserRole role, int page) throws ServiceException;

    Map<User, String> findUsers(Map<String, String> userData, int page) throws ServiceException;

    Map<User, String> findUsersTeachers(int page) throws ServiceException;

    Map<User, String> findTeachers(Map<String, String> teacherData, int page) throws ServiceException;

    boolean isLoginOccupied(String login) throws ServiceException;

    boolean isEmailOccupied(String email) throws ServiceException;

    boolean registerUser(Map<String, String> userData) throws ServiceException;

    boolean checkRoles(Map<String, UserRole> usersRoles) throws ServiceException;

    boolean updateStatuses(Map<String, UserStatus> usersStatuses) throws ServiceException;

    boolean updateRoles(Map<String, UserRole> usersRoles) throws ServiceException;

    boolean updateUserLogin(Map<String, String> userData) throws ServiceException;

    boolean updateUserAccountData(Map<String, String> userData) throws ServiceException;

    boolean updatePicture(String login, InputStream pictureStream) throws ServiceException;

    Optional<String> loadPicture(String login) throws ServiceException;

    Map<Course, String> loadTeachersPictures(List<Course> courses) throws ServiceException;
}
