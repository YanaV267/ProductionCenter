package com.dev.productioncenter.model.dao.mapper.impl;

import com.dev.productioncenter.model.dao.mapper.Mapper;
import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.entity.UserRole;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.dev.productioncenter.model.dao.ColumnName.*;

public class UserMapper implements Mapper<User> {
    private static final UserMapper instance = new UserMapper();

    private UserMapper() {
    }

    public static UserMapper getInstance() {
        return instance;
    }

    @Override
    public List<User> retrieve(ResultSet resultSet) throws SQLException {
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            User user = new User.UserBuilder()
                    .setId(resultSet.getLong(USER_ID))
                    .setLogin(resultSet.getString(USER_LOGIN))
                    .setPassword(resultSet.getString(USER_PASSWORD))
                    .setSurname(resultSet.getString(USER_SURNAME))
                    .setName(resultSet.getString(USER_NAME))
                    .setEmail(resultSet.getString(USER_EMAIL))
                    .setPhoneNumber(BigInteger.valueOf(resultSet.getLong(USER_PHONE_NUMBER)))
                    .setUserRole(UserRole.valueOf(resultSet.getString(USER_ROLE).toUpperCase()))
                    .build();
            users.add(user);
        }
        return users;
    }
}
