package com.dev.productioncenter.model.dao;

import com.dev.productioncenter.entity.*;
import com.dev.productioncenter.exception.DaoException;

import java.util.List;

public abstract class ActivityDao extends BaseDao<Long, Activity> {
    abstract public boolean findActivity(Activity activity) throws DaoException;

    abstract public long findActivityId(Activity activity) throws DaoException;

    abstract public List<String> findCategories() throws DaoException;
}