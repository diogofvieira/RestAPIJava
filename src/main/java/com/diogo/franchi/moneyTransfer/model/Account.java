package com.diogo.franchi.moneyTransfer.model;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

public class Account {

    private final String id;
    private final String accountNumber;
    private BigDecimal amount;

    public Account(BigDecimal amount) {
        Random random = new Random();
        this.accountNumber = Integer.toString(random.nextInt(9999));
        this.id = UUID.randomUUID().toString();
        this.amount = amount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

   public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public static Account newAccount(Account data) {
        return new Account(data.getAmount());
    }
}
