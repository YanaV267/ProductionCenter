package test.dev.productioncenter.model.dao;

import com.dev.productioncenter.entity.BankCard;
import com.dev.productioncenter.exception.DaoException;
import com.dev.productioncenter.model.connection.ConnectionPool;
import com.dev.productioncenter.model.dao.impl.BankCardDaoImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

public class BankCardDaoTest {
    private MockedStatic<ConnectionPool> connectionPool;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @Mock
    private ConnectionPool pool;
    @InjectMocks
    private BankCardDaoImpl bankCardDao;

    @BeforeClass
    public void init() {
        MockitoAnnotations.openMocks(this);
        connectionPool = mockStatic(ConnectionPool.class);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void add() throws DaoException {
        long expected = 1;
        long actual = bankCardDao.add(new BankCard());
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void update() throws DaoException, SQLException {
        connectionPool.when(ConnectionPool::getInstance).thenReturn(pool);
        when(pool.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        boolean actual = bankCardDao.update(new BankCard());
        Assert.assertTrue(actual);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void delete() {
        long bankCardId = 4;
        boolean actual = bankCardDao.delete(bankCardId);
        Assert.assertTrue(actual);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void findAll() {
        List<BankCard> actual = bankCardDao.findAll();
        Assert.assertFalse(actual.isEmpty());
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void findById() {
        long bankCardId = 5;
        Optional<BankCard> actual = bankCardDao.findById(bankCardId);
        Assert.assertTrue(actual.isPresent());
    }

    @Test
    public void findBankCard() throws DaoException, SQLException {
        connectionPool.when(ConnectionPool::getInstance).thenReturn(pool);
        when(pool.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        BankCard bankCard = new BankCard();
        bankCard.setExpirationDate(LocalDate.now());
        Optional<BankCard> actual = bankCardDao.findBankCard(bankCard);
        Assert.assertTrue(actual.isPresent());
    }

    @Test
    public void findBalance() throws DaoException, SQLException {
        connectionPool.when(ConnectionPool::getInstance).thenReturn(pool);
        when(pool.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        BankCard bankCard = new BankCard();
        bankCard.setExpirationDate(LocalDate.now());
        BigDecimal actual = bankCardDao.findBalance(bankCard);
        Assert.assertNotNull(actual);
    }
}
