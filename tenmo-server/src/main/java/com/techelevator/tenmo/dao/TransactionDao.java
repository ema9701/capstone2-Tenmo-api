package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.security.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionDao {

    List<Transaction> allTransactions();

    List<Transaction> transactionByUserId(int user_id);

    List<Transaction> listByOutgoingAccount(int account_id_out);

    List<Transaction> listByIncomingAccount(int account_id_in);

    Transaction getTransaction(int transaction_id);

    Boolean isRequesting(int transaction_id);

    boolean subtractFromBalance(BigDecimal balance, int account_id) throws IllegalArgumentException;

    boolean addToBalance(BigDecimal balance, int account_id) throws IllegalArgumentException;

    boolean transferMoney(int account_id_in, int account_id_out, BigDecimal transferAmount);




}
