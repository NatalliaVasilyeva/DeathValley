package by.java.service.impl;

import by.java.connector.exception.ConnectionPoolException;
import by.java.dao.AccountDao;
import by.java.dao.DaoFactory;
import by.java.dao.exception.DaoException;
import by.java.entity.Account;
import by.java.service.AccountService;
import by.java.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AccountServiceImpl implements AccountService {

    private static final Logger LOGGER = LogManager.getLogger(AccountService.class);
    DaoFactory daoFactory = DaoFactory.getInstance();

    @Override
    public Optional<Account> findAccountById(Integer accountId) throws ServiceException {

        Optional<Account> account = Optional.empty();
        AccountDao accountDao = daoFactory.getAccountDao();
        try {
            account = accountDao.findById(accountId);
        } catch (DaoException | SQLException | InterruptedException e) {
            LOGGER.warn("Service can't find account {}", accountId, e);
            throw new ServiceException(e.getMessage(), e);
        }
        return account;
    }

    @Override
    public Account createAccount(Integer account, Integer userId) throws ServiceException {
        Account newAccount;
        AccountDao accountDao = daoFactory.getAccountDao();
        try {
            newAccount = new Account.AccountBuilder()
                    .withAccount(account)
                    .withUser(userId)
                    .build();

            boolean result = accountDao.create(newAccount);

            if (!result) {
                LOGGER.warn("Can't create account for user with id {}", userId);
                throw new ServiceException("Can't create account for user with id " + userId);
            }
        } catch (DaoException | SQLException e) {
            LOGGER.warn("Can't create account for user with id {}", userId, e);
            throw new ServiceException(e);
        }
        return newAccount;
    }

    @Override
    public Account updateAccountInfo(Integer accountId, Integer userId, Integer account) throws ServiceException {
        Account newAccount = null;
        AccountDao accountDao = daoFactory.getAccountDao();
        try {
            Optional<Account> optionalAccount = findAccountById(accountId);
            if (optionalAccount.isPresent()) {
                newAccount = optionalAccount.get();
                newAccount.setAccount(account);
                newAccount.setUserId(userId);
                accountDao.update(newAccount);
                optionalAccount = findAccountById(accountId);
                if (optionalAccount.isPresent()) {
                    newAccount = optionalAccount.get();
                    LOGGER.info("account: " + accountId + " had been updated.");
                } else {
                    LOGGER.info("account: " + accountId + " had not been updated.");
                }
            }
        } catch (DaoException | SQLException e) {
            LOGGER.warn("Exception: can't update account", e.getMessage());
            throw new ServiceException(e);
        }
        return newAccount;
    }


    @Override
    public Optional<List<Account>> takeAllAccounts() throws ServiceException {
        AccountDao accountDao = daoFactory.getAccountDao();
        Optional<List<Account>> accounts;
        try {
            accounts = Optional.of(accountDao.findAll());
            LOGGER.info("Account list was " + (accounts.isPresent() ? "" : "not") + " found accounts");
        } catch (DaoException | SQLException e) {
            LOGGER.warn("Error with present accounts list", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
        return accounts;
    }

    @Override
    public Integer findSumOfAllAccounts() throws ServiceException {
        AccountDao accountDao = daoFactory.getAccountDao();
        Integer allAccountSum = 0;
        try {
            allAccountSum = accountDao.findSumOfAllAccounts();
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.warn("Error with present information about all account sum", e);
            throw new ServiceException(e.getMessage(), e);
        }
        return allAccountSum;
    }

    @Override
    public Integer findUserAccountSum(Integer user_id) throws ServiceException {
        AccountDao accountDao = daoFactory.getAccountDao();
        Integer userAccountSum = 0;
        try {
            userAccountSum = accountDao.findSumOfUserAccounts(user_id);
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.warn("Error with present information about accounts of user with id {}",user_id, e);
            throw new ServiceException(e.getMessage(), e);
        }
        return userAccountSum;
    }
}
