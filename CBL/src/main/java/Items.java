import java.util.*;

public class Items {
    int newUniqueID;
    int identifierID = 0;
    int price;
    int id;
    int encumbrance;
    int weight;
    String name;
    String description;
    String flavour;
    
    public Items generateRandomItem() {
        int a = (int) (Math.random() * 5);
        switch(a) {
            case 0:
                return new Food();
            case 1:
                return new Drink();
            case 2:
                return new Watch();
            case 3:
                return new Coffee();
            case 4: 
                return new Jawbreaker();
            default:
                return null;
        }
    }
    
    public int returnID() {
        return id;
    }

    public Items returnSelf() {
        return this;
    }

    public void use(Player player, Clock clock) {

    }
    
    
    public class Food extends Items {

        public Food() {

            price = 4;
            id = identifierID;
            identifierID++;
            encumbrance = 2;
            weight = 1;
            name = "Food";
            description = "Restores 5 hunger.";
            flavour = "It's just that - food.";
        }

        @Override
        public void use(Player player, Clock clock) {
            player.HungerChange(5);
            
        }
    }

    public class Drink extends Items {

        public Drink() {
            price = 4;
            id = identifierID;
            identifierID++;
            encumbrance = 2;
            weight = 1;
            name = "Drink";
            description = "Restores 2 thirst.";
            flavour = "A mystery thirst quenching liquid.";
        }

        @Override
        public void use(Player player, Clock clock) {
            player.ThirstChange(2);
        }
    }

    public class Watch extends Items {

        public Watch() {
            price = 10;
            id = identifierID;
            identifierID++;
            encumbrance = 1;
            weight = 1;
            name = "Watch";
            description = "Increases time left by 60 minutes.";
            flavour = """
                      Do not mind the logistics of time manipulation, 
                      for all will become clear in the future... or past? """;
        }

        @Override
        public void use(Player player, Clock clock) {
            clock.passTime(-60);
        }
    }

    public class Coffee extends Items {

        public Coffee() {
            price = 6;
            id = identifierID;
            identifierID++;
            encumbrance = 1;
            weight = 2;
            name = "Coffee";
            description = "Restores 10 exhaustion, reduces 5 thirst.";
            flavour = "It's tea flavoured.";
        }

        @Override
        public void use(Player player, Clock clock) {
            player.ThirstChange(-1 * 5);
            player.ExhaustionChange(10);
        }
    }

    public class Jawbreaker extends Items {

        public Jawbreaker() {
            price = 5;
            id = identifierID;
            identifierID++;
            encumbrance = 3;
            weight = 4;
            name = "Jawbreaker";
            description = "Restores 10 hunger, reduces 10 exhaustion.";
            flavour = "I'd rather eat a rock.";
        }

        @Override
        public void use(Player player, Clock clock) {
            player.ExhaustionChange(-1 * 10);
            player.HungerChange(10);
        }
    }

    //unused
    public class Teleporter extends Items {

        public Teleporter() {
            price = 50;
            id = identifierID;
            identifierID++;
            name = "Teleporter";
            description = "Allows to go to any city within range";
            flavour = "It always works!";
        }
    }
    
    
    
}
    /*
        public String returnName() {
            return name;
        }
    
}
    /*
    
    Simply a colection of items in code
    
    
    
    //set of items, methods check for mathing id
    HashSet<Item> items = new HashSet<>();

    public Items() {
        //Creates item object of each type
        newUniqueID = 0;
        
        Item food = new Food();
        Item watch = new Watch();
        //Item teleporter = new Teleporter();
        Item coffee = new Coffee();
        Item jawbreaker = new Jawbreaker();
        Item drink = new Drink();
        
        //adds all items to the item set
        items.add(food);
        items.add(watch);
        //items.add(teleporter);

        items.add(coffee);
        items.add(jawbreaker);
        items.add(drink);
    }
        
    //superclass for all items
    public class Item {
        int identifierID = 0;
        int price;
        int id;
        int encumbrance;
        int weight;
        String name;
        String description;
        String flavour;
        
        public int returnID() {
            return id;
        }
        
        public Item returnSelf() {
            return this;
        }
        
        public void use(Player player, Clock clock) {
            
        }
        /*
        public String returnName() {
            return name;
        }

    }
    
    public class Food extends Item{
        public Food() {
            
            price = 4;
            id  = identifierID;
            identifierID++;
            encumbrance = 2;
            weight = 1;
            name = "Food";
            description = "Restores 5 hunger.";
            flavour = "It's just that - food.";
        }
        
        @Override
        public void use(Player player, Clock clock) {
            player.HungerChange(5);
        }
    }
    
    public class Drink extends Item{
        public Drink() {
            price = 4;
            id = identifierID;
            identifierID++;
            encumbrance = 2;
            weight = 1;
            name = "Drink";
            description = "Restores 2 thirst.";
            flavour = "A mystery thirst quenching liquid.";
        }
        
        @Override
        public void use(Player player, Clock clock) {
            player.ThirstChange(2);
        }
    }
    
    public class Watch extends Item {
        public Watch() {
            price = 10;
            id = identifierID;
            identifierID++;
            encumbrance = 1;
            weight = 1;
            name = "Watch";
            description = "Increases time left by 60 minutes.";
            flavour = """
                      Do not mind the logistics of time manipulation, 
                      for all will become clear in the future... or past? """;
        }
        
        @Override
        public void use(Player player, Clock clock) {
            clock.passTime(-60);
        }
    }
    
    public class Coffee extends Item{
        public Coffee() {
            price = 6;
            id = identifierID;
            identifierID++;
            encumbrance = 1;
            weight = 2;
            name = "Coffee";
            description = "Restores 10 exhaustion, reduces 5 thirst.";
            flavour = "It's tea flavoured.";
        }
        
        @Override
        public void use(Player player, Clock clock) {
            player.ThirstChange(-1 * 5);
            player.ExhaustionChange(10);
        }
    }
    
    public class Jawbreaker extends Item{
        public Jawbreaker() {
            price = 5;
            id = identifierID;
            identifierID++;
            encumbrance = 3;
            weight = 4;
            name = "Jawbreaker";
            description = "Restores 10 hunger, reduces 10 exhaustion.";
            flavour = "I'd rather eat a rock.";
        }
        
        @Override
        public void use(Player player, Clock clock) {
            player.ExhaustionChange(-1 * 10);
            player.HungerChange(10);
        }
    }
    
    //unused
    public class Teleporter extends Item {
        public Teleporter() {
            price = 50;
            id = identifierID;
            identifierID++;
            name = "Teleporter";
            description = "Allows to go to any city within range";
            flavour = "It always works!";
        }
    }
}
*/