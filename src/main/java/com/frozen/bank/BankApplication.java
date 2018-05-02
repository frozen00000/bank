package com.frozen.bank;

import com.frozen.bank.controller.AbstractRESTHandler;
import com.frozen.bank.controller.AccountHandler;
import com.frozen.bank.controller.TransactionHandler;
import com.frozen.bank.domain.Account;
import com.frozen.bank.domain.Transaction;
import com.frozen.bank.repository.InMemoryRepository;
import com.frozen.bank.repository.InMemoryUpdatableRepository;
import com.frozen.bank.repository.Repository;
import com.frozen.bank.repository.UpdatableRepository;
import com.frozen.bank.service.AccountService;
import com.frozen.bank.service.AccountServiceImpl;
import com.frozen.bank.service.TransactionService;
import com.frozen.bank.service.TransactionServiceImpl;
import com.sun.net.httpserver.HttpServer;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.concurrent.Executors;

/**
 * Entry point for the application.
 */
@Slf4j
public class BankApplication {

    public static void main(String[] args) throws Exception {
        Instant time = Instant.now();
        HttpServer server = HttpServer.create();
        server.bind(new InetSocketAddress(8080), 0); //TODO add parameter for port

        //initialize classes... NOTE: actually, it should be done by DI framework.
        UpdatableRepository<Account> accountRepository = new InMemoryUpdatableRepository<>();
        AccountService accountService = new AccountServiceImpl(accountRepository);
        AbstractRESTHandler<Account> accountHandler = new AccountHandler(accountService);

        Repository<Transaction> transactionRepository = new InMemoryRepository<>();
        TransactionService transactionService = new TransactionServiceImpl(transactionRepository, accountService);
        AbstractRESTHandler<Transaction> transactionHandler = new TransactionHandler(transactionService);

        Arrays.asList(accountHandler, transactionHandler).forEach(c -> server.createContext(c.getContextPath(), c));

        server.setExecutor(Executors.newCachedThreadPool());
        server.start();
        log.info("Started in {} ms.", Duration.between(time, Instant.now()).toMillis());
    }

}
