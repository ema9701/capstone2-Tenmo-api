package com.techelevator.tenmo.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.techelevator.tenmo.model.Transfer;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
@CrossOrigin
public class JdbcTransferDao implements TransferDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Transfer> listTransfers(Long userId) {
        List<Transfer> transferList = new ArrayList<>();

        String sql = "SELECT transfer_id, transfer_date, account_from, account_to, amount, transfer_status " +
                " FROM TRANSFERS  " +
                " JOIN account ON transfers.account_from = account.account_id " +
                " OR (transfers.account_to = account.account_id) " +
                " WHERE user_id = ? " +
                " ORDER BY transfer_date DESC; ";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);

        while (results.next()) {
            transferList.add(mapRowToTransfer(results));
        }
        return transferList;
    }

    @Override
    public Transfer getTransferById(int transferId) {
        Transfer transfer = null;

        String sql = "SELECT transfer_id, transfer_date, account_from, account_to, amount, transfer_status " +
                " FROM TRANSFERS " +
                " WHERE transfer_id = ?; ";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferId);
        if (result.next()) {
            transfer = mapRowToTransfer(result);
        }

        return transfer;
    }

    @Override
    public boolean createTransfer(Transfer newTransfer) {
        String sql = " INSERT INTO TRANSFERS (account_from, account_to, amount, transfer_status) " +
                " VALUES (?, ?, ?, ?) RETURNING transfer_id; ";
        newTransfer.setStatus("APPROVED");
        Integer newTransferId;
        try {
            newTransferId = jdbcTemplate.queryForObject(sql, Integer.class, newTransfer.getAccountFrom(),
                    newTransfer.getAccountTo(), newTransfer.getAmount(), newTransfer.getStatus());
            newTransfer.setTransferId(newTransferId);
        } catch (DataAccessException e) {
            System.out.println(e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean testInsert(Long from, Long to, BigDecimal amount) {

        if (from != to) {

            String sql = " INSERT INTO TRANSFERS (account_from, account_to, amount, transfer_status) " +
                    " VALUES ((SELECT account_id FROM account WHERE user_id = ?), (SELECT account_id FROM account WHERE user_id = ?), ?, 'APPROVED'); ";
            return jdbcTemplate.update(sql, from, to, amount) == 1;
        } else {
            return false;
        }
    }

    private Transfer mapRowToTransfer(SqlRowSet rowSet) {
        Transfer transfer = new Transfer();

        transfer.setTransferId(rowSet.getInt("transfer_id"));
        transfer.setTransferDate(rowSet.getTimestamp("transfer_date"));
        transfer.setAccountFrom(rowSet.getInt("account_from"));
        transfer.setAccountTo(rowSet.getInt("account_to"));
        transfer.setAmount(rowSet.getBigDecimal("amount"));
        transfer.setStatus(rowSet.getString("transfer_status"));

        return transfer;
    }

}
