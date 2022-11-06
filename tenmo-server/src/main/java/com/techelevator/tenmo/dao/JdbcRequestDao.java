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

        final String sql = " SELECT request_id, request_date, requester_account, grantor_account, amount, " +
                " request_status FROM requests " +
                " JOIN account ON requests.requester_account = account.account_id " +
                " OR (requests.grantor_account = account.account_id) " +
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
        final String sql = " SELECT request_id, request_date, requester_account, grantor_account, amount, " +
                " request_status FROM requests " +
                " WHERE request_id = ?; ";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, requestId);
        if (result.next()) {
            request = mapRowToRequest(result);
        }
        return request;
    }

    @Override
    public Integer postRequest(RequestDTO newRequest) {
        final String Sql = " INSERT INTO requests (requester_account, grantor_account, amount) " +
                " VALUES ((SELECT account_id FROM account WHERE user_id = ?), (SELECT account_id FROM account WHERE user_id = ?), ?) RETURNING request_id ";
        Integer newRequestId;
        try {
            newRequestId = jdbcTemplate.queryForObject(Sql, Integer.class, newRequest.getReceivableUserId(),
                    newRequest.getPayableUserId(), newRequest.getAmount());
        } catch (DataAccessException e) {
            System.out.println(e.getLocalizedMessage());
            return -1;
        }
        return newRequestId;
    }

    @Override
    public void updateStatus(Request request, int requestId) {
        final String sql = " UPDATE requests SET request_date = ?, requester_account = ?, " +
                " grantor_account = ?, amount = ?, request_status = ? WHERE request_id = ?; ";
        jdbcTemplate.update(sql, request.getRequestDate(), request.getRequester(), request.getGrantor(),
                request.getAmount(), request.getStatus(), request.getRequestId());
    }

    private Request mapRowToRequest(SqlRowSet rs) {
        Request request = new Request();
        request.setRequestId(rs.getInt("request_id"));
        request.setRequestDate(rs.getTimestamp("request_date"));
        request.setRequester(rs.getInt("requester_account"));
        request.setGrantor(rs.getInt("grantor_account"));
        request.setAmount(rs.getBigDecimal("amount"));
        request.setStatus(rs.getString("request_status"));
        return request;
    }
}
