package com.innowise.gym.dao;

import com.innowise.gym.entity.User;

import java.util.Optional;

public interface UserDao extends BaseDao {
    Optional<User> findUserByLogin(String login);
}
