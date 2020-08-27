package by.java.dao;

import by.java.connector.exception.ConnectionPoolException;
import by.java.entity.Account;

import java.sql.SQLException;

public interface AccountDao extends BaseDao<Account> {

   Integer findSumOfAllAccounts() throws SQLException, ConnectionPoolException;

   Integer findSumOfUserAccounts(Integer user_id) throws SQLException, ConnectionPoolException;

}
