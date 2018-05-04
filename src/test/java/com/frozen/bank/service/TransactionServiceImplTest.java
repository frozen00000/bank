package com.frozen.bank.service;

import com.frozen.bank.domain.Account;
import com.frozen.bank.domain.Transaction;
import com.frozen.bank.repository.Repository;
import com.frozen.bank.repository.Update;
import junit.framework.AssertionFailedError;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.frozen.bank.TestConstants.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceImplTest {

    @Mock
    private Repository<Transaction> repository;
    @Mock
    private AccountService accountService;

    private TransactionServiceImpl transactionService;

    private final BigDecimal amount = BigDecimal.ONE;

    @Before
    public void before() {
        transactionService = new TransactionServiceImpl(repository, accountService);
    }

    private boolean verifyUpdates(List<Update<Account>> updateList) {
        assertEquals(2, updateList.size());

        Update<Account> sourceAccountUpdate = updateList.get(0);
        assertEquals(SOURCE_ID, sourceAccountUpdate.getId());
        Account sourceAccount = new Account(BigDecimal.ONE);
        assertEquals(new Account(BigDecimal.ZERO), sourceAccountUpdate.getFunction().apply(sourceAccount));

        Account emptyAccount = new Account(BigDecimal.ZERO);
        try {
            sourceAccountUpdate.getFunction().apply(emptyAccount);
            throw new AssertionFailedError("NotEnoughMoneyException is expected.");
        } catch (NotEnoughMoneyException e) {
            // expected
        }

        Update<Account> targetAccountUpdate = updateList.get(1);
        assertEquals(TARGET_ID, targetAccountUpdate.getId());
        Account toAccount = new Account(BigDecimal.ZERO);
        assertEquals(new Account(BigDecimal.ONE), targetAccountUpdate.getFunction().apply(toAccount));
        return true;
    }

    @Test
    public void create() {
        Transaction expectedTransaction = new Transaction();
        expectedTransaction.setId(ID);
        when(repository.save(any(Transaction.class))).thenReturn(expectedTransaction);

        assertEquals(ID, transactionService.create(SOURCE_ID, TARGET_ID, amount));

        verify(repository).save(any(Transaction.class));
        verify(accountService).update(argThat(this::verifyUpdates));
    }

    @Test(expected = InvalidTransactionException.class)
    public void transactionWithNegativeAmountShouldFail() {
        assertEquals(ID, transactionService.create(SOURCE_ID, TARGET_ID, BigDecimal.ONE.negate()));
    }

    @Test(expected = EntityNotFoundException.class)
    public void getByFakeId() {
        transactionService.getById(ID);
    }

    @Test
    public void getById() {
        Transaction expected = new Transaction();
        when(repository.getById(ID)).thenReturn(Optional.of(expected));
        assertEquals(expected, transactionService.getById(ID));
    }


}