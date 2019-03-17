package com.diogo.franchi.moneyTransfer.model;

import java.math.BigDecimal;

public class Transfer {

    private final String accountDebit;
    private final String accountCredit;
    private final BigDecimal value;

    public Transfer(String accountDebit, String accountCredit, BigDecimal value) {
        this.accountDebit = accountDebit;
        this.accountCredit = accountCredit;
        this.value = value;
    }

    public String getAccountDebit() {
        return accountDebit;
    }

    public String getAccountCredit() {
        return accountCredit;
    }

    public BigDecimal getValue() {
        return value;
    }

}
