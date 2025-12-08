package com.innowise.gym.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public enum ConnectionPool {
    INSTANCE;

    private static final String DRIVER_LOAD = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/users";
    private static final String USER = "root";
    private static final  String PASSWORD = "root";
    private BlockingQueue<Connection> freeConnections;
    private Queue<Connection> givenAwayConnections;


    private static final int DEFAULT_POOL_SIZE = 10;

    ConnectionPool() {
        try {
            Class.forName(DRIVER_LOAD);
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }
        freeConnections = new ArrayBlockingQueue<>(DEFAULT_POOL_SIZE);
        givenAwayConnections = new ArrayDeque<>();

        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            Connection realConnection = null;
            try {
                realConnection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            freeConnections.add(realConnection);
        }
    }
    public Connection getConnection() {
        Connection connection;
        try {
            connection = freeConnections.take();
            givenAwayConnections.offer(connection);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
    public void releaseConnection(Connection connection) {
        givenAwayConnections.remove(connection);
        freeConnections.offer(connection);
    }
    public void destroyPool() {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                freeConnections.take().close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        deregisterDriver();
    }
    public void deregisterDriver() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
