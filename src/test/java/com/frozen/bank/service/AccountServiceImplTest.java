package com.frozen.bank.service;

import com.frozen.bank.domain.Account;
import com.frozen.bank.repository.UpdatableRepository;
import com.frozen.bank.repository.Update;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.frozen.bank.TestConstants.ID;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImplTest {

    @Mock
    private UpdatableRepository<Account> repository;

    @Test
    public void createAccount() {
        BigDecimal balance = BigDecimal.ZERO;
        Account account = new Account(ID, balance);
        when(repository.save(any(Account.class))).thenReturn(account);
        AccountService accountService = new AccountServiceImpl(repository);
        assertEquals(account, accountService.createAccount(balance));
        verify(repository).save(new Account(balance));
    }

    @Test
    public void getAccount() {
        BigDecimal balance = BigDecimal.ZERO;
        Account expectedAccount = new Account(ID, balance);
        Optional<Account> expected = Optional.of(new Account(ID, balance));
        when(repository.getById(eq(ID))).thenReturn(expected);
        AccountService accountService = new AccountServiceImpl(repository);
        assertEquals(expectedAccount, accountService.getAccount(ID));
        verify(repository).getById(ID);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getMissedAccount() {
        when(repository.getById(any())).thenReturn(Optional.empty());
        AccountService accountService = new AccountServiceImpl(repository);
        accountService.getAccount(ID);
    }

    @Test
    public void update() {
        List<Update<Account>> updates = Collections.emptyList();
        AccountService accountService = new AccountServiceImpl(repository);
        accountService.update(updates);
        verify(repository).update(updates);
    }
}