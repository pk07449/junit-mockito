package com.timwe.sg.apac.techtalk.tdd.entity;

import java.io.Serializable;

/**
 * @author Rashidi Zin
 */
public interface EntityManager<E extends Entity, ID extends Serializable> {

    ID create(E entity);

    E get(ID id);

    E update(E entity);

    void delete(ID id, E entity);
}
