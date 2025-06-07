import java.util.*;

public class BSTLogic {
    public static class Node {
        int value;
        Node left, right;
        NodePosition position; // posisi node dalam koordinat
        public Node(int value) {
            this.value = value;
        }
        // Mengembalikan list TreeEdge dari node ini ke anaknya
        public List<TreeEdge> getConnectionEdges() {
            List<TreeEdge> edges = new ArrayList<>();
            if (left != null && position != null && left.position != null) {
                edges.add(new TreeEdge(position, left.position));
            }
            if (right != null && position != null && right.position != null) {
                edges.add(new TreeEdge(position, right.position));
            }
            return edges;
        }
    }

    private Node root;

    public void insert(int value) {
        root = insertRec(root, value);
    }

    private Node insertRec(Node node, int value) {
        if (node == null) return new Node(value);
        if (value < node.value) node.left = insertRec(node.left, value);
        else if (value > node.value) node.right = insertRec(node.right, value);
        return node;
    }

    public void delete(int value) {
        root = deleteRec(root, value);
    }

    private Node deleteRec(Node node, int value) {
        if (node == null) return null;
        if (value < node.value) node.left = deleteRec(node.left, value);
        else if (value > node.value) node.right = deleteRec(node.right, value);
        else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            node.value = minValue(node.right);
            node.right = deleteRec(node.right, node.value);
        }
        return node;
    }

    private int minValue(Node node) {
        int minv = node.value;
        while (node.left != null) {
            node = node.left;
            minv = node.value;
        }
        return minv;
    }

    public String inorder() { return inorderRec(root).trim(); }
    private String inorderRec(Node node) {
        if (node == null) return "";
        return inorderRec(node.left) + node.value + " " + inorderRec(node.right);
    }

    public String preorder() { return preorderRec(root).trim(); }
    private String preorderRec(Node node) {
        if (node == null) return "";
        return node.value + " " + preorderRec(node.left) + preorderRec(node.right);
    }

    public String postorder() { return postorderRec(root).trim(); }
    private String postorderRec(Node node) {
        if (node == null) return "";
        return postorderRec(node.left) + postorderRec(node.right) + node.value + " ";
    }

    public Node getRoot() { return root; }
}
