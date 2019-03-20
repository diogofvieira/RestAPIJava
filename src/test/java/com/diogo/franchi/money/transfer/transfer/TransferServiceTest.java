package com.diogo.franchi.money.transfer.transfer;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.diogo.franchi.money.transfer.account.AccountDAO;
import com.diogo.franchi.money.transfer.account.AccountService;
import com.diogo.franchi.money.transfer.dao.EmbeddedDatabase;
import com.diogo.franchi.money.transfer.model.Account;

@RunWith(MockitoJUnitRunner.class)
public class TransferServiceTest {
	
	@Mock
    private AccountDAO accountDao;
    @InjectMocks
    private TransferService transferService;
    @InjectMocks
    private AccountService accountService;
    
    @BeforeClass
    public static void setUp() { 
    	EmbeddedDatabase.openServerDataBase();
        EmbeddedDatabase.createTable();
    }
    
    @Test
    public void validateAccountRequest()  {
    	transferService.update(new TransferRequest("2345", "2344", new BigDecimal(100)));
        try {
        	transferService.update(null);
            fail();
        } catch (IllegalArgumentException expected) {
        }
        try {
        	transferService.update(new TransferRequest("2345", "2344", null));
            fail();
        } catch (IllegalArgumentException expected) {
        }
        
        try {
        	transferService.update(new TransferRequest("2345", "2344", new BigDecimal(-1)));
            fail();
        } catch (IllegalArgumentException expected) {
        }
        
        try {
        	transferService.update(new TransferRequest("2345", "2344", BigDecimal.ZERO));
            fail();
        } catch (IllegalArgumentException expected) {
        }
        
        try {
        	transferService.update(new TransferRequest("2345", null, new BigDecimal(100)));
            fail();
        } catch (IllegalArgumentException expected) {
        }
        
        try {
        	transferService.update(new TransferRequest(null, "2344", new BigDecimal(100)));
            fail();
        } catch (IllegalArgumentException expected) {
        }
        
        try {
        	transferService.update(new TransferRequest(null, null, new BigDecimal(100)));
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }
    
    @Test
    public void validRequestShouldUpdateToDatabase()  {
    	Account debit = new Account(new BigDecimal(100));
    	Account credit = new Account(new BigDecimal(100));
        
        TransferRequest transfer = new TransferRequest(debit.getAccountNumber(), 
        											   credit.getAccountNumber(), 
        											   new BigDecimal(100));
        Account account = new Account();
        account.setAccountNumber(debit.getAccountNumber());
        account.setAmount(new BigDecimal(0));
        account.setId(debit.getId());
        
        when(accountDao.update(transfer)).thenReturn(account);
        
        assertEquals(new BigDecimal(0), accountDao.update(transfer).getAmount());
    }

}
