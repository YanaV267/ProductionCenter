package com.dev.productioncenter.model.dao.mapper.impl;

import com.dev.productioncenter.model.dao.ColumnName;
import com.dev.productioncenter.model.dao.mapper.Mapper;
import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.entity.UserRole;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserMapper implements Mapper<User> {
    private static final UserMapper INSTANCE = new UserMapper();

    private UserMapper() {
    }

    public static UserMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public List<User> retrieve(ResultSet resultSet) throws SQLException {
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            User user = new User.UserBuilder()
                    .setLogin(resultSet.getString(ColumnName.USER_LOGIN))
                    .setPassword(resultSet.getString(ColumnName.USER_PASSWORD))
                    .setSurname(resultSet.getString(ColumnName.USER_SURNAME))
                    .setName(resultSet.getString(ColumnName.USER_NAME))
                    .setEmail(resultSet.getString(ColumnName.USER_EMAIL))
                    .setPhoneNumber(BigInteger.valueOf(resultSet.getInt(ColumnName.USER_PHONE_NUMBER)))
                    .setUserRole(UserRole.valueOf(resultSet.getString(ColumnName.USER_ROLE).toUpperCase()))
                    .build();
            users.add(user);
        }
        return users;
    }
}
