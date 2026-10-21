import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;

public class Renderer extends javax.swing.JFrame {
    Color[][] pathQualityHelper;
    ArrayList<JButton> buttonList = new ArrayList<JButton>();
    ArrayList<JButton> itemUseButtonList = new ArrayList<JButton>();
    Player playerReference;
    Clock clockReference;
    int cityIDPasser;
    JLabel[] l;
    int logIncrement;
    
    public void setMoneyLabel(String val) {
        jLabel4.setText(val);
    }
    
    public void setWellbeingBar(int val){
        jProgressBar1.setValue(val);
    }
    
    public void passObjects(Clock b, Player c) {
        playerReference = c;
        clockReference = b;   
        jProgressBar1.setValue((int)playerReference.overallWellbeing);
        jLabel3.setText("Location: " + playerReference.location.cityName);
    }
    
    //don't worry about input handling. the input of a is determined by a getAdjacentCities() method call.
    public void renderTravelOptions (ArrayList<Map.City> a, Map.City position) {
        buttonList.clear();
        jPanel6.removeAll();
        for (int i = 0; i < a.size(); i++) {
            cityIDPasser = a.get(i).cityID;
             int distance = world.pathMatrix[a.get(i).cityID][position.cityID].distance;
             int quality = world.pathMatrix[a.get(i).cityID][position.cityID].quality;
             String travelingTo = a.get(i).cityName;
             int travelTime = 100;
             System.out.println("city id values passed onto event listnerr: " + cityIDPasser);
             jPanel6.add( new TravelOption(distance, quality, travelTime, travelingTo, i));
        }
    }
    
    //subclass built to express a dynamic amount of travel options near the map. Amount of instantiated objects should be = to size of ArrayList returned by getAdjacentCities called in CBL.java
    public class TravelOption extends Panel{
        
        TravelOption(int distance, int quality, int travelTime, String travelingTo, int travelNum) {
            
            this.add(new JLabel("distance: " + distance));
            this.add(new JLabel("quality : " + quality));
            this.add(new JLabel("travel time: " + (distance + 5 - (quality / 51))));
            this.add(new JLabel("traveling to : " + travelingTo));
            buttonList.add(new JButton("travel"));
            this.add(buttonList.get(travelNum));
            this.setVisible(true);
            this.setSize(600, 50);
            this.setLocation(5, travelNum * 55);
            
            
            buttonList.get(travelNum).addActionListener(new ActionListener() {
                int i = cityIDPasser;
                public void actionPerformed(ActionEvent e) {
                    
                    System.out.println("attempting to move to city id: " + i);
                    l[playerReference.location.cityID].setBackground(Color.lightGray);
                    playerReference.tryMoveTo(world.cities[i]);
                    jLabel3.setText("location: " + world.cities[i].cityName);
                    l[i].setBackground(Color.red);
                    jLabel3.setText("Location: " + world.cities[i].cityName);
                    
                }
            });
            
            this.setBackground(Color.white);
            System.out.println("instantiated new travel option");
        }
    }
    
    public void renderInventory(ArrayList<Container.Item> a) {
        itemUseButtonList.clear();
        jPanel7.removeAll();
        for (int i = 0; i < a.size(); i++) {
            jPanel7.add(new itemRenderInstance("thingus", 13, i));
        }
    }
    
    public class itemRenderInstance extends Panel {
        itemRenderInstance(String itemName, int encumbrance, int itemNum) {
            this.add(new JLabel(itemName));
            this.add(new JLabel("encumbrance: " + encumbrance));
            this.setVisible(true);
            this.setSize(600, 50);
            this.setLocation(5, itemNum * 55);
        }
    }
    
    //this is the subclass that paints all the edges between nodes. nifty! :)
    public class DisplayGraphics extends Panel{
        
        public void pathHelperMaker () {
            for (int i = 0; i < world.TOTALCITIES; i++) {
                for (int j = 0; j < world.TOTALCITIES; j++) {
                    pathQualityHelper[i][j] = new Color(255 - world.pathMatrix[i][j].quality, world.pathMatrix[i][j].quality, 4);
                } 
            }
        }
        
        
        /**
         *
         * @param g
         */
        @Override
        public void paint(Graphics g) {
            
            System.out.println("[!] canvas repainted");
            super.paintComponents(g);
            setBackground(Color.white);
            setForeground(Color.RED);
            setVisible(true);
            for(int i = 0; i < world.TOTALCITIES; i++) {
                for (int j = 0; j < world.TOTALCITIES; j++) {
                    if (world.pathMatrix[i][j].isPath) {
                        g.setColor(pathQualityHelper[i][j]);
                        //the following line is an atrocity to checkstyle lmao. let's worry about that later though.
                        g.drawLine(world.cities[i].coords.x + 20 + ((world.cities[i].cityName.length() - 1) * 10) / 2, world.cities[i].coords.y + 20, world.cities[j].coords.x + 20 + ((world.cities[j].cityName.length() - 1) * 10) / 2, world.cities[j].coords.y + 20);
                    }
                }
            }
        }
    }
    
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Renderer.class.getName());
    
    public Map world;
    public Player player;
    
    /**
     * Creates new form Renderer
     */
    
    
    public Renderer(Map a) {
        initComponents();
        world = a;
        jPanel8.setSize(600, 600);
        l = new JLabel[world.TOTALCITIES];
        pathQualityHelper = new Color[world.TOTALCITIES][world.TOTALCITIES];
        mapRender();
        jProgressBar1.setMaximum(100);
        jProgressBar1.setMinimum(0);
        
        
        for (int i = 0; i < world.TOTALCITIES; i++) {
            for (int j = 0; j < world.TOTALCITIES; j++) {
                pathQualityHelper[i][j] = new Color(255 - world.pathMatrix[i][j].quality, world.pathMatrix[i][j].quality ,1);
            }
        }
        
        //builds the canvas for edges to be drawn on
        DisplayGraphics m = new DisplayGraphics();
        m.setVisible(true);
        m.setSize(500, 500);
        
        m.setBackground(Color.white);
        jPanel8.add(m);
        jPanel8.setVisible(true);
        
        repaint();
        revalidate();
        m.pathHelperMaker();
    }
    
    
    
    //renders labels at citi's coordinates.
    public void mapRender() {
        System.out.println("hi!!!!");
        
        for (int i = 0; i < world.TOTALCITIES; i++) {
            l[i] = new JLabel(world.cities[i].cityName, SwingConstants.CENTER);
            jPanel8.add(l[i]);
            l[i].setSize(world.cities[i].cityName.length()*15, 15);
            l[i].setVisible(true);
            l[i].setOpaque(true);
            l[i].setBackground(Color.gray);
            l[i].setForeground(Color.black);
            l[i].setLocation(world.cities[i].coords.x + 20, world.cities[i].coords.y + 20);
        }
        repaint();
        revalidate();
    }
    
    public void addToLog(String content) {
        list1.add(content);
        list1.makeVisible(logIncrement);
        logIncrement++;
        
    }
    
    public void renderTravelContainer(Map.City a) {
        System.out.println("renderTravelContainer executed");   
        
    }
    
    
    
    
    
    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane2 = new javax.swing.JSplitPane();
        jPanel11 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        list1 = new java.awt.List();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanel8 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jSplitPane2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jSplitPane2.setDividerLocation(550);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jLabel2.setText("wellbeing");

        jLabel4.setText("Money: ");

        list1.addActionListener(this::list1ActionPerformed);

        jLabel1.setText("Clock");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(72, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(68, Short.MAX_VALUE))
        );

        jLabel3.setText("Location: ");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel11Layout.createSequentialGroup()
                    .addGap(294, 294, 294)
                    .addComponent(list1, javax.swing.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
                    .addGap(35, 35, 35)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addContainerGap())
            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel11Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(list1, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))
                    .addGap(24, 24, 24)))
        );

        jSplitPane2.setRightComponent(jPanel11);

        jTabbedPane1.setBackground(new java.awt.Color(153, 153, 153));
        jTabbedPane1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 248, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 425, Short.MAX_VALUE)
        );

        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 602, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 599, Short.MAX_VALUE)
        );

        jScrollPane3.setViewportView(jPanel8);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 431, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(152, 152, 152))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(78, 78, 78))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Map", jPanel3);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 932, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("ctl / info", jPanel1);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 433, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 647, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanel7);

        jLabel9.setText("Player inventory. Encumbrance: 0/20");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
                        .addGap(18, 18, 18))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 447, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Inventory", jPanel2);

        jSplitPane2.setLeftComponent(jTabbedPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane2)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane2)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void list1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_list1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_list1ActionPerformed

    /**
     * @param args the command line arguments
     */
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private java.awt.List list1;
    // End of variables declaration//GEN-END:variables

}