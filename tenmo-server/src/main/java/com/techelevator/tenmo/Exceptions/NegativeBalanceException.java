package com.techelevator.tenmo.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Cannot transfer more than your current balance.")
public class NegativeBalanceException extends RuntimeException{
}
