import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class CBL {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        Clock clock = new Clock(5);
        Map world = new Map();
                
        Renderer r = new Renderer();
        Component[] components = r.getComponents();

    }
}
