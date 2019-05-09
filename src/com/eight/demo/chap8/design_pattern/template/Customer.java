package com.eight.demo.chap8.design_pattern.template;

public class Customer {
    int id;

    public Customer getCustomerById(int id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                '}';
    }
}
