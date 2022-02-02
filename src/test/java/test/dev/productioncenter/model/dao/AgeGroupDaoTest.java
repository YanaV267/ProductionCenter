package test.dev.productioncenter.model.dao;

import com.dev.productioncenter.entity.AgeGroup;
import com.dev.productioncenter.exception.DaoException;
import com.dev.productioncenter.model.dao.impl.AgeGroupDaoImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class AgeGroupDaoTest {
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @InjectMocks
    private AgeGroupDaoImpl ageGroupDao;

    @BeforeClass
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void add() throws DaoException, SQLException {
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);

        long expected = 2;
        long actual = ageGroupDao.add(new AgeGroup());
        Assert.assertEquals(actual, expected);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void update() {
        boolean actual = ageGroupDao.update(new AgeGroup());
        Assert.assertFalse(actual);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void delete() {
        long ageGroupId = 4;
        boolean actual = ageGroupDao.delete(ageGroupId);
        Assert.assertTrue(actual);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void findAll() {
        List<AgeGroup> actual = ageGroupDao.findAll();
        Assert.assertFalse(actual.isEmpty());
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void findById() {
        long ageGroupId = 7;
        Optional<AgeGroup> actual = ageGroupDao.findById(ageGroupId);
        Assert.assertTrue(actual.isPresent());
    }

    @Test
    public void findByMinMaxAge() throws DaoException, SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        Optional<Long> actual = ageGroupDao.findByMinMaxAge(new AgeGroup());
        Assert.assertTrue(actual.isPresent());
    }
}
