package by.java.dao;


import by.java.dao.impl.AccountDaoImpl;
import by.java.dao.impl.UserDaoImpl;

public class DaoFactory {

    private final static DaoFactory INSTANCE = new DaoFactory();

    private DaoFactory() {
    }

    public static DaoFactory getInstance() {
        return INSTANCE;
    }

    private AccountDao accountDao;
    private UserDao userDao;

    public AccountDao getAccountDao() {
        return accountDao != null ? accountDao : (accountDao = new AccountDaoImpl());
    }

    public UserDao getUserDao() {
        return userDao != null ? userDao : (userDao = new UserDaoImpl());
    }
}
