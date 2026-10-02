
// This class should serve as the primary method loop & contain the main method.
// instantiates Clock, Player, & Map. All other classes should be considered subclasses of these three.
// Should render output with Swing (possibly do this in a seperate renderer object)


import java.awt.Point;

public class Game {
    public static void main(String[] args) {
        Map map = new Map();
        Player p = new Player(map);
        System.out.print(p.returnStats());
    }
}
