import Algos.Genetique;
import Util.Circuit;
import Util.Graphe;
import org.graphstream.graph.Graph;

import java.util.*;

public class testGraphe {

    public static void main(String[] args) {
//        littleTestRandom();
        testReproduction(4);
//        testGenerateRandomGraph();
    }

    public static void testReproduction(int size) {
        HashMap<Integer, Graphe> grapheHashMap = new HashMap<>();
        for (int i = 0; i < size; i++) {
            Graphe graphe = Graphe.generateRandomGraph("data01");
            grapheHashMap.put(i, graphe);
        }
        HashMap<Integer, Graphe> grapheHashMap1 = Genetique.reproduction(grapheHashMap);
        grapheHashMap.entrySet().stream().forEach(integerGrapheEntry -> {
            System.out.println("cout du graphe " + integerGrapheEntry.getKey() + ": " + integerGrapheEntry.getValue().cout());
        });
        grapheHashMap1.entrySet().stream().forEach(integerGrapheEntry -> {
            System.out.println("cout du graphe reproduit " + integerGrapheEntry.getKey() + ": " + integerGrapheEntry.getValue().cout());
        });
//        System.out.println(grapheHashMap);
    }

    public static void testGenerateRandomGraph() {
        Graphe graphe = Graphe.generateRandomGraph("data01");
        graphe.getCircuits().stream().forEach(circuit -> {
            System.out.println("cout de circuit: " + circuit.cout());
        });
        System.out.println("cout du graphe" + graphe.cout());
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
