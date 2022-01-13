package com.dev.productioncenter.model.dao;

import com.dev.productioncenter.entity.*;
import com.dev.productioncenter.exception.DaoException;

import java.util.List;

public interface ActivityDao extends BaseDao<Activity> {
    boolean findActivity(Activity activity) throws DaoException;

    long findActivityId(Activity activity) throws DaoException;

    List<Activity> findActivitiesByCategory(String category) throws DaoException;

    List<String> findCategories() throws DaoException;
}