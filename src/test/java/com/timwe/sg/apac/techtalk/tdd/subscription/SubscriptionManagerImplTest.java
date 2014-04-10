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
    Subscription persisted;
    @Mock
    SubscriptionRepository repository;
    @Mock
    CustomerManager customerManager;
    @Mock
    Customer customer;

    @Test
    public void testCreate() {
        preTestCreate();
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

        preTestCreate();
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

        preTestCreate();
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

    @Test
    public void testUpdate() {
        preTestUpdate();
        when(persisted.getStatus()).thenReturn(SubscriptionStatusEnum.PENDING);
        when(subscription.getStatus()).thenReturn(SubscriptionStatusEnum.ACTIVE);

        $.update(subscription);

        verify(repository).get(1L);
        verify(repository).get(subscription);
        verify(repository).update(1L, subscription);
    }

    @Test
    public void testUpdateSuspend() {
        preTestUpdate();
        when(persisted.getStatus()).thenReturn(SubscriptionStatusEnum.ACTIVE);
        when(subscription.getStatus()).thenReturn(SubscriptionStatusEnum.SUSPENDED);

        $.update(subscription);

        verify(repository).get(1L);
        verify(repository).get(subscription);
        verify(repository).update(1L, subscription);
    }

    @Test
    public void testUpdateWithoutId() {
        expect.expect(SubscriptionException.class);
        expect.expectMessage("Subscription id is required");

        when(subscription.getId()).thenReturn(null);

        $.update(subscription);
    }
    @Test
    public void testUpdateUnsubscribedSubscription() {
        expect.expect(SubscriptionException.class);
        expect.expectMessage("Unable to update un-subscribed subscription");

        preTestUpdate();
        when(subscription.getStatus()).thenReturn(SubscriptionStatusEnum.ACTIVE);
        when(persisted.getStatus()).thenReturn(SubscriptionStatusEnum.UNSUBSCRIBE);

        $.update(subscription);

        verify(repository).get(anyLong());
    }

    @Test
    public void testUpdateWithMissingStatus() {
        expect.expect(SubscriptionException.class);
        expect.expectMessage("New subscription status is required");

        when(subscription.getStatus()).thenReturn(null);
        $.update(subscription);
    }

    @Test
    public void testUpdateWithInvalidStatus() {
        expect.expect(SubscriptionException.class);
        expect.expectMessage(
                String.format("Invalid status update from '%s' to '%s'",
                        SubscriptionStatusEnum.PENDING.toString(),
                        SubscriptionStatusEnum.SUSPENDED.toString()
                )
        );

        preTestUpdate();
        when(persisted.getStatus()).thenReturn(SubscriptionStatusEnum.PENDING);
        when(subscription.getStatus()).thenReturn(SubscriptionStatusEnum.SUSPENDED);

        $.update(subscription);

        verify(repository).get(anyLong());
    }

    @Test
    public void testUpdateWithNull() {
        Assert.assertNull($.update(null));
    }

    private void preTestCreate() {
        when(subscription.getCustomer()).thenReturn(customer);
        when(customer.getId()).thenReturn(1L);
        when(customerManager.get(1L)).thenReturn(customer);
        when(customer.getStatus()).thenReturn(CustomerStatusEnum.ACTIVE);
    }

    private void preTestUpdate() {
        final Long id = 1L;

        when(subscription.getId()).thenReturn(id);
        when(repository.get(subscription)).thenReturn(persisted);
        when(persisted.getId()).thenReturn(id);
        when(repository.get(id)).thenReturn(subscription);
    }
}