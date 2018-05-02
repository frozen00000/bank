package com.frozen.bank.service;

import com.frozen.bank.BankException;

public class NotEnoughMoneyException extends BankException {

    public NotEnoughMoneyException(String message) {
        super(message);
    }

}
