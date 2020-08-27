package by.java.dao.impl;

import by.java.connector.exception.ConnectionPoolException;
import by.java.dao.AccountDao;
import by.java.dao.exception.DaoException;
import by.java.entity.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class AccountDaoImpl implements AccountDao {

    @Override
    public List<Account> findAll() throws SQLException, DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getSelectAllQuery())) {
            List<Account> accounts = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            accounts = parseResultSet(resultSet, accounts);
            return accounts;

        } catch (ConnectionPoolException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Account> findById(int id) throws DaoException, SQLException {

        Optional<Account> optional;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getSelectQueryById())) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Account account = parseResultSetForOne(resultSet);
            optional = Optional.of(account);
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
    public void delete(Account account) throws SQLException, DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getDeleteQuery())) {
            preparedStatement.setInt(1, account.getAccountId());
            preparedStatement.executeUpdate();
        } catch (ConnectionPoolException e) {
            throw new DaoException(e);
        }

    }

    @Override
    public boolean create(Account account) throws SQLException, DaoException {
        boolean isUpdate;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getCreateQuery())) {
            preparedStatement.setInt(1, account.getAccount());
            preparedStatement.setInt(2, account.getUserId());
            int rowChangeNumber = preparedStatement.executeUpdate();
            isUpdate = rowChangeNumber == 1 ? true : false;
        } catch (ConnectionPoolException e) {
            throw new DaoException(e);
        }
        return isUpdate;
    }


    @Override
    public boolean update(Account account) throws SQLException, DaoException {
        boolean isUpdate;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getUpdateQuery())) {
            preparedStatement.setInt(1, account.getAccount());
            preparedStatement.setInt(2, account.getUserId());
            int rowChangeNumber = preparedStatement.executeUpdate();
            isUpdate = rowChangeNumber == 1 ? true : false;
            return isUpdate;
        } catch (ConnectionPoolException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Integer findSumOfAllAccounts() throws SQLException, ConnectionPoolException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getSelectSumOfAllAccounts())) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Integer sumOfAccounts=0;
            while (resultSet.next()) {
               sumOfAccounts = resultSet.getInt("sum");
            }
            return sumOfAccounts;
        }
    }

    @Override
    public Integer findSumOfUserAccounts(Integer user_id) throws SQLException, ConnectionPoolException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getSelectSumOfUserAccounts())) {
            preparedStatement.setInt(1, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Integer sumOfAccounts=0;
            while (resultSet.next()) {
                sumOfAccounts = resultSet.getInt("sum");
            }
            return sumOfAccounts;
        }
    }


    private List<Account> parseResultSet(ResultSet resultSet, List<Account> accounts) throws DaoException {
        try {
            while (resultSet.next()) {
                Account account = setParameters(resultSet);
                accounts.add(account);
            }
        } catch (SQLException e) {
            throw new DaoException();
        }
        return accounts;
    }

    private Account parseResultSetForOne(ResultSet resultSet) throws DaoException {
        Account account = null;
        try {
            while (resultSet.next()) {
                account = setParameters(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException();
        }
        return account;
    }


    private Account setParameters(ResultSet resultSet) throws SQLException {

        Account account = new Account.AccountBuilder()
                .withId(resultSet.getInt("account_id"))
                .withAccount(resultSet.getInt("account"))
                .withUser(resultSet.getInt("user_id"))
                .build();

        return account;
    }

    private String getSelectQueryById() {
        return "SELECT account_id, account, user_id FROM banks.account WHERE account_id = ?;";
    }


    private String getSelectAllQuery() {
        return "SELECT account_id, account, user_id FROM banks.account";
    }


    private String getCreateQuery() {
        return "INSERT INTO banks.account (account, user_id) VALUES (?, ?);";
    }


    private String getUpdateQuery() {
        return "UPDATE banks.account SET account=? WHERE account_id=?;";
    }

    private String getDeleteQuery() {
        return "DELETE FROM banks.account where account_id = ?";
    }


    private String getDeleteByIdQuery() {
        return "DELETE FROM banks.account where account_id = ?";
    }


    private String getSelectSumOfAllAccounts() {
        return "SELECT SUM(account) as sum from banks.account";
    }

    private String getSelectSumOfUserAccounts() {
        return "SELECT SUM(account) as sum from banks.account WHERE user_id=?";
    }


}
