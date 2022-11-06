package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class TransferDTO {

    @JsonProperty("from")
    @NotNull
    private Long payableUserId;
    @JsonProperty("to")
    @NotNull
    private Long receivableUserId;
    @JsonProperty("amount")
    @DecimalMin(value = "1.00")
    private BigDecimal amount;

    public TransferDTO() {
    }

    public TransferDTO(Long payableUserId, Long receivableUserId, BigDecimal amount) {
        this.payableUserId = payableUserId;
        this.receivableUserId = receivableUserId;
        this.amount = amount;
    }

    public Long getPayableUserId() {
        return payableUserId;
    }

    public void setPayableUserId(Long payableUserId) {
        this.payableUserId = payableUserId;
    }

    public Long getReceivableUserId() {
        return receivableUserId;
    }

    public void setReceivableUserId(Long receivableUserId) {
        this.receivableUserId = receivableUserId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
