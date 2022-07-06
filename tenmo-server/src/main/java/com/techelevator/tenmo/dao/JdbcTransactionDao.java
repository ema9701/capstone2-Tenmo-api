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
    private AccountDao accountDao;

    private final JdbcTemplate jdbcTemplate;

    public JdbcTransactionDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public boolean subtractFromBalance(BigDecimal money, int account_id) throws IllegalArgumentException {
        String sql = "UPDATE balance SET balance = balance - ? WHERE account_id = ?;";
        try {
            SqlRowSet updatedBalance = jdbcTemplate.queryForRowSet(sql, money, account_id);
        } catch (IllegalArgumentException e) {
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
    public boolean transferMoney(int account_id_in, int account_id_out, BigDecimal transferAmount) {
        BigDecimal zeroBalance = new BigDecimal("0.00");

        if (transferAmount.compareTo(zeroBalance) == -1 || transferAmount.compareTo(zeroBalance) == 0) {
            System.out.println("You must transfer more than 0.00");
            return false;
        }
        if (accountDao.getBalance(account_id_out).compareTo(transferAmount) == -1) {
            System.out.println("You cannot transfer an amount greater than your balance.");
            return false;
        }
        subtractFromBalance(transferAmount, account_id_out);
        addToBalance(transferAmount, account_id_in);
        return true;

    }

    @Override
    List<Transaction> allTransactions() {
        String sql = "SELECT transaction_id, account_id_in, account_id_out, is_requesting FROM transaction;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

        List<Transaction> transactions = new ArrayList<>();

        while (results.next()) {
            transactions.add(mapRowToTransaction(results));
        }
    }

    List<Transaction> transactionByUserId(int user_id);

    List<Transaction> listByOutgoingAccount(int account_id_out);

    List<Transaction> listByIncomingAccount(int account_id_in);

    BigDecimal getTransactionAmount(int transaction_id);

    Boolean isApproved(int transaction_id);

    private Transaction mapRowToTransaction(SqlRowSet rowSet) {
        Transaction transaction = new Transaction();
        transaction.setTransaction_id(rowSet.getInt("transaction_id"));
        transaction.setAccount_id_in(rowSet.getInt("account_id_in"));
        transaction.setAccount_id_out(rowSet.getInt("account_id_out"));
        transaction.setTransactionAmount(rowSet.getBigDecimal("transaction_amount"));
        transaction.setRequested(rowSet.getBoolean("is_requesting"));
        return transaction;
    }
}
