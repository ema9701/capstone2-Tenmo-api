package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class RequestDTO {

    @NotNull
    @JsonProperty("requester")
    private Long requesterId;
    @NotNull
    @JsonProperty("grantor")
    private Long grantorId;
    @DecimalMin(value = "1.00")
    @JsonProperty("amount")
    private BigDecimal requestAmount;

    public RequestDTO() {
    }

    public RequestDTO(Long requesterId, Long grantorId, BigDecimal requestAmount) {
        this.requesterId = requesterId;
        this.grantorId = grantorId;
        this.requestAmount = requestAmount;
    }

    public Long getRequesterId() {
        return this.requesterId;
    }

    public void setRequesterId(Long requesterId) {
        this.requesterId = requesterId;
    }

    public Long getGrantorId() {
        return this.grantorId;
    }

    public void setGrantorId(Long grantorId) {
        this.grantorId = grantorId;
    }

    public BigDecimal getRequestAmount() {
        return this.requestAmount;
    }

    public void setRequestAmount(BigDecimal requestAmount) {
        this.requestAmount = requestAmount;
    }

}
