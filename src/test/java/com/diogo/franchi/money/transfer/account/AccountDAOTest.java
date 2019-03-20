package com.diogo.franchi.money.transfer.account;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.diogo.franchi.money.transfer.dao.EmbeddedDatabase;
import com.diogo.franchi.money.transfer.model.Account;
import com.diogo.franchi.money.transfer.transfer.TransferRequest;


public class AccountDAOTest {
	
	    @BeforeClass
	    public static void setUp() throws Exception {
	    	EmbeddedDatabase.openServerDataBase();
	        EmbeddedDatabase.createTable();
	    }
	    
	    @AfterClass
	    public static void tearDown() {
	    	EmbeddedDatabase.closeDataBase();
	    }
	    
	    @Test
	    public void createAccount() {
	    	Account account = new Account(new BigDecimal(100));
	    	assertEquals(new AccountDAO().save(account), account);
	    }

	    @Test
	    public void findAll() {
	    	new AccountDAO().save(new Account(new BigDecimal(100)));
	    	assertThat(new AccountDAO().findAll().size(), is(1));
	    }
	    
	    @Test
	    public void findOne() {
	    	Account account = new AccountDAO().save(new Account(new BigDecimal(100F)));
	    	Account expected = new AccountDAO().find(account.getAccountNumber());
	    	assertEquals(expected.getAccountNumber(), account.getAccountNumber());
	    	
	    }
	    
	    @Test
	    public void update() {
	    	Account accountDebit = new AccountDAO().save(new Account(new BigDecimal(100F)));
	    	Account accountCredit = new AccountDAO().save(new Account(new BigDecimal(100F)));

	    	TransferRequest transferRequest = new  TransferRequest(accountDebit.getAccountNumber(), accountCredit.getAccountNumber(), new BigDecimal(100F));
			
	    	Account accountDebitUpdate = new AccountDAO().update(transferRequest);
	    	
	    	assertEquals(accountDebitUpdate.getAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN), accountDebit.getAmount().subtract(new BigDecimal(100F).setScale(2, BigDecimal.ROUND_HALF_EVEN)));
	    	
	    }
	    
	    @Test
	    public void delete() {
	    	
	    	new AccountDAO().save(new Account(new BigDecimal(100F)));

	    	int expected = new AccountDAO().findAll().size(); 
	    	int deleted = new AccountDAO().clear();
	    	
	    	assertEquals(expected, deleted);
	    	
	    }
	    
	    
}
