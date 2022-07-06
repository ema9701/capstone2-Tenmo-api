package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.security.model.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {

    List<Account> findAccounts();

    Account findAccountByUserId(int user_id);

    BigDecimal updateBalance(BigDecimal balance, int account_id);







}
