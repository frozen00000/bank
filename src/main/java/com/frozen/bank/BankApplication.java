package com.frozen.bank;

import com.frozen.bank.domain.Account;
import com.frozen.bank.domain.Transaction;
import com.frozen.bank.repository.InMemoryUpdatableRepository;
import com.frozen.bank.repository.UpdatableRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Entry point for the application.
 */
@Slf4j
@SpringBootApplication
public class BankApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankApplication.class, args);
    }

    @Bean
    public UpdatableRepository<Account> accountRepository() {
        return new InMemoryUpdatableRepository<>();
    }

    @Bean
    public UpdatableRepository<Transaction> transactionRepository() {
        return new InMemoryUpdatableRepository<>();
    }

}
