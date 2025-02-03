package org.edd.controller;

import java.util.ArrayList;
import java.util.List;

import org.edd.model.entities.Customer;
import org.edd.model.structures.LinkedList;
import org.edd.model.structures.NodeList;

public class SystemController {
    private final LinkedList<NodeList> customers;

    public SystemController() {
        customers = new LinkedList<NodeList>();
    }

    public void addCustomer(int idCustomer, String nameCustomer,String cpfCustomer,String birthDateCustomer,String emailCustomer) {
        Customer customer = new Customer(idCustomer, nameCustomer, cpfCustomer, birthDateCustomer, emailCustomer);
        customers.add(customer);
    }

    public boolean removeCustomer(int id) {
        return customers.remove(id);
    }

    public NodeList findCustomer(int id) {
        return customers.find(id);
    }

    public int findSize() {
        return customers.size();
    }

    public List<Customer> getAllCustomer () {
        List<Customer> listCustomers = new ArrayList<Customer>();

        listCustomers = customers.getAllNode();

        return listCustomers;
    }
}

