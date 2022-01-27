package com.dev.productioncenter.model.dao;

import com.dev.productioncenter.entity.AgeGroup;
import com.dev.productioncenter.exception.DaoException;

import java.util.Optional;

public abstract class AgeGroupDao extends BaseDao<Long, AgeGroup> {
    abstract public Optional<Long> findByMinMaxAge(AgeGroup ageGroup) throws DaoException;
}
