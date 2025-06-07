import javax.swing.SwingUtilities;

public class BSTVisualization {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BSTGUI gui = new BSTGUI();
            gui.setVisible(true);
        });
    }
}
