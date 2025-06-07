import javax.swing.*;
import java.awt.*;

public class InputPanel extends JPanel {
    JTextField txtInput = new JTextField(10);
    JButton btnAdd = new JButton("Add");
    JButton btnDelete = new JButton("Delete");

    public InputPanel() {
        add(new JLabel("Value:"));
        add(txtInput);
        add(btnAdd);
        add(btnDelete);
    }

    public String getInputText() {
        return txtInput.getText();
    }

    public void clearInput() {
        txtInput.setText("");
        txtInput.requestFocus();
    }
}
