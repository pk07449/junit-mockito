package com.timwe.sg.apac.techtalk.tdd;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

/**
 * @author Rashidi Zin
 */
public abstract class AbstractTest {

    @Rule
    public ExpectedException expect = ExpectedException.none();

    @Rule
    public MockitoRule mockito = MockitoJUnit.rule();
}
