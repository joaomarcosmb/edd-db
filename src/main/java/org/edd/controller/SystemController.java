package org.edd.controller;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.edd.model.entities.Customer;
import org.edd.model.entities.ActionsStack;
import org.edd.model.entities.Record;
import org.edd.model.entities.LinkedList;
import org.edd.model.structures.NodeList;
import org.edd.model.structures.NodeStack;
import org.edd.model.structures.QueryQueue;

public class SystemController {
    private final LinkedList linkedList;
    private final ActionsStack actionStack;
    private final QueryQueue queryQueue;

    public SystemController() {
        this.linkedList = new LinkedList();
        this.actionStack = new ActionsStack();
        this.queryQueue = new QueryQueue();
    }

    public List<Record> searchByIndex(String key) {
        return queryQueue.searchByCriteria(key);
    }

    public List<Record> searchComplex(Map<String, Object> criteria, String operator) {
        return queryQueue.searchComplexCriteria(criteria, operator);
    }

    public List<Record> searchByRange(String field, Comparable<?> start, Comparable<?> end) {
        return queryQueue.searchByRange(field, start, end);
    }

    public void reindexCustomer(Customer customer) {
        Map<String, Object> values = new HashMap<>();
        values.put("name", customer.getName());
        values.put("cpf", customer.getCpf());
        values.put("email", customer.getEmail());
        values.put("birthDate", customer.getBirthDate());
        queryQueue.reindexRecord(customer.getId(), values);
    }

    public void addCustomer(String nameCustomer, String cpfCustomer, String birthDateCustomer, String emailCustomer) {
        int idCustomer = this.findSize() + 1;
        Customer customer = new Customer(idCustomer, nameCustomer, cpfCustomer, birthDateCustomer, emailCustomer);

        NodeList lastOfList = this.linkedList.getLastNode();
        this.actionStack.push(customer, lastOfList, NodeStack.ActionType.INSERT);
        this.linkedList.add(customer);
        this.reindexCustomer(customer);
    }

    public boolean removeCustomer(int id) {
        Customer customer = this.linkedList.getCustomerById(id);

        if (customer == null) {
            return false;
        }

        NodeList previousNode = this.linkedList.getPreviousNode(id);
        this.actionStack.push(customer, previousNode, NodeStack.ActionType.DELETE);
        this.linkedList.remove(id);
        this.queryQueue.removeRecord(id);

        return true;
    }

    public boolean editCustomer(int id, String newName, String newCPF, String NewBirthDate, String newEmail, boolean isCreateNodeStack) {
        Customer customer = this.linkedList.getCustomerById(id);

        if (customer == null) {
            return false;
        }

        if (isCreateNodeStack) {
            Customer oldCustomer = new Customer(customer.getId(), customer.getName(), customer.getCpf(), customer.getBirthDate(), customer.getEmail());
            NodeList previousNode = this.linkedList.getPreviousNode(customer.getId());
            this.actionStack.push(oldCustomer, previousNode, NodeStack.ActionType.UPDATE);
        }

        customer.setName(newName);
        customer.setCpf(newCPF);
        customer.setBirthDate(NewBirthDate);
        customer.setEmail(newEmail);
        this.reindexCustomer(customer);

        return true;
    }

    public Customer getCustomerById(int id) {
        return this.linkedList.getCustomerById(id);
    }

    public int findSize() {
        return this.linkedList.size();
    }

    public boolean rollbackStack() {
        NodeStack topStack = this.actionStack.getTop();

        if (topStack == null) {
            return false;
        }

        switch (topStack.actionType) {
            case INSERT:
                this.linkedList.remove(topStack.dataNode.customer.getId());
                this.queryQueue.removeRecord(topStack.dataNode.customer.getId());
                break;

            case UPDATE:
                this.editCustomer(topStack.dataNode.customer.getId(), topStack.dataNode.customer.getName(), topStack.dataNode.customer.getCpf(), topStack.dataNode.customer.getBirthDate(), topStack.dataNode.customer.getEmail(), false);
                break;

            case DELETE:
                this.linkedList.addOfRollback(topStack.dataNode.previousNode, topStack.dataNode.customer);
                this.reindexCustomer(topStack.dataNode.customer);
                break;

            default:
                return false;
        }

        this.actionStack.pop();
        return true;
    }

    public List<Customer> getAllCustomer() {
        List<Customer> listCustomers;
        listCustomers = this.linkedList.getAllNode();
        return listCustomers;
    }
}