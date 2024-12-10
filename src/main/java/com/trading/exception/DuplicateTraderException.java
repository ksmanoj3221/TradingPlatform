package com.trading.exception;

public class DuplicateTraderException extends RuntimeException {
    public DuplicateTraderException(String message) {
        super(message);
    }
}
