package com.techelevator.tenmo.dao;


import com.techelevator.tenmo.security.model.Account;
import com.techelevator.tenmo.security.model.User;
import org.aopalliance.intercept.Invocation;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.security.auth.login.AccountNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao{

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Account> findAccounts(){
        List<Account> account = new ArrayList();

        String sql = "SELECT user_id, account_id, balance FROM account;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

        while(results.next()){
            Account accountSql = mapRowToAccount(results);
            account.add(accountSql);
        }
        return account;
    }

    @Override
    public Account findAccountByUserId(Long user_id){
        Account account = null;
        String sql = "SELECT user_id, account_id, balance FROM account " +
                     "WHERE user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

        while(results.next()){
            account = mapRowToAccount(results);
        }
        return account;
    }

    @Override
    public boolean subtractFromBalance(BigDecimal money, int account_id) throws IllegalArgumentException {
        String sql = "UPDATE balance SET balance = balance - ? WHERE account_id = ?;";
        try {
            SqlRowSet updatedBalance = jdbcTemplate.queryForRowSet(sql, money, account_id);
        } catch (IllegalArgumentException e){
            System.out.println("Please choose an existing account_id");
            return false;
        }

        return true;
    }

    @Override
    public boolean addToBalance(BigDecimal money, int account_id) throws IllegalArgumentException {

        Account account = new Account();

        String sql = "UPDATE balance SET balance = balance + ? WHERE account_id = ?;";
        if (account.getAccount_id() == account_id) {
            System.out.println("You cannot send money to yourself.");
            return false;
        } else {
            try {
                SqlRowSet updatedBalance = jdbcTemplate.queryForRowSet(sql, money, account_id);
            } catch (IllegalArgumentException e) {
                System.out.println("Please choose an existing account_id");
                return false;
            }

            return true;
        }
    }


    @Override
    public BigDecimal getBalanceByUserId(int user_id){
        String sql = "SELECT balance FROM account WHERE user_id = ?;";
        SqlRowSet rowset = jdbcTemplate.queryForRowSet(sql, user_id);
        BigDecimal balance = new BigDecimal("0.00");

        if (rowset.next()){
            balance = rowset.getBigDecimal("balance");
        } else {
            throw new RuntimeException();
        }
        return balance;
    }

    @Override
    public List<Account> findUserIds(){
        List<Account> user_ids = new ArrayList<>();
        String sql = "SELECT user_id FROM account;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

        while(results.next()){
            Account accountSql = mapRowToAccount(results);
            user_ids.add(accountSql);
        }
        return user_ids;
    }

    private Account mapRowToAccount(SqlRowSet rowSet){
        Account account = new Account();
        account.setAccount_id(rowSet.getInt("account_id"));
        account.setUser_id(rowSet.getLong("user_id"));
        account.setBalance(rowSet.getBigDecimal("balance"));
        return account;
    }
}
