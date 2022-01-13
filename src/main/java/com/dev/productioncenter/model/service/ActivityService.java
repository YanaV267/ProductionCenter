package com.dev.productioncenter.model.service;

import com.dev.productioncenter.entity.Activity;
import com.dev.productioncenter.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    boolean addActivity(Map<String, String> activity) throws ServiceException;

    List<Activity> findActivities() throws ServiceException;

    List<Activity> findActivities(String category) throws ServiceException;

    List<String> findCategories() throws ServiceException;
}
