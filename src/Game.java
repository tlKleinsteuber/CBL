
// This class should serve as the primary method loop & contain the main method.
// instantiates Clock, Player, & Map. All other classes should be considered subclasses of these three.
// Should render output with Swing (possibly do this in a seperate renderer object)


//import java.awt.Point;
import java.util.*;
import javax.swing.*;

public class Game {
    static boolean CheckIfDead(Player player, Clock clock) {
        return player.hunger > player.maxHunger || clock.TimeRemaining < 0;
    }
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        Items items = new Items();
        Map map = new Map(items);
        Player player = new Player(map);
        RandomEvents rndEvent = new RandomEvents();
        Clock clock = new Clock();
        Renderer r = new Renderer(map);
        SwingUtilities.invokeLater(() -> r.setVisible(true));
        
        //maybe will use
        //Iterator<Items.Item> itemIterator = items.items.iterator();
        
        /*
        *
        *   MAIN GAME LOOP
        *
        */
        
        boolean isRunning = true;
        int travelDistance;
        System.out.println("Starting the program.");
        player.getAdjescentCities(map);
        String command = input.next();
        
        while (isRunning) {
            switch (command.toLowerCase()) {
                case "stats" -> {
                    player.returnStats(clock);
                }
                case "move" -> {
                    //if player has increased reach, may travel to unconnected cities (teleport)
                    String targetName = input.next();
                    //travelDistance = map.distances[map.getIdByName(targetName)][player.position.cityID];
                    travelDistance = map.pathMatrix[map.getIdByName(targetName)][player.position.cityID].distance;
                    if(player.isConnected(map, targetName)) {
                        //changes time, stats and roll for random event based on distance
                        int timeChange = clock.TimeChange(travelDistance, map.pathMatrix[map.getIdByName(targetName)][player.position.cityID].quality);
                        clock.TimePass(timeChange);
                        player.MovementStatChanges(timeChange);
                        rndEvent.DoEvent(player, clock, rndEvent.rollEvent(timeChange));
                        
                        //checks if after everything the player is still alive
                        if (CheckIfDead(player, clock)) {
                            System.out.println("YOU DIED");
                            isRunning = false;
                            break;
                        }
                        
                        //moves player to chosen city, prints out connected cities
                        System.out.println("Moving to " + targetName);
                        player.MoveTo(map, targetName);
                        player.getAdjescentCities(map);
                    } else {
                        System.out.println("City " + targetName + " is too far away/does not exist");
                    }
                }
                case "list" -> {
                    //prints out all cities and whether they contain the player
                    //for troubleshooting
                    for (int i = 0; i < map.TOTALCITIES; i++) {
                        System.out.println(map.cities[i].cityName + " " + map.cities[i].hasPlayer);
                    }
                }
                case "exit" -> {
                    //stops the game early
                    System.out.println("Closing the system.");
                    isRunning = false;
                }
                case "item" -> {
                    //test for geting specific item
                    for (Items.Item i : items.items) {
                        if(i.returnID() == 0) {
                            //use this loop to get items by id
                            //System.out.println(i.returnSelf().description);
                        }
                    }
                    //System.out.println(items.items.);
                }
                case "inventory" -> {
                    //return all items in inventory
                    //returns only name, can set to return other things
                    System.out.println("You have " + player.inventory.returnSize() + " items:");
                    for (int i = 0; i < player.inventory.returnSize(); i++) {
                        System.out.println(player.inventory.items.get(i).name);
                    }
                    //System.out.println(items.items.);
                }
                case "add" -> {
                    String itemName = input.next();
                    for (Items.Item i : items.items) {
                        if (i.name.equals(itemName)) {
                            player.inventory.addItem(i);
                            System.out.println("Added " + i.name + " to inventory");
                        }
                    }
                }
                case "remove" -> {
                    String itemName = input.next();
                    for (Items.Item i : items.items) {
                        //use exact names, with capital letters;
                        if (i.name.equals(itemName)) {
                            player.inventory.removeItem(i);
                            System.out.println("Removed " + i.name + " from inventory");
                        }
                    }
                }
                case "use" -> {
                    String itemName = input.next();
                    for (Items.Item i : items.items) {
                        //use exact names, with capital letters;
                        if(i.name.equals(itemName)) {
                            i.use(player, clock);
                            player.inventory.removeItem(i);
                        }
                    }
                }
                case "buildings" -> {
                    //returns all buildings in the currenct city
                    System.out.println("There are " + player.position.numberOfBuildings + " buildings in city " + player.position.cityName + ":");
                    for(int i = 0; i < player.position.numberOfBuildings; i++) {
                        System.out.println(player.position.buildings[i].name);
                    }
                }
                case "enter" -> {
                    String buildingName = input.next();
                    for (Map.City.Building i : player.position.buildings) {
                        if (i.name.equals(buildingName)) {
                            i.Enter(player);
                            i.Exit();
                        }
                    }
                }
                default -> {
                    System.out.println("?");
                }

            }
            
            //checks wether player has reached the end
            if (player.position.cityName.equals("Eindhoven")) {
                System.out.println("You Win!");
                isRunning = false;
            }

            if (isRunning) {
                command = input.next();
            }
        }
        
        /*
        *
        *   END OF LOOP
        *
        */
        //r.setVisible(false);
    }
}   

/*
Buildings - seperate class or object in map/city?
shop - subobject of building or extension?
shop inventory - extension of container/ array list of container?
Container - class with item id int?
inventory - container extension in player or an array list of container?
items stored by id
*/

/*
1)
[X]    Items
[X]     Set of all items for use

2)
[X]    Container - seperate class
[X]      int size, arraylist items
[X]    Container methods
[?]        add/remove item, get item by name/id

3)
[X]    Inventory - extension of Container

4)
[X]    Building - sub-class of City
[X]    Each city has rnd array of buildings (non repeating hopefully)
[X]    Building has instance variables:
[X]        String name, int id (?)

5)
[]    Shop - extension of Building
[]        name = "shop"
[]        ShopInventory - extension of container 
*/