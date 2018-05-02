package com.frozen.bank.repository;

import com.frozen.bank.domain.Account;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static com.frozen.bank.TestConstants.ID;
import static org.junit.Assert.assertEquals;

public class InMemoryRepositoryTest {

    @Test
    public void save() {
        InMemoryRepository<Account> repository = new InMemoryRepository<>();
        Account account = repository.save(new Account(BigDecimal.ZERO));
        assertEquals(Optional.of(account), repository.getById(account.getId()));
    }

    @Test
    public void getById() {
        InMemoryRepository<Account> repository = new InMemoryRepository<>();
        assertEquals(Optional.empty(), repository.getById(ID));
    }

}