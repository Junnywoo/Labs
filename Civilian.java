public class Civilian extends BinaryNode implements Comparable {
    String name;
    double num;
    
    public Civilian(String n, double p) {
        super(p);
        num = p;
        name = n;
    }
    
    public int compareTo(Object c) {
        return Double.compare(getNum(), (((Civilian)c).getNum()));
    }
    
    public String getName() {
        return name;
    }
    
    public double getNum() {
        return num;
    }
    
    public Comparable getValue() {
        return toString();
    }
    
    public void setName(String n) {
        name = n;
    }
    
    public void setNum(double n) {
        num = n;
    }
    
    public String toString() {
        return String.format("%s %.3f", name, getNum());
    }
}
