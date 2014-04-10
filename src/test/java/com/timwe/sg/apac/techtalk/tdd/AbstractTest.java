package com.timwe.sg.apac.techtalk.tdd;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Rashidi Zin
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractTest {

    @Rule
    public ExpectedException expect = ExpectedException.none();

    @Before
    public void setUp() {

    }
}
