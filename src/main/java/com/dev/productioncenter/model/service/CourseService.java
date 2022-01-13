package com.dev.productioncenter.model.service;

import com.dev.productioncenter.entity.Course;
import com.dev.productioncenter.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface CourseService {
    boolean addCourse(Map<String, String> courseData) throws ServiceException;

    List<Course> findCourses() throws ServiceException;

    List<Course> findAvailableCourses() throws ServiceException;

}
