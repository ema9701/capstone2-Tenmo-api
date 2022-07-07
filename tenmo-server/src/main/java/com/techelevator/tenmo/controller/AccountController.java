package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransactionDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.security.model.Account;
import com.techelevator.tenmo.security.model.Transaction;
import com.techelevator.tenmo.security.model.User;
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
    public List<Account> list() {return accountDao.findAccounts();}

//    @GetMapping(path = "/account")
//    public List<Account> findUserIds() {return accountDao.findUserIds();}

    @GetMapping(path = "/account/{id}")
    public Account accountByUserId(@PathVariable Long user_id)  {
        return accountDao.findAccountByUserId(user_id);
    }

//    @PutMapping(path = "/account/{id}")
//    public boolean subtractFromBalance(@PathVariable BigDecimal balance, int account_id) {
//        return transactionDao.subtractFromBalance(balance, account_id);
//    }
//
//    @PutMapping(path = "/account/{id}")
//    public boolean addToBalance(@PathVariable BigDecimal balance, int account_id) {
//        return transactionDao.addToBalance(balance, account_id);
//    }

//    @PreAuthorize("hasRole('USER')")
//    @GetMapping (path = "/account/{id}")
//    public BigDecimal getBalanceByUserId(int user_id){
//        return accountDao.getBalanceByUserId(user_id);
//    }

    @GetMapping(path = "/user")
    public List<User>  findAll() {return userDao.findAll();}

//    @GetMapping(path = "/user/{id}")
//    public User findByUserName(@RequestParam String username) {
//        return userDao.findByUsername(username);
//    }

    @GetMapping(path = "/user?username_like=")
    public int findIdByUserName(@RequestParam String username) {
        return userDao.findIdByUsername(username);
    }

    @GetMapping(path = "/transaction")
    public List<Transaction> allTransactions() {
        return transactionDao.allTransactions();
    }

    @GetMapping(path = "/user/{id}/transaction")
    public List<Transaction> transactionsByUserId(@PathVariable int user_id) {
        return transactionDao.transactionByUserId(user_id);
    }
//
//    @GetMapping(path = "/user/{id}/transaction")
//    public List<Transaction> listOutgoingTransfers(@PathVariable  int account_id_out) {
//        return transactionDao.listByOutgoingAccount(account_id_out);
//    }
//
//    @GetMapping(path = "/user/{id}/transaction")
//    public List<Transaction> listIncomingTransfers(@PathVariable int account_id_in) {
////        return transactionDao.listByIncomingAccount(account_id_in);
//    }

    @GetMapping(path = "/transaction/{id}")
    public BigDecimal getTransactionAmount(@PathVariable int transaction_id) {
        return transactionDao.getTransactionAmount(transaction_id);
    }

//    @GetMapping(path = "/transaction/{id}")
//    public Boolean isApproved(@PathVariable int transaction_id) {
//        return transactionDao.isRequesting(transaction_id);
//    }






}
