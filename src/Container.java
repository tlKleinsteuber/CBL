import java.util.*;

public class Container {
    //size seem redundant, can just return items.size();
    int size;
    int maxSize;
    int itemCount;
    ArrayList<Items.Item> items;
    
    void addItem(Items.Item item) {
        if(size + item.encumbrance <= maxSize) {
            items.add(item);
            size += item.encumbrance;
            itemCount++;
        }
    }
    
    void removeItem(Items.Item item) {
        if(size > 0) {
            items.remove(item);
            size -= item.encumbrance;
            itemCount--;
        }
    }
    
    boolean hasItem(Items.Item item) {
        return items.contains(item);
    }
    
    void setSize() {
            size = items.size();
        }
        
    int returnSize() {
        return size;
    }
    
    int returnItemCount() {
        return itemCount;
    }
}
