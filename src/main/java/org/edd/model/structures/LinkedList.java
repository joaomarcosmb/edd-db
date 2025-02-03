package org.edd.model.structures;

import java.util.ArrayList;
import java.util.List;

import org.edd.model.entities.Customer;

public class LinkedList<T> {
    protected NodeList head;

    public LinkedList() {
        this.head = null;
    }

    public void add(Customer costume) {
        NodeList newNode = new NodeList(costume);

        if (head == null) {
            head = newNode;
        } else {
            NodeList temp = head;

            while (temp.next != null) {
                temp = temp.next;
            }

            temp.next = newNode;
        }
    }

    public boolean remove(int id) {
        NodeList currentCustomer = this.head;
        NodeList previousCustomer = null;

        while (currentCustomer != null && currentCustomer.customer.getId() != id) {
            previousCustomer = currentCustomer;
            currentCustomer = currentCustomer.next;
        }

        if (currentCustomer == null) {
            return false;
        }

        if (previousCustomer == null) {
            this.head = currentCustomer.next;
        } else {
            previousCustomer.next = currentCustomer.next;
        }

        return true;
    }

    public NodeList find(int id) {
        NodeList temp = head;

        while (temp != null) {
            if (temp.customer.getId() == id) {
                return temp;
            }

            temp = temp.next;
        }

        return null;
    }

    public int size() {
        int count = 0;
        NodeList temp = head;

        while (temp != null) {
            count++;
            temp = temp.next;
        }

        return count;
    }

    public List<Customer> getAllNode() {
        List<Customer> listCustomers = new ArrayList<Customer>();
        NodeList temp = head;

        while (temp != null) {
            listCustomers.add(temp.customer);
            temp = temp.next;
        }

        return listCustomers;
    }
}