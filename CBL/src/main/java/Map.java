import java.awt.Point;

public class Map {
    //helpful information for map handling / city instantiation
    String[] cityNames = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    int NUMOFCITIES = 23;
    int NESCECARYCITIES = 10;
    City[] cities = new City[NUMOFCITIES];
    int distanceBuffer;
    int chanceForPath;
    double MINBRANCHCITYDISTANCE = 30.0;
    int PATHCHANCECONSTANT = 150;
    boolean validCity = false;
    
    //pathMatrix contains strings that should be in a certain format, to be parsed for information regarding the links between 2 cities.
    //for example, pathMatrix[5][3] = "distance[42],special[none],condition[low]"; would indicate that the path between cities 5 and 3 has a distance of 42, no special attributes 
    //(attribute elaboration below), and is a low-condition road (I.E. more minutes to travel / higher chance of negative random events).
    String[][] pathMatrix = new String[NUMOFCITIES][NUMOFCITIES];
    String[][] secondaryCityPathMatrix = new String[NUMOFCITIES][NUMOFCITIES];
    
    
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
    
    /*          ====== file structure ====== 
    
    
        - instantiate map with various constants
        - when a map is instantiated, also populate the cities[] array with new cities
            -when a city is instantiated, if it's index is less than / eq to NESCECARYCITIES, construct it in a linear manner to ensure that there exuists +1 gauranteed path
            -if the inedx is HIGHER  than NESCECARYCITIES & Lower than NUMOFCITIES, instantiate the city coords completely randomly.
                If the new city is too close to any other city, reset coords to 0,0 & retry.
                once a valid set of coords is determined, chanceForPath is determined in accordance with the distance between all cities. (closer cities
                    have a higher chance to form connections)
                IF there happen to have been no formed connections, set coords to 0,0 & retry
    
    */
    
    
    
    
    
    
    
    public Map() {
        //populates the cities array with new city objects.
        
           for(int i = 0; i < NUMOFCITIES; i++) {
            for (int j = 0 ; j < NUMOFCITIES; j++) {
                secondaryCityPathMatrix[i][j] = "";
            }
        }
        
        
        for (int i = 0; i < NUMOFCITIES; i++) {
            cities[i] = new City(i);
            System.out.println("City " + cities[i].cityName + " num: " + i + " instantiated at coordinates: " + cities[i].coords);
        }

        
        //instantiation of the elements in pathmatrix pertaining to NESCECARY cities. 
        for (int i = 0; i < NUMOFCITIES; i++) {
            for (int j = 0; j < NUMOFCITIES; j++) {
                pathMatrix[i][j] = "";
                distanceBuffer = (int)Math.sqrt(Math.pow(Math.abs(cities[i].coords.x - cities[j].coords.x), 2) + Math.pow(Math.abs(cities[i].coords.y - cities[j].coords.y), 2));
                    //checks if the current city is considered nescecary & if they are sequential
                if ((i == j + 1 || i == j - 1) && (!(j > NESCECARYCITIES - 1) && !(i > NESCECARYCITIES - 1))) {
                    pathMatrix[i][j] += Integer.toString(distanceBuffer)  + ",";
                    System.out.println("distance from city " + i + " to " + j + ": " + pathMatrix[i][j]);
                } else {
                    //Chance based path-instantiation for secondary cities
                    pathMatrix[i][j] = secondaryCityPathMatrix[i][j];
                    pathMatrix[j][i] = secondaryCityPathMatrix[j][i];
                }
            }
        }
        
        for(int i = 0; i < NUMOFCITIES; i++) {
            for (int j = 0 ; j < NUMOFCITIES; j++) {
                System.out.println(pathMatrix[i][j]);
            }
        }
        
    }
    
    
     public class City {
        //declare appropriate city information
        String cityName;
        Point coords;
        public City(int cityNum) {
            //paramater cityNum helps handle city individuality (future proofing, mostly).
            //therefore there should exist an integer counter where cities are instantiated to identify and individualize cities.
            //assign appropriate city information. Further in development, make this generation dependant on a random (or preset) seed.
            cityName = cityNames[cityNum];
            
            //In the instance that the city is either the start or end (first 2 intantiated cities), apply set data.
            if (cityNum == 0) {
                cityName = "startCity";
                coords = new Point(0, 0);
            } else if (cityNum == NESCECARYCITIES - 1) {
                cityName = "Eindhoven";
                coords = new Point(350, 350);
            } else if (cityNum <= NESCECARYCITIES - 2){
                coords = new Point((int)(Math.random()* 20 - 10) + 40 * (cityNum), (int)(Math.random()*20 - 10) + 40 * (cityNum));
            } else {
                //CRITERIA FOR NON-NESCECARY CITY GENERATION!!!!! improtant
                    
                /*
                
                CHECK FOR ISLAND GENERATION!!!!!!!!!!!
                regenerate cities in islands OR force a connection to them
                
                */
                
                
                do {
                    //essentially resets the city. This makes sure paths don't fuck up. also assumes city validity to be true, only to be invalidated if criteria is not met.
                    coords = new Point((int)(Math.random() * 460),(int)(Math.random()* 460));
                    validCity = true;
                    for (int j = 0; j < cityNum; j++) {
                        secondaryCityPathMatrix[j][cityNum] = "";
                        secondaryCityPathMatrix[cityNum][j] = "";

                    }                    
                    
                    for (int i = 0; i < cityNum; i++) {
                        
                        
                        distanceBuffer = (int)Math.sqrt(Math.pow(Math.abs(coords.x - cities[i].coords.x), 2) + Math.pow(Math.abs(coords.y - cities[i].coords.y), 2));
                        
                        if (distanceBuffer < 100) {
                            validCity = false;
                            System.out.println("FAILED ATTEMPT to instantiate secondary city " + cityNum + " at " + coords);
                        } else {
                            //instantiates paths between given secondary city & others
                            if (distanceBuffer != 0) {
                                
                                /*
                                
                                THIS IS THE FORMULA FOR CHANCE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                                
                                
                                */
                                
                                
                                chanceForPath = (int)( PATHCHANCECONSTANT / distanceBuffer) * 100;
                                
                                if (Math.random()*100 < chanceForPath) {
                                    secondaryCityPathMatrix[i][cityNum] = Integer.toString(distanceBuffer)  + ",";
                                    secondaryCityPathMatrix[cityNum][i] = Integer.toString(distanceBuffer)  + ",";
                                    System.out.println("distance from city " + i + " to SECONDARY CITY " + cityNum + ": " + secondaryCityPathMatrix[i][cityNum]);
                        }
                    }
                            
                        }
                    }
                } while (!validCity);
            System.out.println("SUCCESSFULLY INSTANTIATTED secondary city " + cityNum + " at " + coords);
        }
        
    }
}
}

