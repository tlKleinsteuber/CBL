public class Item {
    
    public class Items {
        int price;
        int id;
        String name;
        String description;
    }
    
    public class Food extends Items{
        public void Food() {
            price = 4;
            id = 0;
            name = "Food";
            description = "Restores x hunger.";
        }
    }
    
    public class Watch extends Items {
        public void Clock() {
            price = 10;
            id = 1;
            name = "Watch";
            description = "Increases time left by x minutes.";
        }
    }
}
