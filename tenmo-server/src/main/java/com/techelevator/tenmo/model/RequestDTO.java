package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class RequestDTO {

  private Long requestFrom;
  private Long requestTo;
  private BigDecimal requestAmount;

  public Long getRequestFrom() {
    return this.requestFrom;
  }

  public void setRequestFrom(Long requestFrom) {
    this.requestFrom = requestFrom;
  }

  public Long getRequestTo() {
    return this.requestTo;
  }

  public void setRequestTo(Long requestTo) {
    this.requestTo = requestTo;
  }

  public BigDecimal getRequestAmount() {
    return this.requestAmount;
  }

  public void setRequestAmount(BigDecimal requestAmount) {
    this.requestAmount = requestAmount;
  }

}
