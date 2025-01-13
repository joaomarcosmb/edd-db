package org.edd.model.structures;

import java.util.ArrayList;

public class CustomList<T> {
    private ArrayList<T> list;

    public CustomList() {
        this.list = new ArrayList<>();
    }

    public void add(T item) {
        list.add(item);
    }

    public T get(int index) {
        return list.get(index);
    }

    public void remove(T item) {
        list.remove(item);
    }
}
