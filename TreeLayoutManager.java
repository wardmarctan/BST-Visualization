import java.util.*;

public class TreeLayoutManager {
    private final int nodeRadius;
    private final int levelSpacing;

    public TreeLayoutManager(int nodeRadius, int levelSpacing) {
        this.nodeRadius = nodeRadius;
        this.levelSpacing = levelSpacing;
    }

    public void calculatePositions(BSTLogic.Node root, int startX, int startY, int horizontalSpacing) {
        calculatePositionsRec(root, startX, startY, horizontalSpacing);
    }

    private void calculatePositionsRec(BSTLogic.Node node, int x, int y, int xOffset) {
        if (node == null) return;

        node.position = new NodePosition(x, y);

        if (node.left != null) {
            calculatePositionsRec(node.left, x - xOffset, y + levelSpacing, xOffset / 2);
        }
        if (node.right != null) {
            calculatePositionsRec(node.right, x + xOffset, y + levelSpacing, xOffset / 2);
        }
    }
}
