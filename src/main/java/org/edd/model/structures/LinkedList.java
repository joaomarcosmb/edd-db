package org.edd.model.structures;

import java.util.ArrayList;
import java.util.List;

import org.edd.model.entities.Customer;

public class LinkedList {
    protected NodeList head;

    public LinkedList() {
        this.head = null;
    }

    public void add(Customer costume) {
        NodeList newNode = new NodeList(costume);

        if (this.head == null) {
            this.head = newNode;
            return;
        }

        NodeList temp = this.head;

        while (temp.next != null) {
            temp = temp.next;
        }

        temp.next = newNode;
    }

    public void remove(int id) {
        NodeList currentCustomer = this.head;
        NodeList previousCustomer = null;

        while (currentCustomer != null && currentCustomer.customer.getId() != id) {
            previousCustomer = currentCustomer;
            currentCustomer = currentCustomer.next;
        }

        if (currentCustomer == null) {
            return;
        }

        if (previousCustomer == null) {
            this.head = currentCustomer.next;
        } else {
            previousCustomer.next = currentCustomer.next;
        }
    }

    public void addOfRollback(NodeList previousNode,Customer customer) {
        NodeList backCustomer = new NodeList(customer);

        if(previousNode == null){
            this.add(customer);
            return;
        }

        NodeList nextCurrentCustomer = previousNode.next;
        previousNode.next = backCustomer;
        backCustomer.next = nextCurrentCustomer;
    }

    public Customer getCustomerById(int id) {
        NodeList temp = this.head;

        if (temp == null || temp.customer.getId() == id) {
            return temp == null ? null : temp.customer;
        }

        while (temp.next != null && temp.customer.getId() != id) {
            temp = temp.next;
        }

        return temp.customer.getId() == id ? temp.customer : null;
    }

    public int size() {
        int count = 0;
        NodeList temp = this.head;

        while (temp != null) {
            count++;
            temp = temp.next;
        }

        return count;
    }

    public NodeList getLastNode() {
        if (this.head == null) {
            return null;
        }
        
        NodeList temp = this.head;
        while (temp.next != null) {
            temp = temp.next;
        }
        return temp;
    }

    public NodeList getPreviousNode(int id) {
        NodeList currentCustomer = this.head;
        NodeList previousCustomer = null;

        while (currentCustomer != null && currentCustomer.customer.getId() != id) {
            previousCustomer = currentCustomer;
            currentCustomer = currentCustomer.next;
        }

       return previousCustomer;
    }

    public List<Customer> getAllNode() {
        List<Customer> listCustomers = new ArrayList<>();
        NodeList temp = this.head;

        while (temp != null) {
            listCustomers.add(temp.customer);
            temp = temp.next;
        }

        return listCustomers;
    }
}