package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferDTO;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.techelevator.tenmo.model.Transfer;

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
        final String sql = "SELECT transfer_id, transfer_date, account_from, account_to, amount, transfer_status " +
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
       final String sql = "SELECT transfer_id, transfer_date, account_from, account_to, amount, transfer_status " +
                " FROM TRANSFERS " +
                " WHERE transfer_id = ?; ";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferId);
        if (result.next()) {
            transfer = mapRowToTransfer(result);
        }

        return transfer;
    }

    @Override
    public Integer postTransfer(TransferDTO transfer) {
       final String sql = " INSERT INTO TRANSFERS (account_from, account_to, amount) " +
                " VALUES ((SELECT account_id FROM account WHERE user_id = ?), " +
                " (SELECT account_id FROM account WHERE user_id = ?), ?) RETURNING transfer_id; ";
        Integer newTransferId;
        try {
            newTransferId = jdbcTemplate.queryForObject(sql, Integer.class, transfer.getTransferFrom(), transfer.getTransferTo(), transfer.getTransferAmount());
        } catch (DataAccessException e) {
            System.out.println(e.getLocalizedMessage());
            return -1;
        }
        return newTransferId;
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
