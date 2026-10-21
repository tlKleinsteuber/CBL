import java.util.*;

public class Container {
    //size seem redundant, can just return items.size();
    int currentEncumbrance;
    int maxCapacity;
    int itemCount;
    String containerName;
    ArrayList<Items.Item> items;
    
    
    public Container(int capacity, String name){
        maxCapacity = capacity;
        containerName = name;
    }
    
    void addItem(Items.Item item) {
        if(currentEncumbrance + item.encumbrance <= maxCapacity) {
            items.add(item);
            currentEncumbrance += item.encumbrance;
            itemCount++;
        }
    }
    
    void removeItem(Items.Item item) {
        if(currentEncumbrance > 0) {
            items.remove(item);
            currentEncumbrance -= item.encumbrance;
            itemCount--;
        }
    }
    
    boolean hasItem(Items.Item item) {
        return items.contains(item);
    }
    
    void setSize() {
            currentEncumbrance = items.size();
        }
        
    int returnSize() {
        return currentEncumbrance;
    }
    
    int returnItemCount() {
        return itemCount;
    }
}