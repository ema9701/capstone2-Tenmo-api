package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class TransferDTO {

    @JsonProperty("from")
    @NotNull
    private Long transferFrom;
    @JsonProperty("to")
    @NotNull
    private Long transferTo;
    @JsonProperty("amount")
    @DecimalMin(value = "1.00")
    private BigDecimal transferAmount;

    public TransferDTO() {
    }

    public TransferDTO(Long transferFrom, Long transferTo, BigDecimal transferAmount) {
        this.transferFrom = transferFrom;
        this.transferTo = transferTo;
        this.transferAmount = transferAmount;
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
