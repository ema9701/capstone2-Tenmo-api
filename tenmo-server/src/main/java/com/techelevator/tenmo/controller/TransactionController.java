package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransactionDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.security.model.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@RestController
@PreAuthorize("isAuthenticated()")
public class TransactionController {

    private AccountDao accountDao;
    private UserDao userDao;
    private TransactionDao transactionDao;

    public TransactionController(AccountDao accountDao, UserDao userDao, TransactionDao transactionDao) {
        this.accountDao = accountDao;
        this.userDao = userDao;
        this.transactionDao = transactionDao;
    }


    @GetMapping(path = "/transaction/{transaction_id}")
    public Transaction getTransaction(@PathVariable int transaction_id) {
        return transactionDao.getTransaction(transaction_id);
    }

    @GetMapping(path = "/transaction/{username}")
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
    public void insertTransaction(@RequestBody @Valid Transaction transaction, Principal principal) {
        if (accountDao.accountIdByUserName(principal.getName()) == transaction.getAccount_in() ||
                accountDao.accountIdByUserName(principal.getName()) != transaction.getAccount_out()) {
            throw new AccessDeniedException("Please select a valid recipient");
        }
        if (!transaction.isRequesting()) {
            transactionDao.subtractFromBalance(transaction.getAmount(), transaction.getAccount_out());
            transactionDao.addToBalance(transaction.getAmount(), transaction.getAccount_in());
            transactionDao.insertTransaction(transaction);
        }
    }
}
    /*

     PREVIOUS MAPPING METHODS:

    @GetMapping(path = "/transaction")
    public List<Transaction> allTransactions() {
        return transactionDao.allTransactions();
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
    }

     */

