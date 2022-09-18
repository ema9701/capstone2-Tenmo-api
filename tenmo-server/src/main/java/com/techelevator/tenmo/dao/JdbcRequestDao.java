package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.RequestDTO;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import com.techelevator.tenmo.model.Request;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcRequestDao implements RequestDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcRequestDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Request> listRequests(Long userId) {
        List<Request> requests = new ArrayList<>();

        final String sql = " SELECT request_id, request_date, account_from, account_to, amount, " +
                " approve_request, request_status FROM requests " +
                " JOIN account ON requests.account_from = account.account_id " +
                " OR (requests.account_to = account.account_id) " +
                " WHERE user_id = ?; ";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while (results.next()) {
            requests.add(mapRowToRequest(results));
        }
        return requests;
    }

    @Override
    public Request getRequestById(int requestId) {
        Request request = null;

        final String sql = " SELECT request_id, request_date, account_from, account_to, amount, " +
                " approve_request, request_status FROM requests " +
                " WHERE request_id = ?; ";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, requestId);
        if (result.next()) {
            request = mapRowToRequest(result);
        }
        return request;
    }

    @Override
    public boolean postRequest(RequestDTO newRequest) {
        final String Sql = " INSERT INTO requests (account_from, account_to, amount, request_status) " +
                " VALUES ((SELECT account_id FROM account WHERE user_id = ?), (SELECT account_id FROM account WHERE user_id = ?), ?, 'PENDING') RETURNING request_id ";
        Integer newRequestId;
        try {
            newRequestId = jdbcTemplate.queryForObject(Sql, Integer.class, newRequest.getRequestFrom(), newRequest.getRequestTo(), newRequest.getRequestAmount());
        } catch (DataAccessException e) {
            System.out.println(e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    @Override
    public void approve(Request request) {
        final String sql = " UPDATE requests SET account_from = ?, account_to = ?, amount = ?, approve_request = ?,  request_status = ? WHERE request_id = ?; ";
        request.setApprove(true);
        request.setStatus("APPROVED");
        jdbcTemplate.update(sql, request);
    }

    @Override
    public void reject(Request request) {
        final String sql = " UPDATE requests SET approve_request = ?, request_status = ? WHERE request_id = ?; ";
        request.setApprove(false);
        request.setStatus("REJECTED");
        jdbcTemplate.update(sql, request);
    }

    private Request mapRowToRequest(SqlRowSet rs) {
        Request request = new Request();

        request.setRequestId(rs.getInt("request_id"));
        request.setRequestDate(rs.getTimestamp("request_date"));
        request.setAccountFrom(rs.getInt("account_from"));
        request.setAccountTo(rs.getInt("account_to"));
        request.setAmount(rs.getBigDecimal("amount"));
        request.setApprove(rs.getBoolean("approve_request"));
        request.setStatus(rs.getString("request_status"));

        return request;
    }
}
