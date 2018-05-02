package com.frozen.bank.service;

import com.frozen.bank.domain.Account;
import com.frozen.bank.repository.UpdatableRepository;
import com.frozen.bank.repository.Update;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of Account service. Handles some logic around Account repository.
 * Other parts of application should this service to deal with Account entity.
 */
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final UpdatableRepository<Account> repository;

    @Override
    public Account createAccount(BigDecimal initialBalance) {
        Account account = repository.save(new Account(initialBalance));
        log.info("Created account: {}", account.toString());
        return account;
    }

    @Override
    public Account getAccount(UUID id) {
        return repository.getById(id).orElseThrow(() -> new EntityNotFoundException("Cannot find Account with id: " + id));
    }

    @Override
    public void update(List<Update<Account>> updates) {
        repository.update(updates);
    }

}
