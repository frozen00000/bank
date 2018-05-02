package com.frozen.bank.repository;

import com.frozen.bank.domain.HasId;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple "In-memory" repository that is based on ConcurrentHashMap. Manages objects that have identifier.
 * Potentially, it could be easily replaced by some database repository for example.
 * @see     java.util.concurrent.ConcurrentHashMap
 * @param <T> type of managed entity.
 */
public class InMemoryRepository<T extends HasId<UUID>> implements Repository<T> {

    final Map<UUID, T> map = new ConcurrentHashMap<>();

    /**
     * Added an entity to the repository.
     * @param entity for saving.
     * @return saved entity.
     */
    @Override
    public T save(T entity) {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        map.put(entity.getId(), entity);
        return entity;
    }

    /**
     * Returns entity by identifier.
     * @param id identifier of entity.
     * @return optional with entity or empty optional object if entity is missing.
     */
    @Override
    public Optional<T> getById(UUID id) {
        return Optional.ofNullable(map.get(id));
    }

}
