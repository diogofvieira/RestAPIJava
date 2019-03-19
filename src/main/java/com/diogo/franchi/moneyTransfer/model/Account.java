package com.diogo.franchi.moneyTransfer.model;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

public class Account {

    private String id;
    private String accountNumber;
    private BigDecimal amount;

    public Account(BigDecimal amount) {
        Random random = new Random();
        this.accountNumber = Integer.toString(random.nextInt(9999));
        this.id = UUID.randomUUID().toString();
        this.amount = amount;
    }
    
    public Account() {
        
    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
		
}
