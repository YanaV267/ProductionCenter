package test.dev.productioncenter.model.service;

import com.dev.productioncenter.entity.*;
import com.dev.productioncenter.exception.DaoException;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.dao.*;
import com.dev.productioncenter.model.service.CourseService;
import com.dev.productioncenter.model.service.LessonService;
import com.dev.productioncenter.model.service.impl.CourseServiceImpl;
import com.dev.productioncenter.model.service.impl.LessonServiceImpl;
import com.dev.productioncenter.validator.CourseValidator;
import com.dev.productioncenter.validator.impl.CourseValidatorImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.dev.productioncenter.controller.command.RequestParameter.*;
import static org.mockito.Mockito.*;

public class CourseServiceTest {
    private MockedStatic<CourseValidatorImpl> courseValidator;
    private MockedStatic<Transaction> transactionMocked;
    private MockedStatic<LessonServiceImpl> lessonServiceImpl;
    private MockedStatic<DaoProvider> daoProviderHolder;
    @Mock
    private CourseDao courseDao;
    @Mock
    private LessonDao lessonDao;
    @Mock
    private AgeGroupDao ageGroupDao;
    @Mock
    private ActivityDao activityDao;
    @Mock
    private UserDao userDao;
    @Mock
    private Transaction transaction;
    @Mock
    private DaoProvider daoProvider;
    @Mock
    private CourseValidator validator;
    @Mock
    private LessonService lessonService;
    @InjectMocks
    private CourseService courseService = CourseServiceImpl.getInstance();

    @BeforeClass
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeTest
    public void initStatic() {
        transactionMocked = mockStatic(Transaction.class);
        courseValidator = mockStatic(CourseValidatorImpl.class);
        lessonServiceImpl = mockStatic(LessonServiceImpl.class);
        daoProviderHolder = mockStatic(DaoProvider.class);
    }

    @Test(dataProvider = "courseData")
    public void addCourse(Map<String, String> courseData) throws ServiceException, DaoException {
        daoProviderHolder.when(DaoProvider::getInstance).thenReturn(daoProvider);
        lessonServiceImpl.when(LessonServiceImpl::getInstance).thenReturn(lessonService);
        courseValidator.when(CourseValidatorImpl::getInstance).thenReturn(validator);
        transactionMocked.when(Transaction::getInstance).thenReturn(transaction);
        when(daoProvider.getCourseDao(true)).thenReturn(courseDao);
        when(daoProvider.getAgeGroupDao(true)).thenReturn(ageGroupDao);
        when(daoProvider.getActivityDao(true)).thenReturn(activityDao);
        when(daoProvider.getUserDao(true)).thenReturn(userDao);

        when(validator.checkCourse(anyMap())).thenReturn(true);
        when(validator.checkActivity(anyMap())).thenReturn(true);
        when(ageGroupDao.findByMinMaxAge(any(AgeGroup.class))).thenReturn(Optional.empty());
        when(ageGroupDao.add(any(AgeGroup.class))).thenReturn(Long.valueOf(1));
        when(activityDao.findActivityId(any(Activity.class))).thenReturn(Long.valueOf(4));
        when(userDao.findTeacherByName(anyString(), anyString())).thenReturn(Optional.of(new User()));
        when(courseDao.add(any(Course.class))).thenReturn(Long.valueOf(9));
        when(lessonService.addLessons(anyMap(), anyLong())).thenReturn(true);
        doNothing().when(transaction).begin(ageGroupDao, userDao, courseDao);
        doNothing().when(transaction).commit();
        doNothing().when(transaction).rollback();
        doNothing().when(transaction).end();

        boolean actual = courseService.addCourse(courseData);
        Assert.assertTrue(actual);
    }

    @Test(dataProvider = "courseData")
    public void updateCourse(Map<String, String> courseData) throws ServiceException, DaoException {
        daoProviderHolder.when(DaoProvider::getInstance).thenReturn(daoProvider);
        lessonServiceImpl.when(LessonServiceImpl::getInstance).thenReturn(lessonService);
        courseValidator.when(CourseValidatorImpl::getInstance).thenReturn(validator);
        transactionMocked.when(Transaction::getInstance).thenReturn(transaction);
        when(daoProvider.getCourseDao(true)).thenReturn(courseDao);
        when(daoProvider.getAgeGroupDao(true)).thenReturn(ageGroupDao);
        when(daoProvider.getUserDao(true)).thenReturn(userDao);

        when(validator.checkCourse(anyMap())).thenReturn(true);
        when(ageGroupDao.findByMinMaxAge(any(AgeGroup.class))).thenReturn(Optional.empty());
        when(ageGroupDao.add(any(AgeGroup.class))).thenReturn(Long.valueOf(1));
        when(userDao.findTeacherByName(anyString(), anyString())).thenReturn(Optional.of(new User()));
        when(courseDao.update(any(Course.class))).thenReturn(true);
        when(lessonService.updateLessons(anyMap(), anyLong())).thenReturn(true);
        doNothing().when(transaction).begin(ageGroupDao, userDao, courseDao);
        doNothing().when(transaction).commit();
        doNothing().when(transaction).rollback();
        doNothing().when(transaction).end();

        boolean actual = courseService.updateCourse(courseData);
        Assert.assertTrue(actual);
    }

    @Test
    public void findCoursesByTeacher() throws ServiceException, DaoException {
        daoProviderHolder.when(DaoProvider::getInstance).thenReturn(daoProvider);
        when(daoProvider.getCourseDao(false)).thenReturn(courseDao);
        when(daoProvider.getLessonDao(false)).thenReturn(lessonDao);

        when(courseDao.findCourseByTeacher(any(User.class))).thenReturn(List.of(new Course()));
        when(lessonDao.findLessonsByCourse(anyLong())).thenReturn(List.of(new Lesson()));
        doNothing().when(courseDao).closeConnection();

        User teacher = new User();
        List<Course> actual = courseService.findCourses(teacher);
        Assert.assertTrue(actual.isEmpty());
    }

    @Test
    public void findCourses() throws ServiceException, DaoException {
        daoProviderHolder.when(DaoProvider::getInstance).thenReturn(daoProvider);
        when(daoProvider.getCourseDao(false)).thenReturn(courseDao);

        when(courseDao.findAll()).thenReturn(List.of());
        doNothing().when(courseDao).closeConnection();

        List<Course> actual = courseService.findCourses();
        Assert.assertTrue(actual.isEmpty());
    }

    @Test(dataProvider = "searchCourseData")
    public void findCourses(Activity activity, String[] weekdays) throws ServiceException, DaoException {
        daoProviderHolder.when(DaoProvider::getInstance).thenReturn(daoProvider);
        when(daoProvider.getCourseDao(false)).thenReturn(courseDao);

        when(courseDao.findCourseByActivityWeekday(any(Activity.class), anyString(), anyInt())).thenReturn(List.of(new Course()));
        when(courseDao.findCourseByActivity(any(Activity.class), anyInt())).thenReturn(List.of());
        when(courseDao.findCourseByActivityCategory(any(Activity.class), anyInt())).thenReturn(List.of());
        when(courseDao.findCourseByActivityType(any(Activity.class), anyInt())).thenReturn(List.of());
        when(courseDao.findCourseByWeekday(anyString())).thenReturn(List.of(new Course()));
        doNothing().when(courseDao).closeConnection();

        int page = 1;
        List<Course> actual = courseService.findCourses(activity, weekdays, page);
        Assert.assertFalse(actual.isEmpty());
    }

    @Test
    public void findAvailableCourses() throws ServiceException, DaoException {
        daoProviderHolder.when(DaoProvider::getInstance).thenReturn(daoProvider);
        when(daoProvider.getCourseDao(false)).thenReturn(courseDao);

        when(courseDao.findAvailableCourses(anyInt())).thenReturn(List.of());
        doNothing().when(courseDao).closeConnection();

        int page = 2;
        List<Course> actual = courseService.findAvailableCourses(page);
        Assert.assertTrue(actual.isEmpty());
    }

    @Test
    public void findCoursesAvailableActivities() throws ServiceException, DaoException {
        daoProviderHolder.when(DaoProvider::getInstance).thenReturn(daoProvider);
        when(daoProvider.getCourseDao(false)).thenReturn(courseDao);

        when(courseDao.findCoursesAvailableActivities()).thenReturn(List.of());
        doNothing().when(courseDao).closeConnection();

        List<Course> actual = courseService.findCoursesAvailableActivities();
        Assert.assertFalse(actual.isEmpty());
    }

    @Test
    public void findCoursesAllActivities() throws ServiceException, DaoException {
        daoProviderHolder.when(DaoProvider::getInstance).thenReturn(daoProvider);
        when(daoProvider.getCourseDao(false)).thenReturn(courseDao);

        when(courseDao.findCoursesAllActivities()).thenReturn(List.of());
        doNothing().when(courseDao).closeConnection();

        List<Course> actual = courseService.findCoursesAllActivities();
        Assert.assertTrue(actual.isEmpty());
    }

    @Test
    public void findCourse() throws ServiceException, DaoException {
        daoProviderHolder.when(DaoProvider::getInstance).thenReturn(daoProvider);
        daoProviderHolder.when(LessonServiceImpl::getInstance).thenReturn(lessonService);
        when(daoProvider.getCourseDao(false)).thenReturn(courseDao);

        when(courseDao.findById(anyLong())).thenReturn(Optional.of(new Course()));
        when(lessonService.findLessons(anyLong())).thenReturn(List.of());
        doNothing().when(courseDao).closeConnection();

        long courseId = 5;
        Optional<Course> actual = courseService.findCourse(courseId);
        Assert.assertTrue(actual.isPresent());
    }

    @Test
    public void reservePlaceAtCourse() throws ServiceException, DaoException {
        daoProviderHolder.when(DaoProvider::getInstance).thenReturn(daoProvider);
        when(daoProvider.getCourseDao(false)).thenReturn(courseDao);

        when(courseDao.findById(anyLong())).thenReturn(Optional.of(new Course()));
        when(courseDao.updateCourseStudentAmount(anyLong(), anyInt())).thenReturn(false);
        doNothing().when(courseDao).closeConnection();

        long courseId = 5;
        boolean actual = courseService.reservePlaceAtCourse(courseId);
        Assert.assertTrue(actual);
    }

    @Test
    public void releasePlaceAtCourse() throws ServiceException, DaoException {
        daoProviderHolder.when(DaoProvider::getInstance).thenReturn(daoProvider);
        when(daoProvider.getCourseDao(false)).thenReturn(courseDao);

        when(courseDao.findById(anyLong())).thenReturn(Optional.of(new Course()));
        when(courseDao.updateCourseStudentAmount(anyLong(), anyInt())).thenReturn(true);
        doNothing().when(courseDao).closeConnection();

        long courseId = 5;
        boolean actual = courseService.releasePlaceAtCourse(courseId);
        Assert.assertTrue(actual);
    }

    @DataProvider(name = "courseData")
    public Object[][] getCourseData() {
        return new Object[][]{
                {Map.of(ID, "5", TEACHER, "Шевцов Пётр", CATEGORY, "Инструментальное", TYPE, "Скрипка",
                        MIN_AGE, "6", MAX_AGE, "18", STUDENT_AMOUNT, "20", LESSON_PRICE,
                        "3", DESCRIPTION, "", STATUS, "upcoming")},
                {Map.of(ID, "6", TEACHER, "Лещенко Ирина", CATEGORY, "Хореографическое", TYPE, "Jazz Funk",
                        MIN_AGE, "3", MAX_AGE, "8", STUDENT_AMOUNT, "15", LESSON_PRICE,
                        "4", DESCRIPTION, "", STATUS, "running")}
        };
    }

    @DataProvider(name = "searchCourseData")
    public Object[][] getSearchCourseData() {
        return new Object[][]{
                {new Activity("Вокальное", "Эстрадный вокал"), new String[]{}},
                {new Activity("Хореографическое", "Модерн"), new String[]{"пн", "чт"}},
                {new Activity(), new String[]{"сб"}}
        };
    }
}
