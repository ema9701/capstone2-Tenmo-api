package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.techelevator.tenmo.model.Request;
import com.techelevator.tenmo.model.RequestDTO;

public interface RequestDao {

    List<Request> listRequests(Long user_id);

    Request getRequestById(int requestId);

    boolean postRequest(RequestDTO newRequest);

    void approve(Request request, int requestId);

    void reject(Request request, int request_id);

}
