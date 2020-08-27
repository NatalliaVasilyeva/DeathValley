package by.java.connector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.MissingResourceException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;
import by.java.connector.exception.ConnectionPoolException;

public class ConnectionPool {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
    private final int POOL_SIZE = ConnectionCreator.getPollSize();
    private ArrayBlockingQueue<Connection> freeConnections;
    private ArrayBlockingQueue<Connection> releaseConnections;
    private static ReentrantLock lock = new ReentrantLock();
    private static ConnectionPool instance;

    public static ConnectionPool getInstance() {
        if (instance == null) {
            try {
                lock.lock();
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    private ConnectionPool() {
        try {
            lock.lock();
            if (instance != null) {
                throw new UnsupportedOperationException();
            } else {
                init();
            }
        } finally {
            lock.unlock();
        }
    }

    private void init() {
        freeConnections = new ArrayBlockingQueue<>(POOL_SIZE);
        releaseConnections = new ArrayBlockingQueue<>(POOL_SIZE);

        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                freeConnections.offer(ConnectionCreator.createConnection());
            } catch (MissingResourceException e) {
                LOGGER.fatal("Exception during database initialization", e);
                throw new RuntimeException("Exception during database initialization", e);
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException("Driver is not found" + e.getMessage(), e);
            } catch (SQLException e) {
                try {
                    throw new ConnectionPoolException("Exception with create connection", e);
                } catch (ConnectionPoolException e1) {
                    e1.printStackTrace();
                }
            }

        }

    }

    public Connection getConnection() throws ConnectionPoolException {
        Connection connection = null;
        try {
            connection = freeConnections.take();
            releaseConnections.offer(connection);
        } catch (InterruptedException e) {
            LOGGER.error("Error getting connection ", e);
            throw new ConnectionPoolException("Problem with take connection from pool, e");

        }
        return connection;
    }


    public void closeConnection(Connection connection) {
        releaseConnections.remove(connection);
        if (freeConnections.offer(connection)) {
            LOGGER.error("Connection successfully returned: ", connection.toString());
        }
    }

    public void closeConnectionsQueue(List<Connection> queue) throws SQLException {
        Iterator<Connection> iterator = queue.iterator();
        while (iterator.hasNext()) {
            Connection proxyConnection = iterator.next();
            if (!proxyConnection.getAutoCommit()) {
                proxyConnection.commit();
            }
            proxyConnection.close();
        }
    }

    public void destroyConnectionPool() throws SQLException {
        for (Connection connection : freeConnections) {
            connection.close();
        }
        for (Connection connection: releaseConnections) {
            connection.close();
        }

    }
}
