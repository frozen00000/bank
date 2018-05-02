package com.frozen.bank.repository;

import com.frozen.bank.domain.HasId;

import java.util.List;
import java.util.UUID;

/**
 * Interface for repositories that support updates.
 * @param <T> type of manages entity.
 */
public interface UpdatableRepository<T extends HasId<UUID>> extends Repository<T> {

    /**
     * Performs update of entity.
     * @param updates list of update to perform.
     */
    void update(List<Update<T>> updates);

}
