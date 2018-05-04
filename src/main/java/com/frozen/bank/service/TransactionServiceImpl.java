package com.frozen.bank.service;

import com.frozen.bank.domain.Account;
import com.frozen.bank.domain.Transaction;
import com.frozen.bank.repository.Repository;
import com.frozen.bank.repository.Update;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of Transaction service.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final Repository<Transaction> repository;
    private final AccountService accountService;

    /**
     * Essential method of this service. Creates a list of Updates to make money transfer.
     * First update subtracts specified amount of money from the account with id 'fromId' or throw
     * the exception NotEnoughMoneyException if there is no enough money.
     * The second one adds same amount of money to the account with id 'toId'.
     * @param sourceId identifier of source account.
     * @param targetId identifier of target account.
     * @param amount amount of money for transferring.
     * @return list of updates.
     */
    private List<Update<Account>> createUpdates(UUID sourceId, UUID targetId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidTransactionException("Amount of money should be greater than zero.");
        }
        return Arrays.asList(
                new Update<>(sourceId, a -> {
                    if (a.getBalance().compareTo(amount) < 0) {
                        throw new NotEnoughMoneyException(String.format("Account with id '%s' does not have enough money to transfer %s.", sourceId, amount.toString()));
                    }
                    return new Account(a.getId(), a.getBalance().subtract(amount));
                }),
                new Update<>(targetId, a -> new Account(a.getId(), a.getBalance().add(amount)))
        );
    }

    @Override
    public UUID create(UUID sourceId, UUID targetId, BigDecimal amount) {
        accountService.update(createUpdates(sourceId, targetId, amount));
        Transaction transaction = repository.save(new Transaction(sourceId, targetId, amount));
        log.info("Transaction {} has been performed.", transaction.toString());
        return transaction.getId();
    }

    @Override
    public Transaction getById(UUID id) {
        return repository.getById(id).orElseThrow(() -> new EntityNotFoundException("Cannot find Transaction with id: " + id));
    }

}
