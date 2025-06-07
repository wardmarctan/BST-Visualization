import javax.swing.*;
import java.awt.*;

public class TraversalPanel extends JPanel {
    private JLabel lblInorder = new JLabel("Inorder: ");
    private JLabel lblPreorder = new JLabel("Preorder: ");
    private JLabel lblPostorder = new JLabel("Postorder: ");

    public TraversalPanel() {
        setLayout(new GridLayout(3,1));
        add(lblInorder);
        add(lblPreorder);
        add(lblPostorder);
    }

    public void updateTraversal(String inorder, String preorder, String postorder) {
        lblInorder.setText("Inorder: " + inorder);
        lblPreorder.setText("Preorder: " + preorder);
        lblPostorder.setText("Postorder: " + postorder);
    }
}
