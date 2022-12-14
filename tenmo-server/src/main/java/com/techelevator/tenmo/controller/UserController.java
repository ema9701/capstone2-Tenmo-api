package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.LoginDTO;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    private UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @PreAuthorize("permitAll")
    @GetMapping(path = "")
    public List<User> listAll() {
        return userDao.listAll();
    }

    @GetMapping(path = "/{username}")
    public int findIdByUserName(@PathVariable("username") String username) {
        return userDao.findIdByUsername(username);
    }

    @GetMapping(path = "/details")
    public User findByUsername(@RequestParam String username) {
        return userDao.findByUsername(username);
    }

    @GetMapping(path = "/id/{id}")
    public User getUserById(@PathVariable Long id) {
        return userDao.getUserById(id);
    }

}


