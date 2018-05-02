package com.frozen.bank.controller;

import com.frozen.bank.domain.Account;
import com.frozen.bank.service.AccountService;
import com.frozen.bank.service.EntityNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;

import static com.frozen.bank.TestConstants.ID;
import static com.frozen.bank.controller.AbstractRESTHandler.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountHandlerTest extends AbstractHandlerTest {

    @Mock
    private AccountService accountService;
    private AccountHandler handler;

    @Before
    public void before() {
        handler = new AccountHandler(accountService);
    }

    @Override
    AbstractRESTHandler<Account> getHandler() {
        return handler;
    }

    @Test
    public void notAllowedMethod() throws IOException, URISyntaxException {
        test("PUT", METHOD_NOT_ALLOWED, "Method 'PUT' is not allowed.");
    }

    @Test
    public void getMissingAccount() throws IOException, URISyntaxException {
        when(accountService.getAccount(ID)).thenThrow(new EntityNotFoundException("Cannot find Account with id: 1"));
        test(GET, "/" + ID.toString(), NOT_FOUND, "Cannot find Account with id: 1");
    }

    @Test
    public void post() throws URISyntaxException, IOException {
        when(exchange.getRequestBody()).thenReturn(new ByteArrayInputStream("{\"balance\": 1}".getBytes()));
        when(accountService.createAccount(BigDecimal.ONE)).thenReturn(new Account(ID, BigDecimal.ONE));
        test(POST, CREATED, ID.toString());
    }

}