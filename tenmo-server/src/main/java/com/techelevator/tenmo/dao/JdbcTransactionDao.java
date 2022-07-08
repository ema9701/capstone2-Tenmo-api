package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.security.model.Account;
import com.techelevator.tenmo.security.model.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransactionDao implements TransactionDao {
    private final AccountDao accountDao;

    private final JdbcTemplate jdbcTemplate;

    public JdbcTransactionDao(AccountDao accountDao, JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
        this.accountDao = accountDao;
    }

    @Override
    public boolean subtractFromBalance(BigDecimal money, int account_id) throws IllegalArgumentException {
        String sql = "UPDATE account SET balance = balance - ? WHERE account_id = ?; ";
        try {
            jdbcTemplate.update(sql, money, account_id);
        } catch (IllegalArgumentException e) {
            System.out.println("Please choose an existing account_id");
            return false;
        }

        return true;
    }

    @Override
    public boolean addToBalance(BigDecimal money, int account_id) throws IllegalArgumentException {

        Account account = new Account();

        String sql = "UPDATE account SET balance = balance + ? WHERE account_id = ?; ";
        if (account.getAccount_id() == account_id) {
            System.out.println("You cannot send money to yourself.");
            return false;
        } else {
            try {
                jdbcTemplate.update(sql, money, account_id);
            } catch (IllegalArgumentException e) {
                System.out.println("Please choose an existing account_id");
                return false;
            }

            return true;
        }
    }

    @Override
    public boolean transferMoney(int account_id_in, int account_id_out, BigDecimal transferAmount) {
        BigDecimal zeroBalance = new BigDecimal("0.00");
        BigDecimal balance = accountDao.getBalance(account_id_out);
        Transaction transaction = new Transaction();

        if (account_id_in == account_id_out) {
            System.out.println("You may not send funds to yourself.");
            return false;
        }
        if (balance.compareTo(transferAmount) == -1) {
            System.out.println("You cannot transfer an amount greater than your balance.");
            return false;
        }


        transactionSave(transferAmount);
        subtractFromBalance(transferAmount, account_id_out);
        addToBalance(transferAmount, account_id_in);

        return true;
    }

    public void transactionSave(BigDecimal amount) {

        String sql = " INSERT INTO transactions(amount) " +
                " VALUES(?); ";
        jdbcTemplate.update(sql, amount);
    }

    public void transaction_account(int id, int account_id_in, int account_id_out) {
        String sql = "INSERT INTO transaction_account(transaction_id, account_id_in, account_id_out) " +
                "VALUES (?, ?, ?); ";
        jdbcTemplate.update(sql, id, account_id_in, account_id_out);
    }

    @Override
    public List<Transaction> allTransactions() {
        String sql = "SELECT transaction_id, account_id_in, account_id_out, transaction_amount, is_requesting FROM transaction; ";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

        List<Transaction> transactions = new ArrayList<>();

        while (results.next()) {
            transactions.add(mapRowToTransaction(results));
        }
        return transactions;
    }

    @Override
    public List<Transaction> transactionByUserId(int user_id) {
        List<Transaction> transactionsUserId = new ArrayList<>();
        String sql = "SELECT transaction_id, account_id_in, account_id_out, transaction_amount, is_requesting " +
                "FROM transaction " +
                "WHERE " +
                "user_id = ?; ";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user_id);
        while (results.next()) {
            transactionsUserId.add(mapRowToTransaction(results));
        }
        return transactionsUserId;
    }

    @Override
    public List<Transaction> listByOutgoingAccount(int account_id_out) {
        List<Transaction> outgoingId = new ArrayList<>();
        String sql = "SELECT transaction_id, account_id_in, account_id_out, transaction_amount, is_requesting " +
                "FROM transaction " +
                "WHERE account_id_out = ?; ";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, account_id_out);
        while (results.next()) {
            outgoingId.add(mapRowToTransaction(results));
        }
        return outgoingId;
    }

    @Override
    public List<Transaction> listByIncomingAccount(int account_id_in) {
        List<Transaction> incomingId = new ArrayList<>();
        String sql = "SELECT transaction_id, account_id_in, account_id_out, transaction_amount, is_requesting " +
                "FROM transaction " +
                "WHERE account_id_in = ?; ";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, account_id_in);
        while (results.next()) {
            incomingId.add(mapRowToTransaction(results));
        }
        return incomingId;
    }

    @Override
    public BigDecimal getTransactionAmount(int transaction_id) {
        BigDecimal amount = new BigDecimal("0.00");
        String sql = "SELECT transaction_amount FROM transaction WHERE transaction_id = ?; ";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transaction_id);
        if (results.next()) {
            Transaction transaction = mapRowToTransaction(results);
            amount = transaction.getTransactionAmount();
            return amount;
        }
        return amount;
    }

    @Override
    public Boolean isRequesting(int transaction_id) {
        Transaction transaction = new Transaction();
        String sql = "SELECT is_requesting FROM transaction WHERE transaction_id = ?; ";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transaction_id);
        if (results.next()) {
            transaction = mapRowToTransaction(results);
        }
        return transaction.getRequested();
    }

    private Transaction mapRowToTransaction(SqlRowSet rowSet) {
        Transaction transaction = new Transaction();
        transaction.setTransaction_id(rowSet.getInt("transaction_id"));
        transaction.setAccount_id_in(rowSet.getInt("account_id_in"));
        transaction.setAccount_id_out(rowSet.getInt("account_id_out"));
        transaction.setTransactionAmount(rowSet.getBigDecimal("transaction_amount"));
//        transaction.setRequested(rowSet.getBoolean("is_requesting"));
        return transaction;
    }


}
