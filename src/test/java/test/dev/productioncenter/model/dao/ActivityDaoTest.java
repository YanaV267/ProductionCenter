package test.dev.productioncenter.model.dao;

import com.dev.productioncenter.entity.Activity;
import com.dev.productioncenter.exception.DaoException;
import com.dev.productioncenter.model.dao.impl.ActivityDaoImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

public class ActivityDaoTest {
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private Statement statement;
    @Mock
    private ResultSet resultSet;
    @InjectMocks
    private ActivityDaoImpl activityDao;

    @BeforeClass
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void add() throws DaoException, SQLException {
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);

        long expected = 3;
        long actual = activityDao.add(new Activity());
        Assert.assertEquals(actual, expected);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void update() {
        boolean actual = activityDao.update(new Activity());
        Assert.assertFalse(actual);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void delete() {
        long activityId = 4;
        boolean actual = activityDao.delete(activityId);
        Assert.assertTrue(actual);
    }

    @Test
    public void findAll() throws DaoException, SQLException {
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);

        List<Activity> actual = activityDao.findAll();
        Assert.assertTrue(actual.isEmpty());
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void findById() {
        long activityId = 4;
        Optional<Activity> actual = activityDao.findById(activityId);
        Assert.assertTrue(actual.isPresent());
    }

    @Test
    public void findActivity() throws DaoException, SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        boolean actual = activityDao.findActivity(new Activity());
        Assert.assertTrue(actual);
    }

    @Test
    public void findActivityId() throws DaoException, SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.getLong(1)).thenReturn(Long.valueOf(4));

        long expected = 4;
        long actual = activityDao.findActivityId(new Activity());
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void findCategories() throws DaoException, SQLException {
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);

        List<String> actual = activityDao.findCategories();
        Assert.assertFalse(actual.isEmpty());
    }
}