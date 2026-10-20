import java.util.*;

//import java.awt.Point;

public class Player {    
    //Stats:
    
    //survival
    int hunger;
    int maxHunger;
    int thirst;
    int maxThirst;
    int exhaustion;
    int maxExhaustion;
    
    double overallWellbeing;
    
    //money
    int money;
    
    //inventory
    //is not sorted in any way, each item takes up one space, is not grouped (for example 2 food would be 2 lines of food, not food x2)
    public class Inventory extends Container {
        Inventory() {
            size = 0;
            maxSize = 10;
            itemCount = 0;
            items = new ArrayList<Items.Item>();
        }
        
        int carryWeight() {
            int sum = 0;
            for (Items.Item i : items) {
                sum += i.weight;
            }
            return sum;
        }
    }
    Inventory inventory;
    //int carryWeight;
    boolean hasBackpack;
    
    //wisdom
    int fame;
    boolean isCursed;
    boolean rockDiscarded;
    
    //position
    int startID;
    Map.City position;
    
    //constructor, sets initial stats and position
    public Player(Map map) {
        //survival
        this.hunger = 0;
        this.maxHunger = 100;
        
        this.thirst = 0;
        this.maxThirst = 50;
        
        this.exhaustion = 0;
        this.maxExhaustion = 100;
        
        this.overallWellbeing = ((this.maxHunger - this.hunger) + (this.maxThirst - this.thirst) + (this.maxExhaustion - this.exhaustion))/(this.maxHunger + this.maxThirst + this.maxExhaustion);
        
        //money
        this.money = 1000;
        
        //inventory
        this.inventory = new Inventory();
        this.hasBackpack = false;
        
        //wisdom
        this.fame = 0;
        this.isCursed = false;
        this.rockDiscarded = false;
        
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
        if(this.isCursed) {
            HungerChange(-2 * (timeChange / 10));
            ThirstChange(-2 * (timeChange / 15));
            ExhaustionChange(-2 * (timeChange / 30) * ((this.inventory.carryWeight() / 4) + 1));
        } else {
            HungerChange(-1 * (timeChange / 10));
            ThirstChange(-1 * (timeChange / 15));
            ExhaustionChange(-1 * (timeChange / 30) * ((this.inventory.carryWeight() / 4) + 1));
        }
        WellbeingChange();
    }
    
    public void HungerChange(int change) {
        this.hunger -= change;
        if (this.hunger < 0) {
            this.hunger = 0;
        }
    }
    
    public void ThirstChange(int change) {
        this.thirst -= change;
        if (this.thirst < 0) {
            this.thirst = 0;
        }
    }
    
    public void ExhaustionChange(int change) {
        this.exhaustion -= change;
        if (this.exhaustion < 0) {
            this.exhaustion = 0;
        }
    }
    
    public void WellbeingChange() {
        this.overallWellbeing = (((this.maxHunger - this.hunger) + (this.maxThirst - this.thirst) + (this.maxExhaustion - this.exhaustion)) * 1.0)/((this.maxHunger + this.maxThirst + this.maxExhaustion) * 1.0);
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
    
    public void CurseStatusChange(int change) {
        switch (change) {
            case 0 -> {
                this.isCursed = false;
            }
            case 1 -> {
                this.isCursed = true;
            }
            default -> {
                //nothing
            }
        }
    }
    
    public void rest(Clock clock) {
        HungerChange(-1 * 10);
        ThirstChange(-1 * 5);
        ExhaustionChange(30);
        clock.TimePass(30);
        WellbeingChange();
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
        System.out.println("Hunger: " + this.hunger + "/" + this.maxHunger);
        System.out.println("Thirst: " + this.thirst + "/" + this.maxThirst);
        System.out.println("Exhaustion: " + this.exhaustion + "/" + this.maxExhaustion);
        System.out.println("Overall wellbeing: " + String.format("%.2f", (this.overallWellbeing * 100)) + "%");
        System.out.println("Carry weigth: " + this.inventory.carryWeight());
        System.out.println("Money: " + this.money);
        if (this.fame > 0) {
            System.out.println("Fame: " + this.fame);
        }
        System.out.println("Time left: " + clock.TimeRemaining / (24 * 60) + " day(s) " + (clock.TimeRemaining % (24 * 60)) / 60 + " h " + clock.TimeRemaining % 60 + " min.");
        //System.out.println("Max hunger: " + this.maxHunger);
        System.out.println("City name and coords: " + this.position.cityName + " (" + this.position.coords.getX() + ", " + this.position.coords.getY() + ")");
    }
}