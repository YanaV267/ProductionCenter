package com.dev.productioncenter.model.dao;

import com.dev.productioncenter.entity.*;
import com.dev.productioncenter.exception.DaoException;

import java.util.List;

/**
 * @project Production Center
 * @author YanaV
 * The type Course dao.
 */
public abstract class CourseDao extends BaseDao<Long, Course> {
    /**
     * Find all list.
     *
     * @param startElementNumber the start element number
     * @return the list
     * @throws DaoException the dao exception
     */
    abstract public List<Course> findAll(int startElementNumber) throws DaoException;

    /**
     * Update course student amount boolean.
     *
     * @param id            the id
     * @param studentAmount the student amount
     * @return the boolean
     * @throws DaoException the dao exception
     */
    abstract public boolean updateCourseStudentAmount(long id, int studentAmount) throws DaoException;

    /**
     * Find course by teacher list.
     *
     * @param teacher the teacher
     * @return the list
     * @throws DaoException the dao exception
     */
    abstract public List<Course> findCourseByTeacher(User teacher) throws DaoException;

    /**
     * Find course by age group list.
     *
     * @param ageGroup the age group
     * @return the list
     * @throws DaoException the dao exception
     */
    abstract public List<Course> findCourseByAgeGroup(AgeGroup ageGroup) throws DaoException;

    /**
     * Find course by activity list.
     *
     * @param activity           the activity
     * @param startElementNumber the start element number
     * @return the list
     * @throws DaoException the dao exception
     */
    abstract public List<Course> findCourseByActivity(Activity activity, int startElementNumber) throws DaoException;

    /**
     * Find course by activity category list.
     *
     * @param activity           the activity
     * @param startElementNumber the start element number
     * @return the list
     * @throws DaoException the dao exception
     */
    abstract public List<Course> findCourseByActivityCategory(Activity activity, int startElementNumber) throws DaoException;

    /**
     * Find course by activity type list.
     *
     * @param activity           the activity
     * @param startElementNumber the start element number
     * @return the list
     * @throws DaoException the dao exception
     */
    abstract public List<Course> findCourseByActivityType(Activity activity, int startElementNumber) throws DaoException;

    /**
     * Find course by activity weekday list.
     *
     * @param activity           the activity
     * @param weekday            the weekday
     * @param startElementNumber the start element number
     * @return the list
     * @throws DaoException the dao exception
     */
    abstract public List<Course> findCourseByActivityWeekday(Activity activity, String weekday, int startElementNumber) throws DaoException;

    /**
     * Find course by weekday list.
     *
     * @param weekday the weekday
     * @return the list
     * @throws DaoException the dao exception
     */
    abstract public List<Course> findCourseByWeekday(String weekday) throws DaoException;

    /**
     * Find available courses list.
     *
     * @param startElementNumber the start element number
     * @return the list
     * @throws DaoException the dao exception
     */
    abstract public List<Course> findAvailableCourses(int startElementNumber) throws DaoException;

    /**
     * Find courses all activities list.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    abstract public List<Course> findCoursesAllActivities() throws DaoException;

    /**
     * Find courses available activities list.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    abstract public List<Course> findCoursesAvailableActivities() throws DaoException;
}