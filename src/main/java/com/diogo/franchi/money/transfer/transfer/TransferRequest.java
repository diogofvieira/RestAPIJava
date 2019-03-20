package com.diogo.franchi.money.transfer.transfer;

import java.math.BigDecimal;

public class TransferRequest {
	
	private  String accountDebit;
    private  String accountCredit;
    private  BigDecimal value;

    public TransferRequest() {
    }

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

	@Override
	public String toString() {
		return "TransferRequest [accountDebit=" + accountDebit + ", accountCredit=" + accountCredit + ", value=" + value
				+ "]";
	}
    
    

}
