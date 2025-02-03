package org.edd.model.structures;

import java.util.ArrayList;

// TODO: this class will really be used?
public class CustomList<T> {
    private final ArrayList<T> list;

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

    public int size() {
        return list.size();
    }
}