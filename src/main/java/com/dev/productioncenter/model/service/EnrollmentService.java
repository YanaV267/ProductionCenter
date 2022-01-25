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

    Optional<Enrollment> findEnrollment(long enrollmentId) throws ServiceException;

    Optional<Enrollment> findEnrollment(User user, long chosenCourseId) throws ServiceException;

    Map<Enrollment, LocalDate> findEnrollments(User user) throws ServiceException;

    Map<Enrollment, LocalDate> findEnrollments() throws ServiceException;

    Map<Enrollment, LocalDate> findEnrollments(long courseId) throws ServiceException;

    boolean updateStatus(Map<String, EnrollmentStatus> enrollmentStatuses) throws ServiceException;

    boolean updateStatus(long enrollmentId, EnrollmentStatus status) throws ServiceException;

    boolean updateLessonAmounts(Map<String, String> enrollmentsLessonAmount) throws ServiceException;

    boolean cancelEnrollment(long enrollmentId) throws ServiceException;

    boolean checkEnrollmentsReservationStatus() throws ServiceException;

}
