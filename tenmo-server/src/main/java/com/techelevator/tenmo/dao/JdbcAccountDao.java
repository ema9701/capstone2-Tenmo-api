package com.techelevator.tenmo.dao;
import com.techelevator.tenmo.model.Account;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
public class JdbcAccountDao implements AccountDao {

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private JdbcTemplate jdbcTemplate;

  

    @Override
    public Account findByAccountId(int account_id) {
        Account account = null;
        String sql  = " SELECT user_id, account_id, balance FROM account " +
                " WHERE account_id = ?; ";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, account_id);

        while (results.next()) {
            account = mapRowToAccount(results);
        }
        return account;

    }

    @Override
    public Account findAccountByUserId(Long user_id) {
        Account account = null;
        String sql = " SELECT user_id, account_id, balance FROM account " +
                " WHERE user_id = ?; ";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user_id);

        while (results.next()) {
            account = mapRowToAccount(results);
        }
        return account;
    }

    @Override
    public int accountIdByUserName(String username) {
        String sql = "SELECT account_id FROM account JOIN tenmo_user ON account.user_id = tenmo_user.user_id " +
                " WHERE username ILIKE ?;";
        Integer id = jdbcTemplate.queryForObject(sql, Integer.class, username);
        if (id != null) {
            return id;
        } else {
            return -1;
        }
    }

    @Override
    public boolean withdrawAmount(BigDecimal amount, int account_id) {
        if (!sufficientFunds(amount, account_id)) {
            throw new ArithmeticException("Insufficient funds for transfer.");
        } else {
        String sql = " UPDATE account SET balance = balance - ? " +
                " WHERE account_id = ?; ";
            
            return  (jdbcTemplate.update(sql, amount, account_id) == 1); 
            }
        }
    
    @Override
    public boolean depositAmount(BigDecimal amount, int account_id) { 
        String sql = "UPDATE account SET balance = balance + ? " +
                " WHERE account_id = ?; ";
           
            return  (jdbcTemplate.update(sql, amount, account_id) == 1);
        }

    @Override 
    public boolean sufficientFunds(BigDecimal amount, int account_id) {
        Account account = findByAccountId(account_id);
        BigDecimal balance = account.getBalance();
        BigDecimal zero = new BigDecimal("0.00");
        
        return balance.compareTo(amount) == 1 && amount.compareTo(zero) == 1; 
    }
        
    private Account mapRowToAccount(SqlRowSet rowSet) {
        Account account = new Account();
        account.setAccount_id(rowSet.getInt("account_id"));
        account.setUser_id(rowSet.getLong("user_id"));
        account.setBalance(rowSet.getBigDecimal("balance"));
        return account;
    }



}
