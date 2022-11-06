package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class RequestDTO {

    @NotNull
    @JsonProperty("from")
    private Long receivableUserId;
    @NotNull
    @JsonProperty("to")
    private Long payableUserId;
    @DecimalMin(value = "1.00")
    @JsonProperty("amount")
    private BigDecimal amount;

    public RequestDTO() {
    }

    public RequestDTO(Long receivableUserId, Long payableUserId, BigDecimal amount) {
        this.receivableUserId = receivableUserId;
        this.payableUserId = payableUserId;
        this.amount = amount;
    }

    public Long getReceivableUserId() {
        return this.receivableUserId;
    }

    public void setReceivableUserId(Long receivableUserId) {
        this.receivableUserId = receivableUserId;
    }

    public Long getPayableUserId() {
        return this.payableUserId;
    }

    public void setPayableUserId(Long payableUserId) {
        this.payableUserId = payableUserId;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}
