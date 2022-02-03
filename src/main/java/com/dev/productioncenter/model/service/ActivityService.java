package com.dev.productioncenter.model.service;

import com.dev.productioncenter.entity.Activity;
import com.dev.productioncenter.exception.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * @project Production Center
 * @author YanaV
 * The interface Activity service.
 */
public interface ActivityService {
    /**
     * Add activity boolean.
     *
     * @param activity the activity
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean addActivity(Map<String, String> activity) throws ServiceException;

    /**
     * Find activities list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Activity> findActivities() throws ServiceException;

    /**
     * Find categories list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<String> findCategories() throws ServiceException;
}
