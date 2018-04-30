package algo;

import Algos.Genetique;
import Util.Graphe;
import scala.Int;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class testGenetique {

    public static void main(String[] args) {
//        littleTestRandom();
        testReproduction(4);
    }

    public static void littleTestRandom() {
        HashMap<Integer, Double> tests = new HashMap<>();
        tests.put(1,0.25);
        tests.put(2,0.34);
        tests.put(3,0.75);
        tests.put(4,0.01);
        HashMap<Integer, Double> testss =
                tests.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        Random random = new Random();
        double result = random.nextDouble();
        System.out.println("le tirage: "+ result);
        Map.Entry<Integer, Double> sa = testss.entrySet().stream().filter(integerDoubleEntry ->{
                if (integerDoubleEntry.getValue() < result) {
                    return true;
                }
                return false;
            }).reduce((a,b) -> b).orElse(null);
        System.out.println(sa.getKey());
        System.out.println(sa.getValue());

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
}
