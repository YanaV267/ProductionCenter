package com.dev.productioncenter.model.service;

import com.dev.productioncenter.entity.Enrollment;
import com.dev.productioncenter.entity.EnrollmentStatus;
import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.entity.UserStatus;
import com.dev.productioncenter.exception.ServiceException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface EnrollmentService {
    boolean enrollOnCourse(User user, long chosenCourseId, String lessonAmount) throws ServiceException;

    boolean findEnrollments(User user, long chosenCourseId) throws ServiceException;

    List<Enrollment> findEnrollments(User user) throws ServiceException;

    Map<Enrollment, LocalDate> findEnrollments() throws ServiceException;

    boolean updateStatuses(Map<String, EnrollmentStatus> enrollmentStatuses) throws ServiceException;
}
