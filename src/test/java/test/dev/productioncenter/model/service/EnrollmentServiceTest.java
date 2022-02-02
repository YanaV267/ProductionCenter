package test.dev.productioncenter.model.service;

import com.dev.productioncenter.entity.*;
import com.dev.productioncenter.exception.DaoException;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.dao.DaoProvider;
import com.dev.productioncenter.model.dao.EnrollmentDao;
import com.dev.productioncenter.model.dao.LessonDao;
import com.dev.productioncenter.model.dao.impl.EnrollmentDaoImpl;
import com.dev.productioncenter.model.service.CourseService;
import com.dev.productioncenter.model.service.EnrollmentService;
import com.dev.productioncenter.model.service.impl.CourseServiceImpl;
import com.dev.productioncenter.model.service.impl.EnrollmentServiceImpl;
import com.dev.productioncenter.validator.EnrollmentValidator;
import com.dev.productioncenter.validator.impl.EnrollmentValidatorImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class EnrollmentServiceTest {
    private MockedStatic<EnrollmentDaoImpl> enrollmentDaoImpl;
    private MockedStatic<EnrollmentValidatorImpl> enrollmentValidator;
    private MockedStatic<CourseServiceImpl> courseServiceImpl;
    private MockedStatic<DaoProvider> daoProviderHolder;
    @Mock
    private EnrollmentDao enrollmentDao;
    @Mock
    private LessonDao lessonDao;
    @Mock
    private DaoProvider daoProvider;
    @Mock
    private EnrollmentValidator validator;
    @Mock
    private CourseService courseService;
    @InjectMocks
    private EnrollmentService enrollmentService = EnrollmentServiceImpl.getInstance();

    @BeforeClass
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeTest
    public void initStatic() {
        enrollmentDaoImpl = mockStatic(EnrollmentDaoImpl.class);
        enrollmentValidator = mockStatic(EnrollmentValidatorImpl.class);
        courseServiceImpl = mockStatic(CourseServiceImpl.class);
        daoProviderHolder = mockStatic(DaoProvider.class);
    }

    @Test
    public void enrollOnCourse() throws ServiceException, DaoException {
        enrollmentDaoImpl.when(EnrollmentDaoImpl::getInstance).thenReturn(enrollmentDao);
        enrollmentValidator.when(EnrollmentValidatorImpl::getInstance).thenReturn(validator);

        when(validator.checkLessonAmount(anyString())).thenReturn(true);
        when(enrollmentDao.add(any(Enrollment.class))).thenReturn(Long.valueOf(5));

        long courseId = 7;
        String lessonAmount = "20";
        boolean actual = enrollmentService.enrollOnCourse(new User(), courseId, lessonAmount);
        Assert.assertTrue(actual);
    }

    @Test
    public void findEnrollment() throws ServiceException, DaoException {
        enrollmentDaoImpl.when(EnrollmentDaoImpl::getInstance).thenReturn(enrollmentDao);

        when(enrollmentDao.findById(anyLong())).thenReturn(Optional.of(new Enrollment()));

        long enrollmentId = 5;
        Optional<Enrollment> actual = enrollmentService.findEnrollment(enrollmentId);
        Assert.assertTrue(actual.isPresent());
    }

    @Test
    public void findEnrollmentByCourse() throws ServiceException, DaoException {
        enrollmentDaoImpl.when(EnrollmentDaoImpl::getInstance).thenReturn(enrollmentDao);

        when(enrollmentDao.findEnrollmentsByCourseUser(any(User.class), any(Course.class))).thenReturn(Optional.of(new Enrollment()));

        long courseId = 8;
        Optional<Enrollment> actual = enrollmentService.findEnrollment(new User(), courseId);
        Assert.assertTrue(actual.isPresent());
    }

    @Test
    public void findEnrollmentsByUser() throws ServiceException, DaoException {
        enrollmentDaoImpl.when(EnrollmentDaoImpl::getInstance).thenReturn(enrollmentDao);
        daoProviderHolder.when(DaoProvider::getInstance).thenReturn(daoProvider);
        when(daoProvider.getLessonDao(false)).thenReturn(lessonDao);

        when(enrollmentDao.findEnrollmentsByUser(any(User.class))).thenReturn(List.of(
                new Enrollment.EnrollmentBuilder()
                        .setReservationDateTime(LocalDateTime.now())
                        .build()));
        when(lessonDao.findLessonsByCourse(anyLong())).thenReturn(List.of(new Lesson(), new Lesson()));
        doNothing().when(lessonDao).closeConnection();

        Map<Enrollment, LocalDate> actual = enrollmentService.findEnrollments(new User());
        Assert.assertFalse(actual.isEmpty());
    }

    @Test
    public void findEnrollments() throws ServiceException, DaoException {
        enrollmentDaoImpl.when(EnrollmentDaoImpl::getInstance).thenReturn(enrollmentDao);

        when(enrollmentDao.findEnrollments(anyInt())).thenReturn(List.of());

        int page = 2;
        Map<Enrollment, LocalDate> actual = enrollmentService.findEnrollments(page);
        Assert.assertTrue(actual.isEmpty());
    }

    @Test
    public void findEnrollmentsByCourse() throws ServiceException, DaoException {
        enrollmentDaoImpl.when(EnrollmentDaoImpl::getInstance).thenReturn(enrollmentDao);

        when(enrollmentDao.findEnrollmentsByCourse(any(Course.class), anyInt())).thenReturn(List.of(
                new Enrollment.EnrollmentBuilder()
                        .setReservationDateTime(LocalDateTime.now())
                        .build()));

        long courseId = 4;
        int page = 1;
        Map<Enrollment, LocalDate> actual = enrollmentService.findEnrollments(courseId, page);
        Assert.assertFalse(actual.isEmpty());
    }

    @Test(dataProvider = "enrollmentStatuses")
    public void updateStatus(Map<String, EnrollmentStatus> enrollmentStatuses) throws ServiceException, DaoException {
        enrollmentDaoImpl.when(EnrollmentDaoImpl::getInstance).thenReturn(enrollmentDao);

        when(enrollmentDao.updateEnrollmentStatus(any(Enrollment.class))).thenReturn(false);

        boolean actual = enrollmentService.updateStatus(enrollmentStatuses);
        Assert.assertFalse(actual);
    }

    @Test
    public void updateStatus() throws ServiceException, DaoException {
        enrollmentDaoImpl.when(EnrollmentDaoImpl::getInstance).thenReturn(enrollmentDao);

        when(enrollmentDao.updateEnrollmentStatus(any(Enrollment.class))).thenReturn(true);

        long enrollmentId = 6;
        boolean actual = enrollmentService.updateStatus(enrollmentId, EnrollmentStatus.PAID);
        Assert.assertTrue(actual);
    }

    @Test
    public void updateLessonAmounts() throws ServiceException, DaoException {
        enrollmentDaoImpl.when(EnrollmentDaoImpl::getInstance).thenReturn(enrollmentDao);
        enrollmentValidator.when(EnrollmentValidatorImpl::getInstance).thenReturn(validator);

        when(validator.checkLessonAmount(anyMap())).thenReturn(true);
        when(enrollmentDao.update(any(Enrollment.class))).thenReturn(true);

        Map<String, String> enrollmentsLessonAmount = Map.of("4", "14", "9", "8");
        boolean actual = enrollmentService.updateLessonAmounts(enrollmentsLessonAmount);
        Assert.assertTrue(actual);
    }

    @Test
    public void cancelEnrollment() throws ServiceException, DaoException {
        enrollmentDaoImpl.when(EnrollmentDaoImpl::getInstance).thenReturn(enrollmentDao);

        when(enrollmentDao.delete(anyLong())).thenReturn(false);

        long enrollmentId = 10;
        boolean actual = enrollmentService.cancelEnrollment(enrollmentId);
        Assert.assertTrue(actual);
    }

    @Test
    public void checkEnrollmentsReservationStatus() throws ServiceException, DaoException {
        courseServiceImpl.when(CourseServiceImpl::getInstance).thenReturn(courseService);
        enrollmentDaoImpl.when(EnrollmentDaoImpl::getInstance).thenReturn(enrollmentDao);
        enrollmentValidator.when(EnrollmentValidatorImpl::getInstance).thenReturn(validator);

        when(validator.checkLessonAmount(anyString())).thenReturn(true);
        when(enrollmentDao.findExpiredEnrollments()).thenReturn(List.of());
        when(enrollmentDao.delete(anyLong())).thenReturn(true);
        when(enrollmentDao.updateEnrollmentStatus(any(Enrollment.class))).thenReturn(true);
        when(courseService.releasePlaceAtCourse(anyLong())).thenReturn(true);

        boolean actual = enrollmentService.checkEnrollmentsReservationStatus();
        Assert.assertTrue(actual);
    }

    @DataProvider(name = "enrollmentStatuses")
    public Object[][] getEnrollmentStatuses() {
        return new Object[][]{
                {Map.of("1", EnrollmentStatus.APPROVED, "2", EnrollmentStatus.PAID, "3", EnrollmentStatus.APPROVED)}
        };
    }

}