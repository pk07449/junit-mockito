package com.timwe.sg.apac.techtalk.tdd.subscription;

import com.timwe.sg.apac.techtalk.tdd.customer.Customer;
import com.timwe.sg.apac.techtalk.tdd.entity.Entity;

/**
 * @author Rashidi Zin
 */
public class Subscription implements Entity<Long> {

    private Long id;
    private Customer customer;
    private SubscriptionStatusEnum status;

    public Subscription(Long id) {
        this.id = id;
    }

    public Subscription(Long id, Customer customer, SubscriptionStatusEnum status) {
        this.id = id;
        this.customer = customer;
        this.status = status;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public SubscriptionStatusEnum getStatus() {
        return status;
    }

    public void setStatus(SubscriptionStatusEnum status) {
        this.status = status;
    }
}
