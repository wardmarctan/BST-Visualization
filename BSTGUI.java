import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BSTGUI extends JFrame {
    private BSTLogic tree = new BSTLogic();

    private JTextField txtInput;
    private JButton btnAdd, btnDelete;
    private JLabel lblInorder, lblPreorder, lblPostorder;

    private JPanel treePanel;

    private final int NODE_RADIUS = 20;
    private final int LEVEL_SPACING = 60;

    public BSTGUI() {
        initializeGUI();
        setupEventHandlers();

        // Insert sample data
        tree.insert(50);
        tree.insert(30);
        tree.insert(70);
        tree.insert(20);
        tree.insert(40);
        tree.insert(60);
        tree.insert(80);

        updateTraversalLabels();
    }

    private void initializeGUI() {
        setTitle("BST Visualization");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel controlPanel = new JPanel();
        txtInput = new JTextField(10);
        btnAdd = new JButton("Add");
        btnDelete = new JButton("Delete");
        controlPanel.add(new JLabel("Value:"));
        controlPanel.add(txtInput);
        controlPanel.add(btnAdd);
        controlPanel.add(btnDelete);

        treePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTree(g, tree.getRoot(), getWidth() / 2, 30, getWidth() / 4);
            }
        };
        treePanel.setBackground(Color.WHITE);

        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
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
        btnAdd.addActionListener(e -> handleAdd());
        btnDelete.addActionListener(e -> handleDelete());
        txtInput.addActionListener(e -> handleAdd());
    }

    private void handleAdd() {
        Integer value = validateInput();
        if (value != null) {
            tree.insert(value);
            txtInput.setText("");
            txtInput.requestFocus();
            updateTraversalLabels();
            treePanel.repaint();
        }
    }

    private void handleDelete() {
        Integer value = validateInput();
        if (value != null) {
            tree.delete(value);
            txtInput.setText("");
            txtInput.requestFocus();
            updateTraversalLabels();
            treePanel.repaint();
        }
    }

    private Integer validateInput() {
        try {
            return Integer.parseInt(txtInput.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid integer");
            return null;
        }
    }

    private void updateTraversalLabels() {
        lblInorder.setText("Inorder: " + tree.inorder());
        lblPreorder.setText("Preorder: " + tree.preorder());
        lblPostorder.setText("Postorder: " + tree.postorder());
    }

    private void drawTree(Graphics g, BSTLogic.Node node, int x, int y, int xOffset) {
        if (node == null) return;

        // Draw left subtree
        if (node.left != null) {
            int childX = x - xOffset;
            int childY = y + LEVEL_SPACING;
            g.drawLine(x + NODE_RADIUS, y + NODE_RADIUS, childX + NODE_RADIUS, childY + NODE_RADIUS);
            drawTree(g, node.left, childX, childY, xOffset / 2);
        }

        // Draw right subtree
        if (node.right != null) {
            int childX = x + xOffset;
            int childY = y + LEVEL_SPACING;
            g.drawLine(x + NODE_RADIUS, y + NODE_RADIUS, childX + NODE_RADIUS, childY + NODE_RADIUS);
            drawTree(g, node.right, childX, childY, xOffset / 2);
        }

        // Draw current node
        g.setColor(Color.GREEN);
        g.fillOval(x, y, NODE_RADIUS * 2, NODE_RADIUS * 2);
        g.setColor(Color.BLACK);
        g.drawOval(x, y, NODE_RADIUS * 2, NODE_RADIUS * 2);
        String valStr = String.valueOf(node.value);
        FontMetrics fm = g.getFontMetrics();
        int strWidth = fm.stringWidth(valStr);
        int strHeight = fm.getAscent();
        g.drawString(valStr, x + NODE_RADIUS - strWidth / 2, y + NODE_RADIUS + strHeight / 2);
    }
}
