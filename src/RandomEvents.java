import java.util.*;

public class RandomEvents {
    Items items;
    public RandomEvents(Items items) {
        this.items = items;
    }
    //roll a rnd number to determine if and what type of event happens
    int rollEvent(int timeChange) {
        int chancemaker = (int) ((Math.random() * 100));
        System.out.println("Roll: " + chancemaker);
        if(chancemaker < 50) {
            return 0;
        } else if (chancemaker < 55) {
            return 1;
        } else if (chancemaker < 60) {
            return 2;
        } else if (chancemaker < 65) {
            return 3;
        } else if (chancemaker < 70) {
            return 4;
        } else if (chancemaker < 75) {
            return 5;
        } else if (chancemaker < 80) {
            return 6;
        } else if (chancemaker < 85) {
            return 7;
        } else if (chancemaker < 90) {
            return 8;
        } else if (chancemaker < 95) {
            return 9;
        } else {
            return 10;
        }
    }
    
    void EventTemplate() {
        System.out.println("Random event");
    }
    
    void FoundFood(Player player) {
        int change = (int) ((Math.random() * 4) + 1);
        player.HungerChange(change);
        System.out.println("You found some food, restore " + change + " hunger.");
    }
    
    void LooseChange(Player player) {
        int change = (int) (Math.random() * 40) - 20;
        player.MoneyChange(change);
        
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
        clock.TimePass(change);
        System.out.println(timeAnomalies[(int) (Math.random() * (timeAnomalies.length - 1))]);
        System.out.println("Your time remaining changed by " + (-1 * change));
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
    
    void MetCelebrity(Player player) {
        player.FameChange();
        String celebrity = celebrities[(int) (Math.random() * (celebrities.length - 1))];
        System.out.println("You met " + celebrity + ", gain fame.");
    }
    
    void Curse(Player player) {
        player.CurseStatusChange(1);
        System.out.println("You have been cursed! All stats increase 2x while travelling.");
    }
    
    void Rocks(Player player, Items items) {
        Iterator<Items.Item> iterator = items.items.iterator();
        Items.Item rock;
        int num = player.inventory.maxSize - player.inventory.size;
        while (iterator.hasNext()) {
            rock = iterator.next();
            if(rock.name.equals("Rock")) {
                for (int j = 0; j < num; j++) {
                    player.inventory.addItem(rock);
                }
            }
        }
        System.out.println("ROCKS");
    }
    
    void DoEvent(Player player, Clock clock, int id) {
        switch(id) {
            case 0 -> {
                System.out.println("Nothing ever happens.");
            }
            case 1 -> {
                FoundFood(player);
            }
            case 2 -> {
                LooseChange(player);
            }
            case 3 -> {
                TimeChange(clock);
            }
            case 4 -> {
                MetCelebrity(player);
            }
            case 5 -> {
                Curse(player);
            }
            case 6 -> {
                Rocks(player, items);
            }
            case 7 -> {
                //
            }
            case 8 -> {
                //
            }
            case 9 -> {
                //
            }
            case 10 -> {
                //
            }
            default -> {
                //nothing
            }
        }
    }
}
