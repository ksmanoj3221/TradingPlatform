package com.trading.model;

import java.math.BigDecimal;

public class AddMoneyRequest {
    private String email;
    private BigDecimal amount;

    // Constructors, Getters and Setters
    public AddMoneyRequest() {
    }

    public AddMoneyRequest(String email, BigDecimal amount) {
        this.email = email;
        this.amount = amount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
