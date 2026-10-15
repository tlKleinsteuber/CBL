import java.util.*;

//import java.awt.Point;

public class Player {    
    //Stats:
    
    //survival
    int hunger;
    int maxHunger;
    
    //money
    int money;
    
    //inventory
    //is not sorted in any way, each item takes up one space, is not grouped (for example 2 food would be 2 lines of food, not food x2)
    public class Inventory extends Container {
        Inventory() {
            size = 0;
            maxSize = 10;
            items = new ArrayList<Items.Item>();
        }
    }
    Inventory inventory;
    
    //wisdom
    int fame;
    
    //position
    int startID;
    Map.City position;
    
    //constructor, sets initial stats and position
    public Player(Map map) {
        //survival
        this.hunger = 0;
        this.maxHunger = 100;
        
        //money
        this.money = 10;
        
        //inventory
        this.inventory = new Inventory();
        
        //wisdom
        this.fame = 0;
        
        //position
        //can choose id of starting city
        //NOT startCity
        this.startID = 0;
        this.position = map.SetStartCity(startID);
    }
    
    public void getAdjescentCities(Map map) {
        System.out.println();
        System.out.println("Cities adjescent to city " + this.position.cityName + ":");
        for (int i = 0; i < map.TOTALCITIES; i++) {
            //for now uses connections boolean matrix
            if (map.pathMatrix[i][position.cityID].isPath) {
                System.out.println("City " + map.cities[i].cityName + ", distance to: " + map.pathMatrix[i][position.cityID].distance + 
                        ", path quality: " + map.pathMatrix[i][position.cityID].quality + " " + (5 - (map.pathMatrix[i][position.cityID].quality / 51)));
            }
        }
        System.out.println();
    }
    
    //changes stats when moving
    public void MovementStatChanges(int timeChange) {
        //hunger is incremented based on distance and quality
        HungerChange(-1 * (timeChange / 10));
    }
    
    public void HungerChange(int change) {
        this.hunger -= change;
        if (this.hunger < 0) {
            this.hunger = 0;
        }
    }
    
    public void MoneyChange(int change) {
        this.money += change;
        if (this.money < 0) {
            this.money = 0;
        }
    }
    
    public void FameChange() {
        this.fame += (int) (Math.random() * 5);
    }
    
    public boolean isConnected (Map map, String name) {
        if (map.getIdByName(name) >= 0) {
            if (map.pathMatrix[map.getIdByName(name)][position.cityID].isPath) {
                return true;
            }
        }
        return false;
    }
    
    public void MoveTo (Map map, String target) {
        //removes from current city
        this.position.hasPlayer = false;
        
        //adds to new city
        map.cities[map.getIdByName(target)].hasPlayer = true;
        this.position = map.cities[map.getIdByName(target)];
    }
    
    public void returnStats(Clock clock) {
        System.out.println("Stats: ");
        System.out.println("Hunger: " + this.hunger);
        System.out.println("Money: " + this.money);
        if (this.fame > 0) {
            System.out.println("Fame: " + this.fame);
        }
        System.out.println("Time left: " + clock.TimeRemaining / (24 * 60) + " day(s) " + (clock.TimeRemaining % (24 * 60)) / 60 + " h " + clock.TimeRemaining % 60 + " min.");
        //System.out.println("Max hunger: " + this.maxHunger);
        System.out.println("City name and coords: " + this.position.cityName + " (" + this.position.coords.getX() + ", " + this.position.coords.getY() + ")");
    }
}