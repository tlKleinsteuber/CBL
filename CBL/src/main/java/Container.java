import java.util.*;

public class Container {
    //size seem redundant, can just return items.size();
    int currentEncumbrance;
    int maxCapacity;
    int itemCount;
    String containerName;
    ArrayList<Items> items;
    
    
    public Container(int capacity, String name){
        itemCount = 0;
        
        maxCapacity = capacity;
        containerName = name;
        items = new ArrayList<Items>();
    }
    
    void addItem(Items item) {
        if(currentEncumbrance + item.encumbrance <= maxCapacity) {
            items.add(item);
            currentEncumbrance += item.encumbrance;
            itemCount++;
        }
    }
    
    void removeItem(Items item) {
        if(currentEncumbrance > 0) {
            items.remove(item);
            currentEncumbrance -= item.encumbrance;
            itemCount--;
        }
    }
    
    boolean hasItem(Items item) {
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
    
    public Items getItemByUniqueID(int id) {
        for(int i = 0; i < items.size(); i++) {
            if (items.get(i).identifierID == id) {
                return items.get(i);
            }
        }
        return null;
    }
}