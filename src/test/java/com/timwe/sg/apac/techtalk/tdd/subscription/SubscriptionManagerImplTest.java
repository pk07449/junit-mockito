package com.timwe.sg.apac.techtalk.tdd.subscription;

import com.timwe.sg.apac.techtalk.tdd.AbstractTest;
import com.timwe.sg.apac.techtalk.tdd.customer.Customer;
import com.timwe.sg.apac.techtalk.tdd.customer.CustomerManager;
import com.timwe.sg.apac.techtalk.tdd.customer.CustomerStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

/**
 * @author Rashidi Zin
 */
public class SubscriptionManagerImplTest extends AbstractTest {

    @InjectMocks
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
        when(subscription.getCustomer()).thenReturn(customer);
        when(customer.getId()).thenReturn(1L);
        when(customerManager.get(1L)).thenReturn(customer);
        when(customer.getStatus()).thenReturn(CustomerStatusEnum.ACTIVE);
    }

    @Test
    public void testCreate() {
        when(repository.findByCustomer(customer)).thenReturn(null);
        when(repository.create(subscription)).thenReturn(1L);

        $.create(subscription);

        verify(subscription, times(1)).getCustomer();
        verify(customer, times(1)).getId();
        verify(customerManager, times(1)).get(anyLong());
        verify(customer, times(1)).getStatus();
        verify(repository, times(1)).create(subscription);
    }

    @Test
    public void testCreateWithInactiveCustomer() {
        expect.expect(SubscriptionException.class);
        expect.expectMessage("User has to be ACTIVE");

        when(customer.getStatus()).thenReturn(CustomerStatusEnum.INACTIVE);

        $.create(subscription);
    }

    @Test
    public void testCreateWithNullCustomer() {
        expect.expect(SubscriptionException.class);
        expect.expectMessage("Customer information is required");

        when(subscription.getCustomer()).thenReturn(null);
        $.create(subscription);
    }

    @Test
    public void testCreateWithCustomerWithActiveSubscription() {
        expect.expect(SubscriptionException.class);
        expect.expectMessage("Customer is already an active subscriber");

        when(repository.findByCustomer(customer)).thenReturn(subscription);
        when(subscription.getStatus()).thenReturn(SubscriptionStatusEnum.ACTIVE);

        $.create(subscription);
    }

    @Test
    public void testCreateWithoutCustomerId() {
        expect.expect(SubscriptionException.class);
        expect.expectMessage("Customer id is required");

        when(subscription.getCustomer()).thenReturn(customer);
        when(customer.getId()).thenReturn(null);

        $.create(subscription);
    }

    @Test
    public void testGet() {
        when(repository.get(anyLong())).thenReturn(subscription);
        $.get(1L);

        verify(repository).get(anyLong());
    }

    @Test
    public void testGetWithNullId() {
        expect.expect(SubscriptionException.class);
        expect.expectMessage("Id is required");

        $.get(null);
    }

    @Test
    public void testGetInvalidSubscription() {
        expect.expect(SubscriptionException.class);
        expect.expectMessage("Invalid subscription id");

        when(repository.get(anyLong())).thenReturn(null);

        $.get(112L);

        verify(repository).get(anyLong());
    }
}