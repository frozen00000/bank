package com.frozen.bank.controller;

import com.frozen.bank.domain.Transaction;
import com.frozen.bank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/{id}")
    public Transaction getTransaction(@PathVariable UUID id) {
        return transactionService.getById(id);
    }

    @PostMapping
    public UUID postTransaction(@RequestBody Transaction transaction) {
        return transactionService.create(transaction.getSourceAccountId(), transaction.getTargetAccountId(), transaction.getAmount());
    }

}
