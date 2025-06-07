import javax.swing.*;
import java.awt.*;

public class BSTGUI extends JFrame {
    private BSTLogic tree = new BSTLogic();
    private TreeLayoutManager layoutManager;
    private TreeRenderer renderer;
    private TraversalPanel traversalPanel = new TraversalPanel();
    private InputPanel inputPanel = new InputPanel();

    private JPanel treePanel;

    private final int NODE_RADIUS = 20;
    private final int LEVEL_SPACING = 60;

    public BSTGUI() {
        layoutManager = new TreeLayoutManager(NODE_RADIUS, LEVEL_SPACING);
        renderer = new TreeRenderer(NODE_RADIUS);

        setTitle("BST Visualization");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        treePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                layoutManager.calculatePositions(tree.getRoot(), getWidth()/2, 30, getWidth()/4);
                renderer.render(g, tree.getRoot());
            }
        };
        treePanel.setBackground(Color.WHITE);

        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(treePanel, BorderLayout.CENTER);
        add(traversalPanel, BorderLayout.SOUTH);

        setupEventHandlers();

        // Sample data
        tree.insert(50);
        tree.insert(30);
        tree.insert(70);
        tree.insert(20);
        tree.insert(40);
        tree.insert(60);
        tree.insert(80);

        updateTraversalLabels();
    }

    private void setupEventHandlers() {
        inputPanel.btnAdd.addActionListener(e -> handleAdd());
        inputPanel.btnDelete.addActionListener(e -> handleDelete());
        inputPanel.txtInput.addActionListener(e -> handleAdd());
    }

    private void handleAdd() {
        Integer value = validateInput();
        if (value != null) {
            tree.insert(value);
            inputPanel.clearInput();
            updateTraversalLabels();
            treePanel.repaint();
        }
    }

    private void handleDelete() {
        Integer value = validateInput();
        if (value != null) {
            tree.delete(value);
            inputPanel.clearInput();
            updateTraversalLabels();
            treePanel.repaint();
        }
    }

    private Integer validateInput() {
        try {
            return Integer.parseInt(inputPanel.getInputText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid integer");
            return null;
        }
    }

    private void updateTraversalLabels() {
        traversalPanel.updateTraversal(tree.inorder(), tree.preorder(), tree.postorder());
    }
}
