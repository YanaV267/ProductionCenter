package com.dev.productioncenter.model.service.impl;

import com.dev.productioncenter.entity.*;
import com.dev.productioncenter.exception.DaoException;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.dao.DaoProvider;
import com.dev.productioncenter.model.dao.EnrollmentDao;
import com.dev.productioncenter.model.dao.LessonDao;
import com.dev.productioncenter.model.dao.impl.EnrollmentDaoImpl;
import com.dev.productioncenter.model.service.CourseService;
import com.dev.productioncenter.model.service.EnrollmentService;
import com.dev.productioncenter.validator.EnrollmentValidator;
import com.dev.productioncenter.validator.impl.EnrollmentValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.dev.productioncenter.entity.EnrollmentStatus.*;

/**
 * @author YanaV
 * The type Enrollment service.
 * @project Production Center
 */
public class EnrollmentServiceImpl implements EnrollmentService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final EnrollmentService instance = new EnrollmentServiceImpl();

    private EnrollmentServiceImpl() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static EnrollmentService getInstance() {
        return instance;
    }

    @Override
    public boolean enrollOnCourse(User user, long chosenCourseId, String lessonAmount) throws ServiceException {
        EnrollmentDao enrollmentDao = EnrollmentDaoImpl.getInstance();
        EnrollmentValidator validator = EnrollmentValidatorImpl.getInstance();
        try {
            if (validator.checkLessonAmount(lessonAmount)) {
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
            throw new ServiceException("Error has occurred while adding course's lessons: ", exception);
        }
        return false;
    }

    @Override
    public Optional<Enrollment> findEnrollment(long enrollmentId) throws ServiceException {
        EnrollmentDao enrollmentDao = EnrollmentDaoImpl.getInstance();
        try {
            return enrollmentDao.findById(enrollmentId);
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding enrollment by id: " + exception);
            throw new ServiceException("Error has occurred while finding enrollment by id: ", exception);
        }
    }

    @Override
    public Optional<Enrollment> findEnrollment(User user, long chosenCourseId) throws ServiceException {
        EnrollmentDao enrollmentDao = EnrollmentDaoImpl.getInstance();
        try {
            return enrollmentDao.findEnrollmentsByCourseUser(user, new Course(chosenCourseId));
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding enrollment by user & course: " + exception);
            throw new ServiceException("Error has occurred while finding enrollment by user & course: ", exception);
        }
    }

    @Override
    public Map<Enrollment, LocalDate> findEnrollments(User user, int page) throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        LessonDao lessonDao = daoProvider.getLessonDao(false);
        EnrollmentDao enrollmentDao = EnrollmentDaoImpl.getInstance();
        try {
            int startElementNumber = page * 15 - 15;
            List<Enrollment> allEnrollments = enrollmentDao.findEnrollmentsByUser(user, startElementNumber);
            for (Enrollment enrollment : allEnrollments) {
                List<Lesson> lessons = lessonDao.findLessonsByCourse(enrollment.getCourse().getId());
                enrollment.getCourse().setLessons(lessons);
            }
            Map<Enrollment, LocalDate> enrollments = new HashMap<>();
            for (Enrollment enrollment : allEnrollments) {
                enrollments.put(enrollment, enrollment.getReservationDateTime().toLocalDate());
            }
            return enrollments;
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding user's enrollments: " + exception);
            throw new ServiceException("Error has occurred while finding user's enrollments: ", exception);
        } finally {
            lessonDao.closeConnection();
        }
    }

    @Override
    public Map<Enrollment, LocalDate> findEnrollments(User user) throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        LessonDao lessonDao = daoProvider.getLessonDao(false);
        EnrollmentDao enrollmentDao = EnrollmentDaoImpl.getInstance();
        try {
            List<Enrollment> allEnrollments = enrollmentDao.findEnrollmentsByUser(user);
            for (Enrollment enrollment : allEnrollments) {
                List<Lesson> lessons = lessonDao.findLessonsByCourse(enrollment.getCourse().getId());
                enrollment.getCourse().setLessons(lessons);
            }
            Map<Enrollment, LocalDate> enrollments = new HashMap<>();
            for (Enrollment enrollment : allEnrollments) {
                enrollments.put(enrollment, enrollment.getReservationDateTime().toLocalDate());
            }
            return enrollments;
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding user's enrollments: " + exception);
            throw new ServiceException("Error has occurred while finding user's enrollments: ", exception);
        } finally {
            lessonDao.closeConnection();
        }
    }

    @Override
    public Map<Enrollment, LocalDate> findEnrollments(String status, int page) throws ServiceException {
        EnrollmentDao enrollmentDao = EnrollmentDaoImpl.getInstance();
        try {
            if (status == null) {
                return findEnrollments(page);
            }
            int startElementNumber = page * 15 - 15;
            EnrollmentStatus enrollmentStatus = EnrollmentStatus.valueOf(status.toUpperCase());
            List<Enrollment> allEnrollments = enrollmentDao.findEnrollmentsByStatus(enrollmentStatus, startElementNumber);
            Map<Enrollment, LocalDate> enrollments = new HashMap<>();
            for (Enrollment enrollment : allEnrollments) {
                enrollments.put(enrollment, enrollment.getReservationDateTime().toLocalDate());
            }
            return enrollments;
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding enrollments by status: " + exception);
            throw new ServiceException("Error has occurred while finding enrollments by status: ", exception);
        }
    }

    @Override
    public Map<Enrollment, LocalDate> findEnrollments(int page) throws ServiceException {
        EnrollmentDao enrollmentDao = EnrollmentDaoImpl.getInstance();
        try {
            int startElementNumber = page * 15 - 15;
            List<Enrollment> allEnrollments = enrollmentDao.findEnrollments(startElementNumber);
            Map<Enrollment, LocalDate> enrollments = new HashMap<>();
            for (Enrollment enrollment : allEnrollments) {
                enrollments.put(enrollment, enrollment.getReservationDateTime().toLocalDate());
            }
            return enrollments;
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding all enrollments: " + exception);
            throw new ServiceException("Error has occurred while finding all enrollments: ", exception);
        }
    }

    @Override
    public Map<Enrollment, LocalDate> findEnrollments(long courseId, int page) throws ServiceException {
        EnrollmentDao enrollmentDao = EnrollmentDaoImpl.getInstance();
        try {
            int startElementNumber = page * 15 - 15;
            List<Enrollment> allEnrollments = enrollmentDao.findEnrollmentsByCourse(new Course(courseId), startElementNumber);
            Map<Enrollment, LocalDate> enrollments = new HashMap<>();
            for (Enrollment enrollment : allEnrollments) {
                enrollments.put(enrollment, enrollment.getReservationDateTime().toLocalDate());
            }
            return enrollments;
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding all enrollments on course: " + exception);
            throw new ServiceException("Error has occurred while finding all enrollments on course: ", exception);
        }
    }

    @Override
    public boolean updateStatus(Map<String, EnrollmentStatus> enrollmentStatuses) throws ServiceException {
        EnrollmentDao enrollmentDao = EnrollmentDaoImpl.getInstance();
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
            throw new ServiceException("Error has occurred while changing enrollments' statuses: ", exception);
        }
    }

    @Override
    public boolean updateStatus(long enrollmentId, EnrollmentStatus status) throws ServiceException {
        EnrollmentDao enrollmentDao = EnrollmentDaoImpl.getInstance();
        try {
            Enrollment enrollment = new Enrollment();
            enrollment.setId(enrollmentId);
            enrollment.setEnrollmentStatus(status);
            return enrollmentDao.updateEnrollmentStatus(enrollment);
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while changing enrollment's status: " + exception);
            throw new ServiceException("Error has occurred while changing enrollment's status: ", exception);
        }
    }

    @Override
    public boolean updateLessonAmounts(Map<String, String> enrollmentsLessonAmount) throws ServiceException {
        EnrollmentDao enrollmentDao = EnrollmentDaoImpl.getInstance();
        EnrollmentValidator validator = EnrollmentValidatorImpl.getInstance();
        try {
            if (validator.checkLessonAmount(enrollmentsLessonAmount)) {
                for (Map.Entry<String, String> enrollmentLessonAmount : enrollmentsLessonAmount.entrySet()) {
                    Enrollment enrollment = new Enrollment();
                    enrollment.setLessonAmount(Integer.parseInt(enrollmentLessonAmount.getValue()));
                    enrollment.setId(Long.parseLong(enrollmentLessonAmount.getKey()));
                    if (!enrollmentDao.update(enrollment)) {
                        return false;
                    }
                }
                return true;
            }
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while canceling enrollment: " + exception);
            throw new ServiceException("Error has occurred while canceling enrollment: ", exception);
        }
        return false;
    }

    @Override
    public boolean cancelEnrollment(long enrollmentId) throws ServiceException {
        EnrollmentDao enrollmentDao = EnrollmentDaoImpl.getInstance();
        try {
            return enrollmentDao.delete(enrollmentId);
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while canceling enrollment: " + exception);
            throw new ServiceException("Error has occurred while canceling enrollment: ", exception);
        }
    }

    @Override
    public boolean checkEnrollmentsReservationStatus() throws ServiceException {
        EnrollmentDao enrollmentDao = EnrollmentDaoImpl.getInstance();
        CourseService courseService = CourseServiceImpl.getInstance();
        try {
            List<Enrollment> expiredEnrollments = enrollmentDao.findExpiredEnrollments();
            for (Enrollment enrollment : expiredEnrollments) {
                if (enrollment.getReservationDateTime().plusDays(5).isBefore(LocalDateTime.now())) {
                    enrollmentDao.delete(enrollment.getId());
                    if (enrollment.getEnrollmentStatus() == RESERVED || enrollment.getEnrollmentStatus() == RENEWED) {
                        if (!courseService.releasePlaceAtCourse(enrollment.getCourse().getId())) {
                            return false;
                        }
                    }
                } else {
                    if (enrollment.getEnrollmentStatus() == RESERVED) {
                        enrollment.setEnrollmentStatus(EXPIRED);
                        if (!courseService.releasePlaceAtCourse(enrollment.getCourse().getId())
                                || !enrollmentDao.updateEnrollmentStatus(enrollment)) {
                            return false;
                        }
                    }
                }
            }
            return true;
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while checking enrollments reservation status: " + exception);
            throw new ServiceException("Error has occurred while checking enrollments reservation status: ", exception);
        }
    }
}
