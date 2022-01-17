package com.dev.productioncenter.model.dao.impl;

import com.dev.productioncenter.entity.*;
import com.dev.productioncenter.exception.DaoException;
import com.dev.productioncenter.model.connection.ConnectionPool;
import com.dev.productioncenter.model.dao.ActivityDao;
import com.dev.productioncenter.model.dao.ColumnName;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActivityDaoImpl implements ActivityDao {
    private static final String SQL_INSERT_ACTIVITY =
            "INSERT INTO activities(category, type) VALUES (?, ?)";
    private static final String SQL_SELECT_ALL_ACTIVITIES =
            "SELECT id_activity, category, type FROM activities";
    private static final String SQL_SELECT_ACTIVITY =
            "SELECT id_activity, category, type FROM activities WHERE category = ? AND type = ?";
    private static final String SQL_SELECT_ACTIVITY_BY_CATEGORY =
            "SELECT id_activity, category, type FROM activities WHERE category = ?";
    private static final String SQL_SELECT_ALL_CATEGORIES =
            "SELECT category FROM activities GROUP BY category";
    private static final ActivityDaoImpl instance = new ActivityDaoImpl();

    private ActivityDaoImpl() {
    }

    public static ActivityDaoImpl getInstance() {
        return instance;
    }

    @Override
    public long add(Activity activity) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_ACTIVITY, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, activity.getCategory());
            preparedStatement.setString(2, activity.getType());
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getLong(1);
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while adding activity: " + exception);
            throw new DaoException("Error has occurred while adding activity: ", exception);
        }
    }

    @Override
    public boolean update(Activity activity) {
        throw new UnsupportedOperationException("Updating of activity is unsupported");
    }

    @Override
    public boolean delete(Activity activity) {
        throw new UnsupportedOperationException("Deleting of activity is unsupported");
    }

    @Override
    public List<Activity> findAll() throws DaoException {
        List<Activity> activities = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_ACTIVITIES)) {
            while (resultSet.next()) {
                Activity activity = new Activity.ActivityBuilder()
                        .setId(resultSet.getLong(ColumnName.ACTIVITY_ID))
                        .setCategory(resultSet.getString(ColumnName.ACTIVITY_CATEGORY))
                        .setType(resultSet.getString(ColumnName.ACTIVITY_TYPE))
                        .build();
                activities.add(activity);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding activities: " + exception);
            throw new DaoException("Error has occurred while finding activities: ", exception);
        }
        return activities;
    }

    @Override
    public boolean findActivity(Activity activity) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ACTIVITY, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, activity.getCategory());
            preparedStatement.setString(2, activity.getType());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding activity: " + exception);
            throw new DaoException("Error has occurred while finding activity: ", exception);
        }
    }

    @Override
    public long findActivityId(Activity activity) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ACTIVITY, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, activity.getCategory());
            preparedStatement.setString(2, activity.getType());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getLong(1);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding activity: " + exception);
            throw new DaoException("Error has occurred while finding activity: ", exception);
        }
    }

    @Override
    public List<Activity> findActivitiesByCategory(String category) throws DaoException {
        List<Activity> activities = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ACTIVITY_BY_CATEGORY)) {
            preparedStatement.setString(1, category);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Activity activity = new Activity.ActivityBuilder()
                            .setId(resultSet.getLong(ColumnName.ACTIVITY_ID))
                            .setCategory(resultSet.getString(ColumnName.ACTIVITY_CATEGORY))
                            .setType(resultSet.getString(ColumnName.ACTIVITY_TYPE))
                            .build();
                    activities.add(activity);
                }
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding activity by category: " + exception);
            throw new DaoException("Error has occurred while finding activity by category: ", exception);
        }
        return activities;
    }

    @Override
    public List<String> findCategories() throws DaoException {
        List<String> categories = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_CATEGORIES)) {
            while (resultSet.next()) {
                categories.add(resultSet.getString(ColumnName.ACTIVITY_CATEGORY));
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding categories: " + exception);
            throw new DaoException("Error has occurred while finding categories: ", exception);
        }
        return categories;
    }
}