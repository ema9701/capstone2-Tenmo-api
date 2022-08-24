package com.techelevator.tenmo.dao;
import com.techelevator.tenmo.model.Account;
import java.math.BigDecimal;


public interface AccountDao {

    
    Account findAccountByUserId(Long user_id);

    Account findByAccountId(int account_id);

    int accountIdByUserName(String username); 

    boolean withdrawAmount(BigDecimal balance, int account_id);

    boolean depositAmount(BigDecimal balance, int account_id); 

    boolean sufficientFunds(BigDecimal amount, int account_id);
}
