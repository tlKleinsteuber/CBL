import java.util.*;

public class RandomEvents {
    Items itemList;
    //instantiates random events with an item list for Rock event
    public RandomEvents(Items itemList) {
        this.itemList = itemList;
    }
    
    /*
    void EventTemplate() {
        System.out.println("Random event");
    }
    */
    
    void AteFood(Player playerReference) {
        int change = (int) ((Math.random() * 9) + 1);
        playerReference.HungerChange(change);
        playerReference.WellbeingChange();
        System.out.println("A schwarma dipped in extra tahini fell into your mouth (with a rouge apple slice and ~3L of water), restore " + change + " hunger.");
    }
    
    void MoneyFindLose(Player playerReference) {
        int change = (int) (Math.random() * 40) - 20;
        playerReference.MoneyChange(change);
        
        //may do random phrases for money change later
        if (change > 0) {
            System.out.println("You found some loose change, gain " + change + " money.");
        } else if (change == 0) {
            System.out.println("Money is +- zero.");
        } else {
            System.out.println("A chimpmunk bit off some of your wallet, loose " + change + " money.");
        }
    }
    
    String[] timeAnomalies = {"Forward into the past!", 
                            "You passed a black hole's event horizon.", 
                            "You accidently used a time machine."};
    
    void TimeChange(Clock clock) {
        int change = (int) ((Math.random() * 100) - 50);
        clock.passTime(change);
        System.out.println(timeAnomalies[(int) (Math.random() * (timeAnomalies.length - 1))]);
        System.out.println("Your time changed by " + change + " minutes.");
    }
    
    String[] celebrities = {"Human Person", 
                            "Ted Kaczynski", 
                            "Guy Famous", 
                            "The 'Paper'", 
                            "Inga Karkhuul Haw'kohd Dis'nahm Bi'ahni Lawga Ormo Pohmpus Da'nit Ar'edi Iz Khura'in III", 
                            "Yourself", 
                            "The letter 'A'",
                            "Paul Atishon" , 
                            "Adult Jazz", 
                            "Theodore John Kaczynski", 
                            "Your maker", 
                            "Me", 
                            "Fredderick Fazbear", 
                            "Waga"};
    
    void MetCelebrity(Player playerReference) {
        int change = (int) (int) (Math.random() * 5);
        playerReference.FameChange(change);
        String celebrity = celebrities[(int) (Math.random() * (celebrities.length - 1))];
        System.out.println("You met " + celebrity + ", gain " + change + " fame.");
    }
    
    void Curse(Player playerReference) {
        playerReference.CurseStatusChange(1);
        System.out.println("You have been cursed! All stats increase 2x while travelling.");
    }
    
    void Rocks(Player playerReference, Items itemList) {
        Iterator<Items.Item> iterator = itemList.items.iterator();
        Items.Item rock;
        int numOfRock = playerReference.inventory.maxCapacity - playerReference.inventory.currentEncumbrance;
        while (iterator.hasNext()) {
            rock = iterator.next();
            if(rock.name.equals("Rock")) {
                for (int j = 0; j < numOfRock; j++) {
                    playerReference.inventory.addItem(rock);
                }
            }
        }
        System.out.println("ROCKS");
    }
    
    void DoEvent(Player playerReference, Clock clock) {
        int rollEvent = (int) ((Math.random() * 100));
        if (rollEvent < 50) {
            System.out.println("Nothing ever happens.");
        } else if (rollEvent < 65) {
            AteFood(playerReference);
        } else if (rollEvent < 70) {
            MoneyFindLose(playerReference);
        } else if (rollEvent < 75) {
            TimeChange(clock);
        } else if (rollEvent < 80) {
            MetCelebrity(playerReference);
        } else if (rollEvent < 85) {
            Curse(playerReference);
        } else {
            Rocks(playerReference, itemList);
        }
    }
}
