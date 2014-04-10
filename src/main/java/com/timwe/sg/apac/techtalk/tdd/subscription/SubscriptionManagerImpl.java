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
        if (id == null) {
            throw new SubscriptionException("Id is required");
        }

        Subscription subscription = repository.get(id);

        if (subscription == null) {
            throw new SubscriptionException("Invalid subscription id");
        }

        return subscription;
    }

    @Override
    public Subscription update(Subscription entity) {

        if (entity == null) {
            return null;
        }

        if (entity.getId() == null) {
            throw new SubscriptionException("Subscription id is required");
        }

        if (entity.getStatus() == null) {
            throw new SubscriptionException("New subscription status is required");
        }

        Subscription persisted = repository.get(entity);

        if (SubscriptionStatusEnum.UNSUBSCRIBE.equals(persisted.getStatus())) {
            throw new SubscriptionException("Unable to update un-subscribed subscription");
        }

        if (!isValidUpdate(persisted.getStatus(), entity.getStatus())) {
            throw new SubscriptionException(
                    String.format("Invalid status update from '%s' to '%s'", persisted.getStatus().toString(), entity.getStatus().toString())
            );
        }

        repository.update(persisted.getId(), entity);
        return repository.get(persisted.getId());
    }

    @Override
    public void delete(Long aLong, Subscription entity) {

    }

    private Subscription doGetByCustomer(Customer customer) {
        return repository.findByCustomer(customer);
    }

    private boolean isValidUpdate(SubscriptionStatusEnum currentStatus, SubscriptionStatusEnum updatedStatus) {
            return (SubscriptionStatusEnum.ACTIVE.equals(updatedStatus) && (
                    SubscriptionStatusEnum.PENDING.equals(currentStatus) || SubscriptionStatusEnum.SUSPENDED.equals(currentStatus)
            ) ||
                    (SubscriptionStatusEnum.SUSPENDED.equals(updatedStatus) && SubscriptionStatusEnum.ACTIVE.equals(currentStatus))
            );
    }
}
