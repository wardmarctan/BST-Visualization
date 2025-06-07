public class TreeEdge {
    private NodePosition from;
    private NodePosition to;
    public TreeEdge(NodePosition from, NodePosition to) {
        this.from = from;
        this.to = to;
    }
    public NodePosition getFrom() { return from; }
    public NodePosition getTo() { return to; }
}
