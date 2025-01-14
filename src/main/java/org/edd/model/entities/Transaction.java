package org.edd.model.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Transaction {
    public enum OperationType {
        INSERT, SELECT, UPDATE, DELETE
    }

    private final OperationType type;
    private final Record record;
    private final LocalDateTime timestamp;
    private final Map<String, Object> criteria;
    private final List<Record> results;

    // INSERT, UPDATE, DELETE
    public Transaction(OperationType type, Record record) {
        this.type = type;
        this.record = record;
        this.timestamp = LocalDateTime.now();
        this.criteria = null;
        this.results = null;
    }

    // SELECT
    public Transaction(Map<String, Object> criteria, List<Record> results) {
        this.type = OperationType.SELECT;
        this.record = null;
        this.timestamp = LocalDateTime.now();
        this.criteria = criteria;
        this.results = results;
    }

    public OperationType getType() {
        return type;
    }

    public Record getRecord() {
        return record;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Map<String, Object> getCriteria() {
        return criteria;
    }

    public List<Record> getResults() {
        return results;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Transaction[type=").append(type)
                .append(", timestamp=").append(timestamp);

        if (type == OperationType.SELECT) {
            sb.append(", criteria=").append(criteria)
                    .append(", results=").append(results != null ? results.size() : 0).append(" records");
        } else {
            sb.append(", record=").append(record != null ? record.getId() : "null");
        }

        sb.append("]");
        return sb.toString();
    }
}
