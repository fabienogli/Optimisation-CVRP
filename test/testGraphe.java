import Algos.Genetique;
import Util.Circuit;
import Util.Graphe;
import org.graphstream.graph.Graph;

import java.util.*;

public class testGraphe {

    public static void main(String[] args) {
//        littleTestRandom();
//        testGenerateRandomGraph();
        testDisplay();
    }

    public static void testGenerateRandomGraph() {
        Graphe graphe = Graphe.generateRandomGraph("data01");
        graphe.getCircuits().stream().forEach(circuit -> {
            System.out.println("cout de circuit: " + circuit.cout());
        });
        System.out.println("cout du graphe" + graphe.cout());
    }

    public static void testDisplay() {
        Graphe graphe = Graphe.generateRandomGraph("data01");
        Graph graph = Graphe.adaptGraphe(graphe);
        graph.display();
    }


}
