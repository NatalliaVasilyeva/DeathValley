package by.java.service;


import by.java.entity.User;
import by.java.service.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findUserById(Integer userId) throws ServiceException;

    User createUser(String name, String surname) throws ServiceException;

    User updateUserInfo(int userId, String name, String surname) throws ServiceException;

    Optional<List<User>> takeAllUsers() throws ServiceException;

    Optional<List<User>> findRichestUser() throws ServiceException;
}
