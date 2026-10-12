import javax.swing.*;

public class CBL {
    public static void main(String[] args) {
        Clock clock = new Clock(7);
        Renderer r = new Renderer();
        Map world = r.world;
        SwingUtilities.invokeLater(() -> r.setVisible(true));
        
    }
}
