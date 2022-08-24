package com.techelevator.tenmo.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import com.techelevator.tenmo.model.Request;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component 
public class JdbcRequestDao implements RequestDao{

    private JdbcTemplate jdbcTemplate; 

    public JdbcRequestDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate; 
    }

    @Override 
    public List<Request> listRequests(Long user_id) {
        List<Request> requests = new ArrayList<>(); 

        final String sql = " SELECT request_id, request_date, account_from, account_to, amount, " + 
                     " approve_request, status FROM requests " + 
                     " JOIN account ON requests.account_from = account.account_id " + 
                     " OR (requests.account_to = account.account_id) " + 
                     " WHERE user_id = ?; ";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user_id);
        while(results.next()) {
            requests.add(mapRowToRequest(results));
        } 
        return requests; 
    }

    @Override 
    public Request getRequestById(int requestId) {
        Request request = null; 

        final String sql = " SELECT request_id, request_date, account_from, account_to, amount, " + 
                           " approve_request, status FROM requests " + 
                           " WHERE request_id = ?; ";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, requestId);
        if (result.next()) {
            request = mapRowToRequest(result);
        }
        return request; 
    }

 
    @Override 
    public boolean createRequest(Request newRequest) {
        newRequest.setStatus("PENDING");
        final String sql = " INSERT INTO requests (account_from, account_to, amount, approve_request, status) " +
                           " VALUES (?, ?, ?, ?, ?) RETURNING request_id; ";
        Integer newRequestId; 
        try {
            newRequestId = jdbcTemplate.queryForObject(sql, Integer.class, newRequest.getAccountFrom(), newRequest.getAccountTo(),
            newRequest.getAmount(), newRequest.isApproveRequest(), newRequest.getStatus());
        } catch (DataAccessException e) {
            System.out.println(e.getLocalizedMessage());
            return false; 
        }
        return true; 
    }

    @Override 
    public boolean updateRequest(Request request, int request_id) { 
      final String sql = " UPDATE requests SET account_from = ?, account_to = ?, amount = ?, approve_request = ?, status = ? WHERE request_id = ?; ";
      if (!request.isApproveRequest()) {
        request.setStatus("REJECTED"); 
      } else {
        request.setStatus("APPROVED");
      }
      return (jdbcTemplate.update(sql, request.getAccountFrom(), request.getAccountTo(), 
      request.getAmount(), request.isApproveRequest(), request.getStatus(), request_id) == 1);
    }

    private Request mapRowToRequest (SqlRowSet rs) {
        Request request = new Request();

        request.setRequestId(rs.getInt("request_id"));
        request.setRequestDate(rs.getTimestamp("request_date"));
        request.setAccountFrom(rs.getInt("account_from"));
        request.setAccountTo(rs.getInt("account_to"));
        request.setAmount(rs.getBigDecimal("amount"));
        request.setApproveRequest(rs.getBoolean("approve_request"));
        request.setStatus(rs.getString("status"));

        return request; 
    }
}
