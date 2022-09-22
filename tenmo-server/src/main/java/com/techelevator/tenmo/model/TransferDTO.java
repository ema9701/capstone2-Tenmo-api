package com.techelevator.tenmo.model;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class TransferDTO {

    @NotNull
    private Long transferFrom;
    @NotNull
    private Long transferTo;
    @Positive
    private BigDecimal transferAmount;

    public TransferDTO() {
    }

    public Long getTransferFrom() {
        return transferFrom;
    }

    public void setTransferFrom(Long transferFrom) {
        this.transferFrom = transferFrom;
    }

    public Long getTransferTo() {
        return transferTo;
    }

    public void setTransferTo(Long transferTo) {
        this.transferTo = transferTo;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }
}
