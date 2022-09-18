package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;

public class RequestDTO {

  private Long requesterId;
  private Long grantorId;
  private BigDecimal requestAmount;
//  @JsonIgnore
//  private boolean validate;

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
//
//  public boolean isValidate() {
//    return validate;
//  }
//
//  public void setValidate(boolean validate) {
//    this.validate = validate;
//  }

}
