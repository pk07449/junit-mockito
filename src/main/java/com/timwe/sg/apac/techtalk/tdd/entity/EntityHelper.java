package com.timwe.sg.apac.techtalk.tdd.entity;

/**
 * @author Rashidi Zin
 */
public class EntityHelper {

    public static boolean isValid(Entity entity) {
        return ((entity != null) && (entity.getId() != null));
    }
}
