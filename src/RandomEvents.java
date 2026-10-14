public class RandomEvents {
    public RandomEvents() {
        
    }
    //roll a rnd number to determine if and what type of event happens
    int rollEvent(int timeChange) {
        int chancemaker = (int) ((Math.random() * 150));
        if(chancemaker < 50) {
            return 0;
        } else if (chancemaker < 60) {
            return 1;
        } else if (chancemaker < 70) {
            return 2;
        } else if (chancemaker < 80) {
            return 3;
        } else {
            return 4;
        }
    }
    
    void FoundFood(Player player) {
        int change = (int) ((Math.random() * 4) + 1);
        player.HungerChange(change);
        System.out.println("You found some food, restore " + change + " hunger.");
    }
    
    void EventTemplate() {
        System.out.println("Random event");
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
        }
    }
}
