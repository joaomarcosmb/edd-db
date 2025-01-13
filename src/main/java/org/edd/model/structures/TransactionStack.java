package org.edd.model.structures;

import org.edd.model.entities.Transaction;

import java.util.Stack;

public class TransactionStack {
    private Stack<Transaction> stack;

    public TransactionStack() {
        this.stack = new Stack<>();
    }

    public void push(Transaction transaction) {
        stack.push(transaction);
    }

    public Transaction pop() {
        return stack.pop();
    }

    public Transaction peek() {
        return stack.peek();
    }
}