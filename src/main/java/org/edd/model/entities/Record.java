package org.edd.model.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Record {
    private UUID id;
    private Map<String, Object> data;

    public Record() {
        this.id = UUID.randomUUID();
        this.data = new HashMap<>();
    }

    public UUID getId() {
        return id;
    }

    public void setValue(String field, Object value) {
        data.put(field, value);
    }

    public Object getValue(String field) {
        return data.get(field);
    }

    public Map<String, Object> getData() {
        return new HashMap<>(data);
    }
}
