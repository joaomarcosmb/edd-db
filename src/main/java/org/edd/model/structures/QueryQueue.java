package org.edd.model.structures;

import java.util.LinkedList;
import java.util.Queue;

public class QueryQueue<T> {
    private Queue<T> queue;

    public QueryQueue() {
        this.queue = new LinkedList<>();
    }

    public void enqueue(T item) {
        queue.offer(item);
    }

    public T dequeue() {
        return queue.poll();
    }

    public T peek() {
        return queue.peek();
    }
}
