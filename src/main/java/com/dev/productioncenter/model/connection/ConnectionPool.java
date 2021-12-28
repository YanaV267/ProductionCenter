package com.dev.productioncenter.model.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String POOL_PROPERTY_FILE = "pool";
    private static final String POOL_SIZE_PROPERTY = "size";
    private static final int DEFAULT_POOL_SIZE = 4;
    private static final int POOL_SIZE;
    private static final AtomicBoolean isCreated = new AtomicBoolean(false);
    private static final Lock creationLock = new ReentrantLock(true);
    private static BlockingDeque<ProxyConnection> freeConnections;
    private static BlockingDeque<ProxyConnection> takenConnections;
    private static ConnectionPool instance;

    static {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(POOL_PROPERTY_FILE);
        String poolSize;
        if (resourceBundle.containsKey(POOL_SIZE_PROPERTY)) {
            poolSize = resourceBundle.getString(POOL_SIZE_PROPERTY);
            POOL_SIZE = Integer.parseInt(poolSize);
        } else {
            LOGGER.warn("Error of retrieving pool size value: pool size will be initialised by a default value");
            POOL_SIZE = DEFAULT_POOL_SIZE;
        }
    }

    private ConnectionPool() {
        freeConnections = new LinkedBlockingDeque<>(POOL_SIZE);
        takenConnections = new LinkedBlockingDeque<>(POOL_SIZE);
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                Connection connection = ConnectionFactory.getConnection();
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                freeConnections.add(proxyConnection);
            } catch (SQLException exception) {
                LOGGER.error("Error has occurred while creating connection: " + exception);
            }
        }
        if (freeConnections.isEmpty()) {
            LOGGER.fatal("Error: no connections were created");
            throw new RuntimeException("Error: no connections were created");
        }
        LOGGER.info("{} connections were created", freeConnections.size());
    }

    public static ConnectionPool getInstance() {
        if (!isCreated.get()) {
            try {
                creationLock.lock();
                if (isCreated.compareAndSet(false, true)) {
                    instance = new ConnectionPool();
                }
            } finally {
                creationLock.unlock();
            }
        }
        return instance;
    }

    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnections.take();
            takenConnections.put(connection);
        } catch (InterruptedException exception) {
            LOGGER.error("Error has occurred while getting connection: " + exception.getMessage());
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public boolean releaseConnection(Connection connection) {
        if (!(connection instanceof ProxyConnection)) {
            return false;
        }
        try {
            takenConnections.remove(connection);
            freeConnections.put((ProxyConnection) connection);
        } catch (InterruptedException exception) {
            LOGGER.error("Error has occurred while releasing connection: " + exception.getMessage());
            Thread.currentThread().interrupt();
        }
        return true;
    }

    public void destroyPool() {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                freeConnections.take().reallyClose();
            } catch (InterruptedException | SQLException exception) {
                LOGGER.error("Error has occurred while destroying pool: " + exception.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        deregisterDrivers();
    }

    private void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException exception) {
                LOGGER.error("Error has occurred while deregistering drivers: " + exception.getMessage());
                Thread.currentThread().interrupt();
            }
        });
    }
}


