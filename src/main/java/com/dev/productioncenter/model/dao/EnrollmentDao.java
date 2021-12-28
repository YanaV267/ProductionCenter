package com.dev.productioncenter.model.dao;

import com.dev.productioncenter.entity.Course;
import com.dev.productioncenter.entity.Enrollment;
import com.dev.productioncenter.entity.EnrollmentStatus;
import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.exception.DaoException;

import java.sql.Date;
import java.util.List;

public interface EnrollmentDao extends BaseDao<Enrollment> {
    boolean checkEnrollmentReservationStatus() throws DaoException;

    boolean updateEnrollmentStatus(Enrollment enrollment) throws DaoException;

    List<Enrollment> findEnrollmentsByUser(User user) throws DaoException;

    List<Enrollment> findEnrollmentsByCourse(Course course) throws DaoException;

    List<Enrollment> findEnrollmentsByLessonAmount(int minAmount, int maxAmount) throws DaoException;

    List<Enrollment> findEnrollmentsByReservationDatetime(Date dateTime) throws DaoException;

    List<Enrollment> findEnrollmentsByStatus(EnrollmentStatus status) throws DaoException;

}
