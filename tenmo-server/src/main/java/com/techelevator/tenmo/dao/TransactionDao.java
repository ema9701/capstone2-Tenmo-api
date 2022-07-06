package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.security.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionDao {

    List<Transaction> allTransactions();

    List<Transaction> transactionByUserId(int user_id);

    List<Transaction> listByOutgoingAccount(int account_id_out);

    List<Transaction> listByIncomingAccount(int account_id_in);

    BigDecimal getTransactionAmount(int transaction_id);

    Boolean isApproved(int transaction_id);

}
