import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Stack;

public class BSTVisualization extends JFrame {
    private static class Node {
        int value;
        Node left, right;
        NodePosition position;
        
        public Node(int value) {
            this.value = value;
            this.position = new NodePosition(0, 0);
        }
    }

    private static class NodePosition {
        int x, y;
        public NodePosition(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static class TreeEdge {
        NodePosition start, end;
        public TreeEdge(NodePosition start, NodePosition end) {
            this.start = start;
            this.end = end;
        }
        
        public void draw(Graphics g) {
            g.drawLine(start.x + 20, start.y + 20, end.x + 20, end.y + 20);
        }
    }

    private Node root;
    private int treeHeight = 0;

    private JPanel treePanel;
    private JLabel lblInorder, lblPreorder, lblPostorder;
    private JTextField txtInput;
    private final int NODE_RADIUS = 20;
    private final int LEVEL_SPACING = 60;

    public BSTVisualization() {
        initializeGUI();
        setupEventHandlers();
    }

    private void initializeGUI() {
        setTitle("BST Visualization");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel controlPanel = new JPanel();
        txtInput = new JTextField(10);
        JButton btnAdd = new JButton("Add");
        JButton btnDelete = new JButton("Delete");
        controlPanel.add(new JLabel("Value:"));
        controlPanel.add(txtInput);
        controlPanel.add(btnAdd);
        controlPanel.add(btnDelete);
        
        treePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTree(g, root, getWidth()/2, 30, getWidth()/4);
            }
        };
        treePanel.setBackground(Color.WHITE);
        
        JPanel infoPanel = new JPanel(new GridLayout(3,1));
        lblInorder = new JLabel("Inorder: ");
        lblPreorder = new JLabel("Preorder: ");
        lblPostorder = new JLabel("Postorder: ");
        infoPanel.add(lblInorder);
        infoPanel.add(lblPreorder);
        infoPanel.add(lblPostorder);
        
        setLayout(new BorderLayout());
        add(controlPanel, BorderLayout.NORTH);
        add(treePanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.SOUTH);
    }

    private void setupEventHandlers() {
        JButton btnAdd = (JButton)((JPanel)getContentPane().getComponent(0)).getComponent(2);
        JButton btnDelete = (JButton)((JPanel)getContentPane().getComponent(0)).getComponent(3);
        
        btnAdd.addActionListener(e -> handleAdd());
        btnDelete.addActionListener(e -> handleDelete());
        txtInput.addActionListener(e -> handleAdd());
    }

    private void insert(int value) {
        root = insertRec(root, value, getWidth()/2, 30, getWidth()/4);
        updateTraversalLabels();
        treePanel.repaint();
    }

    private Node insertRec(Node node, int value, int x, int y, int xOffset) {
        if (node == null) {
            Node newNode = new Node(value);
            newNode.position = new NodePosition(x, y);
            return newNode;
        }
        
        if (value < node.value) {
            node.left = insertRec(node.left, value, x - xOffset, y + LEVEL_SPACING, xOffset/2);
        } else if (value > node.value) {
            node.right = insertRec(node.right, value, x + xOffset, y + LEVEL_SPACING, xOffset/2);
        }
        
        return node;
    }

    private void delete(int value) {
        root = deleteRec(root, value);
        updateTreePositions();
        updateTraversalLabels();
        treePanel.repaint();
    }

    private Node deleteRec(Node root, int value) {
        if (root == null) return null;
        
        if (value < root.value) {
            root.left = deleteRec(root.left, value);
        } else if (value > root.value) {
            root.right = deleteRec(root.right, value);
        } else {
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;
            
            root.value = minValue(root.right);
            root.right = deleteRec(root.right, root.value);
        }
        return root;
    }

    private int minValue(Node root) {
        int min = root.value;
        while (root.left != null) {
            min = root.left.value;
            root = root.left;
        }
        return min;
    }

    private void updateTreePositions() {
    }

    private void drawTree(Graphics g, Node node, int x, int y, int xOffset) {
        if (node == null) return;
        
        node.position.x = x;
        node.position.y = y;
        
        g.setColor(Color.GREEN);
        g.fillOval(x, y, NODE_RADIUS*2, NODE_RADIUS*2);
        g.setColor(Color.BLACK);
        g.drawOval(x, y, NODE_RADIUS*2, NODE_RADIUS*2);
        g.drawString(String.valueOf(node.value), x + NODE_RADIUS-5, y + NODE_RADIUS+5);
        
        if (node.left != null) {
            g.drawLine(x + NODE_RADIUS, y + NODE_RADIUS*2, 
                      node.left.position.x + NODE_RADIUS, node.left.position.y);
            drawTree(g, node.left, x - xOffset, y + LEVEL_SPACING, xOffset/2);
        }
        if (node.right != null) {
            g.drawLine(x + NODE_RADIUS, y + NODE_RADIUS*2, 
                      node.right.position.x + NODE_RADIUS, node.right.position.y);
            drawTree(g, node.right, x + xOffset, y + LEVEL_SPACING, xOffset/2);
        }
    }

    private void updateTraversalLabels() {
        lblInorder.setText("Inorder: " + inorder(root));
        lblPreorder.setText("Preorder: " + preorder(root));
        lblPostorder.setText("Postorder: " + postorder(root));
    }

    private String inorder(Node node) {
        if (node == null) return "";
        return inorder(node.left) + node.value + " " + inorder(node.right);
    }

    private String preorder(Node node) {
        if (node == null) return "";
        return node.value + " " + preorder(node.left) + preorder(node.right);
    }

    private String postorder(Node node) {
        if (node == null) return "";
        return postorder(node.left) + postorder(node.right) + node.value + " ";
    }

    private void handleAdd() {
        try {
            int value = Integer.parseInt(txtInput.getText());
            insert(value);
            txtInput.setText("");
            txtInput.requestFocus();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid integer");
        }
    }

    private void handleDelete() {
        try {
            int value = Integer.parseInt(txtInput.getText());
            delete(value);
            txtInput.setText("");
            txtInput.requestFocus();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid integer");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BSTVisualization bst = new BSTVisualization();
            bst.setVisible(true);
            
            bst.insert(50);
            bst.insert(30);
            bst.insert(70);
            bst.insert(20);
            bst.insert(40);
            bst.insert(60);
            bst.insert(80);
        });
    }
}