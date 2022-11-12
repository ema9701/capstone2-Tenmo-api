package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;


@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
@RequestMapping("/account")
public class AccountController {

    private AccountDao accountDao;
    private UserDao userDao;

    public AccountController(AccountDao accountDao) {
        this.accountDao = accountDao;

    }

    @GetMapping(path = "/userid/{userId}")
    public Account accountByAccountId(@PathVariable Long userId, Principal principal) {
        return accountDao.findAccountByUserId(userId);
    }

    @GetMapping(path = "/{accountId}")
    public Account accountByAccountId(@PathVariable int accountId) {
        return accountDao.findByAccountId(accountId);
    }

    @GetMapping(path = "/idbyname/{username}")
    public int accountIdByUserName(@PathVariable String username, Principal principal) {
        return accountDao.accountIdByUserName(username);
    }

    @GetMapping(path = "/accountid/{userId}")
    public int accountIdByUserId(@PathVariable Long userId) {
        return accountDao.accountIdByUserId(userId);
    }

}
