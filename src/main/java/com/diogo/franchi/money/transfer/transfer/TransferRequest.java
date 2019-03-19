package com.diogo.franchi.money.transfer.transfer;

import java.math.BigDecimal;

public class TransferRequest {
	
	private final String accountDebit;
    private final String accountCredit;
    private final BigDecimal value;

    public TransferRequest(String accountDebit, String accountCredit, BigDecimal value) {
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
