import java.awt.Point;

public class Map {
    //helpful information for map handling / city instantiation
    String[] cityNames = {"a", "b", "c", "d", "e", "f", "g",  "c", "d", "e", "f", "g",  "c", "d", "e", "f", "g"};
    int NUMOFCITIES = 10;
    City[] cities = new City[NUMOFCITIES];
    int distanceBuffer;
    
    //pathMatrix contains strings that should be in a certain format, to be parsed for information regarding the links between 2 cities.
    //for example, pathMatrix[5][3] = "distance[42],special[none],condition[low]"; would indicate that the path between cities 5 and 3 has a distance of 42, no special attributes 
    //(attribute elaboration below), and is a low-condition road (I.E. more minutes to travel / higher chance of negative random events).
    String[][] pathMatrix = new String[NUMOFCITIES][NUMOFCITIES];
    
    
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

            -also pretty important to simplify the base line so that (idealy) it's cities are only connected to 1,2, (maybe) 3 others. 
                We can do this by either reducing the path cuttoff range, or hard-setting their values in the distance matrix.
    
    
    */
    
    
    
    public Map() {
        //populates the cities array with new city objects. (i should be considered the seed of the city).
        for (int i = 0; i < NUMOFCITIES; i++) {
            cities[i] = new City(i);
            System.out.println("City " + cities[i].cityName + " instantiated at coordinates: " + cities[i].coords);
        }
        
        for (int i = 0; i < NUMOFCITIES; i++) {
            for (int j = 0; j < NUMOFCITIES; j++) {
                pathMatrix[i][j] = "";
                distanceBuffer = (int)Math.sqrt(Math.pow(Math.abs(cities[i].coords.x - cities[j].coords.x), 2) + Math.pow(Math.abs(cities[i].coords.y - cities[j].coords.y), 2));
                if (distanceBuffer < 20) {
                    pathMatrix[i][j] += Integer.toString(distanceBuffer);
                    System.out.println("distance from city " + i + " to " + j + ": " + pathMatrix[i][j]);
                }
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
            } else if (cityNum == 1) {
                cityName = "Eindhoven";
                coords = new Point(100, 100);
            } else  {
                coords = new Point((int)(Math.random()*10) - 5 * cityNum, (int)(Math.random()*10) - 5 * cityNum);
            }
            
        }
    }
}

