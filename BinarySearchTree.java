import java.util.*;

public class BinarySearchTree {
    private BinaryNode root;

    public BinarySearchTree() {
        root = null;
    }

    public void add(BinaryNode node) {
        if (root == null) {
            root = node;
        } else {
            root = add(root, (Comparable) node.getValue());
        }
    }

    private BinaryNode add(BinaryNode current, Comparable val) {
        if (current == null) return new BinaryNode(val);
        if (val.compareTo(current.getValue()) < 0)
            current.setLeft(add(current.left(), val));
        else 
            current.setRight(add(current.right(), val));
        return current;
    }

    // --- Traversals ---
    public String preOrder() {
        String res = preOrder(root).trim();
        return res.isEmpty() ? "\n" : res + " \n";
    }

    private String preOrder(BinaryNode node) {
        if (node == null) return "";
        return node.getValue() + " " + preOrder(node.left()) + preOrder(node.right());
    }

    public String inOrder() {
        String res = inOrder(root).trim();
        return res.isEmpty() ? "\n" : res + " \n";
    }

    private String inOrder(BinaryNode node) {
        if (node == null) return "";
        return inOrder(node.left()) + node.getValue() + " " + inOrder(node.right());
    }

    public String postOrder() {
        String res = postOrder(root).trim();
        return res.isEmpty() ? "\n" : res + " \n";
    }

    private String postOrder(BinaryNode node) {
        if (node == null) return "";
        return postOrder(node.left()) + postOrder(node.right()) + node.getValue() + " ";
    }

    public String reverseOrder() {
        String res = reverseOrder(root).trim();
        return res.isEmpty() ? "\n" : res + " \n";
    }

    private String reverseOrder(BinaryNode node) {
        if (node == null) return "";
        return reverseOrder(node.right()) + node.getValue() + " " + reverseOrder(node.left());
    }

    public List<Comparable> levelOrder() {
        List<Comparable> list = new ArrayList<>();
        Queue<BinaryNode> q = new LinkedList<>();
        if (root != null) q.add(root);
        while (!q.isEmpty()) {
            BinaryNode curr = q.poll();
            list.add(curr.getValue());
            if (curr.left() != null) q.add(curr.left());
            if (curr.right() != null) q.add(curr.right());
        }
        return list;
    }

    // --- Tree Display ---
    public List<BinaryNode> fullLevelOrder() {
        List<BinaryNode> nodes = new ArrayList<>();
        if (root == null) return nodes;
        Queue<BinaryNode> queue = new LinkedList<>();
        queue.add(root);
        int maxNodes = (int) Math.pow(2, getNumLevels()) - 1;
        while (nodes.size() < maxNodes) {
            BinaryNode curr = queue.poll();
            nodes.add(curr);
            if (curr != null) {
                queue.add(curr.left());
                queue.add(curr.right());
            } else {
                queue.add(null);
                queue.add(null);
            }
        }
        return nodes;
    }

    public void printFullTree(List<BinaryNode> list, int levels) {
        int index = 0;
        for (int i = 0; i < levels; i++) {
            int nodesInLevel = (int) Math.pow(2, i);
            for (int j = 0; j < nodesInLevel; j++) {
                if (index < list.size()) {
                    BinaryNode node = list.get(index++);
                    System.out.print(node == null ? "--" : node.getValue());
                    if (j < nodesInLevel - 1) System.out.print("|");
                }
            }
            System.out.println();
        }
    }

    // --- Metrics ---
    public int getNumLeaves() { return getNumLeaves(root); }
    private int getNumLeaves(BinaryNode n) {
        if (n == null) return 0;
        if (n.left() == null && n.right() == null) return 1;
        return getNumLeaves(n.left()) + getNumLeaves(n.right());
    }

    public int getNumLevels() { return getHeight(root) + 1; }
    public int getHeight() { return getHeight(root); }
    private int getHeight(BinaryNode n) {
        if (n == null) return -1;
        return 1 + Math.max(getHeight(n.left()), getHeight(n.right()));
    }

    public int getWidth() {
        int max = 0;
        for (int i = 0; i < getNumLevels(); i++) max = Math.max(max, getWidthAtLevel(i));
        return max;
    }

    public int getWidthAtLevel(int level) { return getWidthAtLevel(root, level); }
    private int getWidthAtLevel(BinaryNode n, int level) {
        if (n == null) return 0;
        if (level == 0) return 1;
        return getWidthAtLevel(n.left(), level - 1) + getWidthAtLevel(n.right(), level - 1);
    }

    /**
     * Textbook Definition Diameter:
     * Nodes from lowest left to lowest right passing THROUGH the root.
     */
    public int diameter() {
        if (root == null) return 0;
        return getHeight(root.left()) + getHeight(root.right()) + 3;
    }

    public int getNumNodes() { return getNumNodes(root); }
    private int getNumNodes(BinaryNode n) {
        return n == null ? 0 : 1 + getNumNodes(n.left()) + getNumNodes(n.right());
    }

    public boolean isFull() { return isFull(root); }
    private boolean isFull(BinaryNode n) {
        if (n == null) return true;
        if (n.degree() == -1 || n.degree() == 1) return false;
        return isFull(n.left()) && isFull(n.right());
    }

    public boolean contains(Comparable val) {
        BinaryNode curr = root;
        while (curr != null) {
            int cmp = val.compareTo(curr.getValue());
            if (cmp == 0) return true;
            curr = (cmp < 0) ? curr.left() : curr.right();
        }
        return false;
    }

    public Comparable getLargest() {
        BinaryNode curr = root;
        while (curr != null && curr.right() != null) curr = curr.right();
        return curr == null ? null : curr.getValue();
    }

    public Comparable getSmallest() {
        BinaryNode curr = root;
        while (curr != null && curr.left() != null) curr = curr.left();
        return curr == null ? null : curr.getValue();
    }

    public BinaryNode remove(Comparable val) {
        BinaryNode[] rem = new BinaryNode[1];
        root = remove(root, val, rem);
        return rem[0];
    }

    private BinaryNode remove(BinaryNode n, Comparable val, BinaryNode[] rem) {
        if (n == null) return null;
        int cmp = val.compareTo(n.getValue());
        if (cmp < 0) n.setLeft(remove(n.left(), val, rem));
        else if (cmp > 0) n.setRight(remove(n.right(), val, rem));
        else {
            rem[0] = n;
            if (n.left() == null) return n.right();
            if (n.right() == null) return n.left();
            Comparable succ = getSmallest(n.right()).getValue();
            n.setValue(succ);
            n.setRight(remove(n.right(), succ, new BinaryNode[1]));
        }
        return n;
    }

    private BinaryNode getSmallest(BinaryNode n) {
        while (n.left() != null) n = n.left();
        return n;
    }

    @Override
    public String toString() {
        String res = inOrder(root).trim();
        return res.isEmpty() ? "" : res + " ";
    }
}
