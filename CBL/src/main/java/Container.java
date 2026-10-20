import java.util.ArrayList;


public class Container {
    int maxCapacity;
    String containerName;
    ArrayList<Item> containerContents;
    Player playerReference;
    
    public void dropItem(Item a) {
        a = null;
    }
    
    public void addItemToContainer(Item a){
        if (getCurrentEncumbrance() < maxCapacity) {
            playerReference.inventory.containerContents.add(a);
            
        }
    }
    
    public Item createItem() {
        return new Item(3);
    }
    
    public int getCurrentEncumbrance() {
        int encumbraceAccumalator = 0;
        for (int i = 0; i < containerContents.size(); i++) {
            encumbraceAccumalator += containerContents.get(i).itemEncumbrance;
        }
        return encumbraceAccumalator;
    }
    
    Container(int capacity, String name, Player a) {
        maxCapacity = capacity;
        containerContents = new ArrayList<Item>();
        containerName = name;
        playerReference = a;
    }
    
    public class Item {
        int itemEncumbrance;    
        //interactType will be set to whatever primary action should be able to be done using the object. For example, food will have a value of "eat", etc etc bluh bluh!
        String interactType;
        
        Item(int encumbrance) {
            itemEncumbrance = encumbrance;
        }
        
    }




}






