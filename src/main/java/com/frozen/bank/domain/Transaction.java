package com.frozen.bank.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Represents the Bank Transaction entity. Contains information about transactions between accounts.
 */
@Data
@NoArgsConstructor
public class Transaction implements HasId<UUID> {

    /**
     * Identifier of Transaction.
     */
    private UUID id;
    /**
     * Identifier of source account.
     */
    private UUID sourceAccountId;
    /**
     * Identifier of target account.
     */
    private UUID targetAccountId;
    /**
     * Amount of money for transferring.
     */
    private BigDecimal amount;

    public Transaction(UUID sourceAccountId, UUID targetAccount, BigDecimal amount) {
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccount;
        this.amount = amount;
    }

}
