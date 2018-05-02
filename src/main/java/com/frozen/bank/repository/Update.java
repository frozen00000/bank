package com.frozen.bank.repository;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.UUID;
import java.util.function.Function;

/**
 * This class represents an update of repository. It contains and identifier of entity and mapping function.
 * @param <T> class of entity.
 */
@Value
@AllArgsConstructor
public class Update<T> {

    /**
     * Identifier of entity that should be updated.
     */
    private UUID id;

    /**
     * Mapping function that should be applied to existing entity. The result should be saved as a new value.
     */
    private Function<T, T> function;

}
