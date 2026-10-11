//import java.awt.Point;

public class Player {    
    int startID;
    int hunger;
    int maxHunger;
    int fame;
    Map.City position;
    
    //constructor, sets initial stats and position
    //position is currently a Point variable
    public Player(Map map) {
        //for now stats are set manually within code
        this.hunger = 0;
        this.maxHunger = 10;
        //can choose id of starting city
        //NOT startCity
        this.startID = 3;
        this.position = map.SetStartCity(startID);
        this.position.displayName = "O";
        this.fame = 0;
    }
    
    public void getAdjescentCities(Map map) {
        System.out.println();
        System.out.println("Cities adjescent to city " + this.position.cityName + ":");
        for (int i = 0; i < map.NUMOFCITIES; i++) {
            //for now uses connections boolean matrix
            if (map.connections[i][position.cityID]) {
                System.out.println("City " + map.cities[i].cityName + ", distance to: " + map.distances[i][position.cityID]);
            }
        }
        System.out.println();
    }
    
    //changes stats when moving
    public void MovementStatChanges(int distance) {
        //hunger is incremented based on distance and quality
        HungerChange(-1 * (distance / 10));
    }
    
    public void HungerChange(int change) {
        this.hunger -= change;
        if (this.hunger < 0) {
            this.hunger = 0;
        }
    }
    
    public void FameChange() {
        this.fame += (int) (Math.random() * 5);
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
    
    public void returnStats(Clock clock) {
        System.out.println("Stats: ");
        System.out.println("Hunger: " + this.hunger);
        if (this.fame > 0) {
            System.out.println("Fame: " + this.fame);
        }
        System.out.println("Time left: " + clock.TimeRemaining / (24 * 60) + " day(s) " + (clock.TimeRemaining % (24 * 60)) / 60 + " h " + clock.TimeRemaining % 60 + " min.");
        //System.out.println("Max hunger: " + this.maxHunger);
        System.out.println("City name and coords: " + this.position.cityName + " (" + this.position.coords.getX() + ", " + this.position.coords.getY() + ")");
    }
}