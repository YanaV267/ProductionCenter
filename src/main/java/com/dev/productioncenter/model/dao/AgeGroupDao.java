package com.dev.productioncenter.model.dao;

import com.dev.productioncenter.entity.AgeGroup;
import com.dev.productioncenter.exception.DaoException;

import java.util.Optional;

/**
 * @project Production Center
 * @author YanaV
 * The type Age group dao.
 */
public abstract class AgeGroupDao extends BaseDao<Long, AgeGroup> {
    /**
     * Find by min max age optional.
     *
     * @param ageGroup the age group
     * @return the optional
     * @throws DaoException the dao exception
     */
    abstract public Optional<Long> findByMinMaxAge(AgeGroup ageGroup) throws DaoException;
}
