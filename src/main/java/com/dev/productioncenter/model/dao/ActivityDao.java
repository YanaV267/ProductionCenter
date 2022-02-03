package com.dev.productioncenter.model.dao;

import com.dev.productioncenter.entity.*;
import com.dev.productioncenter.exception.DaoException;

import java.util.List;

/**
 * @project Production Center
 * @author YanaV
 * The type Activity dao.
 */
public abstract class ActivityDao extends BaseDao<Long, Activity> {
    /**
     * Find activity boolean.
     *
     * @param activity the activity
     * @return the boolean
     * @throws DaoException the dao exception
     */
    abstract public boolean findActivity(Activity activity) throws DaoException;

    /**
     * Find activity id long.
     *
     * @param activity the activity
     * @return the long
     * @throws DaoException the dao exception
     */
    abstract public long findActivityId(Activity activity) throws DaoException;

    /**
     * Find categories list.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    abstract public List<String> findCategories() throws DaoException;
}