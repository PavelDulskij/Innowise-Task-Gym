package com.innowise.gym.dao.impl;

import com.innowise.gym.dao.UserDao;
import com.innowise.gym.entity.User;
import com.innowise.gym.exception.DaoException;
import com.innowise.gym.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private static final Logger log = LogManager.getLogger();
    private static final String INSERT_USER = "INSERT INTO users(login, password, email) VALUES (?, ?, ?)";
    private static final String DELETE_USER = "DELETE FROM users WHERE id = ?";
    private static final String FIND_BY_LOGIN = "SELECT id, login, password, email FROM users WHERE login = ?";
    private static final String FIND_ALL = "SELECT id, login, password, email FROM users";
    private static final String UPDATE_USER = "UPDATE users SET login = ?, password = ?, email = ? WHERE id = ?";

    @Override
    public boolean insert(User user) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_USER)) {

            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());

            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            log.error("Error inserting user", e);
            throw new DaoException("Error inserting user", e);
        }
    }

    @Override
    public boolean delete(User user) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_USER)) {

            ps.setLong(1, user.getId());
            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            log.error("Error deleting user", e);
            throw new DaoException("Error deleting user", e);
        }
    }

    @Override
    public User update(User user) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_USER)) {

            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setLong(4, user.getId());

            ps.executeUpdate();
            return user;

        } catch (SQLException e) {
            log.error("Error updating user", e);
            throw new DaoException("Error updating user", e);
        }
    }

    @Override
    public List<User> findAll() throws DaoException {
        List<User> users = new ArrayList<>();

        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                users.add(mapUser(rs));
            }

        } catch (SQLException e) {
            log.error("Error finding users", e);
            throw new DaoException("Error finding users", e);
        }

        return users;
    }

    @Override
    public Optional<User> findUserByLogin(String login) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_BY_LOGIN)) {

            ps.setString(1, login);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapUser(rs));
                }
            }
        } catch (SQLException e) {
            log.error("Error finding user by login", e);
            throw new DaoException("Error finding user by login", e);
        }

        return Optional.empty();
    }

    private User mapUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setLogin(rs.getString("login"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        return user;
    }
}
