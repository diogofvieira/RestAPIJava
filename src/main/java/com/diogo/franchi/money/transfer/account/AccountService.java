package com.diogo.franchi.money.transfer.account;


import java.math.BigDecimal;
import java.util.List;

import com.diogo.franchi.money.transfer.model.Account;

import static spark.utils.Assert.notNull;

public class AccountService {

    private static final String INVALID_AMOUNT_PROVIDED = "Invalid amount provided";

    private final AccountDAO accountDao;

    public AccountService(AccountDAO accountDao) {
        this.accountDao = accountDao;
    }

	public Account create(AccountRequest accountRequest) {
        validateAccountRequest(accountRequest);
        return accountDao.save(new Account(accountRequest.getAmount()));
    }

    public List<Account> listAll(){
    	return accountDao.findAll();
    }

	public int clear()  {
		return accountDao.clear();
    }

    private void validateAccountRequest(AccountRequest accountRequest) {
        notNull(accountRequest, INVALID_AMOUNT_PROVIDED);
        notNull(accountRequest.getAmount(), INVALID_AMOUNT_PROVIDED);
        positive(accountRequest.getAmount(), INVALID_AMOUNT_PROVIDED);
    }

    private void positive(BigDecimal value, String message) {
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(message);
        }
    }

}
