package com.dev.productioncenter.model.service;

import com.dev.productioncenter.entity.Course;
import com.dev.productioncenter.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CourseService {
    boolean addCourse(Map<String, String> courseData) throws ServiceException;

    List<Course> findCourses() throws ServiceException;

    List<Course> findAvailableCourses() throws ServiceException;

    Optional<Course> findCourse(Map<String, String> chosenCourseData) throws ServiceException;
}
