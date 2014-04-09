package com.timwe.sg.apac.techtalk.tdd.entity;

import com.timwe.sg.apac.techtalk.tdd.AbstractTest;
import com.timwe.sg.apac.techtalk.tdd.subscription.Subscription;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Rashidi Zin
 */
public class EntityHelperTest extends AbstractTest {

    @Test
    public void testIsValid() {
        Assert.assertFalse(EntityHelper.isValid(null));
        Assert.assertTrue(EntityHelper.isValid(new Subscription(1L)));
    }
}
