import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
class RedBlackNode {
   Comparable key;
   int myColor; 
   RedBlackNode left, right;
   public RedBlackNode(Comparable x) {
       key = x;
       myColor = 0; 
   }
   public void setLeft(RedBlackNode n) { left = n; }
   public void setRight(RedBlackNode n) { right = n; }
   public RedBlackNode left() { return left; }
   public RedBlackNode right() { return right; }
   public int getColor() { return myColor; }
   public void setColor(int c) { myColor = c; }
}
//MAIN CLASW
public class RBVisualizer extends JFrame {
   private RedBlackTree tree = new RedBlackTree();
   private TreePanel panel = new TreePanel();
   private JTextField inputField = new JTextField(5);
   public RBVisualizer() {
       setTitle("Red-Black Tree Visualizer");
       setSize(1000, 700);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
       JPanel controls = new JPanel();
       JButton insertBtn = new JButton("Insert");
       JButton deleteBtn = new JButton("Delete");
       JButton resetBtn = new JButton("Reset"); 
      
       controls.add(new JLabel("Value:"));
       controls.add(inputField);
       controls.add(insertBtn);
       controls.add(deleteBtn);
       controls.add(resetBtn);
      
      
       insertBtn.addActionListener(e -> performInsert());
       inputField.addActionListener(e -> performInsert()); 
       deleteBtn.addActionListener(e -> {
           try {
               int val = Integer.parseInt(inputField.getText());
               tree.delete(val);
               panel.repaint();
               inputField.setText("");
               inputField.requestFocusInWindow();
           } catch (Exception ex) { }
       });
       resetBtn.addActionListener(e -> {
           tree.root = null;
           panel.repaint();
           inputField.setText("");
           inputField.requestFocusInWindow();
       });
       add(controls, BorderLayout.NORTH);
       add(panel, BorderLayout.CENTER);
   }
   private void performInsert() {
       try {
           int val = Integer.parseInt(inputField.getText());
           tree.insert(val);
           panel.repaint();
           inputField.setText("");
           inputField.requestFocusInWindow();
       } catch (Exception ex) {
           JOptionPane.showMessageDialog(this, "Please enter a valid integer.");
       }
   }
   class TreePanel extends JPanel {
       @Override
       protected void paintComponent(Graphics g) {
           super.paintComponent(g);
           if (tree.root != null) drawNode(g, tree.root, getWidth() / 2, 50, getWidth() / 4);
       }
       private void drawNode(Graphics g, RedBlackNode node, int x, int y, int hGap) {
           Graphics2D g2 = (Graphics2D) g;
           g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
           if (node.left != null) {
               g2.setColor(Color.GRAY);
               g2.drawLine(x, y, x - hGap, y + 60);
               drawNode(g2, node.left, x - hGap, y + 60, hGap / 2);
           }
           if (node.right != null) {
               g2.setColor(Color.GRAY);
               g2.drawLine(x, y, x + hGap, y + 60);
               drawNode(g2, node.right, x + hGap, y + 60, hGap / 2);
           }
          
         
           if (node.myColor == 0) g2.setColor(new Color(255, 50, 50));
           else if (node.myColor == 1) g2.setColor(new Color(40, 40, 40));
           else g2.setColor(new Color(0, 0, 150)); 
          
           g2.fillOval(x - 20, y - 20, 40, 40);
           g2.setColor(Color.WHITE);
           String label = node.key.toString();
           FontMetrics fm = g2.getFontMetrics();
           g2.drawString(label, x - fm.stringWidth(label)/2, y + 5);
       }
   }
   class RedBlackTree {
       RedBlackNode root;
       public void insert(int data) {
           root = insertRecursive(root, data);
           if (root != null) root.setColor(1);
       }
       private RedBlackNode insertRecursive(RedBlackNode curr, int data) {
           if (curr == null) return new RedBlackNode(data);
          
          
           if (isBlack(curr) && isRed(curr.left) && isRed(curr.right)) {
               colorSwap(curr);
           }
           if (data < (Integer)curr.key) curr.left = insertRecursive(curr.left, data);
           else if (data > (Integer)curr.key) curr.right = insertRecursive(curr.right, data);
           return balance(curr);
       }
       private RedBlackNode balance(RedBlackNode g) {
           if (isRed(g.left) && isRed(g.left.left)) return leftLeftRotation(g, g.left);
           if (isRed(g.left) && isRed(g.left.right)) return leftRightRotation(g, g.left, g.left.right);
           if (isRed(g.right) && isRed(g.right.right)) return rightRightRotation(g, g.right);
           if (isRed(g.right) && isRed(g.right.left)) return rightLeftRotation(g, g.right, g.right.left);
           return g;
       }
//ROTATIONS
       private RedBlackNode leftLeftRotation(RedBlackNode g, RedBlackNode p) {
           g.setLeft(p.right());
           p.setRight(g);
           g.setColor(g.getColor() - 1);
           p.setColor(p.getColor() + 1);
           return p;
       }
       private RedBlackNode rightRightRotation(RedBlackNode g, RedBlackNode p) {
           g.setRight(p.left());
           p.setLeft(g);
           g.setColor(g.getColor() - 1);
           p.setColor(p.getColor() + 1);
           return p;
       }
       private RedBlackNode leftRightRotation(RedBlackNode g, RedBlackNode p, RedBlackNode x) {
           p.setRight(x.left());
           x.setLeft(p);
           return leftLeftRotation(g, x);
       }
       private RedBlackNode rightLeftRotation(RedBlackNode g, RedBlackNode p, RedBlackNode x) {
           p.setLeft(x.right());
           x.setRight(p);
           return rightRightRotation(g, x);
       }


       private void colorSwap(RedBlackNode x) {
           x.setColor(x.getColor() - 1);
           if (x.left() != null) x.left().setColor(x.left().getColor() + 1);
           if (x.right() != null) x.right().setColor(x.right().getColor() + 1);
       }
       private boolean isRed(RedBlackNode n) { return n != null && n.myColor == 0; }
       private boolean isBlack(RedBlackNode n) { return n == null || n.myColor >= 1; }
       public void delete(int data) {
           root = deleteNode(root, data);
           if (root != null) root.setColor(1);
       }
       private RedBlackNode deleteNode(RedBlackNode curr, int data) {
           if (curr == null) return null;
           if (data < (Integer)curr.key) curr.left = deleteNode(curr.left, data);
           else if (data > (Integer)curr.key) curr.right = deleteNode(curr.right, data);
           else {
               if (curr.left != null && curr.right != null) {
                   RedBlackNode succ = curr.right;
                   while (succ.left != null) succ = succ.left;
                   curr.key = succ.key;
                   curr.right = deleteNode(curr.right, (Integer)succ.key);
               } else {
                   RedBlackNode child = (curr.left != null) ? curr.left : curr.right;
                   return simpleCaseDeletion(curr, child);
               }
           }
           return curr;
       }
       private RedBlackNode simpleCaseDeletion(RedBlackNode v, RedBlackNode u) {
           if (isRed(v) || isRed(u)) {
               if (u != null) u.setColor(1);
               return u;
           }
           if (u != null) u.setColor(2); 
           return u;
       }
   }
   public static void main(String[] args) {
       SwingUtilities.invokeLater(() -> new RBVisualizer().setVisible(true));
   }
}

