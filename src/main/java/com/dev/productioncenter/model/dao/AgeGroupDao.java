package com.dev.productioncenter.model.dao;

import com.dev.productioncenter.entity.AgeGroup;
import com.dev.productioncenter.exception.DaoException;

import java.util.Optional;

public interface AgeGroupDao extends BaseDao<Long, AgeGroup> {
    Optional<Long> findByMinMaxAge(AgeGroup ageGroup) throws DaoException;
}
