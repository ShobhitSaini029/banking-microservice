package com.nagarro.account.axception;

public class InsufficientFundsException extends RuntimeException {

	public InsufficientFundsException(String message) {
        super(message);
    }
}
