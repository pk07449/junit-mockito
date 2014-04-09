package com.timwe.sg.apac.techtalk.tdd.entity;

import java.io.Serializable;

/**
 * @author Rashidi Zin
 */
public interface Entity<ID extends Serializable> {

    ID getId();

    void setId(ID id);
}
