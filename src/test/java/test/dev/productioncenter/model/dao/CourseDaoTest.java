package test.dev.productioncenter.model.dao;

import com.dev.productioncenter.entity.*;
import com.dev.productioncenter.exception.DaoException;
import com.dev.productioncenter.model.dao.impl.CourseDaoImpl;
import com.dev.productioncenter.model.dao.mapper.impl.CourseMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

public class CourseDaoTest {
    private MockedStatic<CourseMapper> courseMapper;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private Statement statement;
    @Mock
    private ResultSet resultSet;
    @Mock
    private CourseMapper mapper;
    @InjectMocks
    private CourseDaoImpl courseDao;

    @BeforeClass
    public void init() {
        MockitoAnnotations.openMocks(this);
        courseMapper = mockStatic(CourseMapper.class);
    }

    @Test
    public void add() throws DaoException, SQLException {
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);

        Course course = new Course();
        course.setTeacher(new User());
        long expected = 6;
        long actual = courseDao.add(course);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void update() throws DaoException, SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        Course course = new Course();
        course.setTeacher(new User());
        course.setStatus(CourseStatus.RUNNING);
        boolean actual = courseDao.update(course);
        Assert.assertTrue(actual);
    }

    @Test
    public void delete() throws DaoException, SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        long courseId = 13;
        boolean actual = courseDao.delete(courseId);
        Assert.assertTrue(actual);
    }

    @Test
    public void findAll() throws DaoException, SQLException {
        courseMapper.when(CourseMapper::getInstance).thenReturn(mapper);

        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(mapper.retrieve(any())).thenReturn(List.of(new Course()));

        List<Course> actual = courseDao.findAll();
        Assert.assertFalse(actual.isEmpty());
    }

    @Test
    public void findById() throws DaoException, SQLException {
        courseMapper.when(CourseMapper::getInstance).thenReturn(mapper);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(mapper.retrieve(any())).thenReturn(List.of(new Course()));

        long courseId = 4;
        Optional<Course> actual = courseDao.findById(courseId);
        Assert.assertFalse(actual.isEmpty());
    }

    @Test
    public void updateCourseStudentAmount() throws DaoException, SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        long id = 10;
        int studentAmount = 20;
        boolean actual = courseDao.updateCourseStudentAmount(id, studentAmount);
        Assert.assertTrue(actual);

    }

    @Test
    public void findCourseByTeacher() throws DaoException, SQLException {
        courseMapper.when(CourseMapper::getInstance).thenReturn(mapper);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(mapper.retrieve(any())).thenReturn(List.of(new Course()));

        List<Course> actual = courseDao.findCourseByTeacher(new User());
        Assert.assertTrue(actual.isEmpty());
    }

    @Test
    public void findCourseByAgeGroup() throws DaoException, SQLException {
        courseMapper.when(CourseMapper::getInstance).thenReturn(mapper);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(mapper.retrieve(any())).thenReturn(List.of(new Course()));

        List<Course> actual = courseDao.findCourseByAgeGroup(new AgeGroup());
        Assert.assertTrue(actual.isEmpty());
    }

    @Test
    public void findCourseByActivity() throws DaoException, SQLException {
        courseMapper.when(CourseMapper::getInstance).thenReturn(mapper);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(mapper.retrieve(any())).thenReturn(List.of(new Course()));

        int startElementNumber = 15;
        List<Course> actual = courseDao.findCourseByActivity(new Activity(), startElementNumber);
        Assert.assertFalse(actual.isEmpty());
    }

    @Test
    public void findCourseByActivityCategory() throws DaoException, SQLException {
        courseMapper.when(CourseMapper::getInstance).thenReturn(mapper);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(mapper.retrieve(any())).thenReturn(List.of());

        int startElementNumber = 15;
        List<Course> actual = courseDao.findCourseByActivityCategory(new Activity(), startElementNumber);
        Assert.assertTrue(actual.isEmpty());
    }

    @Test
    public void findCourseByActivityType() throws DaoException, SQLException {
        courseMapper.when(CourseMapper::getInstance).thenReturn(mapper);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(mapper.retrieve(any())).thenReturn(List.of(new Course()));

        int startElementNumber = 15;
        List<Course> actual = courseDao.findCourseByActivityType(new Activity(), startElementNumber);
        Assert.assertFalse(actual.isEmpty());
    }

    @Test
    public void findCourseByActivityWeekday() throws DaoException, SQLException {
        courseMapper.when(CourseMapper::getInstance).thenReturn(mapper);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(mapper.retrieve(any())).thenReturn(List.of(new Course()));

        String weekday = "monday";
        int startElementNumber = 15;
        List<Course> actual = courseDao.findCourseByActivityWeekday(new Activity(), weekday, startElementNumber);
        Assert.assertFalse(actual.isEmpty());
    }

    @Test
    public void findCourseByWeekday() throws DaoException, SQLException {
        courseMapper.when(CourseMapper::getInstance).thenReturn(mapper);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(mapper.retrieve(any())).thenReturn(List.of());

        String weekday = "friday";
        List<Course> actual = courseDao.findCourseByWeekday(weekday);
        Assert.assertTrue(actual.isEmpty());
    }

    @Test
    public void findAvailableCourses() throws DaoException, SQLException {
        courseMapper.when(CourseMapper::getInstance).thenReturn(mapper);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(mapper.retrieve(any())).thenReturn(List.of(new Course()));

        int startElementNumber = 15;
        List<Course> actual = courseDao.findAvailableCourses(startElementNumber);
        Assert.assertFalse(actual.isEmpty());
    }

    @Test
    public void findCoursesAllActivities() throws DaoException, SQLException {
        courseMapper.when(CourseMapper::getInstance).thenReturn(mapper);

        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);

        List<Course> actual = courseDao.findCoursesAllActivities();
        Assert.assertFalse(actual.isEmpty());
    }

    @Test
    public void findCoursesAvailableActivities() throws DaoException, SQLException {
        courseMapper.when(CourseMapper::getInstance).thenReturn(mapper);

        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);

        List<Course> actual = courseDao.findCoursesAvailableActivities();
        Assert.assertFalse(actual.isEmpty());
    }
}