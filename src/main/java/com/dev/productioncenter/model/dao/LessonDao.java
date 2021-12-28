package com.dev.productioncenter.model.dao;

import com.dev.productioncenter.entity.Lesson;
import com.dev.productioncenter.exception.DaoException;

import java.sql.Time;
import java.util.List;

public interface LessonDao extends BaseDao<Lesson> {
    List<Lesson> findLessonsByCourse(long courseId) throws DaoException;

    List<Lesson> findLessonsByWeekDay(String weekDay) throws DaoException;

    List<Lesson> findLessonsByStartTime(Time startTime) throws DaoException;

    List<Lesson> findLessonsByDuration(int minDuration, int maxDuration) throws DaoException;
}
