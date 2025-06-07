public class BSTLogic {
    public static class Node {
        int value;
        Node left, right;
        public Node(int value) {
            this.value = value;
        }
    }

    private Node root;

    public void insert(int value) {
        root = insertRec(root, value);
    }

    private Node insertRec(Node node, int value) {
        if (node == null) {
            return new Node(value);
        }
        if (value < node.value) {
            node.left = insertRec(node.left, value);
        } else if (value > node.value) {
            node.right = insertRec(node.right, value);
        }
        return node;
    }

    public void delete(int value) {
        root = deleteRec(root, value);
    }

    private Node deleteRec(Node node, int value) {
        if (node == null) return null;

        if (value < node.value) {
            node.left = deleteRec(node.left, value);
        } else if (value > node.value) {
            node.right = deleteRec(node.right, value);
        } else {
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

    public String inorder() {
        return inorderRec(root).trim();
    }

    private String inorderRec(Node node) {
        if (node == null) return "";
        return inorderRec(node.left) + node.value + " " + inorderRec(node.right);
    }

    public String preorder() {
        return preorderRec(root).trim();
    }

    private String preorderRec(Node node) {
        if (node == null) return "";
        return node.value + " " + preorderRec(node.left) + preorderRec(node.right);
    }

    public String postorder() {
        return postorderRec(root).trim();
    }

    private String postorderRec(Node node) {
        if (node == null) return "";
        return postorderRec(node.left) + postorderRec(node.right) + node.value + " ";
    }

    // Getter untuk root agar GUI bisa akses posisi node
    public Node getRoot() {
        return root;
    }
}
