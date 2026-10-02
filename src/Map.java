import java.awt.Point;

public class Map {
    int numNodes;
    int connections;
    
    Node[] nodes;
    
    //array of connections containing node pairs
    
    public Map() {
        this.numNodes = 10;
        this.nodes = new Node[numNodes];
        for (int i = 1; i < numNodes - 1; i++) {
            this.nodes[i] = new Node();
        }
        SetStartEnd(numNodes);
    }
    
    public class Node {
        Point node = new Point();
        public Node() {
            this.node.setLocation(1, 1);
        }
        public Point returnPos() {
            return this.node;
        }
    }
    
    public class StartNode extends Node {
        StartNode() {
            this.node.setLocation(0, 0);
        }
    }
    
    public class EndNode extends Node{
        EndNode() {
            this.node.setLocation(50, 50);
        }
    }
    
    void SetStartEnd(int numNodes) {
        this.nodes[0] = new StartNode();
        this.nodes[numNodes - 1] = new EndNode();
    }
}
