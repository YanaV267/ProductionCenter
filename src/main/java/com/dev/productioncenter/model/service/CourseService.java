package com.dev.productioncenter.model.service;

import com.dev.productioncenter.entity.Activity;
import com.dev.productioncenter.entity.Course;
import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @project Production Center
 * @author YanaV
 * The interface Course service.
 */
public interface CourseService {
    /**
     * Add course boolean.
     *
     * @param courseData the course data
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean addCourse(Map<String, String> courseData) throws ServiceException;

    /**
     * Update course boolean.
     *
     * @param courseData the course data
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateCourse(Map<String, String> courseData) throws ServiceException;

    /**
     * Find courses list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Course> findCourses() throws ServiceException;

    /**
     * Find courses list.
     *
     * @param teacher the teacher
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Course> findCourses(User teacher) throws ServiceException;

    /**
     * Find courses list.
     *
     * @param page the page
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Course> findCourses(int page) throws ServiceException;

    /**
     * Find courses list.
     *
     * @param activity the activity
     * @param weekdays the weekdays
     * @param page     the page
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Course> findCourses(Activity activity, String[] weekdays, int page) throws ServiceException;

    /**
     * Find available courses list.
     *
     * @param page the page
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Course> findAvailableCourses(int page) throws ServiceException;

    /**
     * Find courses available activities list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Course> findCoursesAvailableActivities() throws ServiceException;

    /**
     * Find courses all activities list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Course> findCoursesAllActivities() throws ServiceException;

    /**
     * Find course optional.
     *
     * @param id the id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Course> findCourse(long id) throws ServiceException;

    /**
     * Reserve place at course boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean reservePlaceAtCourse(long id) throws ServiceException;

    /**
     * Release place at course boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean releasePlaceAtCourse(long id) throws ServiceException;

}
