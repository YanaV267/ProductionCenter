package test.dev.productioncenter.model.dao;

import com.dev.productioncenter.entity.Course;
import com.dev.productioncenter.entity.Lesson;
import com.dev.productioncenter.exception.DaoException;
import com.dev.productioncenter.model.dao.impl.LessonDaoImpl;
import com.dev.productioncenter.model.dao.mapper.impl.LessonMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.*;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

public class LessonDaoTest {
    private MockedStatic<LessonMapper> lessonMapper;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private Statement statement;
    @Mock
    private ResultSet resultSet;
    @Mock
    private LessonMapper mapper;
    @InjectMocks
    private LessonDaoImpl lessonDao;

    @BeforeClass
    public void init() {
        MockitoAnnotations.openMocks(this);
        lessonMapper = mockStatic(LessonMapper.class);
    }

    @Test
    public void add() throws DaoException, SQLException {
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);

        Lesson lesson = new Lesson();
        lesson.setCourse(new Course(5));
        lesson.setStartTime(LocalTime.now());
        long expected = 5;
        long actual = lessonDao.add(lesson);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void update() throws DaoException, SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        Lesson lesson = new Lesson();
        lesson.setStartTime(LocalTime.now());
        boolean actual = lessonDao.update(lesson);
        Assert.assertTrue(actual);
    }

    @Test
    public void delete() throws DaoException, SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        long lessonId = 12;
        boolean actual = lessonDao.delete(lessonId);
        Assert.assertTrue(actual);
    }

    @Test
    public void findAll() throws DaoException, SQLException {
        lessonMapper.when(LessonMapper::getInstance).thenReturn(mapper);

        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(mapper.retrieve(any())).thenReturn(List.of(new Lesson()));

        List<Lesson> actual = lessonDao.findAll();
        Assert.assertFalse(actual.isEmpty());
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void findById() {
        long lessonId = 5;
        Optional<Lesson> actual = lessonDao.findById(lessonId);
        Assert.assertTrue(actual.isPresent());
    }

    @Test
    public void findLessonsByCourse() throws DaoException, SQLException {
        lessonMapper.when(LessonMapper::getInstance).thenReturn(mapper);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(mapper.retrieve(any())).thenReturn(List.of(new Lesson()));

        long courseId = 4;
        List<Lesson> actual = lessonDao.findLessonsByCourse(courseId);
        Assert.assertFalse(actual.isEmpty());
    }

    @Test
    public void findLessonsByCourseWeekDay() throws DaoException, SQLException {
        lessonMapper.when(LessonMapper::getInstance).thenReturn(mapper);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(mapper.retrieve(any())).thenReturn(List.of());

        String weekDay = "wednesday";
        long courseId = 17;
        Optional<Lesson> actual = lessonDao.findLessonsByCourseWeekDay(weekDay, courseId);
        Assert.assertFalse(actual.isPresent());
    }
}
