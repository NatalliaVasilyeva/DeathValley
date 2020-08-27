package by.java.dao;

import by.java.connector.exception.ConnectionPoolException;
import by.java.dao.exception.DaoException;
import by.java.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao extends BaseDao<User> {

    List<User> findRichestUser() throws SQLException, ConnectionPoolException, DaoException;

}
