package com.innowise.gym.service;

import com.innowise.gym.entity.User;
import com.innowise.gym.exception.ServiceException;

import java.util.Optional;

public interface UserService {
    Optional<User> login(String login, String password) throws ServiceException;
    boolean register(User user) throws ServiceException;
}
