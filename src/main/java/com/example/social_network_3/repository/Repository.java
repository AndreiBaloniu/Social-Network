package com.example.social_network_3.repository;

import com.example.social_network_3.domain.Entity;
import com.example.social_network_3.domain.validators.ValidationException;

import java.util.List;

public interface Repository<ID, E extends Entity<Long>> {

    /**
     * @param id@return an {@code Optional} encapsulating the entity with the given id
     * @throws IllegalArgumentException if id is null.
     */
    E findOne(Long id);

    /**
     * @return all entities
     */
    List<E> findAll();

    /**
     *
     * @param entity
     *         entity must be not null
     * @return an {@code Optional} - null if the entity was saved,
     *                             - the entity (id already exists)
     * @throws ValidationException
     *            if the entity is not valid
     * @throws IllegalArgumentException
     *             if the given entity is null.     *
     */
    E save(E entity);


    /**
     * removes the entity with the specified id
     *
     * @param id@return an {@code Optional}
     *                  - null if there is no entity with the given id,
     *                  - the removed entity, otherwise
     * @throws IllegalArgumentException if the given id is null.
     */
    E delete(Long id);

    /**
     *
     * @param entity
     *          entity must not be null
     * @return  an {@code Optional}
     *             - null if the entity was updated
     *             - otherwise (e.g. id does not exist) returns the entity.
     * @throws IllegalArgumentException
     *             if the given entity is null.
     * @throws ValidationException
     *             if the entity is not valid.
     */
    E update(E entity);

}