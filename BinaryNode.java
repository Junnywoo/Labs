import java.util.*;

public class BinaryNode {
	private BinaryNode left, right;
	private Comparable myValue;
	
	public BinaryNode(Comparable x)
	{	myValue = x;	}
	
	public void setValue(Comparable x)
	{
		myValue = x;
	}
	
	// returns 0 for leaf, 2 for 2 degree node, 
	//-1 for 1 degree node with left child, 
	//and 1 for 1 degree node with right child
	public int degree()
	{
		if(left == null && right == null)
			return 0;
		else if(left != null && right != null)
			return 2;
		else if(left != null)
			return -1;
		else 
			return 1;
	}
	
	public Comparable getValue()
	{	
		return myValue;
	}
	
	public BinaryNode left()
	{	return left;	}
	
	public BinaryNode right()
	{	return right;	}
	
	public void setLeft(BinaryNode x)
	{	left = x;	}
	
	public void setRight(BinaryNode x)
	{	right = x;	}
	
	public String toString()
	{
		String temp ="Value:"+myValue+
				     ", Left:"+(left==null?null:left.myValue)+
	                 ", Right:"+(right==null?null:right.myValue);
		return temp;
	}
}
