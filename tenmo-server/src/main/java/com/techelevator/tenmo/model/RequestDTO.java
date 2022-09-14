package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class RequestDTO {

  private Long requestFromId;
  private Long requestToId;
  private BigDecimal requestAmount;

  public Long getRequestFromId() {
    return this.requestFromId;
  }

  public void setRequestFromId(Long requestFromId) {
    this.requestFromId = requestFromId;
  }

  public Long getRequestToId() {
    return this.requestToId;
  }

  public void setRequestToId(Long requestToId) {
    this.requestToId = requestToId;
  }

  public BigDecimal getRequestAmount() {
    return this.requestAmount;
  }

  public void setRequestAmount(BigDecimal requestAmount) {
    this.requestAmount = requestAmount;
  }

}
