import java.awt.Point;

public class Map {
    StartNode start;
    EndNode end;
    int nodes;
    int connections;
    
    //array of connections containing node pairs
    
    class StartNode {
        Point startNode;
        StartNode() {
            startNode.setLocation(0, 0);
        }
    }
    
    class EndNode {
        Point endNode;
        EndNode() {
            endNode.setLocation(50, 50);
        }
    }
    
    void SetStartEnd() {
        this.start = new StartNode();
        this.end = new EndNode();
    }
}
