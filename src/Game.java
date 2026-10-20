
// This class should serve as the primary method loop & contain the main method.
// instantiates Clock, Player, & Map. All other classes should be considered subclasses of these three.
// Should render output with Swing (possibly do this in a seperate renderer object)


//import java.awt.Point;
import java.util.*;
import javax.swing.*;

public class Game {
    static boolean CheckIfDead(Player player, Clock clock) {
        return player.hunger > player.maxHunger || player.thirst > player.maxThirst || player.exhaustion > player.maxExhaustion || clock.TimeRemaining < 0;
    }
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        Items items = new Items();
        Map map = new Map(items);
        Player player = new Player(map);
        RandomEvents rndEvent = new RandomEvents(items);
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
                    System.out.println("You have " + player.inventory.returnItemCount() + " items:");
                    for (int i = 0; i < player.inventory.returnItemCount(); i++) {
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
                case "rest" -> {
                    player.rest(clock);
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
1) New stats:
    [X] thirst - similar to hunger, increases by less, has lower maxThirst
    [X] carryWeight - sum of weight of all items in inventory
    [X] exhaustion - increases with travel time (less than others) * carryWeight
    [X] overallWellbeing - sum(maxStat - currentStat) / sum(maxStat)
    Boolean variables (not survival stats):
    [] boolean hasBackpack - is decided wether the player has a backpack
    [X] boolean isCursed - after random event set to true, makes stat increases double, may be cured in church

2) New items:
    [] Backpack - does not take up inventory space, weight = 0, increases inventory space (size) by 10
    [] Drink - reduce thirst
    [] Rock - heavy, may not sell, may only discard one per city
    [] Teleporter - allow to instantly travel to any city in distance regardless of connections, no time or stat changes occur
    Stat trading items (increase stats more than single stat items, but also reduce other stats):
    [] Coffee - reduce exhaustion, increase thirst
    [] item 1 - reduce exhaustion, increase hunger
    [] Jawbraker - reduce hunger, increase exhaustion
    [] item 2 - reduce hunger, increase thirst
    [] item 3 - reduce thirst, increase hunger
    [] item 4 - reduce thirst, increase exhaustion

3) New buildings:
    [] Temple - allow to cure curse for money
    [] building 1 - TBD
    [] building 2 - TBD
    [] building 3 - TBD
    [] building 4 - TBD
    [] building 5 - TBD
    [] building 6 - TBD

4) New random events:
    [X] Rocks fill up empty inventory space
    [X] Become cursed
    [] event that gives a random item

5) New mechnics:
    [X] item weight - each item has its own weight
    [X] all paths between primary cities have a quality cap lower than max to encourage 'exploration'
    [] rest - exchange time and stats to reduce exhaustion
    [] difficulties - determine starting stats and maxStats
    [] weather - impact calculations with path quality, heat reduces thirst more
*/