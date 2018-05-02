package com.frozen.bank.repository;

import com.frozen.bank.domain.Account;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static com.frozen.bank.TestConstants.ID;
import static org.junit.Assert.assertEquals;

public class InMemoryUpdatableRepositoryTest {

    @Test(expected = UpdateOfMissingEntityException.class)
    public void updateOfMissedAccount() {
        InMemoryUpdatableRepository<Account> repository = new InMemoryUpdatableRepository<>();
        repository.update(Collections.singletonList(new Update<>(ID, null)));
    }

    @Test
    public void update() {
        InMemoryUpdatableRepository<Account> repository = new InMemoryUpdatableRepository<>();
        Account account = repository.save(new Account(BigDecimal.ZERO));
        repository.update(Collections.singletonList(new Update<>(account.getId(), a -> new Account(a.getId(), a.getBalance().add(BigDecimal.TEN)))));
        assertEquals(Optional.of(new Account(account.getId(), BigDecimal.TEN)), repository.getById(account.getId()));
    }

}