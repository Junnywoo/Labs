public class BinaryCountNode implements Comparable<BinaryCountNode>
{
    private Comparable value;
    private int count;
    private BinaryCountNode left, right;

    public BinaryCountNode(Comparable val)
    {
        value = val;
        count = 1;
    }

    public Comparable getValue()
    {
        return value;
    }

    public int getCount()
    {
        return count;
    }

    public void increment()
    {
        count++;
    }
    public void setValue(Comparable val)
{
    value = val;
}

    public void decrement()
    {
        count--;
    }

    public void setLeft(BinaryCountNode n)
    {
        left = n;
    }

    public void setRight(BinaryCountNode n)
    {
        right = n;
    }

    public BinaryCountNode left()
    {
        return left;
    }

    public BinaryCountNode right()
    {
        return right;
    }

    public int compareTo(BinaryCountNode other)
    {
        return value.compareTo(other.value);
    }

    public String toString()
    {
        return value + " (" + count + ")";
    }
}
