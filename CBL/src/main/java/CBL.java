import javax.swing.*;

public class CBL { 
    public static void main(String[] args) {
        
        Clock clock = new Clock(7);
        Player player = new Player();
        Map map = new Map();
        Renderer r = new Renderer(map);
        
        SwingUtilities.invokeLater(() -> r.setVisible(true));
        
        r.passObjects(clock, player);
        player.passObjects(map, clock, r);
        r.renderTravelOptions(player.getAdjacentCities(player.location), player.location);
        
        
        //it's really fucking annoying that you can't just pass each object onto each other constructor bcuz of the order of instantation. 
        //to work around that, define a passObjects method for every top-level object (player, world, clock, etc) so that every object is referencable from every other object.
        //just as kind terry the terible would have wanted <3
        
        
        
    }
}

/*


hooray!! :D



*/