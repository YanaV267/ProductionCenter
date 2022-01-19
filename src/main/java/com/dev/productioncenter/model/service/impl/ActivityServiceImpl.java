package com.dev.productioncenter.model.service.impl;

import com.dev.productioncenter.entity.Activity;
import com.dev.productioncenter.exception.DaoException;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.dao.ActivityDao;
import com.dev.productioncenter.model.dao.impl.ActivityDaoImpl;
import com.dev.productioncenter.model.service.ActivityService;
import com.dev.productioncenter.validator.impl.CourseValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

import static com.dev.productioncenter.controller.command.RequestParameter.*;

public class ActivityServiceImpl implements ActivityService {
    private static final Logger LOGGER = LogManager.getLogger();
    private final ActivityDao activityDao = ActivityDaoImpl.getInstance();

    @Override
    public boolean addActivity(Map<String, String> activityData) throws ServiceException {
        try {
            if (CourseValidatorImpl.getInstance().checkActivity(activityData)) {
                Activity activity = new Activity(activityData.get(CATEGORY), activityData.get(TYPE));
                if (!activityDao.findActivity(activity)) {
                    activityDao.add(activity);
                    return true;
                }
            }
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while adding activity: " + exception);
            throw new ServiceException("Error has occurred while adding activity: " + exception);
        }
        return false;
    }

    @Override
    public List<Activity> findActivities() throws ServiceException {
        try {
            return activityDao.findAll();
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding all activities: " + exception);
            throw new ServiceException("Error has occurred while finding all activities: " + exception);
        }
    }

    @Override
    public List<Activity> findActivities(String category) throws ServiceException {
        try {
            return activityDao.findActivitiesByCategory(category);
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding activities by category: " + exception);
            throw new ServiceException("Error has occurred while finding activities by category: " + exception);
        }
    }

    @Override
    public List<String> findCategories() throws ServiceException {
        try {
            return activityDao.findCategories();
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding all activities: " + exception);
            throw new ServiceException("Error has occurred while finding all activities: " + exception);
        }
    }
}