package com.dev.productioncenter.model.dao;

import com.dev.productioncenter.entity.*;
import com.dev.productioncenter.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface CourseDao extends BaseDao<Course> {
    Optional<Course> findCourseById(long id) throws DaoException;

    List<Course> findCourseByTeacher(User teacher) throws DaoException;

    List<Course> findCourseByAgeGroup(AgeGroup ageGroup) throws DaoException;

    List<Course> findCourseByActivity(Activity activity) throws DaoException;

    List<Course> findCourseByActivityCategory(Activity activity) throws DaoException;

    List<Course> findCourseByActivityType(Activity activity) throws DaoException;

    List<Course> findCourseByActivityWeekday(Activity activity, String weekday) throws DaoException;

    List<Course> findCourseByWeekday(String weekday) throws DaoException;

    List<Course> findCourseByStatus(CourseStatus courseStatus) throws DaoException;

    List<Course> findAvailableCourses() throws DaoException;
}