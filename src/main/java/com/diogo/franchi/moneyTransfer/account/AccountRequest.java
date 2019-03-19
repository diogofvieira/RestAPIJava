package com.diogo.franchi.moneyTransfer.account;

import java.math.BigDecimal;

public class AccountRequest {

    private BigDecimal amount;

    public AccountRequest() {
    }

    public AccountRequest(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
