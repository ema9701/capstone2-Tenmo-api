package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransactionDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.security.model.Account;
import com.techelevator.tenmo.security.model.Transaction;
import com.techelevator.tenmo.security.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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

    @GetMapping(path = "/account/{user_id}")
    public Account accountByUserId(@PathVariable Long user_id) {
        return accountDao.findAccountByUserId(user_id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/transaction")
    public void transferMoney(@RequestParam int account_id_in, @RequestParam int account_id_out, @RequestParam BigDecimal transferAmount) {
        transactionDao.transferMoney(account_id_in, account_id_out, transferAmount);
    }

    @GetMapping(path = "/user")
    public List<User> findAll() {
        return userDao.findAll();
    }

    @GetMapping(path = "/user/{username}")
    public int findIdByUserName(@PathVariable String username) {
        return userDao.findIdByUsername(username);
    }

    @GetMapping(path = "/transaction")
    public List<Transaction> allTransactions() {
        return transactionDao.allTransactions();
    }

    @GetMapping(path = "/account/{account_id}/transaction")
    public List<Transaction> transactionsByIncomingAccount(@PathVariable int account_id) {
        return transactionDao.listByIncomingAccount(account_id);

    }

    @GetMapping(path = "/user/{user_id}/transaction")
    public List<Transaction> transactionsByUserId(@PathVariable int user_id) {
        return transactionDao.transactionByUserId(user_id);
    }


    @GetMapping(path = "/transaction/{transaction_id}")
    public Transaction getTransaction(@PathVariable int transaction_id) {
        return transactionDao.getTransaction(transaction_id);
    }



}
