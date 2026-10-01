import java.awt.Point;

public class Map {

    //create matrix of connections
    //if a cell is 0, the intersecting nodes are NOT connected
    //else the number is positive and represents the weight of each connection
    
    //are nodes objects? if so, should start and end nodes inherit them?

    //for now ignore the current code, should formulate plan and template
    
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
