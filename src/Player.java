import java.awt.Point;

public class Player {
    //current stats, listed as intergers:
    
    //hunger - NOT saturation, higher value means more hungry
    int hunger;
    //max hunger - set the maximum amount of hunger the player may have before death
    int maxHunger;
    
    //position - the players current position, is a point
    //forgot to include in backlog!
    Point position;
    
    //constructor, sets initial stats and position
    //position is currently a Point variable
    public Player(Map map) {
        //for now stats are set manually within code
        this.hunger = 0;
        this.maxHunger = 10;
        
        
        this.position = map.nodes[0].returnPos();
    }
    
    public String returnStats() {
        String S = "";
        S += this.hunger;
        S += " ";
        S += this.maxHunger;
        S += " ";
        S += this.position;
        S += " ";
        return S;
    }
}
