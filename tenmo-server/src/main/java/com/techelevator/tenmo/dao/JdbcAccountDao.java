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
    public Account findByAccountId(int accountId) {
        Account account = null;
        String sql = " SELECT * FROM account " +
                " WHERE account_id = ?; ";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);

        if (results.next()) {
            account = mapRowToAccount(results);
        }
        return account;

    }

    @Override
    public Account findAccountByUserId(Long userId) {
        Account account = null;
        String sql = " SELECT * FROM account " +
                " WHERE user_id = ?; ";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);

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
    public int accountIdByUserId(Long userId) {
        String sql = " SELECT account_id FROM account WHERE user_id = ?; ";

        Integer id = jdbcTemplate.queryForObject(sql, Integer.class, userId);

        if (id != null) {
            return id;
        } else {
            return -1;
        }
    }

    @Override
    public boolean withdrawAmount(BigDecimal amount, int accountId) {
        if (!sufficientFunds(amount, accountId)) {
            throw new ArithmeticException("Insufficient funds for transfer.");
        } else {
            String sql = " UPDATE account SET balance = balance - ? " +
                    " WHERE account_id = ?; ";

            return (jdbcTemplate.update(sql, amount, accountId) == 1);
        }
    }

    @Override
    public boolean depositAmount(BigDecimal amount, int accountId) {
        String sql = "UPDATE account SET balance = balance + ? " +
                " WHERE account_id = ?; ";

        return (jdbcTemplate.update(sql, amount, accountId) == 1);
    }

    @Override
    public boolean sufficientFunds(BigDecimal amount, int accountId) {
        Account account = findByAccountId(accountId);
        return account.getBalance().compareTo(amount) > 0;
    }

    private Account mapRowToAccount(SqlRowSet rowSet) {
        Account account = new Account();
        account.setaccountId(rowSet.getInt("account_id"));
        account.setuserId(rowSet.getLong("user_id"));
        account.setBalance(rowSet.getBigDecimal("balance"));
        return account;
    }

}
