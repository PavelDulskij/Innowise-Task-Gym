package com.innowise.gym.service;

import com.innowise.gym.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> login(String login, String password);
    boolean register(User user);
}
