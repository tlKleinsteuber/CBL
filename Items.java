public class Items {
    
    //should id's be set manually?
    
    public class Item {
        int price;
        int id;
        String name;
        String description;
        String flavour;
    }
    
    public class Food extends Item{
        public void Food() {
            price = 4;
            id = 0;
            name = "Food";
            description = "Restores 5 hunger.";
            flavour = "It's just that - food.";
        }
    }
    
    public class Watch extends Item {
        public void Watch() {
            price = 10;
            id = 1;
            name = "Watch";
            description = "Increases time left by 60 minutes.";
            flavour = """
                      Do not mind the logistics of time manipulation, 
                      for all will become clear in the future... or past? """;
        }
    }
    
    public class Teleporter extends Item {
        public void Teleporter() {
            price = 50;
            id = 2;
            name = "Teleporter";
            description = "Allows to go to any city within range";
            flavour = "It always works!";
        }
    }
}
