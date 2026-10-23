import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.*;

/*
           /============/ MAP CLASS DOCUMENTATION \============\
            *MAP represents the world map.            
*/

public class Map {
    
    public String[] allNames = {"Atlantis", "Dallas", "Not-Paris", "Tokyo", "Elektrėnai", "Mumbai", "Trakai", "North America", "Aberdeen", "Abilene", "Akron", "Albany", "Albuquerque", "Alexandria", "Allentown", "Amarillo", "Anaheim", "Anchorage", "Ann Arbor", "Antioch", "Apple Valley", "Appleton", "Arlington", "Arvada", "Asheville", "Athens", "Atlanta", "Atlantic City", "Augusta", "Aurora", "Austin", "Bakersfield", "Baltimore", "Barnstable", "Baton Rouge", "Beaumont", "Bel Air", "Bellevue", "Berkeley", "Bethlehem", "Billings", "Birmingham", "Bloomington"};
    List<String> shuffleNames;
    String[] CITYNAMES = new String[allNames.length];
    public int PATHCHANCECONSTANT = 150;
    public double MINSECONDARYCITYDISTANCE = 30.0;
    public int TOTALCITIES = 23;
    public int PRIMARYCITIES = 10;
    public int chanceForPath;
    public City[] cities = new City[TOTALCITIES];
    public Edge[][] pathMatrix = new Edge[TOTALCITIES][TOTALCITIES];
    int totalPaths = 0;
    int totalEdges = 0;
    Items items;
    Player playerReference;
    Renderer renderReference;
    boolean leavingBuilding;
    
   
    public void randomizeNames() {
        Collections.shuffle(shuffleNames);
        for (int i = 0; i < allNames.length; i++) {
            CITYNAMES[i] = shuffleNames.get(i);
        }
    }
    
    public void passObjects(Renderer r) {
        renderReference = r;
    }
    
    
    public class City {
        
        public String cityName;
        public Point coords;
        public boolean isValidSecondaryCity;
        public int cityID;
        public int numberOfBuildings;
        public Building[] buildings;
        
        public City(int cityNum) {
            
            numberOfBuildings = (int) ((Math.random() * 4) + 1);
            SetBuildings(numberOfBuildings);
            if (numberOfBuildings > buildings.length) {
                numberOfBuildings = buildings.length;
            }
            
            cityID = cityNum;
            cityName = CITYNAMES[cityNum];
            System.out.println(cityNum + " cityName: " + cityName);
            if (cityNum < PRIMARYCITIES) {
                //city instantiation for primary cities
                if (cityNum == 0) {
                    cityName = "far away city";
                    coords = new Point(0, 0);
                } else if (cityNum == PRIMARYCITIES - 1) {
                    cityName = "Eindhoven";
                    coords = new Point(350, 350);
                } else {
                    //(FORMULA) Formula for coordinates of Primary non-start/end cities:
                    coords = new Point((int)(Math.random()* 20 - 10) + 40 * (cityNum), (int)(Math.random()*20 - 10) + 40 * (cityNum));
                }
                for (int i = 0; i < cityNum; i++) {
                    //instantiates a new edge between all previously instantiated cities & this one. Sets the distance too.
                    pathMatrix[i][cityNum] = new Edge(cities[i], this);
                    pathMatrix[i][cityNum].distance = (int)Math.sqrt(Math.pow(Math.abs(coords.x - cities[i].coords.x), 2) + Math.pow(Math.abs(coords.y - cities[i].coords.y), 2));
                    pathMatrix[cityNum][i] = pathMatrix[i][cityNum];
                }
                
            } else {
                //city instantiation for secondary cities
                do {
                    //essentially resets the city's edges. This is useful for iterative generation, in case a city does not meet criteria. then, rerolls coords & tests criteria again
                    for (int i = 0; i < cityNum; i++) {
                        pathMatrix[i][cityNum] = null;
                        pathMatrix[cityNum][i] = null;
                    }
                    
                    isValidSecondaryCity = true;
                    coords = new Point(coords = new Point((int)(Math.random() * 460),(int)(Math.random()* 460)));
                    
                    for (int i = 0; i < cityNum; i++) {
                        pathMatrix[i][cityNum] = new Edge(cities[i], this);
                        // (FORMULA) criteria for failed cities. Regenerates if these are satisfied.
                        if (pathMatrix[i][cityNum].distance < 100) {
                            isValidSecondaryCity = false;
                        } 
                        pathMatrix[cityNum][i] = pathMatrix[i][cityNum];
                    }
                } while (!isValidSecondaryCity);
            }
        }
        
        HashSet<Building> allBuildings = new HashSet<>();
        List<Building> shuffleBuildings;
        Building[] possibleBuildings;

        //Building building;
        Shop shop;
        Casino casino;
        Temple temple;

        void MakeBuildingSet() {
            //generates each buildingg seperately for uniqueness
            //building = new Building();
            shop = new Shop(items);
            casino = new Casino();
            temple = new Temple();

            //adds all unique buildings to a set
            //allBuildings.add(building);
            allBuildings.add(shop);
            allBuildings.add(casino);
            allBuildings.add(temple);

            //shuffles the set and turns into list
            shuffleBuildings = new ArrayList<>(allBuildings);
            Collections.shuffle(shuffleBuildings);

            possibleBuildings = new Building[shuffleBuildings.size()];
            //creates an array of all possible buildings for this city
            for (int i = 0; i < shuffleBuildings.size(); i++) {
                possibleBuildings[i] = shuffleBuildings.get(i);
            }
        }

        //sets buildings for city
        void SetBuildings(int numberOfBuildings) {
            MakeBuildingSet();
            if (numberOfBuildings > possibleBuildings.length) {
                numberOfBuildings = possibleBuildings.length;
            }
            buildings = new Building[numberOfBuildings];
            for (int i = 0; i < numberOfBuildings; i++) {
                buildings[i] = possibleBuildings[i];
            }
        }

        //super-class of all buildings
        public class Building {

            String name;
            int id;
            int ordererdID;
            

            void Enter(Player player) {
                /*
                Scanner input = new Scanner(System.in);
                
                System.out.println("ENTER MESSAGE");
                String command = input.next();
                while(!command.equals("leave")) {
                
                    //body
                
                    command = input.next();
                }
                 */
            }

            void Exit() {
                //System.out.println("EXIT MESSAGE");
            }
        }

        public class Casino extends Building {

            //allows gambling for kids
            Casino() {
                name = "Casino";
                id = 1;
            }

            @Override
            void Enter(Player player) {
                renderReference.addToLog("Gambling is FUN! How much you wanna bet?");
                renderReference.swapBuildingOptions("casino");
            }

            @Override
            void Exit() {
                renderReference.addToLog("May the Chancemaker be with you...");
                renderReference.renderTravelOptions(playerReference.getAdjacentCities(playerReference.location), playerReference.location);
                renderReference.swapBuildingOptions("noBuilding");
                
            }

            void Bet(Player player, int moola) {
                playerReference.MoneyChange(-1 * moola);
                int win = moola * ((int) (Math.random() * 100) - 50);
                if (playerReference.money + win < 0) {
                    renderReference.addToLog("Congrats! You're in debt!");
                    playerReference.money += win;
                } else if (win < 0) {
                    renderReference.addToLog("You lost " + -1 * win + " money");
                    playerReference.money += win;
                } else {
                    renderReference.addToLog("You won " + win + " money");
                    playerReference.money += win;
                }
            }
        }

        public class Shop extends Building {

            //writes all items into array for randomization
            Container stock;

            Shop(Items items) {
                stock = new Container(1000, "shop");
                
                stock.itemCount = (int) (Math.random() * 4 + 2);
                name = "Shop";
                id = 0;
                for (int i = 0; i < stock.itemCount; i++) {
                    stock.items.add(items.generateRandomItem());
                    System.out.println(stock.items.get(i).name);
                }
            }

            @Override
            void Enter(Player player) {
                //shows the wares
                System.out.println("The shop has " + stock.itemCount + " item(s) for sale:");
                PrintStock();
                renderReference.swapBuildingOptions("shop");
                
                renderReference.renderShopItems(stock.items);
                        /*
                        case "sell" -> {
                            affectedItemName = input.next();
                            boolean isInInventory = false;
                            for (int i = 0; i < player.inventory.itemCount; i++) {
                                if (player.inventory.items.get(i).name.equals(affectedItemName)) {
                                    isInInventory = true;
                                    if (player.inventory.items.get(i).price < 0) {
                                        System.out.println("You cannot sell this item!");
                                        break;
                                    } else {
                                        player.MoneyChange(player.inventory.items.get(i).price - 2);
                                        player.inventory.removeItem(player.inventory.items.get(i));
                                        break;
                                    }
                                }
                            }
                            if (!isInInventory) {
                                System.out.println("Item is not in inventory!");
                            }

                            /*
                            Iterator<Items.Item> iterator = player.inventory.items.iterator();
                            Items.Item i;
                            while (iterator.hasNext()) {
                                i = iterator.next();
                                if (i.name.equals(affectedItemName)) {
                                    if (i.price < 0) {
                                        System.out.println("You cannot sell this item!" + i.name);
                                        break;
                                    } else {
                                        player.MoneyChange(i.price - 2);
                                        //player.inventory.removeItem(i);
                                        player.inventory.itemCount--;
                                        iterator.remove();
                                        break;
                                    }
                                } else {
                                    System.out.println("Item is not in inventory!");
                                    break;
                                }
                            }
                             */
                        }
                    

            void PrintStock() {
                for (int i = 0; i < stock.itemCount; i++) {
                    System.out.println(stock.items.get(i).name + ": " + stock.items.get(i).price);
                }
            }

            @Override
            void Exit() {
                System.out.println("Come back again!");
                renderReference.renderTravelOptions(playerReference.getAdjacentCities(playerReference.location), playerReference.location);
                renderReference.swapBuildingOptions("noBuilding");
            }
        }

        public class Temple extends Building {

            Temple() {
                name = "Temple";
                id = 2;
            }

            @Override
            void Enter(Player player) {
                renderReference.swapBuildingOptions("temple");
                renderReference.addToLog("Come, pray! For 10$ be cleansed your soul!");
                renderReference.addToLog("Do you accept?");
            }

            @Override
            void Exit() {
                renderReference.addToLog("The Grandmatriarch will be pleased.");
                renderReference.renderTravelOptions(playerReference.getAdjacentCities(playerReference.location), playerReference.location);
                renderReference.swapBuildingOptions("noBuilding");
            }
        }
    }
    
    public class Edge {
        int distance;
        boolean isPath;
        int quality;
        
        public Edge(City a, City b) {
            isPath = false;
            distance = (int)Math.sqrt(Math.pow(Math.abs(a.coords.x - b.coords.x), 2) + Math.pow(Math.abs(a.coords.y - b.coords.y), 2));
            //Primary - primary path generation
            if (a.cityID < PRIMARYCITIES && b.cityID < PRIMARYCITIES) {
                if (a.cityID == b.cityID + 1 || a.cityID == b.cityID - 1) {
                    isPath = true;
  //[debug]         //System.out.println("path between primary city " + a.cityID + " & " + b.cityID + " established");
                }
            } else {
                //secondary <-> primary OR secondary <-> secondary path generation
                if (this.distance != 0) {
                    //(FORMULA) formula for path formation chance:
                    chanceForPath = (int) (PATHCHANCECONSTANT / this.distance) * 100;
  //[debug]         //System.out.println("[_] Attempting path instantiation between " + a.cityName + " ( " + a.cityID + ") and " + b.cityName + " (" + b.cityID + ")"
                    //+ "\n     Chance of path: " + chanceForPath);
                }
                if (Math.random() * 100 < chanceForPath) {
                    this.isPath = true;
                } else {
  //[debug          //System.out.print("[X] PATH INSTANTIATION FAILED! retrying...");
                }
            }
            if (isPath){
                //(FORMULA) formula for quality:                                *** NOTE TO SELF:           a good way to possibly provide balancing is to 
                                                                                                //             have edges further from the primary line tend to 
                                                                                                //              generate higher qualities, (or just primary
                                                                                                     //            ones having lower qualities). Otherwise, 
                                                                                                        //          taking the Primary branch is simply
                                                                                                         //         the meta too often
                
                    
                quality = (int)(Math.random()*256);
  //[debug]     //System.out.println("[O] Path instantiatied between " + a.cityName + " ( " + a.cityID + ") and " + b.cityName + " (" + b.cityID + ")."
                //        + "\n   Path quality: " + quality + ". Path diistance: " + distance);
            }
        }
    }
    
    public Map(Items a, Player b) {
        items = a;
        playerReference = b;
        
        shuffleNames = new ArrayList<>(Arrays.asList(allNames));
        randomizeNames();
        
        for (int i = 0; i < TOTALCITIES; i++) {
            cities[i] = new City(i);
        }
        
        for (int i = 0; i < TOTALCITIES; i++) {
            for (int j = 0; j < TOTALCITIES; j++) {
                pathMatrix[i][j] = new Edge(cities[i], cities[j]);
                totalEdges++;
                if (pathMatrix[i][j].isPath) {
                    totalPaths++;
                    pathMatrix[j][i] = pathMatrix[i][j];
                } else if (pathMatrix[j][i].isPath) {
                    pathMatrix[i][j] = pathMatrix[j][i];
                }
            }
        }
        
        System.out.println("total paths: " + totalPaths);
        System.out.println("total edges: " + totalEdges);
        
        
    }
}