package com.innowise.gym.dao;

import com.innowise.gym.entity.AbstractEntity;
import com.innowise.gym.exception.DaoException;

import java.io.Serializable;
import java.util.List;

public interface BaseDao <T extends AbstractEntity> {
    boolean insert(T t) throws DaoException;
    boolean delete(T t) throws DaoException;
    T update(T t) throws DaoException;
    List<T> findAll() throws DaoException;

}
