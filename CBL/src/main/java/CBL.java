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
        player.inventory.containerContents.add(player.inventory.createItem());
        player.inventory.containerContents.add(player.inventory.createItem());
        r.renderInventory(player.inventory.containerContents);
        
    }
}