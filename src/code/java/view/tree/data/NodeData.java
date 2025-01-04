package code.java.view.tree.data;

public class NodeData {
    public int nodeType;
    public String nodeData;

    public NodeData(int nodeType, String nodeData) {
        this.nodeType = nodeType;
        this.nodeData = nodeData;
    }

    public String toString() {
        return nodeData;
    }
}

