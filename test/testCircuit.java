import Util.Circuit;
import Util.Graphe;
import org.graphstream.graph.Graph;

import java.util.*;

public class testCircuit {

    public static void main(String[] args) {
        littleTestRandom();
    }

    public static void testReproduction(int size) {
        HashMap<Integer, Graphe> grapheHashMap = new HashMap<>();
        for (int i = 0; i < size; i++) {
            grapheHashMap.put(i, new Graphe(Graphe.generateRandomGraph("data01")));
        }
    }

    public static void littleTestRandom() {
        HashMap<Integer, Double> tests = new HashMap<>();
        tests.put(1,0.25);
        tests.put(2,0.34);
        tests.put(3,0.75);
        tests.put(4,0.01);
        List<Double> list = Arrays.asList(0.25, 0.34, 0.75, 0.01);
        Random random = new Random();
        double result = random.nextDouble();
        System.out.println("le tirage: "+ result);
        Integer key = tests.entrySet().stream().min(Comparator.comparingDouble(f -> Math.abs(f.getValue() - result))).get().getKey();
        System.out.println(key);
        double closest = list.stream().min(Comparator.comparingDouble(f -> Math.abs(f - result))).get();
        System.out.println("le plus proche: " +closest);
    }
}
