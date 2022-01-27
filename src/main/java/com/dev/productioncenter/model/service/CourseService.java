package com.dev.productioncenter.model.service;

import com.dev.productioncenter.entity.Activity;
import com.dev.productioncenter.entity.Course;
import com.dev.productioncenter.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CourseService {
    boolean addCourse(Map<String, String> courseData) throws ServiceException;

    boolean updateCourse(Map<String, String> courseData) throws ServiceException;

    List<Course> findCourses() throws ServiceException;

    List<Course> findCourses(Activity activity, String[] weekdays) throws ServiceException;

    List<Course> findAvailableCourses() throws ServiceException;

    List<Course> findCoursesAvailableActivities() throws ServiceException;

    List<Course> findCoursesAllActivities() throws ServiceException;

    Optional<Course> findCourse(long id) throws ServiceException;

    boolean reservePlaceAtCourse(long id) throws ServiceException;

    boolean releasePlaceAtCourse(long id) throws ServiceException;
}
