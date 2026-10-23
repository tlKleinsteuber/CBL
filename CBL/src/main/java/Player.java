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
        int timeToMove = distance + 5 - (quality / 51);
        clockReference.passTime(timeToMove);
        MovementStatChanges(timeToMove);
        if(WellbeingChange() && a.cityName.equals("Eindhoven")) {
            renderReference.win();
        }
        renderReference.setWellbeingBar();
        renderReference.renderBuildings(location.buildings);
        
        
        
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
            ExhaustionChange(-2 * (timeChange / 30) * ((inventory.currentEncumbrance / 4) + 1));
        } else {
            HungerChange(-1 * (timeChange / 10));
            ThirstChange(-1 * (timeChange / 15));
            ExhaustionChange(-1 * (timeChange / 30) * ((inventory.currentEncumbrance / 4) + 1));
        }
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

    public boolean WellbeingChange() {
        overallWellbeing = ((((this.maxHunger - this.hunger) + (this.maxThirst - this.thirst) + (this.maxExhaustion - this.exhaustion)) * 1.0)/((this.maxHunger + this.maxThirst + this.maxExhaustion) * 1.0)) * 100;
            //alternate wellbeing calculation.
        //overallWellbeing = 100 - ((hunger / (maxHunger + 0.0)) + (thirst / (maxThirst + 0.0)) + (exhaustion / (maxExhaustion + 0.0))) * 100;
       
        System.out.println("Stats: ");
        System.out.println("Hunger: " + this.hunger + "/" + this.maxHunger);
        System.out.println("Thirst: " + this.thirst + "/" + this.maxThirst);
        System.out.println("Exhaustion: " + this.exhaustion + "/" + this.maxExhaustion);
        System.out.println("Overall wellbeing: " + String.format("%.2f", (this.overallWellbeing * 100)) + "%");
        renderReference.revalidate();
        if (hunger > maxHunger || thirst > maxThirst || exhaustion > maxExhaustion) {
            renderReference.loseGame("You lose! you neglected your health & were hospitalized! unfortunately, you cannot resit your finals... You have failed!");
            return false;
        }
        return true;
    }

    public void MoneyChange(int change) {
        money += change;
        if (money < 0) {
            money = 0;
        }
        renderReference.setMoneyLabel("Money: " + money);
    }

    public void FameChange(int change) {
        this.fame += change;
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
        inventory = new Container(20, "inventory");
        //survival
        hunger = 0;
        maxHunger = 100;

        thirst = 0;
        maxThirst = 50;

        exhaustion = 0;
        maxExhaustion = 100;

        overallWellbeing = 100.00;

        //money
        money = 1000;

        //wisdom
        fame = 0;
        isCursed = false;
    }
}
