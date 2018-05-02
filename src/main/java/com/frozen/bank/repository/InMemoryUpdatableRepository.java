package com.frozen.bank.repository;

import com.frozen.bank.domain.HasId;

import java.util.List;
import java.util.UUID;

/**
 * Implementation of "In-memory" repository that supports updates.
 * @param <T> type of managed entity.
 */
public class InMemoryUpdatableRepository<T extends HasId<UUID>> extends InMemoryRepository<T> implements UpdatableRepository<T> {

    /**
     * Performs updates of accounts. The implementation is based on the concurrent hash map.
     * @param updates list of updates to perform.
     */
    @Override
    public void update(List<Update<T>> updates) {
        updates.forEach(u -> map.compute(u.getId(), (k, v) -> {
            if (v == null) {
                throw new UpdateOfMissingEntityException("Cannot update missing entity with id: " + k);
            }
            return u.getFunction().apply(v);
        }));
    }

}
