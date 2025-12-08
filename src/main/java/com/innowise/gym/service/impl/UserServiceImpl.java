package com.innowise.gym.service.impl;

import com.innowise.gym.dao.UserDao;
import com.innowise.gym.dao.impl.UserDaoImpl;
import com.innowise.gym.entity.User;
import com.innowise.gym.service.UserService;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserDao userDao = new UserDaoImpl();

    @Override
    public Optional<User> login(String login, String password) {
        Optional<User> user = userDao.findUserByLogin(login);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user;
        }
        return Optional.empty();
    }

    @Override
    public boolean register(User user) {
        Optional<User> userOptional = userDao.findUserByLogin(user.getLogin());
        if (userOptional.isEmpty() ) {
            return true;
        }
        return false;
    }
}
