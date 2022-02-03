package com.dev.productioncenter.model.service;

import com.dev.productioncenter.entity.Enrollment;
import com.dev.productioncenter.entity.EnrollmentStatus;
import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.exception.ServiceException;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

/**
 * @project Production Center
 * @author YanaV
 * The interface Enrollment service.
 */
public interface EnrollmentService {
    /**
     * Enroll on course boolean.
     *
     * @param user           the user
     * @param chosenCourseId the chosen course id
     * @param lessonAmount   the lesson amount
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean enrollOnCourse(User user, long chosenCourseId, String lessonAmount) throws ServiceException;

    /**
     * Find enrollment optional.
     *
     * @param enrollmentId the enrollment id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Enrollment> findEnrollment(long enrollmentId) throws ServiceException;

    /**
     * Find enrollment optional.
     *
     * @param user           the user
     * @param chosenCourseId the chosen course id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Enrollment> findEnrollment(User user, long chosenCourseId) throws ServiceException;

    /**
     * Find enrollments map.
     *
     * @param user the user
     * @param page the page
     * @return the map
     * @throws ServiceException the service exception
     */
    Map<Enrollment, LocalDate> findEnrollments(User user, int page) throws ServiceException;

    /**
     * Find enrollments map.
     *
     * @param user the user
     * @return the map
     * @throws ServiceException the service exception
     */
    Map<Enrollment, LocalDate> findEnrollments(User user) throws ServiceException;

    /**
     * Find enrollments map.
     *
     * @param page the page
     * @return the map
     * @throws ServiceException the service exception
     */
    Map<Enrollment, LocalDate> findEnrollments(int page) throws ServiceException;

    /**
     * Find enrollments map.
     *
     * @param courseId the course id
     * @param page     the page
     * @return the map
     * @throws ServiceException the service exception
     */
    Map<Enrollment, LocalDate> findEnrollments(long courseId, int page) throws ServiceException;

    /**
     * Update status boolean.
     *
     * @param enrollmentStatuses the enrollment statuses
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateStatus(Map<String, EnrollmentStatus> enrollmentStatuses) throws ServiceException;

    /**
     * Update status boolean.
     *
     * @param enrollmentId the enrollment id
     * @param status       the status
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateStatus(long enrollmentId, EnrollmentStatus status) throws ServiceException;

    /**
     * Update lesson amounts boolean.
     *
     * @param enrollmentsLessonAmount the enrollments lesson amount
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateLessonAmounts(Map<String, String> enrollmentsLessonAmount) throws ServiceException;

    /**
     * Cancel enrollment boolean.
     *
     * @param enrollmentId the enrollment id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean cancelEnrollment(long enrollmentId) throws ServiceException;

    /**
     * Check enrollments reservation status boolean.
     *
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean checkEnrollmentsReservationStatus() throws ServiceException;

}
