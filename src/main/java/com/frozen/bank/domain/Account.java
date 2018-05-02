package com.frozen.bank.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Represents the Bank Account entity.
 * Contains only identifier and current balance.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account implements HasId<UUID> {

    /**
     * Identifier of Account.
     */
    private UUID id;

    /**
     * Current balance of Account.
     */
    private BigDecimal balance;

    public Account(BigDecimal initialBalance) {
        balance = initialBalance;
    }

}
