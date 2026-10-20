import java.awt.Point;
import java.util.Collections;
import java.util.*;

public class Map {
    //helpful information for map handling / city instantiation
    
    //city names: all possible names, list for shuffling, array of shuffled names
    //when instantiating map, should use collections shuffle to randomly sort list of possible names
    String[] allNames = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",  "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    List<String> shuffleNames = new ArrayList<>(Arrays.asList(allNames));
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
    
    void randomizeNames() {
        Collections.shuffle(shuffleNames);
        for (int i = 0; i < allNames.length; i++) {
            CITYNAMES[i] = shuffleNames.get(i);
        }
    }
    
    public Map(Items items) {
        this.items = items;
        randomizeNames();
        
        for (int i = 0; i < TOTALCITIES; i++) {
            cities[i] = new City(i);
        }
        
        for (int i = 0; i < TOTALCITIES; i++) {
            for (int j = 0; j < TOTALCITIES; j++) {
                pathMatrix[i][j] = new Edge(cities[i], cities[j], i, j);
                totalEdges++;
                if (pathMatrix[i][j].isPath) {
                    totalPaths++;
                    pathMatrix[j][i] = pathMatrix[i][j];
                } else if (pathMatrix[j][i].isPath) {
                    pathMatrix[i][j] = pathMatrix[j][j];
                }
            }
        } 
    }
    
    public City SetStartCity(int i) {
        cities[i].hasPlayer = true;
        return cities[i];
    }
    
    public int getIdByName (String name) {
        for (int i = 0; i < TOTALCITIES; i++) {
            if (cities[i].cityName.equals(name)) {
                return i;
            }
        }
        return -1;
    }
        
    public class City {
        
        public String cityName;
        public Point coords;
        public boolean isValidSecondaryCity;
        public int cityID;
        public boolean hasPlayer;
        public int numberOfBuildings;
        public Building[] buildings;
        
        public City(int cityNum) {
            cityID = cityNum;
            cityName = CITYNAMES[cityNum];
            
            //gives the city a random colection of up to 5 unique buildings
            numberOfBuildings = (int) ((Math.random() * 4) + 1);
            SetBuildings(numberOfBuildings);
            if (numberOfBuildings > buildings.length) {
                numberOfBuildings = buildings.length;
            }
            
            if (cityNum < PRIMARYCITIES) {
                //city instantiation for primary cities
                if (cityNum == 0) {
                    cityName = "start";
                    coords = new Point(0, 0);
                    numberOfBuildings = 0;
                } else if (cityNum == PRIMARYCITIES - 1) {
                    cityName = "Eindhoven";
                    coords = new Point(350, 350);
                } else {
                    //(FORMULA) Formula for coordinates of Primary non-start/end cities:
                    coords = new Point((int)(Math.random()* 20 - 10) + 40 * (cityNum), (int)(Math.random()*20 - 10) + 40 * (cityNum));
                }
                for (int i = 0; i < cityNum; i++) {
                    //instantiates a new edge between all previously instantiated cities & this one. Sets the distance too.
                    pathMatrix[i][cityNum] = new Edge(cities[i], this, cities[i].cityID, this.cityID);
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
                        pathMatrix[i][cityNum] = new Edge(cities[i], this, cities[i].cityID, this.cityID);
                        // (FORMULA) criteria for failed cities. Regenerates if these are satisfied.
                        if (pathMatrix[i][cityNum].distance < 100) {
                            isValidSecondaryCity = false;
                        } 
                        pathMatrix[cityNum][i] = pathMatrix[i][cityNum];
                    }
                } while (!isValidSecondaryCity);
            }
        }
        
        /*
        
        G           A           P
        
        */
        
        //when instantiated, will pack random amount of buildings form this set
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
                Scanner input = new Scanner(System.in);
                
                System.out.println("Gambling is FUN! How much you wanna bet?");
                String command = input.next();
                while(!command.equals("leave")) {
                    int moola = Integer.parseInt(command);
                    if(moola <= player.money) {
                        Bet(player, moola);
                    } else {
                        System.out.println("U be poor!");
                    }
                    
                    System.out.println("Wanna bet again?");
                    command = input.next();
                }
            }
            
            @Override
            void Exit() {
                System.out.println("May the Chancemaker be with you...");
            }
            
            void Bet(Player player, int moola) {
                player.MoneyChange(-1 * moola);
                int win = moola * ((int) (Math.random() * 100) - 50);
                if (player.money + win < 0) {
                    System.out.println("Congrats! You're in debt!");
                    player.money += win;
                } else if (win < 0) {
                    System.out.println("You lost " + -1 * win + " money");
                    player.money += win;
                } else {
                    System.out.println("You won " + win + " money");
                    player.money += win;
                }
            }
        }
        
        public class Shop extends Building {
            //writes all items into array for randomization
            ArrayList<Items.Item> initialPossibleItems;
            Items.Item[] possibleItems;
            ShopInventory stock;
            
            void ArrayofPossibilities(Items items) {
                initialPossibleItems = new ArrayList(items.items);
                possibleItems = new Items.Item[initialPossibleItems.size()];
                for (int i = 0; i < initialPossibleItems.size(); i++) {
                    possibleItems[i] = initialPossibleItems.get(i);
                }
            }
            
            Shop(Items items) {
                name = "Shop";
                id = 0;
                stock = new ShopInventory();
                ArrayofPossibilities(items);
                for (int i = 0; i < stock.itemCount; i++) {
                    stock.items.add(possibleItems[(int) (Math.random() * items.items.size() - 1)]);
                }
            }
            
            class ShopInventory extends Container {
                ShopInventory() {
                    itemCount = (int) (Math.random() * 9) + 1;
                    items = new ArrayList<Items.Item>();
                }
            }
            
            @Override
            void Enter(Player player) {
                Scanner input = new Scanner(System.in);
                //shows the wares
                System.out.println("The shop has " + stock.itemCount + " item(s) for sale:");
                PrintStock();
                String command = input.next();
                String affectedItemName;
                while(!command.equals("leave")) {
                    switch(command) {
                        case "buy" -> {
                            affectedItemName = input.next();
                            Iterator<Items.Item> iterator = stock.items.iterator();
                            Items.Item i;
                            while (iterator.hasNext()) {
                                i = iterator.next();
                                if (i.name.equals(affectedItemName)) {
                                    if (player.money >= i.price) {
                                        if(player.inventory.returnSize() + i.encumbrance <= player.inventory.maxSize) {
                                            player.inventory.addItem(i);
                                            player.MoneyChange(-1 * i.price);
                                            iterator.remove();
                                            stock.itemCount--;
                                            PrintStock();
                                            break;
                                        } else {
                                            System.out.println("Not enough space!");
                                            break;
                                        }
                                    } else {
                                        System.out.println("Not enough money!");
                                        break;
                                    }
                                } /*else {
                                    System.out.println("Item is not in stock!");
                                }*/
                            }
                        }
                        case "sell" -> {
                            affectedItemName = input.next();
                            boolean isInInventory = false;
                            for (int i = 0; i < player.inventory.itemCount; i++) {
                                if(player.inventory.items.get(i).name.equals(affectedItemName)) {
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
                        default -> {
                            System.out.println("?");
                        }
                    }
                    
                    command = input.next();
                }
            }
            
            void PrintStock() {
                for (int i = 0; i < stock.itemCount; i++) {
                    System.out.println(stock.items.get(i).name + ": " + stock.items.get(i).price);
                }
            }
            
            @Override
            void Exit() {
                System.out.println("Come back again!");
            }
        }
        
        public class Temple extends Building {
            Temple() {
                name = "Temple";
                id = 2;
            }
            
            @Override
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
            
            @Override
            void Exit() {
                //System.out.println("EXIT MESSAGE");
            }
        }
    }
    
    public class Edge {
        int distance;
        boolean isPath;
        int quality;
        
        public Edge(City a, City b, int cityNum1, int cityNum2) {
            isPath = false;
            distance = (int)Math.sqrt(Math.pow(Math.abs(a.coords.x - b.coords.x), 2) + Math.pow(Math.abs(a.coords.y - b.coords.y), 2));
            //Primary - primary path generation
            if (a.cityID < PRIMARYCITIES && b.cityID < PRIMARYCITIES) {
                if (a.cityID == b.cityID + 1 || a.cityID == b.cityID - 1) {
                    isPath = true;
                }
            } else {
                //secondary <-> primary OR secondary <-> secondary path generation
                if (this.distance != 0) {
                    //(FORMULA) formula for path formation chance:
                    chanceForPath = (int) (PATHCHANCECONSTANT / this.distance) * 100;
                }
                if (Math.random() * 100 < chanceForPath) {
                    this.isPath = true;
                } else {
                }
            }
            if (isPath){
                if (cityNum1 < PRIMARYCITIES && cityNum2 < PRIMARYCITIES) {
                    quality = (int)(Math.random()*128);
                } else {
                    quality = (int)(Math.random()*256);
                }
            }
        }
    }
}