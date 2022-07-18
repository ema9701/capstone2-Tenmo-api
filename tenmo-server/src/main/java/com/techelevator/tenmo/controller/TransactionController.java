package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransactionDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transaction;
import com.techelevator.tenmo.model.TransactionStatus;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/transaction")
public class TransactionController {

    private AccountDao accountDao;
    private UserDao userDao;
    private TransactionDao transactionDao;

    public TransactionController(AccountDao accountDao, UserDao userDao, TransactionDao transactionDao) {
        this.accountDao = accountDao;
        this.userDao = userDao;
        this.transactionDao = transactionDao;
    }


    @GetMapping(path = "/{transaction_id}")
    public Transaction getTransaction(@PathVariable int transaction_id) {
        return transactionDao.getTransaction(transaction_id);
    }

    @GetMapping(path = "/{username}")
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
    @PostMapping(path = "")
    public Transaction insertTransaction(@RequestBody @Valid Transaction transaction, Principal principal) {

        if (!transaction.isIs_requesting()) {
            if (accountDao.accountIdByUserName(principal.getName()) == transaction.getAccount_in() ||
                    accountDao.accountIdByUserName(principal.getName()) != transaction.getAccount_out()) {
                throw new AccessDeniedException("Please select a valid recipient");
            } else if (accountDao.getBalance(transaction.getAccount_out()).compareTo(transaction.getAmount()) == -1) {
                throw new ArithmeticException("You cannot send more money than you have.");
            } else {
                transactionDao.subtractFromBalance(transaction.getAmount(), transaction.getAccount_out());
                transactionDao.addToBalance(transaction.getAmount(), transaction.getAccount_in());
                transactionDao.insertTransaction(transaction);
            }
        }
        if (transaction.isIs_requesting()) {
            if (accountDao.accountIdByUserName(principal.getName()) == transaction.getAccount_in() &&
                    accountDao.accountIdByUserName(principal.getName()) != transaction.getAccount_out()) {
                transactionDao.insertTransaction(transaction);
            }
        }
        return transaction;
    }

    @PutMapping(path = "")
    public TransactionStatus updateTransaction(@RequestBody @Valid TransactionStatus trans, Principal principal) {
        Transaction pendingTrans = transactionDao.getTransaction(trans.getTransaction_id());
        if (accountDao.accountIdByUserName(principal.getName()) == pendingTrans.getAccount_in()) {
            throw new AccessDeniedException("You cannot approve a transaction you requested");
        } else if (accountDao.getBalance(pendingTrans.getAccount_out()).compareTo(pendingTrans.getAmount()) == -1) {
            throw new ArithmeticException("You cannot send more money than you have.");
        }

        if (trans.isChecked()) {
            throw new AuthorizationServiceException("You cannot re-approve a previously approved transfer request");
        } else {
            transactionDao.approveTransaction(true, trans.getStatus_id(), trans.getTransaction_id());
            transactionDao.subtractFromBalance(pendingTrans.getAmount(), pendingTrans.getAccount_out());
            transactionDao.addToBalance(pendingTrans.getAmount(), pendingTrans.getAccount_in());
            return trans;
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

