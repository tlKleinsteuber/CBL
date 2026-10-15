import java.util.ArrayList;


public class Container {
    int maxCapacity;
    String containerName;
    ArrayList<Item> containerContents = new ArrayList<Item>();
    
    public void dropItem(Item a) {
        a = null;
    }
    
    
    Container(int capacity, String name) {
        maxCapacity = capacity;
        containerName = name;
    }
    
    public class Item {
        int itemEncumbrance;
        Container parent;
        //interactType will be set to whatever primary action should be able to be done using the object. For example, food will have a value of "eat", etc etc bluh bluh!
        String interactType;
        
        Item(int encumbrance, Container parentContainer) {
            itemEncumbrance = encumbrance;
            Container parent = parentContainer;
        }
        
    }




}






