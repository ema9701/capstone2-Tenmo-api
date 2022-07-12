package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransactionDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.security.model.Account;
import com.techelevator.tenmo.security.model.Transaction;
import com.techelevator.tenmo.security.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@PreAuthorize("isAuthenticated()")
@RestController
public class AccountController {

    private AccountDao accountDao;
    private UserDao userDao;
    private TransactionDao transactionDao;

    public AccountController(AccountDao accountDao, UserDao userDao, TransactionDao transactionDao) {
        this.accountDao = accountDao;
        this.userDao = userDao;
        this.transactionDao = transactionDao;
    }

    @GetMapping(path = "/account")
    public List<Account> list() {
        return accountDao.findAccounts();
    }

    @GetMapping(path = "/account/user/{user_id}")
    public Account accountByUserId(@PathVariable Long user_id) {
        return accountDao.findAccountByUserId(user_id);
    }

    @GetMapping(path = "/account/{account_id}")
    public Account accountByUserId(@PathVariable int account_id) {
        return accountDao.findByAccountId(account_id);
    }

    @GetMapping(path = "/account/id/{username}")
    public int accountIdByUserName(@PathVariable String username) {
        return accountDao.accountIdByUserName(username);
    }

    @GetMapping(path = "account/balance/{account_id}")
    public BigDecimal getBalance(@PathVariable int account_id) {
        return accountDao.getBalance(account_id);
    }


    /*
        MAPPING METHODS THAT WERE MOVED TO RESPECTIVE CONTROLLERS:

            USER MAPPING:
                @GetMapping(path = "/users")
    public List<User> findAllSafe() {
        return userDao.findAllSafe();
    }


    @GetMapping(path = "/user_id/{username}")
    public int findIdByUserName(@PathVariable("username") String username) {
        return userDao.findIdByUsername(username);
    }

    @GetMapping(path = "/user")
    public User findByUsername(@RequestParam String username) {
        return userDao.findByUsername(username);
    }


    TRANSACTION MAPPING:

     @GetMapping(path = "/transaction")
    public List<Transaction> allTransactions() {
        return transactionDao.allTransactions();
    }

    @GetMapping(path = "/transaction/{transaction_id}")
    public Transaction getTransaction(@PathVariable int transaction_id) {
        return transactionDao.getTransaction(transaction_id);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(path = "/account/{account_id}/transaction")
    public List<Transaction> transactionsByIncomingAccount(@PathVariable int account_id, Principal principal) {
        return transactionDao.listByIncomingAccount(account_id);

    }

    @GetMapping(path = "/user/{username}/transaction")
    public List<Transaction> transactionsByUsername(@PathVariable String username, Principal principal) {
        List<Transaction> blank = new ArrayList<>();
        if (username.equalsIgnoreCase(principal.getName())) {
            try {
                return transactionDao.listUserTrans(username);
            } catch (AccessDeniedException e) {
                e.getLocalizedMessage();
            }
        }
        return blank;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/transaction")
    public void transferMoney(@RequestParam int account_id_in, @RequestParam int account_id_out, @RequestParam BigDecimal transferAmount, Principal principal) {
        if (accountDao.accountIdByUserName(principal.getName()) == account_id_out) {
            transactionDao.transferMoney(account_id_in, account_id_out, transferAmount);
        } else {
            throw new AccessDeniedException("Access denied");
        }
     */

}
