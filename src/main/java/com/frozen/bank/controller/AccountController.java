package com.frozen.bank.controller;

import com.frozen.bank.domain.Account;
import com.frozen.bank.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/{id}")
    public Account getAccount(@PathVariable UUID id) {
        return accountService.getAccount(id);
    }

    @PostMapping
    public UUID postAccount(@RequestBody Account account) {
        return accountService.createAccount(account.getBalance()).getId();
    }

}
