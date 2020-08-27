package by.java.service;

import by.java.entity.Account;
import by.java.service.exception.ServiceException;

import java.util.List;
import java.util.Optional;


public interface AccountService {

    Optional<Account> findAccountById(Integer accountId) throws ServiceException;

    Account createAccount(Integer account, Integer userId) throws ServiceException;

    Account updateAccountInfo(Integer accountId, Integer userId, Integer account) throws ServiceException;

    Optional<List<Account>> takeAllAccounts() throws ServiceException;

    Integer findSumOfAllAccounts() throws ServiceException;

    Integer findUserAccountSum(Integer user_id) throws ServiceException;
}
