import java.util.ArrayList;

public class Player {
    int hunger;
    Container inventory;
    Map.City location;
    Map worldReference;
    Clock clockReference;
    Renderer renderReference;
    
    /**
     *
     * @param <ArrayList>
     * @param currentCity
     * @return
     */
    
    public void passObjects(Map a, Clock b, Renderer c) {
        worldReference = a;
        clockReference = b;
        renderReference = c;
        location = worldReference.cities[0];
        
    }
    
    public ArrayList<Map.City> getAdjacentCities(Map.City currentCity) {
        ArrayList<Map.City> cityReturn = new ArrayList<Map.City>();
        for (int j = 0; j < worldReference.TOTALCITIES; j++) {
            if (worldReference.pathMatrix[currentCity.cityID][j].isPath) {
                cityReturn.add(worldReference.cities[j]);
                System.out.println("currently adjacent to city " + worldReference.cities[j].cityName);
            }
        }
        return cityReturn;
}
    
    public void tryMoveTo(Map.City a){
        int distance = worldReference.pathMatrix[a.cityID][location.cityID].distance;
        int quality = worldReference.pathMatrix[a.cityID][location.cityID].quality;
        if (worldReference.pathMatrix[a.cityID][location.cityID].isPath) {
            location = a;
            renderReference.addToLog("[" + clockReference.currentDate + "] - travelled to city " + location.cityName);
            System.out.println("just moved to city: " + location.cityName);
            renderReference.renderTravelOptions(this.getAdjacentCities(location), location);
        } else {
            System.out.println("cannot move to " + a.cityName + " because it is not adjacent!");
        }
        //The formula for determining the amount of time nescecary for a moving action:

    }
    
    
    
    
    
    Player() {
        inventory = new Container(20, "inventory");
    }
}
