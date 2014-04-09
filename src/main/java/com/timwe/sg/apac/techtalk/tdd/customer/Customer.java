package com.timwe.sg.apac.techtalk.tdd.customer;

import com.timwe.sg.apac.techtalk.tdd.entity.Entity;

/**
 * @author Rashidi Zin
 */
public class Customer implements Entity<Long> {

    private Long id;
    private String firstName;
    private String lastName;
    private String msisdn;
    private CustomerStatusEnum status;

    public Customer(Long id) {
        this.id = id;
    }

    public Customer(Long id, String firstName, String lastName, String msisdn, CustomerStatusEnum status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.msisdn = msisdn;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public CustomerStatusEnum getStatus() {
        return status;
    }

    public void setStatus(CustomerStatusEnum status) {
        this.status = status;
    }
}
