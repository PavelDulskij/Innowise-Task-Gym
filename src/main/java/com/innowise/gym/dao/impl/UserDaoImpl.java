package com.innowise.gym.dao.impl;

import com.innowise.gym.dao.UserDao;
import com.innowise.gym.entity.User;

import java.sql.Connection;
import java.util.Optional;


public class UserDaoImpl implements UserDao {
    private static final String FIND_BY_LOGIN = "SELECT login, password, email FROM users WHERE login = ?;";
    private static final String INSERT_USER = "INSERT INTO users(login, password, email) VALUES (?, ?, ?);";
    @Override
    public boolean save() {
        return false;
    }

    @Override
    public boolean delete() {
        return false;
    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        return Optional.empty();
    }
}
