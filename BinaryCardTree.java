import java.util.*;

public class BinaryCardTree extends BinarySearchTree
{
    public void add(BinaryCountNode node)
    {
        root = add(root, node);
    }

    private BinaryCountNode add(BinaryCountNode current, BinaryCountNode node)
    {
        if(current == null)
            return node;

        int cmp = node.compareTo(current);

        if(cmp == 0)
            current.increment();
        else if(cmp < 0)
            current.setLeft(add(current.left(), node));
        else
            current.setRight(add(current.right(), node));

        return current;
    }

    // ------------------------------------

    public int numCardsInHand()
    {
        return total(root);
    }

    private int total(BinaryCountNode node)
    {
        if(node == null)
            return 0;

        return node.getCount() + total(node.left()) + total(node.right());
    }

    // ------------------------------------

    public ArrayList<Comparable> getAllCards()
    {
        ArrayList<Comparable> list = new ArrayList<>();
        inorder(root, list);
        return list;
    }

    private void inorder(BinaryCountNode node, ArrayList<Comparable> list)
    {
        if(node == null) return;

        inorder(node.left(), list);

        for(int i=0;i<node.getCount();i++)
            list.add(node.getValue());

        inorder(node.right(), list);
    }

    // ------------------------------------

    public int searchColorCount(Comparable color)
    {
        BinaryCountNode node = search(root, color);
        if(node == null) return 0;
        return node.getCount();
    }

    private BinaryCountNode search(BinaryCountNode node, Comparable color)
    {
        if(node == null) return null;

        int cmp = color.compareTo(node.getValue());

        if(cmp == 0) return node;
        if(cmp < 0) return search(node.left(), color);
        return search(node.right(), color);
    }

    // ------------------------------------

    public ArrayList<Comparable> remove(Comparable color, int amount)
    {
        ArrayList<Comparable> removed = new ArrayList<>();

        int colorCount = searchColorCount(color);
        int wildCount = searchColorCount("wild");

        if(colorCount + wildCount < amount)
            return null;

        for(int i=0;i<amount;i++)
        {
            if(searchColorCount(color) > 0)
            {
                removeOne(color);
                removed.add(color);
            }
            else
            {
                removeOne("wild");
                removed.add("wild");
            }
        }

        return removed;
    }

    private void removeOne(Comparable color)
{
    root = removeOne(root, color);
}

    private BinaryCountNode removeOne(BinaryCountNode node, Comparable color)
{
    if(node == null)
        return null;

    int cmp = color.compareTo(node.getValue());

    if(cmp < 0)
    {
        node.setLeft(removeOne(node.left(), color));
    }
    else if(cmp > 0)
    {
        node.setRight(removeOne(node.right(), color));
    }
    else
    {
      
        if(node.getCount() > 1)
        {
            node.decrement();
            return node;
        }


   
        if(node.left() == null)
            return node.right();

        if(node.right() == null)
            return node.left();

     
        BinaryCountNode successor = getMin(node.right());

     
        node.setValue(successor.getValue());
        while(node.getCount() < successor.getCount())
            node.increment();

 
        node.setRight(removeAll(node.right(), successor.getValue()));
    }

    return node;
}
    
    private BinaryCountNode removeAll(BinaryCountNode node, Comparable value)
{
    if(node == null) return null;

    int cmp = value.compareTo(node.getValue());

    if(cmp < 0)
        node.setLeft(removeAll(node.left(), value));
    else if(cmp > 0)
        node.setRight(removeAll(node.right(), value));
    else
    {
        if(node.left() == null) return node.right();
        if(node.right() == null) return node.left();
    }

    return node;
}

    private BinaryCountNode getMin(BinaryCountNode node)
    {
        while(node.left() != null)
            node = node.left();
        return node;
    }

    private BinaryCountNode deleteMin(BinaryCountNode node)
    {
        if(node.left() == null)
            return node.right();

        node.setLeft(deleteMin(node.left()));
        return node;
    }

    // ------------------------------------

    public ArrayList<Comparable> getMostColorCard()
    {
        ArrayList<Comparable> result = new ArrayList<>();
        int[] max = {0};
        findMost(root, result, max);
        return result;
    }

    private void findMost(BinaryCountNode node, ArrayList<Comparable> result, int[] max)
    {
        if(node == null) return;

        findMost(node.left(), result, max);

        if(node.getCount() > max[0])
        {
            max[0] = node.getCount();
            result.clear();
            result.add(node.getValue());
        }
        else if(node.getCount() == max[0])
            result.add(node.getValue());

        findMost(node.right(), result, max);
    }

    public ArrayList<Comparable> getLeastColorCard()
    {
        ArrayList<Comparable> result = new ArrayList<>();
        int[] min = {Integer.MAX_VALUE};
        findLeast(root, result, min);
        return result;
    }

    private void findLeast(BinaryCountNode node, ArrayList<Comparable> result, int[] min)
    {
        if(node == null) return;

        findLeast(node.left(), result, min);

        if(node.getCount() < min[0])
        {
            min[0] = node.getCount();
            result.clear();
            result.add(node.getValue());
        }
        else if(node.getCount() == min[0])
            result.add(node.getValue());

        findLeast(node.right(), result, min);
    }
}
