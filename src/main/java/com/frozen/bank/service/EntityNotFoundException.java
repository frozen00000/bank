package com.frozen.bank.service;

import com.frozen.bank.BankException;

public class EntityNotFoundException extends BankException {

    public EntityNotFoundException(String message) {
        super(message);
    }

}
