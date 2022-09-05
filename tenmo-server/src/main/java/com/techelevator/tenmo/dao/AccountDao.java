package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import java.math.BigDecimal;

public interface AccountDao {

    Account findAccountByUserId(Long userId);

    Account findByAccountId(int accountId);

    int accountIdByUserName(String username);

    int accountIdByUserId(Long userId);

    boolean withdrawAmount(BigDecimal balance, int accountId);

    boolean depositAmount(BigDecimal balance, int accountId);

    boolean sufficientFunds(BigDecimal amount, int accountId);

    boolean transact(BigDecimal balance, int from, int to) throws ArithmeticException;
}
