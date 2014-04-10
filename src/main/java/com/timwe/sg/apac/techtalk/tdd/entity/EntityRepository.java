package com.timwe.sg.apac.techtalk.tdd.entity;

import java.io.Serializable;

/**
 * @author Rashidi Zin
 */
public interface EntityRepository<E extends Entity, ID extends Serializable> {

    Long create(E entity);

    E get(ID id);

    E get(E entity);

    E update(ID id, E entity);

    void delete(ID id);
}
