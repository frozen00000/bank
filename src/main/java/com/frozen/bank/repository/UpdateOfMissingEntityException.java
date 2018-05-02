package com.frozen.bank.repository;

import com.frozen.bank.BankException;

public class UpdateOfMissingEntityException extends BankException {

    UpdateOfMissingEntityException(String message) {
        super(message);
    }

}
