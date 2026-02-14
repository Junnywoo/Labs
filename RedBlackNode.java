import java.util.*;

public class RedBlackNode extends BinaryNode {
    public static final int RED = 0;
    public static final int BLACK = 1;
    
    private int color;
    private RedBlackNode parent;

    public RedBlackNode(Comparable val) {
        super(val);
        this.color = RED; // Default to RED
        this.parent = null;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public RedBlackNode getParent() {
        return parent;
    }

    public void setParent(RedBlackNode parent) {
        this.parent = parent;
    }
    
    // Convenience methods to avoid constant casting
    public RedBlackNode getLeft() {
        return (RedBlackNode) super.left();
    }

    public RedBlackNode getRight() {
        return (RedBlackNode) super.right();
    }
    
    public String toString() {
        return super.toString() + ", Color:" + (color == RED ? "Red" : "Black");
    }
}
