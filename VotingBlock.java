import java.util.*;
import java.io.*;

public class VotingBlock {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner s = new Scanner(System.in);
        Scanner input = new Scanner(new File(s.next()));
        double range = s.nextDouble();
        AVLTree tree = new AVLTree();
        
        while (input.hasNextLine()) {
            String[] line = input.nextLine().split("\\|");
            Civilian civ = new Civilian(line[0], Double.parseDouble(line[1]));
            tree.insert(civ);
        }
        
        System.out.println("AVL Tree:");
        Queue<Comparable> levels = new LinkedList<>();
        tree.levelOrder(levels, tree.getRoot());
        tree.printFullTree(levels, Math.min(6, tree.getNumLevels()));
        System.out.println("Fingerprint average:");
        System.out.printf("%.4f\n", tree.getAverage());
        
        ArrayList<Civilian> liberals = new ArrayList<>();
        tree.meetThreshold(liberals, 0, -1, (Civilian)tree.getRoot());
        ArrayList<Civilian> conservatives = new ArrayList<>();
        tree.meetThreshold(conservatives, 0, 1, (Civilian)tree.getRoot());
        ArrayList<Civilian> removed = new ArrayList<>();
        
        System.out.println("Liberal roll:");
        System.out.println(liberals);
        System.out.println("Conservative roll:");
        System.out.println(conservatives);
        
        while (Math.abs(tree.getAverage()) > range) {
            Civilian removing;
            if (tree.getAverage() > 0) {
                removing = conservatives.remove(conservatives.size()-1);
            } else {
                removing = liberals.remove(0);
            }
            removed.add(removing);
            tree.remove(removing);
        }

        System.out.println("Removed roll:");
        System.out.println(removed);
        System.out.println("Fingerprint average:");
        System.out.printf("%.4f\n", tree.getAverage());
    }
}
