package com.frozen.bank.service;

import com.frozen.bank.domain.Transaction;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Transaction service handles bank transactions.
 * It supports creation and retrieving a transaction by id.
 */
public interface TransactionService {

    /**
     * Creates new transaction.
     * @param sourceId id of source account.
     * @param targetId id of target account.
     * @param amount amount of money for transferring.
     * @return identifier of transaction.
     */
    UUID create(UUID sourceId, UUID targetId, BigDecimal amount);

    /**
     * Retrieves Transaction by the identifier.
     * @param id transaction identifier.
     * @return transaction for specified identifier.
     */
    Transaction getById(UUID id);

}
