package by.java.dao;

import by.java.connector.ConnectionPool;
import by.java.dao.exception.DaoException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface BaseDao<T> {

    ConnectionPool connectionPool = ConnectionPool.getInstance();

    List<T> findAll() throws SQLException, DaoException;

    Optional<T> findById(int id) throws DaoException, SQLException, InterruptedException;

    void deleteById(int id) throws SQLException, DaoException;

    void delete(T object) throws SQLException, DaoException;

    boolean create(T object) throws SQLException, DaoException;

    boolean update(T object) throws SQLException, DaoException;


}
