package com.innowise.gym.service.impl;

import com.innowise.gym.dao.UserDao;
import com.innowise.gym.dao.impl.UserDaoImpl;
import com.innowise.gym.entity.User;
import com.innowise.gym.exception.DaoException;
import com.innowise.gym.exception.ServiceException;
import com.innowise.gym.util.PasswordHasher;

import java.util.Optional;

public class UserServiceImpl {

    private final UserDao userDao;

    public UserServiceImpl() {
        this.userDao = new UserDaoImpl();
    }

    public void register(String login, String password, String email) throws ServiceException {

        if (login == null || login.isBlank()
                || password == null || password.isBlank()
                || email == null || email.isBlank()) {
            throw new ServiceException("All fields are required");
        }

        try {
            if (userDao.findUserByLogin(login).isPresent()) {
                throw new ServiceException("Login already exists");
            }
            String hashedPassword = PasswordHasher.hash(password);

            User user = new User();
            user.setLogin(login);
            user.setPassword(hashedPassword);
            user.setEmail(email);
            user.setRole("USER");

            userDao.insert(user);

        } catch (DaoException e) {
            throw new ServiceException("Error during registration", e);
        }
    }

    public Optional<User> login(String login, String password) throws ServiceException {

        if (login == null || password == null) {
            return Optional.empty();
        }
        try {
            Optional<User> userOpt = userDao.findUserByLogin(login);
            if (userOpt.isEmpty()) {
                return Optional.empty();
            }
            User user = userOpt.get();
            if (PasswordHasher.check(password, user.getPassword())) {
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (DaoException e) {
            throw new ServiceException("Error during login", e);
        }
    }
}
