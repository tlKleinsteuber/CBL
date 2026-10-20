import java.util.*;

public class Items {
    
    /*
    
    Simply a colection of items in code
    
    */
    
    //set of items, methods check for mathing id
    HashSet<Item> items = new HashSet<>();

    public Items() {
        //Creates item object of each type
        Item food = new Food();
        Item watch = new Watch();
        Item teleporter = new Teleporter();
        Item rock = new Rock();
        
        //adds all items to the item set
        items.add(food);
        items.add(watch);
        items.add(teleporter);
        items.add(rock);
    }
        
    //superclass for all items
    public class Item {
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
*/
    }
    
    public class Food extends Item{
        public Food() {
            price = 4;
            id = 0;
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
    
    public class Watch extends Item {
        public Watch() {
            price = 10;
            id = 1;
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
            clock.TimeRemaining += 60;
        }
    }
    
    public class Rock extends Item{
        public Rock() {
            price = -1;
            id = 3;
            encumbrance = 1;
            weight = 10;
            name = "Rock";
            description = "Heavy. Can only discard one before moving";
            flavour = "These make great pets.";
        }
        
        @Override
        public void use(Player player, Clock clock) {
            //player.HungerChange(5);
        }
    }
    
    public class Teleporter extends Item {
        public Teleporter() {
            price = 50;
            id = 2;
            name = "Teleporter";
            description = "Allows to go to any city within range";
            flavour = "It always works!";
        }
    }
}
