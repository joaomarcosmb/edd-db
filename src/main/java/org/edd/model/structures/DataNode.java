package org.edd.model.structures;

import org.edd.model.entities.Customer;

public class DataNode {
    public Customer customer;
    public NodeList previousNode;

    public DataNode(Customer customer, NodeList previousNode){
        this.customer = customer;
        this.previousNode = previousNode;
    }
}