package com.techelevator.tenmo.model;

import com.techelevator.tenmo.Exceptions.InvalidMoneyWireException;

import java.sql.Timestamp;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import java.math.BigDecimal;

public class Request {

    private int requestId;
    private Timestamp requestDate;
    @NotNull
    private int requester;
    @NotNull
    private int grantor;
    @DecimalMin(value = "1.00")
    private BigDecimal amount;
    @NotNull
    private String status;

    public static final String[] STATUS_TEXT = {"PENDING", "APPROVED", "REJECTED"};

    public Request() {
    }

    public Request(int requestId, Timestamp requestDate, int requester, int accountTo, BigDecimal amount) {
        this.requestId = requestId;
        this.requestDate = requestDate;
        this.requester = requester;
        this.grantor = accountTo;
        this.amount = amount;
        this.status = STATUS_TEXT[0];
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public Timestamp getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Timestamp requestDate) {
        this.requestDate = requestDate;
    }

    public int getRequester() {
        return requester;
    }

    public void setRequester(int requester) {
        this.requester = requester;
    }

    public int getGrantor() {
        return grantor;
    }

    public void setGrantor(int grantor) {
        this.grantor = grantor;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isValid() {
        return getStatus().equals(STATUS_TEXT[0]);
    }

    public void finalizeRequest(boolean approve) {
        if (this.isValid() && approve) {
            this.setStatus(STATUS_TEXT[1]);
        } else if (this.isValid() && !approve) {
            this.setStatus(STATUS_TEXT[2]);
        } else {
            throw new InvalidMoneyWireException();
        }
    }
}
