import java.util.ArrayList;

public class Player {
    Container inventory;
    Map.City location;
    Map worldReference;
    Clock clockReference;
    Renderer renderReference;
    
    int hunger;
    int maxHunger;
    int thirst;
    int maxThirst;
    int exhaustion;
    int maxExhaustion;
    int fame;
    boolean isCursed;

    double overallWellbeing;

    //money
    int money;
    
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
        clockReference.passTime(distance + 5 - (quality / 51));
        MovementStatChanges(distance + 5 - (quality / 51));
        renderReference.setWellbeingBar((int)overallWellbeing);
        
        if (worldReference.pathMatrix[a.cityID][location.cityID].isPath) {
            location = a;
            renderReference.addToLog("[" + clockReference.getDate() + " | " + clockReference.getTime() + "] - travelled to city " + location.cityName);
            System.out.println("just moved to city: " + location.cityName);
            renderReference.renderTravelOptions(this.getAdjacentCities(location), location);
        } else {
            System.out.println("cannot move to " + a.cityName + " because it is not adjacent!");
        }
        //The formula for determining the amount of time nescecary for a moving action:

    }
    
    public void MovementStatChanges(int timeChange) {
        if (isCursed) {
            HungerChange(-2 * (timeChange / 10));
            ThirstChange(-2 * (timeChange / 15));
            ExhaustionChange(-2 * (timeChange / 30) * ((inventory.getCurrentEncumbrance() / 4) + 1));
        } else {
            HungerChange(-1 * (timeChange / 10));
            ThirstChange(-1 * (timeChange / 15));
            ExhaustionChange(-1 * (timeChange / 30) * ((inventory.getCurrentEncumbrance() / 4) + 1));
        }
        WellbeingChange();
    }

    public void HungerChange(int change) {
        hunger -= change;
        if (hunger < 0) {
            hunger = 0;
        }
    }

    public void ThirstChange(int change) {
        thirst -= change;
        if (thirst < 0) {
            thirst = 0;
        }
    }

    public void ExhaustionChange(int change) {
        exhaustion -= change;
        if (exhaustion < 0) {
            exhaustion = 0;
        }
    }

    public void WellbeingChange() {
        //figure out whuts up with this formula
        overallWellbeing =  (((maxHunger - hunger) + (maxThirst - thirst) + (maxExhaustion - exhaustion)) * 1.0) / ((maxHunger + maxThirst + maxExhaustion) * 1.0);
        renderReference.revalidate();
    }

    public void MoneyChange(int change) {
        money += change;
        if (money < 0) {
            money = 0;
        }
        renderReference.setMoneyLabel("Money: " + money);
    }

    public void FameChange() {
        fame += (int) (Math.random() * 5);
    }

    public void CurseStatusChange(int change) {
        switch (change) {
            case 0 -> {
                isCursed = false;
            }
            case 1 -> {
                isCursed = true;
            }
            default -> {
                //nothing
            }
        }
    }

    public void rest() {
        HungerChange(-1 * 10);
        ThirstChange(-1 * 5);
        ExhaustionChange(30);
        clockReference.passTime(30);
        WellbeingChange();
    }
    
    
    
    Player() {
        inventory = new Container(20, "inventory", this);
        //survival
        hunger = 0;
        maxHunger = 100;

        thirst = 0;
        maxThirst = 50;

        exhaustion = 0;
        maxExhaustion = 100;

        overallWellbeing = 100;//((maxHunger - hunger) + (maxThirst - thirst) + (maxExhaustion - exhaustion)) / (maxHunger + maxThirst + maxExhaustion);

        //money
        money = 1000;

        //wisdom
        fame = 0;
        isCursed = false;
    }
}
