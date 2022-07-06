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
import java.security.Principal;
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
    @Override
    public BigDecimal getBalance(int account_id){
        String sql = "SELECT balance FROM account WHERE account_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, account_id);
        BigDecimal bigDecimal = new BigDecimal("0.00");

        if(results.next()){
            Account account = mapRowToAccount(results);
            return account.getBalance();
        }
        else {
            return bigDecimal;
        }

    }

    private Account mapRowToAccount(SqlRowSet rowSet){
        Account account = new Account();
        account.setAccount_id(rowSet.getInt("account_id"));
        account.setUser_id(rowSet.getLong("user_id"));
        account.setBalance(rowSet.getBigDecimal("balance"));
        return account;
    }
}
