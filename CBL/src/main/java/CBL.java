import javax.swing.*;

public class CBL { 
    public static void main(String[] args) {
        
        Items itemList = new Items();
        Player player = new Player();
        
        //random events are a seperate class of methods, will be called in Clock
        RandomEvents rndEvents = new RandomEvents(itemList);
        
        Clock clock = new Clock(7, player, rndEvents);
        
        Map map = new Map(itemList, player);
        Renderer r = new Renderer(map);
        
        SwingUtilities.invokeLater(() -> r.setVisible(true));
        player.passObjects(map, clock, r);
        r.passObjects(clock, player);
        
        r.renderTravelOptions(player.getAdjacentCities(player.location), player.location);
        
    }
}