package com.dev.productioncenter.model.dao;

import com.dev.productioncenter.entity.*;
import com.dev.productioncenter.exception.DaoException;

import java.util.List;

public abstract class CourseDao extends BaseDao<Long, Course> {
    abstract public boolean updateCourseStudentAmount(long id, int studentAmount) throws DaoException;

    abstract public List<Course> findCourseByTeacher(User teacher) throws DaoException;

    abstract public List<Course> findCourseByAgeGroup(AgeGroup ageGroup) throws DaoException;

    abstract public List<Course> findCourseByActivity(Activity activity) throws DaoException;

    abstract public List<Course> findCourseByActivityCategory(Activity activity) throws DaoException;

    abstract public List<Course> findCourseByActivityType(Activity activity) throws DaoException;

    abstract public List<Course> findCourseByActivityWeekday(Activity activity, String weekday) throws DaoException;

    abstract public List<Course> findCourseByWeekday(String weekday) throws DaoException;

    abstract public List<Course> findCourseByStatus(CourseStatus courseStatus) throws DaoException;

    abstract public List<Course> findAvailableCourses() throws DaoException;

    abstract public List<Course> findCoursesAllActivities() throws DaoException;

    abstract public List<Course> findCoursesAvailableActivities() throws DaoException;
}