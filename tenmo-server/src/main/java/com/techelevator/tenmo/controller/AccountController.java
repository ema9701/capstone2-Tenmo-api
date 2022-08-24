package com.techelevator.tenmo.controller;
import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/account")
public class AccountController {

    private AccountDao accountDao;

    public AccountController(AccountDao accountDao) {
        this.accountDao = accountDao;
  
    }

    @GetMapping(path = "/userid/{user_id}")
    public Account accountByUserId(@PathVariable Long user_id) {
        return accountDao.findAccountByUserId(user_id);
    }

    @GetMapping(path = "/{account_id}")
    public Account accountByUserId(@PathVariable int account_id) {
        return accountDao.findByAccountId(account_id);
    }

    @GetMapping(path = "")
    public int accountIdByUserName(@RequestParam String username) {
        return accountDao.accountIdByUserName(username);
    }


}
