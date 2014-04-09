package com.timwe.sg.apac.techtalk.tdd.subscription;

import com.timwe.sg.apac.techtalk.tdd.customer.Customer;
import com.timwe.sg.apac.techtalk.tdd.customer.CustomerManager;
import com.timwe.sg.apac.techtalk.tdd.customer.CustomerStatusEnum;

/**
 * @author Rashidi Zin
 */
public class SubscriptionManagerImpl implements SubscriptionManager {

    CustomerManager customerManager;
    SubscriptionRepository repository;

    @Override
    public Long create(Subscription entity) {

        Customer customer = entity.getCustomer();
        if (customer == null) {
            throw new SubscriptionException("Customer information is required");
        }

        Long customerId = customer.getId();
        if (customerId == null) {
            throw new SubscriptionException("Customer id is required");
        }

        customer = customerManager.get(customerId);
        if (!CustomerStatusEnum.ACTIVE.equals(customer.getStatus())) {
            throw new SubscriptionException("User has to be ACTIVE");
        }

        Subscription persistedSubscription = doGetByCustomer(customer);
        if (persistedSubscription != null && SubscriptionStatusEnum.ACTIVE.equals(persistedSubscription.getStatus())) {
            throw new SubscriptionException("Customer is already an active subscriber");
        }

        return repository.create(entity);
    }

    @Override
    public Subscription get(Long id) {
        return null;
    }

    @Override
    public Subscription update(Subscription entity) {
        return null;
    }

    @Override
    public void delete(Long aLong, Subscription entity) {

    }

    private Subscription doGetByCustomer(Customer customer) {
        return repository.findByCustomer(customer);
    }
}
