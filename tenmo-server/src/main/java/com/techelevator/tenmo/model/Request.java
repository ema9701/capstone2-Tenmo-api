package com.techelevator.tenmo.model;
import java.sql.Timestamp;
import java.math.BigDecimal;

public class Request {
    
    private int requestId; 
    private Timestamp requestDate; 
    private int accountFrom; 
    private int accountTo;
    private BigDecimal amount; 
    private Boolean approveRequest; 
    private String status; 

    public Request() {}

    public Request(int requestId, Timestamp requestDate, int accountFrom, int accountTo, BigDecimal amount, Boolean approveRequest, String status) {
        this.requestId = requestId; 
        this.requestDate = requestDate; 
        this.accountFrom = accountFrom; 
        this.accountTo = accountTo; 
        this.amount = amount;  
        this.approveRequest = approveRequest;
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

    public int getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(int accountFrom) {
        this.accountFrom = accountFrom;
    }

    public int getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(int accountTo) {
        this.accountTo = accountTo;
    }

    public BigDecimal getAmount() {
        return amount; 
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount; 
    }

    public Boolean isApproveRequest() {
        return approveRequest;
    }

    public void setApproveRequest(Boolean approveRequest) {
        this.approveRequest = approveRequest;
    }

    public String getStatus() {
        return status; 
    }

    public void setStatus(String status) {
        this.status = status; 
    }

 

    
}
