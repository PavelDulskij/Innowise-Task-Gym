package com.innowise.gym.dao;

import java.io.Serializable;

public interface BaseDao <T extends Serializable & Cloneable> {
    boolean save();
    boolean delete();
    boolean update();
}
