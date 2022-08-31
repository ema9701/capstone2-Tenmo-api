package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.techelevator.tenmo.model.Request;

public interface RequestDao {

    List<Request> listRequests(Long user_id);

    Request getRequestById(int requestId);

    boolean createRequest(Request newRequest);

    boolean updateRequest(Request request, int request_id);

}
