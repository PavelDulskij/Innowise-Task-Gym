package com.innowise.gym.service.impl;

import com.innowise.gym.dao.UserDao;
import com.innowise.gym.dao.impl.UserDaoImpl;
import com.innowise.gym.entity.User;
import com.innowise.gym.exception.DaoException;
import com.innowise.gym.exception.ServiceException;
import com.innowise.gym.util.PasswordHasher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class UserServiceImpl {

    private static final Logger log = LogManager.getLogger();

    private final UserDao userDao;

    public UserServiceImpl() {
        this.userDao = new UserDaoImpl();
    }

    public void register(String login, String password, String email) throws ServiceException {
        log.debug("SERVICE: register started, login={}, email={}", login, email);

        if (login == null || login.isBlank()
                || password == null || password.isBlank()
                || email == null || email.isBlank()) {

            log.warn("SERVICE: registration failed - empty fields, login={}", login);
            throw new ServiceException("All fields are required");
        }

        try {
            if (userDao.findUserByLogin(login).isPresent()) {
                log.warn("SERVICE: registration failed - login already exists, login={}", login);
                throw new ServiceException("Login already exists");
            }

            String hashedPassword = PasswordHasher.hash(password);

            User user = new User();
            user.setLogin(login);
            user.setPassword(hashedPassword);
            user.setEmail(email);
            user.setRole("USER");

            userDao.insert(user);
            log.info("SERVICE: user registered successfully, login={}", login);

        } catch (DaoException e) {
            log.error("SERVICE: error during registration, login={}", login, e);
            throw new ServiceException("Error during registration", e);
        }
    }

    public Optional<User> login(String login, String password) throws ServiceException {
        log.debug("SERVICE: login attempt, login={}", login);

        if (login == null || password == null) {
            log.warn("SERVICE: login failed - null credentials");
            return Optional.empty();
        }

        try {
            Optional<User> userOpt = userDao.findUserByLogin(login);

            if (userOpt.isEmpty()) {
                log.warn("SERVICE: login failed - user not found, login={}", login);
                return Optional.empty();
            }

            User user = userOpt.get();

            if (PasswordHasher.check(password, user.getPassword())) {
                log.info("SERVICE: login successful, login={}", login);
                return Optional.of(user);
            }

            log.warn("SERVICE: login failed - invalid password, login={}", login);
            return Optional.empty();

        } catch (DaoException e) {
            log.error("SERVICE: error during login, login={}", login, e);
            throw new ServiceException("Error during login", e);
        }
    }
}
