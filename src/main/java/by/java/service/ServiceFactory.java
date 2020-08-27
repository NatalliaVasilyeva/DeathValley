package by.java.service;

import by.java.service.impl.AccountServiceImpl;
import by.java.service.impl.UserServiceImpl;

public class ServiceFactory {

    private final static ServiceFactory INSTANCE = new ServiceFactory();

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        return INSTANCE;
    }

    private AccountService accountService;
    private UserService userService;


    public AccountService getAccountService() {
        return accountService != null ? accountService : (accountService = new AccountServiceImpl());
    }

    public UserService getUserService() {
        return userService != null ? userService : (userService = new UserServiceImpl());
    }
}
