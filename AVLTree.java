import java.util.*;

public class AVLTree extends BinarySearchTree {
	
	public int balanceFactor(BinaryNode n) {
		if (n == null) return 0;
		return getHeight(n.left()) - getHeight(n.right());
	}
	
	public void copy(BinaryNode x, BinaryNode y)
	{
		Civilian cx = (Civilian)x;
		Civilian cy = (Civilian)y;
		cx.setNum(cy.getNum());
		cx.setName(cy.getName());
	}
	
	public double getAverage() {
		return getSum(getRoot()) / getNumNodes();
	}
	
	public int getHeight(BinaryNode n) {
		if (n == null) return -1;
		return 1 + Math.max(getHeight(n.left()), getHeight(n.right()));
	}
	
	public double getSum(BinaryNode x) {
		if (x == null) return 0;
		return ((Civilian)x).getNum() + getSum(x.left()) + getSum(x.right());
	}
	
	public void meetThreshold(ArrayList<Civilian> data, double threshold, int comp, Civilian n) {
		if (n == null) return;
		meetThreshold(data, threshold, comp, (Civilian)n.left());
		if (comp > 0) {
			if (n.getNum() > threshold) {data.add(n);}
		} else if (comp < 0) {
			if (n.getNum() < threshold) {data.add(n);}
		}
		meetThreshold(data, threshold, comp, (Civilian)n.right());
	}
	
	public void insert(BinaryNode x) {
		setRoot(insert(getRoot(), x));
	}
	public BinaryNode insert(BinaryNode parent, BinaryNode x) {
		if (parent == null) return x;
		if (x == null) return null;
		int comp = x.compareTo(parent);
		if (comp < 0) {
			parent.setLeft(insert(parent.left(), x));
		} else {
			parent.setRight(insert(parent.right(), x));
		}
		
		return rebalance(parent, x);
	}
	
	public BinaryNode remove(BinaryNode t) {
		setRoot(remove(getRoot(), t));
		return getRoot();
	}
	public BinaryNode remove(BinaryNode parent, BinaryNode target) {
		if (parent == null) return null;
		int comp = target.compareTo(((Civilian)parent));
		if (comp < 0) {
			parent.setLeft(remove(parent.left(), target));
		} else if (comp > 0) {
			parent.setRight(remove(parent.right(), target));
		} else { // found you boy
			if (parent.left() == null) parent = parent.right();
			else if (parent.right() == null) parent = parent.left();
			else {
				BinaryNode inorder = successor(parent);
				copy(parent, inorder);
				parent.setRight(remove(parent.right(), target));
			}
		}
		
		return rebalance(parent);
	}
	
	public BinaryNode rebalance(BinaryNode parent) {
		int balance = balanceFactor(parent);
		if (balance > 1 && balanceFactor(parent.left()) >= 0)
			return leftLeftRotation(parent, parent.left());
		
		if (balance > 1 && balanceFactor(parent.left()) < 0)
			return leftRightRotation(parent, parent.left(), parent.left().right());
		
		if (balance < -1 && balanceFactor(parent.right()) <= 0)
			return rightRightRotation(parent, parent.right());
		
		if (balance < -1 && balanceFactor(parent.right()) > 0)
			return rightLeftRotation(parent, parent.right(), parent.right().left());
		
		return parent;
	}
	
	public BinaryNode rebalance(BinaryNode parent, BinaryNode x) {
		int balance = balanceFactor(parent);
		int comp;
		if (balance > 1) { // heavy left
			comp = x.compareTo(parent.left());
			if (comp > 0) {
				return leftRightRotation(parent, parent.left(), parent.left().right());
			}
			return leftLeftRotation(parent, parent.left());
		} else if (balance < -1) {
			comp = x.compareTo(parent.right());
			if (comp < 0) {
				return rightLeftRotation(parent, parent.right(), parent.right().left());
			}
			return rightRightRotation(parent, parent.right());
		}
		return parent;
	}
	
	public BinaryNode rightRightRotation(BinaryNode p, BinaryNode c) {
		p.setRight(c.left());
		c.setLeft(p);
		return c;
	}
	
	public BinaryNode leftLeftRotation(BinaryNode p, BinaryNode c) {
		p.setLeft(c.right());
		c.setRight(p);
		return c;
	}
	
	public BinaryNode rightLeftRotation(BinaryNode a, BinaryNode p, BinaryNode c) {
		p.setLeft(c.right());
		c.setRight(p);
		return rightRightRotation(a, c);
	}
	
	public BinaryNode leftRightRotation(BinaryNode a, BinaryNode p, BinaryNode c) {
		p.setRight(c.left());
		c.setLeft(p);
		return leftLeftRotation(a, c);
	}
}
