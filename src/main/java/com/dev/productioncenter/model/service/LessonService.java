package com.dev.productioncenter.model.service;

import com.dev.productioncenter.entity.Lesson;
import com.dev.productioncenter.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface LessonService {
    boolean addLessons(Map<String, String> lessonData, long courseId) throws ServiceException;

    List<Lesson> findLessons(long courseId) throws ServiceException;
}
