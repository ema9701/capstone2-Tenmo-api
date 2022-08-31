package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.LoginDTO;
import com.techelevator.tenmo.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UserDao {

    List<User> listAll();

    User getUserById(Long userId);

    User findByUsername(String username);

    int findIdByUsername(String username);

    boolean create(String username, String password);

    int changePassword(String newPassword, Long userId);

}
