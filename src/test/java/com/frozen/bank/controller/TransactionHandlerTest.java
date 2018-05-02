package com.frozen.bank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frozen.bank.domain.Transaction;
import com.frozen.bank.service.NotEnoughMoneyException;
import com.frozen.bank.service.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;

import static com.frozen.bank.TestConstants.SOURCE_ID;
import static com.frozen.bank.TestConstants.ID;
import static com.frozen.bank.TestConstants.TARGET_ID;
import static com.frozen.bank.controller.AbstractRESTHandler.*;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionHandlerTest extends AbstractHandlerTest {

    @Mock
    private TransactionService transactionService;
    private TransactionHandler transactionHandler;
    private final ObjectMapper mapper = new ObjectMapper();

    @Before
    public void before() {
        transactionHandler = new TransactionHandler(transactionService);
    }

    @Override
    AbstractRESTHandler<Transaction> getHandler() {
        return transactionHandler;
    }

    @Test
    public void get() throws IOException, URISyntaxException {
        Transaction expectedTransaction = new Transaction();
        when(transactionService.getById(ID)).thenReturn(expectedTransaction);
        test(GET, "/" + ID.toString(), OK, new ObjectMapper().writeValueAsString(expectedTransaction));
    }

    @Test
    public void post() throws IOException, URISyntaxException {
        Transaction transaction = new Transaction(SOURCE_ID, TARGET_ID, BigDecimal.ONE);
        when(exchange.getRequestBody()).thenReturn(new ByteArrayInputStream(mapper.writeValueAsBytes(transaction)));
        when(transactionService.create(
                transaction.getSourceAccountId(),
                transaction.getTargetAccountId(),
                transaction.getAmount())
        ).thenReturn(ID);
        test(POST, CREATED, ID.toString());
    }

    @Test
    public void postInvalidTransaction() throws IOException, URISyntaxException {
        when(exchange.getRequestBody()).thenReturn(
                new ByteArrayInputStream("Invalid json".getBytes()),
                new ByteArrayInputStream(new byte[] {})
        );
        test(POST, BAD_REQUEST, actual -> assertTrue(actual.startsWith("Unrecognized token 'Invalid'")));
        test(POST, BAD_REQUEST, actual -> assertTrue(actual.startsWith("No content to map due to end-of-input")));
    }

    @Test
    public void transferFromEmptyAccount() throws IOException, URISyntaxException {
        String expectedMessage = "Account with id 'source' does not have enough money to transfer 1.";
        Transaction transaction = new Transaction(ID, ID, BigDecimal.ONE);
        when(exchange.getRequestBody()).thenReturn(new ByteArrayInputStream(mapper.writeValueAsBytes(transaction)));
        when(transactionService.create(
                transaction.getSourceAccountId(),
                transaction.getTargetAccountId(),
                transaction.getAmount())
        ).thenThrow(new NotEnoughMoneyException(expectedMessage));
        test(POST, NOT_ACCEPTABLE, expectedMessage);
    }

}