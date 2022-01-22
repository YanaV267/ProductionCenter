package com.dev.productioncenter.model.dao.impl;

import com.dev.productioncenter.entity.AgeGroup;
import com.dev.productioncenter.exception.DaoException;
import com.dev.productioncenter.model.connection.ConnectionPool;
import com.dev.productioncenter.model.dao.AgeGroupDao;
import com.dev.productioncenter.model.dao.ColumnName;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class AgeGroupDaoImpl implements AgeGroupDao {
    private static final String SQL_INSERT_AGE_GROUP =
            "INSERT INTO age_group(min_age, max_age) VALUES (?, ?)";
    private static final String SQL_SELECT_AGE_GROUP =
            "SELECT id_age_group FROM age_group WHERE min_age = ? AND max_age = ?";
    private static final AgeGroupDaoImpl instance = new AgeGroupDaoImpl();

    private AgeGroupDaoImpl() {
    }

    public static AgeGroupDaoImpl getInstance() {
        return instance;
    }

    @Override
    public long add(AgeGroup ageGroup) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_AGE_GROUP, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, ageGroup.getMinAge());
            preparedStatement.setInt(2, ageGroup.getMaxAge());
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getLong(1);
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while adding age group: " + exception);
            throw new DaoException("Error has occurred while adding age group: ", exception);
        }
    }

    @Override
    public boolean update(AgeGroup ageGroup) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        throw new UnsupportedOperationException("Deleting of an age group is unsupported");
    }

    @Override
    public List<AgeGroup> findAll() {
        throw new UnsupportedOperationException("Deleting of an age group is unsupported");
    }

    @Override
    public Optional<AgeGroup> findById(Long id) {
        throw new UnsupportedOperationException("Finding age group by id is unsupported");
    }

    @Override
    public Optional<Long> findByMinMaxAge(AgeGroup ageGroup) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_AGE_GROUP)) {
            preparedStatement.setInt(1, ageGroup.getMinAge());
            preparedStatement.setInt(2, ageGroup.getMaxAge());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(resultSet.getLong(ColumnName.AGE_GROUP_ID));
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding age group: " + exception);
            throw new DaoException("Error has occurred while finding age group: ", exception);
        }
        return Optional.empty();
    }
}
