package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/account")
@CrossOrigin
public class AccountController {

    private AccountDao accountDao;

    public AccountController(AccountDao accountDao) {
        this.accountDao = accountDao;

    }

    @GetMapping(path = "/userid/{userId}")
    public Account accountByUserId(@PathVariable Long userId) {
        return accountDao.findAccountByUserId(userId);
    }

    @GetMapping(path = "/{accountId}")
    public Account accountByUserId(@PathVariable int accountId) {
        return accountDao.findByAccountId(accountId);
    }

    @GetMapping(path = "")
    public int accountIdByUserName(@RequestParam String username) {
        return accountDao.accountIdByUserName(username);
    }

    @GetMapping(path = "/accountid/{userId}")
    public int accountIdByUserId(@PathVariable Long userId) {
        return accountDao.accountIdByUserId(userId);
    }

}
