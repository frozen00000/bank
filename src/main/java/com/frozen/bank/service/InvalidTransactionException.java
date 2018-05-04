package com.frozen.bank.service;

import com.frozen.bank.BankException;

public class InvalidTransactionException extends BankException {

    InvalidTransactionException(String message) {
        super(message);
    }

}
