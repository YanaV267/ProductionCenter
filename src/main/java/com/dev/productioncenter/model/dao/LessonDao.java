package com.dev.productioncenter.model.dao;

import com.dev.productioncenter.entity.Lesson;
import com.dev.productioncenter.exception.DaoException;

import java.sql.Time;
import java.util.List;
import java.util.Optional;

/**
 * @project Production Center
 * @author YanaV
 * The type Lesson dao.
 */
public abstract class LessonDao extends BaseDao<Long, Lesson> {
    /**
     * Find lessons by course list.
     *
     * @param courseId the course id
     * @return the list
     * @throws DaoException the dao exception
     */
    abstract public List<Lesson> findLessonsByCourse(long courseId) throws DaoException;

    /**
     * Find lessons by course week day optional.
     *
     * @param weekDay  the week day
     * @param courseId the course id
     * @return the optional
     * @throws DaoException the dao exception
     */
    abstract public Optional<Lesson> findLessonsByCourseWeekDay(String weekDay, long courseId) throws DaoException;
}
