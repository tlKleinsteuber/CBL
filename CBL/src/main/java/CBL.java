import javax.swing.*;

public class CBL { 
    public static void main(String[] args) {
        
        Items itemList = new Items();
        Clock clock = new Clock(7);
        Player player = new Player();
        Map map = new Map(itemList, player);
        Renderer r = new Renderer(map);
        
        SwingUtilities.invokeLater(() -> r.setVisible(true));
        player.passObjects(map, clock, r);
        r.passObjects(clock, player);
        
        r.renderTravelOptions(player.getAdjacentCities(player.location), player.location);
        
    }
}