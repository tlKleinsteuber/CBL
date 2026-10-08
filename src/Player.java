//import java.awt.Point;

public class Player {
    //current stats, listed as intergers:
    
    int startID;
    //hunger - NOT saturation, higher value means more hungry
    int hunger;
    //max hunger - set the maximum amount of hunger the player may have before death
    int maxHunger;
    
    //maximum distance cities can be to be reached
    //default is 20
    int maxReach;
    
    //position - the players current position, is a point
    //forgot to include in backlog!
    Map.City position;
    
    //constructor, sets initial stats and position
    //position is currently a Point variable
    public Player(Map map) {
        //for now stats are set manually within code
        this.hunger = 0;
        this.maxHunger = 10;
        this.maxReach = 20;   
        this.startID = 3;
        //can choose id of starting city
        //NOT startCity
        this.position = map.SetStartCity(startID);
        this.position.displayName = "O";
    }
    
    public void getAdjescentCities(Map map) {
        System.out.println("Cities adjescent to city " + this.position.cityName);
        for (int i = 0; i < map.NUMOFCITIES; i++) {
            //for now uses connections boolean matrix
            //may use distances matrix and compare with maxReach (default is 20, items may increase)
            
            //if have teleport
            /*
            if (map.distances[i][position.cityID] < maxReach) {
                
            }
            */
            
            //if no teleport
            if (map.connections[i][position.cityID]) {
                System.out.println("City " + map.cities[i].cityName + ", distance to: " + map.distances[i][position.cityID]);
            }
        }
    }
    
    public boolean isConnected (Map map, String name) {
        if (map.getIdByName(name) >= 0 && map.connections[map.getIdByName(name)][position.cityID]) {
            return true;
        }
        return false;
    }
    
    public void MoveTo (Map map, String target) {
        //removes from current city
        this.position.hasPlayer = false;
        this.position.displayName = this.position.cityName;
        
        //adds to new city
        map.cities[map.getIdByName(target)].hasPlayer = true;
        map.cities[map.getIdByName(target)].displayName = "O";
        this.position = map.cities[map.getIdByName(target)];
    }
    
    public void returnStats() {
        System.out.println("Stats: ");
        System.out.println("Hunger: " + this.hunger);
        System.out.println("Max hunger: " + this.maxHunger);
        System.out.println("Max reach: " + this.maxReach);
        System.out.println("City name and coords: " + this.position.cityName + " (" + this.position.coords.getX() + ", " + this.position.coords.getY() + ")");
    }
}