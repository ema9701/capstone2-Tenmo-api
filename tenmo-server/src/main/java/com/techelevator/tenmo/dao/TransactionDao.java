package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.security.model.Transaction;
import com.techelevator.tenmo.security.model.TransactionStatus;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionDao {



    List<Transaction> listUserTrans(String username);

    Transaction getTransaction(int transaction_id);

    boolean subtractFromBalance(BigDecimal balance, int account_id) throws IllegalArgumentException;

    boolean addToBalance(BigDecimal balance, int account_id) throws IllegalArgumentException;

    void transactionPost(int account_out, int account_in, BigDecimal amount, boolean is_Requested);

    boolean transferMoney(int account_id_in, int account_id_out, BigDecimal transferAmount);

    boolean insertTransaction(Transaction transaction);



    /*

    List<Transaction> listByOutgoingAccount(int account_id_out);
    List<Transaction> listByIncomingAccount(int account_id_in);
    List<Transaction> allTransactions();

     */



}
