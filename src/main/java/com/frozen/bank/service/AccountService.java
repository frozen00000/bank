package com.frozen.bank.service;

import com.frozen.bank.domain.Account;
import com.frozen.bank.repository.Update;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Interface for Account service.
 * Account service manages bank accounts: creates new, provides access to existing
 * and performs updates. The implementations should be threadsafe.
 */
public interface AccountService {

    /**
     * Creates new account with the specified initial balance.
     * @param initialBalance initial balance for a new account.
     * @return created account.
     */
    Account createAccount(BigDecimal initialBalance);

    /**
     * Returns an account by the specified identifier.
     * @param id identifier of account.
     * @return an account by the specified identifier.
     * @throws EntityNotFoundException if account does not exist.
     */
    Account getAccount(UUID id);

    /**
     * Performs updates of accounts.
     * Each Update object consists of account ID and function for updating.
     * Updates should be performed according to their order in the list. If some update is failed,
     * then remaining should not be performed.
     * @param updates list of updates to perform.
     */
    void update(List<Update<Account>> updates);

}
