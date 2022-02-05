package test.dev.productioncenter.model.dao;

import com.dev.productioncenter.entity.Course;
import com.dev.productioncenter.entity.Enrollment;
import com.dev.productioncenter.entity.EnrollmentStatus;
import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.exception.DaoException;
import com.dev.productioncenter.model.connection.ConnectionPool;
import com.dev.productioncenter.model.dao.impl.EnrollmentDaoImpl;
import com.dev.productioncenter.model.dao.mapper.impl.EnrollmentMapper;
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

public class EnrollmentDaoTest {
    private MockedStatic<EnrollmentMapper> enrollmentMapper;
    private MockedStatic<ConnectionPool> connectionPool;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private Statement statement;
    @Mock
    private ResultSet resultSet;
    @Mock
    private ConnectionPool pool;
    @Mock
    private EnrollmentMapper mapper;
    @InjectMocks
    private EnrollmentDaoImpl enrollmentDao;

    @BeforeClass
    public void init() {
        MockitoAnnotations.openMocks(this);
        connectionPool = mockStatic(ConnectionPool.class);
        enrollmentMapper = mockStatic(EnrollmentMapper.class);
    }

    @Test
    public void add() throws DaoException, SQLException {
        connectionPool.when(ConnectionPool::getInstance).thenReturn(pool);
        when(pool.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);

        long expected = 9;
        long actual = enrollmentDao.add(new Enrollment());
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void update() throws DaoException, SQLException {
        connectionPool.when(ConnectionPool::getInstance).thenReturn(pool);
        when(pool.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        boolean actual = enrollmentDao.update(new Enrollment());
        Assert.assertTrue(actual);
    }

    @Test
    public void delete() throws DaoException, SQLException {
        connectionPool.when(ConnectionPool::getInstance).thenReturn(pool);
        when(pool.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        long enrollmentId = 4;
        boolean actual = enrollmentDao.delete(enrollmentId);
        Assert.assertTrue(actual);
    }

    @Test
    public void findAll() throws DaoException, SQLException {
        enrollmentMapper.when(EnrollmentMapper::getInstance).thenReturn(mapper);
        connectionPool.when(ConnectionPool::getInstance).thenReturn(pool);
        when(pool.getConnection()).thenReturn(connection);

        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(mapper.retrieve(any())).thenReturn(List.of(new Enrollment()));

        List<Enrollment> actual = enrollmentDao.findAll();
        Assert.assertFalse(actual.isEmpty());
    }

    @Test
    public void findById() throws DaoException, SQLException {
        enrollmentMapper.when(EnrollmentMapper::getInstance).thenReturn(mapper);
        connectionPool.when(ConnectionPool::getInstance).thenReturn(pool);
        when(pool.getConnection()).thenReturn(connection);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(mapper.retrieve(any())).thenReturn(List.of(new Enrollment()));

        long enrollmentId = 8;
        Optional<Enrollment> actual = enrollmentDao.findById(enrollmentId);
        Assert.assertFalse(actual.isEmpty());
    }

    @Test
    public void updateEnrollmentStatus() throws DaoException, SQLException {
        connectionPool.when(ConnectionPool::getInstance).thenReturn(pool);
        when(pool.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        Enrollment enrollment = new Enrollment();
        enrollment.setEnrollmentStatus(EnrollmentStatus.EXPIRED);
        boolean actual = enrollmentDao.updateEnrollmentStatus(enrollment);
        Assert.assertTrue(actual);
    }

    @Test
    public void findEnrollments() throws DaoException, SQLException {
        enrollmentMapper.when(EnrollmentMapper::getInstance).thenReturn(mapper);
        connectionPool.when(ConnectionPool::getInstance).thenReturn(pool);
        when(pool.getConnection()).thenReturn(connection);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(mapper.retrieve(any())).thenReturn(List.of(new Enrollment()));

        int startElementNumber = 30;
        List<Enrollment> actual = enrollmentDao.findEnrollments(startElementNumber);
        Assert.assertFalse(actual.isEmpty());
    }

    @Test
    public void findEnrollmentsByUser() throws DaoException, SQLException {
        enrollmentMapper.when(EnrollmentMapper::getInstance).thenReturn(mapper);
        connectionPool.when(ConnectionPool::getInstance).thenReturn(pool);
        when(pool.getConnection()).thenReturn(connection);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(mapper.retrieve(any())).thenReturn(List.of());

        List<Enrollment> actual = enrollmentDao.findEnrollmentsByUser(new User());
        Assert.assertFalse(actual.isEmpty());
    }

    @Test
    public void findEnrollmentsByCourse() throws DaoException, SQLException {
        enrollmentMapper.when(EnrollmentMapper::getInstance).thenReturn(mapper);
        connectionPool.when(ConnectionPool::getInstance).thenReturn(pool);
        when(pool.getConnection()).thenReturn(connection);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(mapper.retrieve(any())).thenReturn(List.of(new Enrollment()));

        int startElementNumber = 45;
        List<Enrollment> actual = enrollmentDao.findEnrollmentsByCourse(new Course(), startElementNumber);
        Assert.assertTrue(actual.isEmpty());
    }

    @Test
    public void findEnrollmentsByCourseUser() throws DaoException, SQLException {
        enrollmentMapper.when(EnrollmentMapper::getInstance).thenReturn(mapper);
        connectionPool.when(ConnectionPool::getInstance).thenReturn(pool);
        when(pool.getConnection()).thenReturn(connection);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(mapper.retrieve(any())).thenReturn(List.of(new Enrollment()));

        Optional<Enrollment> actual = enrollmentDao.findEnrollmentsByCourseUser(new User(), new Course());
        Assert.assertFalse(actual.isPresent());
    }

    @Test
    public void findEnrollmentsByStatus() throws DaoException, SQLException {
        enrollmentMapper.when(EnrollmentMapper::getInstance).thenReturn(mapper);
        connectionPool.when(ConnectionPool::getInstance).thenReturn(pool);
        when(pool.getConnection()).thenReturn(connection);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(mapper.retrieve(any())).thenReturn(List.of());

        int startElementNumber = 15;
        List<Enrollment> actual = enrollmentDao.findEnrollmentsByStatus(EnrollmentStatus.APPROVED, startElementNumber);
        Assert.assertTrue(actual.isEmpty());
    }

    @Test
    public void findExpiredEnrollments() throws DaoException, SQLException {
        enrollmentMapper.when(EnrollmentMapper::getInstance).thenReturn(mapper);
        connectionPool.when(ConnectionPool::getInstance).thenReturn(pool);
        when(pool.getConnection()).thenReturn(connection);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(mapper.retrieve(any())).thenReturn(List.of(new Enrollment()));

        List<Enrollment> actual = enrollmentDao.findExpiredEnrollments();
        Assert.assertFalse(actual.isEmpty());
    }
}
