package org.edd.model.entities.index;

import org.edd.model.entities.Record;

import java.util.ArrayList;
import java.util.List;

public class AVLTree {
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
        }

        private AVLNode root;

        public void insert(String key, Record record) {
            root = insert(root, key, record);
        }

        private AVLNode insert(AVLNode root, String key, Record record) {
            if (root == null)
                return new AVLNode(key, record);

            var comparison = key.compareTo(root.key);
            if (comparison < 0)
                root.leftChild = insert(root.leftChild, key, record);
            else if (comparison > 0)
                root.rightChild = insert(root.rightChild, key, record);
            else
                root.records.add(record);

            updateHeight(root);

            return balance(root, key);
        }

        public List<Record> search(String key) {
            AVLNode node = search(root, key);
            return (node != null) ? new ArrayList<>(node.records) : new ArrayList<>();
        }

        private AVLNode search(AVLNode root, String key) {
            if (root == null || root.key.equals(key))
                return root;

            if (key.compareTo(root.key) < 0)
                return search(root.leftChild, key);

            return search(root.rightChild, key);
        }

        public void delete(String key) {
            root = delete(root, key);
        }

        private AVLNode delete(AVLNode root, String key) {
            if (root == null)
                return null;

            var comparison = key.compareTo(root.key);
            if (comparison < 0)
                root.leftChild = delete(root.leftChild, key);
            else if (comparison > 0)
                root.rightChild = delete(root.rightChild, key);
            else {
                // Caso 1: Nó com um ou nenhum filho
                if (root.leftChild == null || root.rightChild == null) {
                    AVLNode temp = null;
                    if (temp == root.leftChild)
                        temp = root.rightChild;
                    else
                        temp = root.leftChild;

                    if (temp == null) {
                        temp = root;
                        root = null;
                    } else
                        root = temp;
                } else {
                    // Caso 2: Nó com dois filhos
                    AVLNode temp = findMin(root.rightChild);
                    root.key = temp.key;
                    root.records = temp.records;
                    root.rightChild = delete(root.rightChild, temp.key);
                }
            }

            if (root == null)
                return root;

            updateHeight(root);

            return balance(root, key);
        }

        private AVLNode balance(AVLNode root, String key) {
            if (isLeftHeavy(root)) {
                if (key.compareTo(root.leftChild.key) < 0) // Esquerda-Esquerda
                    return rightRotate(root);
                else { // Esquerda-Direita
                    root.leftChild = leftRotate(root.leftChild);
                    return rightRotate(root);
                }
            }
            if (isRightHeavy(root)) {
                if (key.compareTo(root.rightChild.key) > 0) // Direita-Direita
                    return leftRotate(root);
                else { // Direita-Esquerda
                    root.rightChild = rightRotate(root.rightChild);
                    return leftRotate(root);
                }
            }
            return root;
        }

        private int height(AVLNode node) {
            return (node == null) ? -1 : node.height;
        }

        private void updateHeight(AVLNode node) {
            node.height = Math.max(height(node.leftChild), height(node.rightChild)) + 1;
        }

        private boolean isLeftHeavy(AVLNode node) {
            return balanceFactor(node) > 1;
        }

        private boolean isRightHeavy(AVLNode node) {
            return balanceFactor(node) < -1;
        }

        private int balanceFactor(AVLNode node) {
            return (node == null) ? 0 : height(root.leftChild) - height(root.rightChild);
        }

        private AVLNode leftRotate(AVLNode node) {
            var newRoot = node.rightChild;
            node.rightChild = newRoot.leftChild;
            newRoot.leftChild = node;

            node.height = Math.max(height(node.leftChild), height(node.rightChild)) + 1;
            newRoot.height = Math.max(height(newRoot.leftChild), height(newRoot.rightChild)) + 1;

            return newRoot;
        }

        private AVLNode rightRotate(AVLNode node) {
            var newRoot = node.leftChild;
            node.leftChild = newRoot.rightChild;
            newRoot.rightChild = node;

            node.height = Math.max(height(node.leftChild), height(node.rightChild)) + 1;
            newRoot.height = Math.max(height(newRoot.leftChild), height(newRoot.rightChild)) + 1;

            return newRoot;
        }

        private AVLNode findMin(AVLNode root) {
            AVLNode current = root;
            while (current.leftChild != null)
                current = current.leftChild;
            return current;
        }
    }
}
