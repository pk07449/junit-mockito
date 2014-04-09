package com.timwe.sg.apac.techtalk.tdd.subscription;

import com.timwe.sg.apac.techtalk.tdd.customer.Customer;
import com.timwe.sg.apac.techtalk.tdd.entity.EntityRepository;

/**
 * @author Rashidi Zin
 */
public interface SubscriptionRepository extends EntityRepository<Subscription, Long> {

    Subscription findByCustomer(Customer customer);

}
