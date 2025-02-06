package org.edd.model.structures;

import org.edd.model.entities.Customer;

public class NodeList {
    public Customer customer;
    protected NodeList next;

    public NodeList(Customer customer) {
        this.customer = customer;
        this.next = null;
    }
}