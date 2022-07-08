package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.security.model.Account;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {

    List<Account> findAccounts();

    Account findAccountByUserId(Long user_id);

    BigDecimal getBalanceByUserId(int user_id);

    List<Account> findUserIds();

    BigDecimal getBalance(int account_id);

    int accountIdByUserName(String username);

}
