package com.dev.productioncenter.model.service.impl;

import com.dev.productioncenter.entity.*;
import com.dev.productioncenter.exception.DaoException;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.dao.EnrollmentDao;
import com.dev.productioncenter.model.dao.LessonDao;
import com.dev.productioncenter.model.dao.impl.EnrollmentDaoImpl;
import com.dev.productioncenter.model.dao.impl.LessonDaoImpl;
import com.dev.productioncenter.model.service.EnrollmentService;
import com.dev.productioncenter.validator.impl.EnrollmentValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EnrollmentServiceImpl implements EnrollmentService {
    private static final Logger LOGGER = LogManager.getLogger();
    private final EnrollmentDao enrollmentDao = EnrollmentDaoImpl.getInstance();
    private final LessonDao lessonDao = LessonDaoImpl.getInstance();

    @Override
    public boolean enrollOnCourse(User user, long chosenCourseId, String lessonAmount) throws ServiceException {
        try {
            if (EnrollmentValidatorImpl.getInstance().checkLessonAmount(lessonAmount)) {
                Enrollment enrollment = new Enrollment.EnrollmentBuilder()
                        .setCourse(new Course(chosenCourseId))
                        .setUser(user)
                        .setLessonAmount(Integer.parseInt(lessonAmount))
                        .build();
                enrollmentDao.add(enrollment);
                return true;
            }
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while adding course's lessons: " + exception);
            throw new ServiceException("Error has occurred while adding course's lessons: " + exception);
        }
        return false;
    }

    @Override
    public boolean findEnrollments(User user, long chosenCourseId) throws ServiceException {
        try {
            Optional<Enrollment> enrollment = enrollmentDao.findEnrollmentsByCourseUser(user, new Course(chosenCourseId));
            return enrollment.isPresent();
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while adding course's lessons: " + exception);
            throw new ServiceException("Error has occurred while adding course's lessons: " + exception);
        }
    }

    @Override
    public List<Enrollment> findEnrollments(User user) throws ServiceException {
        try {
            List<Enrollment> enrollments = enrollmentDao.findEnrollmentsByUser(user);
            for (Enrollment enrollment : enrollments) {
                List<Lesson> lessons = lessonDao.findLessonsByCourse(enrollment.getCourse().getId());
                enrollment.getCourse().setLessons(lessons);
            }
            return enrollments;
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding user's enrollments: " + exception);
            throw new ServiceException("Error has occurred while finding user's enrollments: " + exception);
        }
    }

    @Override
    public Map<Enrollment, LocalDate> findEnrollments() throws ServiceException {
        try {
            List<Enrollment> allEnrollments = enrollmentDao.findAll();
            Map<Enrollment, LocalDate> enrollments = new HashMap<>();
            for (Enrollment enrollment : allEnrollments) {
                enrollments.put(enrollment, enrollment.getReservationDateTime().toLocalDate());
            }
            return enrollments;
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding all enrollments: " + exception);
            throw new ServiceException("Error has occurred while finding all enrollments: " + exception);
        }
    }

    @Override
    public boolean updateStatuses(Map<String, EnrollmentStatus> enrollmentStatuses) throws ServiceException {
        try {
            for (Map.Entry<String, EnrollmentStatus> enrollmentStatus : enrollmentStatuses.entrySet()) {
                Enrollment enrollment = new Enrollment();
                enrollment.setEnrollmentStatus(enrollmentStatus.getValue());
                enrollment.setId(Long.parseLong(enrollmentStatus.getKey()));
                if (!enrollmentDao.updateEnrollmentStatus(enrollment)) {
                    return false;
                }
            }
            return true;
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while changing enrollments' statuses: " + exception);
            throw new ServiceException("Error has occurred while changing enrollments' statuses: " + exception);
        }
    }
}
