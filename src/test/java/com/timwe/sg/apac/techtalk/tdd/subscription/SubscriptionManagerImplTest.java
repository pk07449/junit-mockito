package com.timwe.sg.apac.techtalk.tdd.subscription;

import com.timwe.sg.apac.techtalk.tdd.AbstractTest;
import com.timwe.sg.apac.techtalk.tdd.customer.Customer;
import com.timwe.sg.apac.techtalk.tdd.customer.CustomerManager;
import com.timwe.sg.apac.techtalk.tdd.customer.CustomerStatusEnum;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * @author Rashidi Zin
 */
public class SubscriptionManagerImplTest extends AbstractTest {

    SubscriptionManagerImpl $;

    @Mock
    Subscription subscription;
    @Mock
    SubscriptionRepository repository;
    @Mock
    CustomerManager customerManager;
    @Mock
    Customer customer;

    @Override
    public void setUp() {
        super.setUp();

        $ = new SubscriptionManagerImpl();
        $.customerManager = customerManager;
        $.repository = repository;

        Mockito.when(subscription.getCustomer()).thenReturn(customer);
        Mockito.when(customer.getId()).thenReturn(1L);
        Mockito.when(customerManager.get(1L)).thenReturn(customer);
        Mockito.when(customer.getStatus()).thenReturn(CustomerStatusEnum.ACTIVE);
    }

    @Test
    public void testCreate() {
        Mockito.when(repository.findByCustomer(customer)).thenReturn(null);
        Mockito.when(repository.create(subscription)).thenReturn(1L);

        $.create(subscription);

        Mockito.verify(subscription, Mockito.times(1)).getCustomer();
        Mockito.verify(customer, Mockito.times(1)).getId();
        Mockito.verify(customerManager, Mockito.times(1)).get(Mockito.anyLong());
        Mockito.verify(customer, Mockito.times(1)).getStatus();
        Mockito.verify(repository, Mockito.times(1)).create(subscription);
    }

    @Test
    public void testCreateWithInactiveCustomer() {
        expect.expect(SubscriptionException.class);
        expect.expectMessage("User has to be ACTIVE");

        Mockito.when(customer.getStatus()).thenReturn(CustomerStatusEnum.INACTIVE);

        $.create(subscription);
    }

    @Test
    public void testCreateWithNullCustomer() {
        expect.expect(SubscriptionException.class);
        expect.expectMessage("Customer information is required");

        Mockito.when(subscription.getCustomer()).thenReturn(null);
        $.create(subscription);
    }

    @Test
    public void testCreateWithCustomerWithActiveSubscription() {
        expect.expect(SubscriptionException.class);
        expect.expectMessage("Customer is already an active subscriber");

        Mockito.when(repository.findByCustomer(customer)).thenReturn(subscription);
        Mockito.when(subscription.getStatus()).thenReturn(SubscriptionStatusEnum.ACTIVE);

        $.create(subscription);
    }
}