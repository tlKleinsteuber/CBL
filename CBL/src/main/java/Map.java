import java.awt.Point;

/*
           /============/ MAP CLASS DOCUMENTATION \============\
            *MAP represents the world map.            
*/

public class Map {
    
    public String[] CITYNAMES = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    public int PATHCHANCECONSTANT = 150;
    public double MINSECONDARYCITYDISTANCE = 30.0;
    public int TOTALCITIES = 23;
    public int PRIMARYCITIES = 10;
    public int chanceForPath;
    public City[] cities = new City[TOTALCITIES];
    public Edge[][] pathMatrix = new Edge[TOTALCITIES][TOTALCITIES];
    int totalPaths = 0;
    int totalEdges = 0;
   
    
    public class City {
        
        public String cityName;
        public Point coords;
        public boolean isValidSecondaryCity;
        public int cityID;
        
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
    
    public Map() {
        
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
        
        System.out.println("total paths: " + totalPaths);
        System.out.println("total edges: " + totalEdges);
        
        
    }
}