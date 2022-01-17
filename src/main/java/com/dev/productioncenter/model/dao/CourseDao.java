package com.dev.productioncenter.model.dao;

import com.dev.productioncenter.entity.*;
import com.dev.productioncenter.exception.DaoException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CourseDao extends BaseDao<Course> {
    List<Course> findCourseByTeacher(User teacher) throws DaoException;

    List<Course> findCourseByActivityCategory(Activity activity) throws DaoException;

    List<Course> findCourseByActivityType(Activity activity) throws DaoException;

    List<Course> findCourseByAgeGroup(AgeGroup ageGroup) throws DaoException;

    List<Course> findCourseByLessonPrice(BigDecimal lessonPrice) throws DaoException;

    List<Course> findCourseByStudentAmount(int studentAmount) throws DaoException;

    List<Course> findCourseByStatus(CourseStatus courseStatus) throws DaoException;

    List<Course> findAvailableCourses() throws DaoException;

    Optional<Course> findChosenCourse(Activity activity, User teacher, AgeGroup ageGroup) throws DaoException;
}