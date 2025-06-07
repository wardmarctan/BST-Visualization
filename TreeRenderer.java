import java.awt.*;
import java.util.List;

public class TreeRenderer {
    private final int nodeRadius;

    public TreeRenderer(int nodeRadius) {
        this.nodeRadius = nodeRadius;
    }

    public void render(Graphics g, BSTLogic.Node root) {
        if (root == null) return;

        // Draw edges first
        drawEdges(g, root);

        // Draw nodes
        drawNodes(g, root);
    }

    private void drawEdges(Graphics g, BSTLogic.Node node) {
        if (node == null) return;

        List<TreeEdge> edges = node.getConnectionEdges();
        g.setColor(Color.BLACK);
        for (TreeEdge edge : edges) {
            NodePosition from = edge.getFrom();
            NodePosition to = edge.getTo();
            g.drawLine(from.getX() + nodeRadius, from.getY() + nodeRadius,
                       to.getX() + nodeRadius, to.getY() + nodeRadius);
        }

        drawEdges(g, node.left);
        drawEdges(g, node.right);
    }

    private void drawNodes(Graphics g, BSTLogic.Node node) {
        if (node == null) return;

        NodePosition pos = node.position;
        if (pos == null) return;

        g.setColor(Color.GREEN);
        g.fillOval(pos.getX(), pos.getY(), nodeRadius * 2, nodeRadius * 2);

        g.setColor(Color.BLACK);
        g.drawOval(pos.getX(), pos.getY(), nodeRadius * 2, nodeRadius * 2);

        String valStr = String.valueOf(node.value);
        FontMetrics fm = g.getFontMetrics();
        int strWidth = fm.stringWidth(valStr);
        int strHeight = fm.getAscent();
        g.drawString(valStr, pos.getX() + nodeRadius - strWidth / 2,
                          pos.getY() + nodeRadius + strHeight / 2);

        drawNodes(g, node.left);
        drawNodes(g, node.right);
    }
}
