package com.dev.productioncenter.model.dao;

import com.dev.productioncenter.entity.Lesson;
import com.dev.productioncenter.exception.DaoException;

import java.sql.Time;
import java.util.List;
import java.util.Optional;

public interface LessonDao extends BaseDao<Long, Lesson> {
    List<Lesson> findLessonsByCourse(long courseId) throws DaoException;

    Optional<Lesson> findLessonsByCourseWeekDay(String weekDay, long courseId) throws DaoException;

    List<Lesson> findLessonsByStartTime(Time startTime) throws DaoException;

    List<Lesson> findLessonsByDuration(int minDuration, int maxDuration) throws DaoException;
}
