package com.diogo.franchi.money.transfer.transfer;

import java.math.BigDecimal;

import com.diogo.franchi.money.transfer.account.AccountDAO;
import com.diogo.franchi.money.transfer.model.Account;

import static spark.utils.Assert.notNull;

public class TransferService {
	
	private final AccountDAO  accountDao;

	private static final String INVALID_DEBIT_PROVIDED = "Invalid account credit provided";
	private static final String INVALID_CREDIT_PROVIDED = "Invalid account debit provided";
	private static final String INVALID_VALUE_PROVIDED = "Invalid value provided";
	private static final String INVALID_PROVIDED = "Null request provided";
	
	public TransferService(AccountDAO accountDao) {
        this.accountDao = accountDao;
    }

	public Account update(TransferRequest transferRequest){
		validateTransferRequest(transferRequest);
        return  accountDao.update(transferRequest);
	}

	private void validateTransferRequest(TransferRequest transferRequest) {
		notNull(transferRequest, INVALID_PROVIDED);
        notNull(transferRequest.getAccountDebit(), INVALID_DEBIT_PROVIDED);
        notNull(transferRequest.getAccountCredit(), INVALID_CREDIT_PROVIDED);
		notNull(transferRequest.getValue(), INVALID_VALUE_PROVIDED);
        positive(transferRequest.getValue(), INVALID_VALUE_PROVIDED);		
	}

	private void positive(BigDecimal value, String message) {
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(message);
        }
    }
}
