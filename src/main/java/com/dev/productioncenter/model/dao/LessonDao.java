package com.dev.productioncenter.model.dao;

import com.dev.productioncenter.entity.Lesson;
import com.dev.productioncenter.exception.DaoException;

import java.sql.Time;
import java.util.List;
import java.util.Optional;

public abstract class LessonDao extends BaseDao<Long, Lesson> {
    abstract public List<Lesson> findLessonsByCourse(long courseId) throws DaoException;

    abstract public Optional<Lesson> findLessonsByCourseWeekDay(String weekDay, long courseId) throws DaoException;
}
