package com.techelevator.tenmo.security.model;

import java.math.BigDecimal;

public class Transaction {

    private int transaction_id;
    private int account_id_out;
    private int account_id_in;
    private BigDecimal transactionAmount;
    private Boolean isRequested;

    public Transaction() {

    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public int getAccount_id_out() {
        return account_id_out;
    }

    public void setAccount_id_out(int account_id_out) {
        this.account_id_out = account_id_out;
    }

    public int getAccount_id_in() {
        return account_id_in;
    }

    public void setAccount_id_in(int account_id_in) {
        this.account_id_in = account_id_in;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Boolean getRequested() {
        return isRequested;
    }

    public void setRequested(Boolean requested) {
        isRequested = requested;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "account_receiving_id=" + account_id_in +
                ", account_sending_id='" + account_id_out +
                ", transfer_amount = " + transactionAmount +
                '}';
    }
}

