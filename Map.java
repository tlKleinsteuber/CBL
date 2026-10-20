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
    
    //make into Object
    //public boolean[][] connections = new boolean[TOTALCITIES][TOTALCITIES];
    //public int[][] distances = new int[TOTALCITIES][TOTALCITIES];    
    
    /*
    
    =================================
            IMPORTANT NOTE
    =================================
        - heya! 
        basic map instantiation is *technically* implemented now.
        i say technically because it's unbalanced and jank as hell
        things to do in this file from here:
            - make the formation of paths between citiess not tied to a hard cutoff of <20 units
                instead make it chance based, with a higher chance of a path being formed at lower distances.
            -further randomize city ditribution. As of now, i've got a system set up so that it draws a *relatively* straight line with ~5 units of deviation
                between x & y coordinates of cities. This results in practically no choice on where to go. The solution to this is to continue instantiating this sort of
                "sure win" path, but instantiation some additional cities that are not constrained to small range deviations. (we should also do some cleanup and delete cities that are 
                generated too far from any others, to such an extent that they do not have any paths whatsoever).
            -"units" is entirely arbitrary. it will be easy to convert this to whatever unit we'd like (likely km) & change the scaling of such.
    
    
    */
    
    void randomizeNames() {
        Collections.shuffle(shuffleNames);
        for (int i = 0; i < allNames.length; i++) {
            CITYNAMES[i] = shuffleNames.get(i);
        }
    }
    
    public Map() {
        
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
                    pathMatrix[i][j] = pathMatrix[j][j];
                }
            }
        }
        
        //System.out.println("total paths: " + totalPaths);
        //System.out.println("total edges: " + totalEdges);
        
        
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
    
    /*public class City {
        //declare appropriate city information
        String cityName;
        String displayName;
        Point coords;
        public int cityID;
        boolean hasPlayer;
        
        /*public City(int cityNum) {
            //paramater cityNum helps handle city individuality (future proofing, mostly).
            //therefore there should exist an integer counter where cities are instantiated to identify and individualize cities.
            //assign appropriate city information. Further in development, make this generation dependant on a random (or preset) seed.
            cityName = cityNames[cityNum];
            cityID = cityNum;
            
            //In the instance that the city is either the start or end (first 2 intantiated cities), apply set data.
            switch (cityNum) {
                case 0 -> {
                    cityName = "startCity";
                    coords = new Point(0, 0);
                    hasPlayer = false;
                    //break;
                }
                case 1 -> {
                    cityName = "Eindhoven";
                    coords = new Point(300, 300);
                    hasPlayer = false;
                    //break;
                }
                default -> {
                    coords = new Point((int)(Math.random()*30) + 15 * cityNum, (int)(Math.random()*30) + 15 * cityNum);
                    hasPlayer = false;
                    //break;
                }
            }
            
        }
    }*/
        
    public class City {
        
        public String cityName;
        public Point coords;
        public boolean isValidSecondaryCity;
        public int cityID;
        public boolean hasPlayer;
        
        public City(int cityNum) {
            cityID = cityNum;
            cityName = CITYNAMES[cityNum];
            if (cityNum < PRIMARYCITIES) {
                //city instantiation for primary cities
                if (cityNum == 0) {
                    cityName = "start";
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
                quality = (int)(Math.random()*256);
            }
        }
    }
}