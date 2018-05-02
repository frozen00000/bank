package com.frozen.bank.controller;

import com.frozen.bank.domain.Transaction;
import com.frozen.bank.service.TransactionService;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class TransactionHandler extends AbstractRESTHandler<Transaction> {

    private final static String PATH = "/v0/transaction";
    private final TransactionService transactionService;

    @Override
    public String getContextPath() {
        return PATH;
    }

    @Override
    Transaction get(UUID id) {
        return transactionService.getById(id);
    }

    @Override
    UUID post(Transaction body) {
        return transactionService.create(body.getSourceAccountId(), body.getTargetAccountId(), body.getAmount());
    }

    @Override
    Class<Transaction> getBodyClass() {
        return Transaction.class;
    }

}