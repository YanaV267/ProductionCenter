package com.dev.productioncenter.model.service;

import com.dev.productioncenter.entity.Enrollment;
import com.dev.productioncenter.entity.EnrollmentStatus;
import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.exception.ServiceException;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

public interface EnrollmentService {
    boolean enrollOnCourse(User user, long chosenCourseId, String lessonAmount) throws ServiceException;

    Optional<Enrollment> findEnrollment(long chosenCourseId) throws ServiceException;

    Optional<Enrollment> findEnrollment(User user, long chosenCourseId) throws ServiceException;

    Map<Enrollment, LocalDate> findEnrollment(User user) throws ServiceException;

    Map<Enrollment, LocalDate> findEnrollment() throws ServiceException;

    boolean updateStatus(Map<String, EnrollmentStatus> enrollmentStatuses) throws ServiceException;

    boolean updateStatus(Enrollment enrollment) throws ServiceException;

    boolean updateLessonAmounts(Map<String, String> enrollmentsLessonAmount) throws ServiceException;

    boolean cancelEnrollment(long enrollmentId) throws ServiceException;
}
