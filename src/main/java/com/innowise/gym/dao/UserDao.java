package com.innowise.gym.dao;

import com.innowise.gym.entity.User;
import com.innowise.gym.exception.DaoException;

import java.util.Optional;

public interface UserDao extends BaseDao<User> {
    Optional<User> findUserByLogin(String login) throws DaoException;
}
