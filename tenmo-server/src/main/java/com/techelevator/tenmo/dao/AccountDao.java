package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.security.model.Account;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {

    List<Account> findAccounts();

    Account findAccountByUserId(Long user_id);

    boolean subtractFromBalance(BigDecimal balance, int account_id) throws IllegalArgumentException;

    boolean addToBalance(BigDecimal balance, int account_id) throws IllegalArgumentException;

    BigDecimal getBalanceByUserId(int user_id);

    List<Account> findUserIds();


}
