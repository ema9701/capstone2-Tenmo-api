package com.techelevator.tenmo.model;

import java.sql.Timestamp;

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
    @Positive
    private BigDecimal amount;
    private boolean validate;
    private String status;

    public Request() {
    }

    public Request(int requestId, Timestamp requestDate, int requester, int accountTo, BigDecimal amount, boolean validate,
                   String status) {
        this.requestId = requestId;
        this.requestDate = requestDate;
        this.requester = requester;
        this.grantor = accountTo;
        this.amount = amount;
        this.validate = validate;
        this.status = status;
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

    public boolean isValidate() {
        return validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
