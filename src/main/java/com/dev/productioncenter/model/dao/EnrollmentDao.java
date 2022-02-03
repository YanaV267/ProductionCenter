package com.dev.productioncenter.model.dao;

import com.dev.productioncenter.entity.Course;
import com.dev.productioncenter.entity.Enrollment;
import com.dev.productioncenter.entity.EnrollmentStatus;
import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.exception.DaoException;

import java.util.List;
import java.util.Optional;

/**
 * @project Production Center
 * @author YanaV
 * The type Enrollment dao.
 */
public abstract class EnrollmentDao extends BaseDao<Long, Enrollment> {
    /**
     * Update enrollment status boolean.
     *
     * @param enrollment the enrollment
     * @return the boolean
     * @throws DaoException the dao exception
     */
    abstract public boolean updateEnrollmentStatus(Enrollment enrollment) throws DaoException;

    /**
     * Find enrollments list.
     *
     * @param startElementNumber the start element number
     * @return the list
     * @throws DaoException the dao exception
     */
    abstract public List<Enrollment> findEnrollments(int startElementNumber) throws DaoException;

    /**
     * Find enrollments by user list.
     *
     * @param user               the user
     * @param startElementNumber the start element number
     * @return the list
     * @throws DaoException the dao exception
     */
    abstract public List<Enrollment> findEnrollmentsByUser(User user, int startElementNumber) throws DaoException;

    /**
     * Find enrollments by user list.
     *
     * @param user the user
     * @return the list
     * @throws DaoException the dao exception
     */
    abstract public List<Enrollment> findEnrollmentsByUser(User user) throws DaoException;

    /**
     * Find enrollments by course list.
     *
     * @param course             the course
     * @param startElementNumber the start element number
     * @return the list
     * @throws DaoException the dao exception
     */
    abstract public List<Enrollment> findEnrollmentsByCourse(Course course, int startElementNumber) throws DaoException;

    /**
     * Find enrollments by course user optional.
     *
     * @param user   the user
     * @param course the course
     * @return the optional
     * @throws DaoException the dao exception
     */
    abstract public Optional<Enrollment> findEnrollmentsByCourseUser(User user, Course course) throws DaoException;

    /**
     * Find enrollments by status list.
     *
     * @param status the status
     * @return the list
     * @throws DaoException the dao exception
     */
    abstract public List<Enrollment> findEnrollmentsByStatus(EnrollmentStatus status) throws DaoException;

    /**
     * Find expired enrollments list.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    abstract public List<Enrollment> findExpiredEnrollments() throws DaoException;
}
