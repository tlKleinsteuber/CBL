
// This class should serve as the primary method loop & contain the main method.
// instantiates Clock, Player, & Map. All other classes should be considered subclasses of these three.
// Should render output with Swing (possibly do this in a seperate renderer object)


import java.awt.Point;
import java.util.Scanner;

public class Game {
    
    static boolean CheckIfDead(Player player, Clock clock) {
        if (player.hunger > player.maxHunger || clock.TimeRemaining < 0) {
            return true;
        }
        return false;
    }
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        Map map = new Map();
        Player player = new Player(map);
        RandomEvents rndEvent = new RandomEvents();
        Clock clock = new Clock();
        
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
                    travelDistance = map.distances[map.getIdByName(targetName)][player.position.cityID];
                    if(player.isConnected(map, targetName)) {
                        //changes time, stats and roll for random event based on distance
                        clock.TimePass(travelDistance);
                        player.MovementStatChanges(travelDistance);
                        rndEvent.DoEvent(player, clock, rndEvent.rollEvent(travelDistance));
                        
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
                    for (int i = 0; i < map.NUMOFCITIES; i++) {
                        System.out.println(map.cities[i].cityName + " " + map.cities[i].hasPlayer);
                    }
                }
                case "exit" -> {
                    //stops the game early
                    System.out.println("Closing the system.");
                    isRunning = false;
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
    }
}   