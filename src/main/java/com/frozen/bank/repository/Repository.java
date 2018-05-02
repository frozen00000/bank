package com.frozen.bank.repository;

import com.frozen.bank.domain.HasId;

import java.util.Optional;
import java.util.UUID;

/**
 * Simple interface for repository that supports only Save and Retrieve by Id operations.
 * @param <T> type of managed entity.
 */
public interface Repository<T extends HasId<UUID>> {

    /**
     * Saves an entity in the repository.
     * @param entity an entity for saving.
     * @return saved entity.
     */
    T save(T entity);

    /**
     * Returns entity by identifier.
     * @param id identifier of entity.
     * @return optional with entity or empty optional object if entity is missed.
     */
    Optional<T> getById(UUID id);

}
