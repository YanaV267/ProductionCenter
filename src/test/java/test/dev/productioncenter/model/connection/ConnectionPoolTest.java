package test.dev.productioncenter.model.connection;

import com.dev.productioncenter.model.connection.ConnectionPool;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Connection;

public class ConnectionPoolTest {
    private ConnectionPool connectionPool;

    @BeforeClass
    public void init() {
        connectionPool = ConnectionPool.getInstance();
    }

    @Test
    public void getConnection() {
        Connection connection = connectionPool.getConnection();
        Assert.assertNotNull(connection);
    }

    @Test
    public void releaseConnection() {
        Connection connection = connectionPool.getConnection();
        boolean actual = connectionPool.releaseConnection(connection);
        Assert.assertTrue(actual);
    }
}
