package com.techelevator.tenmo.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class Transfer {

    private int transferId;
    private Timestamp transferDate;
    @NotNull
    private int accountFrom;
    @NotNull
    private int accountTo;
    @DecimalMin(value = "1.00")
    private BigDecimal amount;
    private String status;

    public static final String[] STATUS_TEXT = {"PENDING", "APPROVED", "REJECTED"};

    public Transfer() {
    }

    public Transfer(int transferId, Timestamp transferDate, int accountFrom, int accountTo, BigDecimal amount) {
        this.transferId = transferId;
        this.transferDate = transferDate;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
        this.status = STATUS_TEXT[1];
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public Timestamp getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Timestamp transferDate) {
        this.transferDate = transferDate;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
