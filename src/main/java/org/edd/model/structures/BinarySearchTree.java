package org.edd.model.structures;

public class BinarySearchTree<T extends Comparable<T>> {
    private Node root;

    private class Node {
        T data;
        Node left, right;

        Node(T data) {
            this.data = data;
            left = right = null;
        }
    }

    public void insert(T data) {
        root = insertRec(root, data);
    }

    private Node insertRec(Node root, T data) {
        if (root == null) {
            root = new Node(data);
            return root;
        }

        if (data.compareTo(root.data) < 0)
            root.left = insertRec(root.left, data);
        else if (data.compareTo(root.data) > 0)
            root.right = insertRec(root.right, data);

        return root;
    }

    public T search(T data) {
        Node result = searchRec(root, data);
        return result != null ? result.data : null;
    }

    private Node searchRec(Node root, T data) {
        if (root == null || root.data.equals(data))
            return root;

        if (data.compareTo(root.data) < 0)
            return searchRec(root.left, data);

        return searchRec(root.right, data);
    }
}
