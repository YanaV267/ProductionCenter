package com.dev.productioncenter.model.dao;

import com.dev.productioncenter.entity.*;
import com.dev.productioncenter.exception.DaoException;

import java.util.List;

public abstract class CourseDao extends BaseDao<Long, Course> {
    abstract public List<Course> findAll(int startElementNumber) throws DaoException;

    abstract public boolean updateCourseStudentAmount(long id, int studentAmount) throws DaoException;

    abstract public List<Course> findCourseByTeacher(User teacher) throws DaoException;

    abstract public List<Course> findCourseByAgeGroup(AgeGroup ageGroup) throws DaoException;

    abstract public List<Course> findCourseByActivity(Activity activity, int startElementNumber) throws DaoException;

    abstract public List<Course> findCourseByActivityCategory(Activity activity, int startElementNumber) throws DaoException;

    abstract public List<Course> findCourseByActivityType(Activity activity, int startElementNumber) throws DaoException;

    abstract public List<Course> findCourseByActivityWeekday(Activity activity, String weekday, int startElementNumber) throws DaoException;

    abstract public List<Course> findCourseByWeekday(String weekday) throws DaoException;

    abstract public List<Course> findAvailableCourses(int startElementNumber) throws DaoException;

    abstract public List<Course> findCoursesAllActivities() throws DaoException;

    abstract public List<Course> findCoursesAvailableActivities() throws DaoException;
}