
// This class should serve as the primary method loop & contain the main method.
// instantiates Clock, Player, & Map. All other classes should be considered subclasses of these three.
// Should render output with Swing (possibly do this in a seperate renderer object)


import java.awt.Point;
import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        //System.out.println("start");
        Scanner input = new Scanner(System.in);
        
        Map map = new Map();
        //System.out.println("after map");
        Player player = new Player(map);
        //System.out.println("after player");
        
        boolean isRunning = true;
        player.getAdjescentCities(map);
        String command = input.next();
        while (isRunning) {
            switch (command.toLowerCase()) {
                case "stats" -> {
                    player.returnStats();
                }
                case "move" -> {
                    //if player has increased reach, may travel to unconnected citeis (teleport)
                    String targetName = input.next();
                    if(player.isConnected(map, targetName)) {
                        System.out.println("Moving to " + targetName);
                        player.MoveTo(map, targetName);
                        player.getAdjescentCities(map);
                    } else {
                        System.out.println("City " + targetName + " is too far away/does not exist");
                    }
                }
                case "list" -> {
                    for (int i = 0; i < map.NUMOFCITIES; i++) {
                        System.out.println(map.cities[i].cityName + " " + map.cities[i].hasPlayer);
                    }
                }
                case "exit" -> {
                    System.out.println("Closing the system.");
                    isRunning = false;
                }
                default -> {
                    System.out.println("?");
                }

            }

            if (isRunning) {
                command = input.next();
            }
        }
    }
}   