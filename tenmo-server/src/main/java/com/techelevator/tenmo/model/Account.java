package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Account {

    private int accountId;
    private Long userId;
    private BigDecimal balance;

    public Account() {

    }

    public int getaccountId() {
        return accountId;
    }

    public void setaccountId(int accountId) {
        this.accountId = accountId;
    }

    public Long getuserId() {
        return userId;
    }

    public void setuserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
