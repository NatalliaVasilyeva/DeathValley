package by.java.dao.impl;

import by.java.connector.exception.ConnectionPoolException;
import by.java.dao.UserDao;
import by.java.dao.exception.DaoException;
import by.java.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    @Override
    public List<User> findAll() throws SQLException, DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getSelectAllQuery())) {
            List<User> users = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            users = parseResultSet(resultSet, users);
            return users;

        } catch (ConnectionPoolException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<User> findById(int id) throws DaoException, SQLException {

        Optional<User> optional;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getSelectQueryById())) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            User user = parseResultSetForOne(resultSet);
            optional = Optional.of(user);
            return optional;
        } catch (ConnectionPoolException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void deleteById(int id) throws SQLException, DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getDeleteByIdQuery())) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (ConnectionPoolException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(User user) throws SQLException, DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getDeleteQuery())) {
            preparedStatement.setInt(1, user.getUserId());
            preparedStatement.executeUpdate();
        } catch (ConnectionPoolException e) {
            throw new DaoException(e);
        }

    }

    @Override
    public boolean create(User user) throws SQLException, DaoException {
        boolean isUpdate;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getCreateQuery())) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            int rowChangeNumber = preparedStatement.executeUpdate();
            isUpdate = rowChangeNumber == 1 ? true : false;
        } catch (ConnectionPoolException e) {
            throw new DaoException(e);
        }
        return isUpdate;
    }


    @Override
    public boolean update(User user) throws SQLException, DaoException {
        boolean isUpdate;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getUpdateQuery())) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setInt(3, user.getUserId());
            int rowChangeNumber = preparedStatement.executeUpdate();
            isUpdate = rowChangeNumber == 1 ? true : false;
            return isUpdate;
        } catch (ConnectionPoolException e) {
            throw new DaoException(e);
        }
    }


    public List<User> findRichestUser() throws SQLException, ConnectionPoolException, DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getSelectRichestUser())) {
            List<User> users = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            users = parseResultSet(resultSet, users);
            return users;
        }
    }

    private List<User> parseResultSet(ResultSet resultSet, List<User> users) throws DaoException {
        try {
            while (resultSet.next()) {
                User user = setParameters(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException();
        }
        return users;
    }

    private User parseResultSetForOne(ResultSet resultSet) throws DaoException {
        User user = null;
        try {
            while (resultSet.next()) {
                user = setParameters(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException();
        }
        return user;
    }


    private User setParameters(ResultSet resultSet) throws SQLException {

        User user = new User.UserBuilder()
                .withId(resultSet.getInt("user_id"))
                .withName(resultSet.getString("name"))
                .withSurname(resultSet.getString("surname"))
                .build();

        return user;
    }

    private String getSelectQueryById() {
        return "SELECT user_id, name, surname FROM banks.user WHERE user_id = ?;";
    }


    private String getSelectAllQuery() {
        return "SELECT user_id, name, surname FROM banks.user";
    }


    private String getCreateQuery() {
        return "INSERT INTO banks.user (name,surname) VALUES (?, ?);";
    }


    private String getUpdateQuery() {
        return "UPDATE banks.user SET name=?, surname=? WHERE user_id=?;";
    }

    private String getDeleteQuery() {
        return "DELETE FROM banks.user where user_id = ?";
    }


    private String getDeleteByIdQuery() {
        return "DELETE FROM banks.user where user_id = ?";
    }


    private String getSelectRichestUser() {
        return "SELECT \n" +
                "    user.user_id, user.name, user.surname, SUM(account) AS sum\n" +
                "FROM\n" +
                "    banks.user\n" +
                "        JOIN\n" +
                "    banks.account ON user.user_id = account.user_id\n" +
                "GROUP BY user_id\n" +
                "HAVING sum = (SELECT \n" +
                "        MAX(init.sum)\n" +
                "    FROM\n" +
                "        (SELECT \n" +
                "            user.user_id, SUM(account) AS sum\n" +
                "        FROM\n" +
                "            banks.user\n" +
                "        JOIN banks.account ON user.user_id = account.user_id\n" +
                "        GROUP BY user_id) init);";
    }
}
