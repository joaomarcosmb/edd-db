package org.edd.model.structures;

import org.edd.model.entities.Record;
import org.edd.model.entities.index.AVLTree;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueryQueue {
    private final Queue<Query> queryQueue;
    private final Map<String, AVLTree> indexTrees;
    private final Map<Integer, Record> records;
    private static final int MAX_QUEUE_SIZE = 1000;

    public QueryQueue() {
        this.queryQueue = new ConcurrentLinkedQueue<>();
        this.indexTrees = new HashMap<>();
        this.records = new HashMap<>();
    }

    public static class Query {
        private final String queryType;
        private final Map<String, Object> criteria;
        private final long timestamp;
        private Record result;

        public Query(String queryType, Map<String, Object> criteria) {
            this.queryType = queryType;
            this.criteria = criteria;
            this.timestamp = System.currentTimeMillis();
        }

        public String getQueryType() {
            return queryType;
        }

        public Map<String, Object> getCriteria() {
            return criteria;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setResult(Record result) {
            this.result = result;
        }

        public Record getResult() {
            return result;
        }
    }

    public void enqueue(Query query) {
        if (queryQueue.size() >= MAX_QUEUE_SIZE) {
            queryQueue.poll();
        }
        queryQueue.offer(query);
    }

    public Query dequeue() {
        return queryQueue.poll();
    }

    public boolean isEmpty() {
        return queryQueue.isEmpty();
    }

    public int size() {
        return queryQueue.size();
    }

    public void reindexRecord(int id, Map<String, Object> values) {
        Record record = new Record();
        record.setValue("id", id);

        // First store all values in the record
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            String value = String.valueOf(entry.getValue());
            record.setValue(entry.getKey(), value);
        }

        // Then index the record in all trees
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            String value = String.valueOf(entry.getValue()).toLowerCase();
            AVLTree tree = indexTrees.computeIfAbsent(entry.getKey(), k -> new AVLTree());
            tree.insert(value, record);
        }

        records.put(id, record);
    }

    public void removeRecord(int id) {
        Record record = records.remove(id);
        if (record != null) {
            for (Map.Entry<String, AVLTree> entry : indexTrees.entrySet()) {
                String key = String.valueOf(record.getValue(entry.getKey()));
                AVLTree tree = entry.getValue();
                List<Record> records = tree.search(key);
                records.remove(record);
                tree.insert(key, records.isEmpty() ? null : records.get(0));
                for (int i = 1; i < records.size(); i++) {
                    tree.insert(key, records.get(i));
                }
            }
        }
    }

    public List<Record> searchByCriteria(String key) {
        String[] parts = key.split(":", 2);
        if (parts.length != 2) {
            return new ArrayList<>();
        }

        String field = parts[0];
        String value = parts[1];

        AVLTree tree = indexTrees.get(field);
        if (tree == null) {
            return new ArrayList<>();
        }

        return tree.search(value);
    }

    public List<Record> searchComplexCriteria(Map<String, Object> criteria, String operator) {
        if (criteria.isEmpty()) {
            return new ArrayList<>();
        }

        List<Set<Record>> resultSets = new ArrayList<>();
        for (Map.Entry<String, Object> entry : criteria.entrySet()) {
            AVLTree tree = indexTrees.get(entry.getKey());
            if (tree != null) {
                List<Record> searchResults = tree.search(String.valueOf(entry.getValue()));
                if (!searchResults.isEmpty()) {
                    resultSets.add(new HashSet<>(searchResults));
                }
            }
        }

        if (resultSets.isEmpty()) {
            return new ArrayList<>();
        }

        Set<Record> finalResult = resultSets.get(0);
        for (int i = 1; i < resultSets.size(); i++) {
            if ("AND".equalsIgnoreCase(operator)) {
                finalResult.retainAll(resultSets.get(i));
            } else if ("OR".equalsIgnoreCase(operator)) {
                finalResult.addAll(resultSets.get(i));
            }
        }

        return new ArrayList<>(finalResult);
    }

    public List<Record> searchByRange(String field, Comparable<?> start, Comparable<?> end) {
        AVLTree tree = indexTrees.get(field);
        if (tree == null) {
            return new ArrayList<>();
        }

        List<Record> results = new ArrayList<>();
        String startStr = String.valueOf(start);
        String endStr = String.valueOf(end);

        for (Record record : records.values()) {
            String value = String.valueOf(record.getValue(field));
            if (value.compareTo(startStr) >= 0 && value.compareTo(endStr) <= 0) {
                results.add(record);
            }
        }

        return results;
    }
}