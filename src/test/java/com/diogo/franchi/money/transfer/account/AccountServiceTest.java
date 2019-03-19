package com.diogo.franchi.money.transfer.account;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;

import com.diogo.franchi.money.transfer.dao.EmbeddedDatabase;
import com.diogo.franchi.money.transfer.model.Account;


@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {
	
	@Mock
    private AccountDAO accountDAO;
    @InjectMocks
    private AccountService accountService;
    
    @BeforeClass
    public static void setUp() { 
    	EmbeddedDatabase.openServerDataBase();
        EmbeddedDatabase.createTable();
    }

    @Test
    public void validateAccountRequest() {
        accountService.create(new AccountRequest(new BigDecimal(100)));
        try {
            accountService.create(null);
            fail();
        } catch (IllegalArgumentException expected) {
        }

        try {
            accountService.create(new AccountRequest(null));
            fail();
        } catch (IllegalArgumentException expected) {
        }

        try {
            accountService.create(new AccountRequest(new BigDecimal(-1)));
            fail();
        } catch (IllegalArgumentException expected) {
        }

        try {
            accountService.create(new AccountRequest(BigDecimal.ZERO));
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    @Test
    public void validRequestShouldSaveToDatabase() {
        AccountRequest accountRequest = new AccountRequest(new BigDecimal(100));
        ArgumentCaptor<Account> argument = ArgumentCaptor.forClass(Account.class);

        accountService.create(accountRequest);

        verify(accountDAO).save(argument.capture());
        assertThat(argument.getValue().getAmount(), is(accountRequest.getAmount()));
    }
}
