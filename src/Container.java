import java.util.*;

public class Container {
    //size seem redundant, can just return items.size();
    int size;
    int maxSize;
    ArrayList<Items.Item> items;
    
    void addItem(Items.Item item) {
        if(size < maxSize) {
            items.add(item);
            size++;
        }
    }
    
    void removeItem(Items.Item item) {
        if(size > 0) {
            items.remove(item);
            size--;
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
}
