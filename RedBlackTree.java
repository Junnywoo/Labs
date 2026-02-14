import java.util.*;
import static java.lang.System.*;

public class RedBlackTree extends BinarySearchTree {
    
    public RedBlackTree() {
        super();
    }

 
    public void add(RedBlackNode n) {
        if (root == null) {
            root = n;
            n.setColor(RedBlackNode.BLACK);
            return;
        }

        RedBlackNode current = (RedBlackNode) root;
        RedBlackNode parent = null;

        while (current != null) {
            parent = current;
            if (n.getValue().compareTo(current.getValue()) < 0) {
                current = current.getLeft();
            } else {
                current = current.getRight();
            }
        }

        n.setParent(parent);
        if (n.getValue().compareTo(parent.getValue()) < 0) {
            parent.setLeft(n);
        } else {
            parent.setRight(n);
        }
        
        n.setColor(RedBlackNode.RED);
        fixInsert(n);
    }

    private void fixInsert(RedBlackNode k) {
        RedBlackNode u;
        while (k.getParent() != null && k.getParent().getColor() == RedBlackNode.RED) {
            if (k.getParent() == k.getParent().getParent().getLeft()) {
                u = k.getParent().getParent().getRight();
                if (colorOf(u) == RedBlackNode.RED) {
                    k.getParent().setColor(RedBlackNode.BLACK);
                    u.setColor(RedBlackNode.BLACK);
                    k.getParent().getParent().setColor(RedBlackNode.RED);
                    k = k.getParent().getParent();
                } else {
                    if (k == k.getParent().getRight()) {
                        k = k.getParent();
                        rotateLeft(k);
                    }
                    k.getParent().setColor(RedBlackNode.BLACK);
                    k.getParent().getParent().setColor(RedBlackNode.RED);
                    rotateRight(k.getParent().getParent());
                }
            } else {
                u = k.getParent().getParent().getLeft();
                if (colorOf(u) == RedBlackNode.RED) {
                    k.getParent().setColor(RedBlackNode.BLACK);
                    u.setColor(RedBlackNode.BLACK);
                    k.getParent().getParent().setColor(RedBlackNode.RED);
                    k = k.getParent().getParent();
                } else {
                    if (k == k.getParent().getLeft()) {
                        k = k.getParent();
                        rotateRight(k);
                    }
                    k.getParent().setColor(RedBlackNode.BLACK);
                    k.getParent().getParent().setColor(RedBlackNode.RED);
                    rotateLeft(k.getParent().getParent());
                }
            }
            if (k == root) break;
        }
        ((RedBlackNode) root).setColor(RedBlackNode.BLACK);
    }


    public void remove(int val) {
        RedBlackNode z = findNode(val);
        if (z != null) removeNode(z);
    }

    private RedBlackNode findNode(int val) {
        RedBlackNode curr = (RedBlackNode) root;
        while (curr != null) {
            if (curr.getValue().equals(val)) return curr;
            if (val < (Integer)curr.getValue()) curr = curr.getLeft();
            else curr = curr.getRight();
        }
        return null;
    }

    private void removeNode(RedBlackNode z) {
        RedBlackNode y = z;
        RedBlackNode x;
        int yOriginalColor = y.getColor();
        RedBlackNode xParent;

        if (z.getLeft() == null) {
            x = z.getRight();
            xParent = z.getParent(); 
            transplant(z, z.getRight());
        } else if (z.getRight() == null) {
            x = z.getLeft();
            xParent = z.getParent();
            transplant(z, z.getLeft());
        } else {
            y = minimum(z.getRight());
            yOriginalColor = y.getColor();
            x = y.getRight();

            if (y.getParent() == z) {
              
                xParent = y;
            } else {
              
                xParent = y.getParent();
                transplant(y, y.getRight());
                y.setRight(z.getRight());
                y.getRight().setParent(y);
            }
            transplant(z, y);
            y.setLeft(z.getLeft());
            y.getLeft().setParent(y);
            y.setColor(z.getColor());
        }

        if (yOriginalColor == RedBlackNode.BLACK) {
            fixDelete(x, xParent);
        }
    }

    private void fixDelete(RedBlackNode x, RedBlackNode xParent) {
        while (x != root && colorOf(x) == RedBlackNode.BLACK) {
            if (x == leftOf(xParent)) {
                RedBlackNode w = rightOf(xParent);
                
                if (colorOf(w) == RedBlackNode.RED) {
                    w.setColor(RedBlackNode.BLACK);
                    xParent.setColor(RedBlackNode.RED);
                    rotateLeft(xParent);
                    w = rightOf(xParent);
                }
                
                if (colorOf(leftOf(w)) == RedBlackNode.BLACK && colorOf(rightOf(w)) == RedBlackNode.BLACK) {
                    w.setColor(RedBlackNode.RED);
                    x = xParent;
                    xParent = x.getParent();
                } else {
                    if (colorOf(rightOf(w)) == RedBlackNode.BLACK) {
                        if (leftOf(w) != null) leftOf(w).setColor(RedBlackNode.BLACK);
                        w.setColor(RedBlackNode.RED);
                        rotateRight(w);
                        w = rightOf(xParent);
                    }
                    w.setColor(colorOf(xParent));
                    xParent.setColor(RedBlackNode.BLACK);
                    if (rightOf(w) != null) rightOf(w).setColor(RedBlackNode.BLACK);
                    rotateLeft(xParent);
                    x = (RedBlackNode) root;
                    xParent = null;
                }
            } else {
                RedBlackNode w = leftOf(xParent);
                
                if (colorOf(w) == RedBlackNode.RED) {
                    w.setColor(RedBlackNode.BLACK);
                    xParent.setColor(RedBlackNode.RED);
                    rotateRight(xParent);
                    w = leftOf(xParent);
                }
                
                if (colorOf(rightOf(w)) == RedBlackNode.BLACK && colorOf(leftOf(w)) == RedBlackNode.BLACK) {
                    w.setColor(RedBlackNode.RED);
                    x = xParent;
                    xParent = x.getParent();
                } else {
                    if (colorOf(leftOf(w)) == RedBlackNode.BLACK) {
                        if (rightOf(w) != null) rightOf(w).setColor(RedBlackNode.BLACK);
                        w.setColor(RedBlackNode.RED);
                        rotateLeft(w);
                        w = leftOf(xParent);
                    }
                    w.setColor(colorOf(xParent));
                    xParent.setColor(RedBlackNode.BLACK);
                    if (leftOf(w) != null) leftOf(w).setColor(RedBlackNode.BLACK);
                    rotateRight(xParent);
                    x = (RedBlackNode) root;
                    xParent = null;
                }
            }
        }
        if (x != null) x.setColor(RedBlackNode.BLACK);
    }

    
    private RedBlackNode leftOf(RedBlackNode n) {
        return n == null ? null : n.getLeft();
    }
    
    private RedBlackNode rightOf(RedBlackNode n) {
        return n == null ? null : n.getRight();
    }

    private int colorOf(RedBlackNode n) {
        return n == null ? RedBlackNode.BLACK : n.getColor();
    }

    private void transplant(RedBlackNode u, RedBlackNode v) {
        if (u.getParent() == null) {
            root = v;
        } else if (u == u.getParent().getLeft()) {
            u.getParent().setLeft(v);
        } else {
            u.getParent().setRight(v);
        }
        if (v != null) {
            v.setParent(u.getParent());
        }
    }

    private RedBlackNode minimum(RedBlackNode n) {
        while (n.getLeft() != null) {
            n = n.getLeft();
        }
        return n;
    }

    private void rotateLeft(RedBlackNode x) {
        if (x == null || x.getRight() == null) return;
        RedBlackNode y = x.getRight();
        x.setRight(y.getLeft());
        if (y.getLeft() != null) {
            y.getLeft().setParent(x);
        }
        y.setParent(x.getParent());
        if (x.getParent() == null) {
            root = y;
        } else if (x == x.getParent().getLeft()) {
            x.getParent().setLeft(y);
        } else {
            x.getParent().setRight(y);
        }
        y.setLeft(x);
        x.setParent(y);
    }

    private void rotateRight(RedBlackNode x) {
        if (x == null || x.getLeft() == null) return;
        RedBlackNode y = x.getLeft();
        x.setLeft(y.getRight());
        if (y.getRight() != null) {
            y.getRight().setParent(x);
        }
        y.setParent(x.getParent());
        if (x.getParent() == null) {
            root = y;
        } else if (x == x.getParent().getRight()) {
            x.getParent().setRight(y);
        } else {
            x.getParent().setLeft(y);
        }
        y.setRight(x);
        x.setParent(y);
    }


    public List<RedBlackNode> fullLevelOrder() {
        List<RedBlackNode> list = new ArrayList<>();
        Queue<RedBlackNode> q = new LinkedList<>();
        
        q.add((RedBlackNode)root);
        
    
        int maxNodes = 63; 
        
        while (list.size() < maxNodes) {
            RedBlackNode curr = q.poll();
            list.add(curr);
            
            if (curr != null) {
                q.add(curr.getLeft());
                q.add(curr.getRight());
            } else {
                q.add(null);
                q.add(null);
            }
        }
        return list;
    }

    public void printFullTree(List<RedBlackNode> list, int levels) {
        int index = 0;
        for (int i = 0; i < levels; i++) {
            int nodesInLevel = (int) Math.pow(2, i);
            String levelStr = "";
            for (int j = 0; j < nodesInLevel; j++) {
                if (index < list.size()) {
                    RedBlackNode node = list.get(index++);
                    if (j > 0) levelStr += "|";
                    levelStr += (node == null ? "--" : node.getValue());
                }
            }
            System.out.println(levelStr);
        }
    }
}
