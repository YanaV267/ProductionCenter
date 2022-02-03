package com.dev.productioncenter.model.service;

import com.dev.productioncenter.entity.Lesson;
import com.dev.productioncenter.exception.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * @project Production Center
 * @author YanaV
 * The interface Lesson service.
 */
public interface LessonService {
    /**
     * Add lessons boolean.
     *
     * @param lessonData the lesson data
     * @param courseId   the course id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean addLessons(Map<String, String> lessonData, long courseId) throws ServiceException;

    /**
     * Update lessons boolean.
     *
     * @param lessonData the lesson data
     * @param courseId   the course id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateLessons(Map<String, String> lessonData, long courseId) throws ServiceException;

    /**
     * Find lessons list.
     *
     * @param courseId the course id
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Lesson> findLessons(long courseId) throws ServiceException;
}
