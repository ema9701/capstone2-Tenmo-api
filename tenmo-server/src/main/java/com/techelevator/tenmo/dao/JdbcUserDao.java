package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.Exceptions.UserNotFoundException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcUserDao implements UserDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> listAll() {
        List<User> users = new ArrayList<>();

        String sql = " SELECT * FROM tenmo_user; ";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

        while (results.next()) {
            User user = mapRowToUser(results);
            users.add(user);
        }
        return users;
    }

    @Override
    public User getUserById(Long userId) {
        String sql = " SELECT * FROM tenmo_user WHERE user_id = ?; ";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        if (results.next()) {
            return mapRowToUser(results);
        } else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public int findIdByUsername(String username) {
        String sql = " SELECT user_id FROM tenmo_user WHERE username ILIKE ?; ";
        Integer id = jdbcTemplate.queryForObject(sql, Integer.class, username);
        if (id != null) {
            return id;
        } else {
            return -1;
        }
    }

    @Override
    public User findByUsername(String username) {
        if (username == null)
            throw new IllegalArgumentException("Username cannot be null");

        for (User user : this.listAll()) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        throw new UsernameNotFoundException("User " + username + " was not found.");
    }

    @Override
    public boolean create(String username, String password) {
        String sql = " INSERT INTO tenmo_user (username, password_hash) VALUES (?, ?) RETURNING user_id; ";
         String accountSql = " INSERT INTO account (user_id) VALUES (?); ";
        String password_hash = new BCryptPasswordEncoder().encode(password);
        Integer newUserId;
        try {
            newUserId = jdbcTemplate.queryForObject(sql, Integer.class, username, password_hash);
            jdbcTemplate.update(accountSql, newUserId);
        } catch (DataAccessException e) {
            System.out.println(e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    @Override
    public int changePassword(String newPassword, Long userId) {
        String changedPasswordSql = " UPDATE tenmo_user SET password_hash = ? WHERE user_id = ?; ";
        String password_hash = new BCryptPasswordEncoder().encode(newPassword);
        return jdbcTemplate.update(changedPasswordSql, password_hash, userId);
    }

    private User mapRowToUser(SqlRowSet rs) {
        User user = new User();
        user.setId(rs.getLong("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password_hash"));
        user.setActivated(true);
        user.setAuthorities("USER");
        return user;
    }
}
