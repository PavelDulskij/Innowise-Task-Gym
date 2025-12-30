package com.innowise.gym.pool;

import com.innowise.gym.manager.DBParameter;
import com.innowise.gym.manager.PropertyManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public enum ConnectionPool {
    INSTANCE;

    private final String DRIVER_LOAD = PropertyManager.get(DBParameter.DB_DRIVER);
    private final String URL = PropertyManager.get(DBParameter.DB_URL);
    private final String USER = PropertyManager.get(DBParameter.DB_USER);
    private final String PASSWORD = PropertyManager.get(DBParameter.DB_PASSWORD);
    private final int POOL_SIZE = Integer.parseInt(PropertyManager.get(DBParameter.DB_POOL_SIZE));

    private final BlockingQueue<ProxyConnection> freeConnections;
    private final Queue<ProxyConnection> usedConnections;

    ConnectionPool() {
        try {
            Class.forName(DRIVER_LOAD);
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError("Cannot load DB driver");
        }

        freeConnections = new ArrayBlockingQueue<>(POOL_SIZE);
        usedConnections = new ArrayDeque<>();

        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                Connection realConnection =
                        DriverManager.getConnection(URL, USER, PASSWORD);

                ProxyConnection proxyConnection =
                        new ProxyConnection(realConnection);

                freeConnections.add(proxyConnection);

            } catch (SQLException e) {
                throw new ExceptionInInitializerError("Cannot create DB connection");
            }
        }
    }

    public Connection getConnection() {
        try {
            ProxyConnection connection = freeConnections.take();
            usedConnections.offer(connection);
            return connection; // возвращаем как Connection
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while waiting for DB connection", e);
        }
    }

    void releaseConnection(ProxyConnection connection) {
        usedConnections.remove(connection);
        freeConnections.offer(connection);
    }

    public void destroyPool() {
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                ProxyConnection connection = freeConnections.take();
                connection.reallyClose();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        deregisterDrivers();
    }

    private void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException ignored) {
            }
        });
    }
}
