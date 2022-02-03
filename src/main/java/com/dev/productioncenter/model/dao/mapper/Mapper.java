package com.dev.productioncenter.model.dao.mapper;

import com.dev.productioncenter.entity.AbstractEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @project Production Center
 * @author YanaV
 * The interface Mapper.
 *
 * @param <T> the type parameter
 */
public interface Mapper<T extends AbstractEntity> {
    /**
     * Retrieve list.
     *
     * @param resultSet the result set
     * @return the list
     * @throws SQLException the sql exception
     */
    List<T> retrieve(ResultSet resultSet) throws SQLException;
}
