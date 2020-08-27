package by.java.service.impl;

import by.java.connector.exception.ConnectionPoolException;
import by.java.dao.DaoFactory;
import by.java.dao.UserDao;
import by.java.dao.exception.DaoException;
import by.java.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.java.service.UserService;
import by.java.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserService.class);
    DaoFactory daoFactory = DaoFactory.getInstance();

    @Override
    public Optional<User> findUserById(Integer userId) throws ServiceException {

        Optional<User> user = Optional.empty();
        UserDao userDao = daoFactory.getUserDao();
        try {
            user = userDao.findById(userId);
        } catch (DaoException | SQLException | InterruptedException e) {
            LOGGER.warn("Service can't find user {}", userId, e);
            throw new ServiceException(e.getMessage(), e);
        }
        return user;
    }

    @Override
    public User createUser(String name, String surname) throws ServiceException {
        User user;
        UserDao userDao = daoFactory.getUserDao();
        try {
                user = new User.UserBuilder()
                    .withName(name)
                    .withSurname(surname)
                    .build();

            boolean result=userDao.create(user);

            if(!result) {
                LOGGER.warn("Can't create user");
                throw  new ServiceException("Can't create user");
            }
        } catch (DaoException | SQLException e) {
            LOGGER.warn("Can't create user",  e);
            throw new ServiceException(e);
        }

        return user;
    }

    @Override
    public User updateUserInfo(int userId, String name, String surname) throws ServiceException {
        User user=null;
        UserDao userDao = daoFactory.getUserDao();
        try {
            Optional<User> optionalUser = findUserById(userId);
            if (optionalUser.isPresent()) {
                user = optionalUser.get();
                user.setName(name);
                user.setSurname(surname);
                userDao.update(user);
                optionalUser = findUserById(userId);
                if (optionalUser.isPresent()) {
                    user = optionalUser.get();
                    LOGGER.info("user: " + userId + " had been updated.");
                } else {
                    LOGGER.info("user: " + userId + " had not been updated.");
                }
            }
        } catch (DaoException | SQLException e) {
            LOGGER.warn("Exception: can't update user", e.getMessage());
            throw new ServiceException(e);
        }
        return user;
    }


    @Override
    public Optional<List<User>> takeAllUsers() throws ServiceException {
        LOGGER.debug("Service looking for all users");
        UserDao userDao = daoFactory.getUserDao();
        Optional<List<User>> users;
        try {
            users = Optional.of(userDao.findAll());
            LOGGER.info("User list was " + (users.isPresent() ? "" : "not") + " found accounts");
        } catch (DaoException | SQLException e) {
            LOGGER.warn("Error with present user list", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
        return users;
    }

    @Override
    public Optional<List<User>> findRichestUser() throws ServiceException {
        UserDao userDao = daoFactory.getUserDao();
        Optional<List<User>> users;;
        try{
            users = Optional.of(userDao.findRichestUser());
        } catch (DaoException | SQLException | ConnectionPoolException e) {
            LOGGER.warn("Error with present information about the richest user", e);
            throw new ServiceException(e.getMessage(), e);
        }
        return users;
    }

}
