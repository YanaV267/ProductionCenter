package com.dev.productioncenter.model.dao;

import com.dev.productioncenter.entity.Course;
import com.dev.productioncenter.entity.Enrollment;
import com.dev.productioncenter.entity.EnrollmentStatus;
import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.exception.DaoException;

import java.util.List;
import java.util.Optional;

public abstract class EnrollmentDao extends BaseDao<Long, Enrollment> {
    abstract public boolean updateEnrollmentStatus(Enrollment enrollment) throws DaoException;

    abstract public List<Enrollment> findEnrollmentsByUser(User user) throws DaoException;

    abstract public List<Enrollment> findEnrollmentsByCourse(Course course) throws DaoException;

    abstract public Optional<Enrollment> findEnrollmentsByCourseUser(User user, Course course) throws DaoException;

    abstract public List<Enrollment> findEnrollmentsByStatus(EnrollmentStatus status) throws DaoException;

    abstract public List<Enrollment> findExpiredEnrollments() throws DaoException;
}
