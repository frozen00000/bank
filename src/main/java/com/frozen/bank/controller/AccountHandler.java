package com.frozen.bank.controller;

import com.frozen.bank.domain.Account;
import com.frozen.bank.service.AccountService;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

/**
 * Handles request that are related to Account entity.
 */
@RequiredArgsConstructor
public class AccountHandler extends AbstractRESTHandler<Account> {

    private final static String PATH = "/v0/account";
    private final AccountService accountService;

    @Override
    public String getContextPath() {
        return PATH;
    }

    /**
     * Returns account object.
     * @param id identifier of entity.
     * @return account for specified id.
     */
    @Override
    Account get(UUID id) {
        return accountService.getAccount(id);
    }

    /**
     * Creates new account.
     * @param account properties of new account.
     * @return identifier of created account.
     */
    @Override
    UUID post(Account account) {
        return accountService.createAccount(account.getBalance()).getId();
    }

    /**
     * @return class for object that should be provided in the 'post' method.
     */
    @Override
    Class<Account> getBodyClass() {
        return Account.class;
    }

}
