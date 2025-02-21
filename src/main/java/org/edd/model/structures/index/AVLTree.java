package org.edd.model.structures.index;

import org.edd.model.entities.Record;

import java.util.ArrayList;
import java.util.List;

public class AVLTree {
    private AVLNode root;

    private class AVLNode {
        private String key;
        private List<Record> records;
        private int height;
        private AVLNode leftChild;
        private AVLNode rightChild;

        AVLNode(String key, Record record) {
            this.key = key;
            this.records = new ArrayList<>();
            this.records.add(record);
            this.height = 1;
        }
    }

    public void insert(String key, Record record) {
        root = insert(root, key, record);
    }

    private AVLNode insert(AVLNode node, String key, Record record) {
        if (node == null) {
            return new AVLNode(key, record);
        }

        if (key.equals(node.key)) {
            node.records.add(record);
        } else if (key.compareTo(node.key) < 0) {
            node.leftChild = insert(node.leftChild, key, record);
        } else {
            node.rightChild = insert(node.rightChild, key, record);
        }

        setHeight(node);

        return balance(node);
    }

    public List<Record> search(String key) {
        AVLNode node = search(root, key);
        return node != null ? new ArrayList<>(node.records) : new ArrayList<>();
    }

    private AVLNode search(AVLNode node, String key) {
        if (node == null || key.equals(node.key)) {
            return node;
        }

        if (key.compareTo(node.key) < 0) {
            return search(node.leftChild, key);
        }
        return search(node.rightChild, key);
    }

    private AVLNode balance(AVLNode root) {
        if (isLeftHeavy(root)) {
            if (getBalance(root.leftChild) < 0)
                root.leftChild = rotateLeft(root.leftChild);
            return rotateRight(root);
        } else if (isRightHeavy(root)) {
            if (getBalance(root.rightChild) > 0)
                root.rightChild = rotateRight(root.rightChild);
            return rotateLeft(root);
        }
        return root;
    }

    private AVLNode rotateLeft(AVLNode root) {
        var newRoot = root.rightChild;
        root.rightChild = newRoot.leftChild;
        newRoot.leftChild = root;

        setHeight(root);
        setHeight(newRoot);

        return newRoot;
    }

    private AVLNode rotateRight(AVLNode root) {
        var newRoot = root.leftChild;
        root.leftChild = newRoot.rightChild;
        newRoot.rightChild = root;

        setHeight(root);
        setHeight(newRoot);

        return newRoot;
    }

    private int getHeight(AVLNode node) {
        if (node == null)
            return 0;
        return node.height;
    }

    private void setHeight(AVLNode node) {
        node.height = Math.max(getHeight(node.leftChild), getHeight(node.rightChild)) + 1;
    }

    private int getBalance(AVLNode node) {
        if (node == null)
            return 0;
        return getHeight(node.leftChild) - getHeight(node.rightChild);
    }

    private boolean isLeftHeavy(AVLNode node) {
        return getBalance(node) > 1;
    }

    private boolean isRightHeavy(AVLNode node) {
        return getBalance(node) < -1;
    }
}