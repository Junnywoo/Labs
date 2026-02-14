import javax.swing.*;
import java.awt.*;
import java.util.*;
public class BSTVisualizer extends JFrame {
   private BST tree = new BST();
   private JTextField inputField = new JTextField(5);
   private TreePanel paintScene = new TreePanel();
   private final Color LIGHT_PINK = new Color(255, 240, 245);
   private final Color VIBRANT_PINK = new Color(255, 105, 180);
   private final Color DARK_PINK = new Color(219, 112, 147);
   public BSTVisualizer() {
       setTitle("BST Visualizer");
       setSize(900, 700);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setLocationRelativeTo(null);
       JPanel controls = new JPanel();
       controls.setBackground(Color.WHITE);
       controls.setBorder(BorderFactory.createMatteBorder(3, 0, 0, 0, VIBRANT_PINK));
       JLabel label = new JLabel("Number:");
       label.setFont(new Font("Serif", Font.BOLD, 14));
       label.setForeground(DARK_PINK);
      
       JButton insertBtn = createPinkButton("Insert");
       JButton deleteBtn = createPinkButton("Delete");
       JButton resetBtn = createPinkButton("Reset");
       controls.add(label);
       controls.add(inputField);
       controls.add(insertBtn);
       controls.add(deleteBtn);
       controls.add(resetBtn);
       add(paintScene, BorderLayout.CENTER);
       add(controls, BorderLayout.SOUTH);
       insertBtn.addActionListener(e -> {
           try {
               tree.insert(Integer.parseInt(inputField.getText()));
               paintScene.repaint();
               inputField.setText("");
           } catch (Exception ex) {}
       });
       deleteBtn.addActionListener(e -> {
           try {
               tree.delete(Integer.parseInt(inputField.getText()));
               paintScene.repaint();
               inputField.setText("");
           } catch (Exception ex) {}
       });
       resetBtn.addActionListener(e -> {
           tree = new BST();
           paintScene.repaint();
       });
   }
   private JButton createPinkButton(String text) {
       JButton btn = new JButton(text);
       btn.setBackground(VIBRANT_PINK);
       btn.setForeground(Color.WHITE);
       btn.setFocusPainted(false);
       btn.setFont(new Font("SansSerif", Font.BOLD, 12));
       btn.setBorder(BorderFactory.createLineBorder(DARK_PINK, 2));
       return btn;
   }
   class TreePanel extends JPanel {
       private int radius = 22;
       private int vGap = 60;
       public TreePanel() { setBackground(LIGHT_PINK); }
       @Override
       protected void paintComponent(Graphics g) {
           super.paintComponent(g);
           Graphics2D g2 = (Graphics2D) g;
           g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
           if (tree.root != null) {
               drawNodes(g2, tree.root, getWidth() / 2, 50, getWidth() / 4);
           }
       }
       private void drawNodes(Graphics2D g, Node root, int x, int y, int hGap) {
           g.setStroke(new BasicStroke(3));
           g.setColor(DARK_PINK);
           if (root.left != null) {
               g.drawLine(x, y, x - hGap, y + vGap);
               drawNodes(g, root.left, x - hGap, y + vGap, hGap / 2);
           }
           if (root.right != null) {
               g.drawLine(x, y, x + hGap, y + vGap);
               drawNodes(g, root.right, x + hGap, y + vGap, hGap / 2);
           }
           g.setColor(Color.WHITE);
           g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
          
           g.setColor(VIBRANT_PINK);
           g.drawOval(x - radius, y - radius, 2 * radius, 2 * radius);
           g.setColor(DARK_PINK);
           g.setFont(new Font("SansSerif", Font.BOLD, 14));
           String val = String.valueOf(root.key);
           FontMetrics fm = g.getFontMetrics();
           int tx = x - fm.stringWidth(val) / 2;
           int ty = y + fm.getAscent() / 2 - 2;
           g.drawString(val, tx, ty);
       }
   }
   class Node {
       int key;
       Node left, right;
       Node(int item) { key = item; }
   }
   class BST {
       Node root;
       void insert(int key) { root = insertRec(root, key); }
       Node insertRec(Node root, int key) {
           if (root == null) return new Node(key);
           if (key < root.key) root.left = insertRec(root.left, key);
           else if (key > root.key) root.right = insertRec(root.right, key);
           return root;
       }
       void delete(int key) { root = deleteRec(root, key); }
       Node deleteRec(Node root, int key) {
           if (root == null) return root;
           if (key < root.key) root.left = deleteRec(root.left, key);
           else if (key > root.key) root.right = deleteRec(root.right, key);
           else {
               if (root.left == null) return root.right;
               if (root.right == null) return root.left;
               root.key = minValue(root.right);
               root.right = deleteRec(root.right, root.key);
           }
           return root;
       }
       int minValue(Node root) {
           int minv = root.key;
           while (root.left != null) { minv = root.left.key; root = root.left; }
           return minv;
       }
   }
   public static void main(String[] args) {
       SwingUtilities.invokeLater(() -> new BSTVisualizer().setVisible(true));
   }
}

