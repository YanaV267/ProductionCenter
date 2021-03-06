package com.dev.productioncenter.model.service.impl;

import com.dev.productioncenter.entity.Activity;
import com.dev.productioncenter.exception.DaoException;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.dao.ActivityDao;
import com.dev.productioncenter.model.dao.DaoProvider;
import com.dev.productioncenter.model.service.ActivityService;
import com.dev.productioncenter.validator.CourseValidator;
import com.dev.productioncenter.validator.impl.CourseValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

import static com.dev.productioncenter.controller.command.RequestParameter.CATEGORY;
import static com.dev.productioncenter.controller.command.RequestParameter.TYPE;

/**
 * @project Production Center
 * @author YanaV
 * The type Activity service.
 */
public class ActivityServiceImpl implements ActivityService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ActivityService instance = new ActivityServiceImpl();

    private ActivityServiceImpl() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ActivityService getInstance() {
        return instance;
    }

    @Override
    public boolean addActivity(Map<String, String> activityData) throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        ActivityDao activityDao = daoProvider.getActivityDao(false);
        CourseValidator validator = CourseValidatorImpl.getInstance();
        try {
            if (validator.checkActivity(activityData)) {
                Activity activity = new Activity(activityData.get(CATEGORY), activityData.get(TYPE));
                if (!activityDao.findActivity(activity)) {
                    activityDao.add(activity);
                    return true;
                }
            }
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while adding activity: " + exception);
            throw new ServiceException("Error has occurred while adding activity: ", exception);
        } finally {
            activityDao.closeConnection();
        }
        return false;
    }

    @Override
    public List<Activity> findActivities() throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        ActivityDao activityDao = daoProvider.getActivityDao(false);
        try {
            return activityDao.findAll();
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding all activities: " + exception);
            throw new ServiceException("Error has occurred while finding all activities: ", exception);
        } finally {
            activityDao.closeConnection();
        }
    }

    @Override
    public List<String> findCategories() throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        ActivityDao activityDao = daoProvider.getActivityDao(false);
        try {
            return activityDao.findCategories();
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding all categories: " + exception);
            throw new ServiceException("Error has occurred while finding all categories: ", exception);
        } finally {
            activityDao.closeConnection();
        }
    }
}