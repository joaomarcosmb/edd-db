package org.edd.model.structures;

import org.edd.model.entities.Customer;

public class ActionsStack {
    protected NodeStack head;

    public ActionsStack() {
        this.head = null;
    }

    public void push(Customer customer, NodeList nodeList, NodeStack.ActionType actionType){
        NodeStack current = this.head;
        DataNode dataNode = new DataNode(customer, nodeList);

        if (current == null){
            this.head = new NodeStack(dataNode, actionType);
            return;
        }

        while (current.next != null){
            current = current.next;
        }

        current.next = new NodeStack(dataNode, actionType);
    }

    public void pop() {
        if (this.head == null) {
            return;
        }

        if (this.head.next == null) {
            this.head = null;
            return;
        }

        NodeStack current = this.head;
        while (current.next.next != null) {
            current = current.next;
        }

        current.next = null;
    }


    public NodeStack getTop() {
        if (this.head == null) {
            return null;
        }

        NodeStack current = this.head;

        while (current.next != null) {
            current = current.next;
        }

        return current;
    }
}