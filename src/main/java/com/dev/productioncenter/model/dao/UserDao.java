package com.dev.productioncenter.model.dao;

import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.entity.UserRole;
import com.dev.productioncenter.entity.UserStatus;
import com.dev.productioncenter.exception.DaoException;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public abstract class UserDao extends BaseDao<Long, User> {
    abstract public Optional<User> findUserByLogin(String login) throws DaoException;

    abstract public Optional<User> findTeacherByName(String surname, String name) throws DaoException;

    abstract public Optional<User> findUserByEmail(String email) throws DaoException;

    abstract public List<User> findUsersByNameStatus(User user, int startElementNumber) throws DaoException;

    abstract public List<User> findUsersBySurnameStatus(User user, int startElementNumber) throws DaoException;

    abstract public List<User> findUsersBySurname(User user, int startElementNumber) throws DaoException;

    abstract public List<User> findUsersByFullName(User user, int startElementNumber) throws DaoException;

    abstract public List<User> findUsersByStatus(User user, int startElementNumber) throws DaoException;

    abstract public List<User> findUsersByRole(UserRole role) throws DaoException;

    abstract public List<User> findUsersByRole(UserRole role, int startElementNumber) throws DaoException;

    abstract public List<User> findUsersTeachers(int startElementNumber) throws DaoException;

    abstract public List<User> findTeachersHoldingLessons(int startElementNumber) throws DaoException;

    abstract public List<User> findTeachersHoldingLessonsBySurname(String surname, int startElementNumber) throws DaoException;

    abstract public long findLastElementId() throws DaoException;

    abstract public boolean updateUserStatus(String login, UserStatus currentStatus) throws DaoException;

    abstract public boolean updateUserRole(String login, UserRole currentRole) throws DaoException;

    abstract public boolean updatePicture(String login, InputStream pictureStream) throws DaoException;

    abstract public Optional<InputStream> loadPicture(String login) throws DaoException;
}
