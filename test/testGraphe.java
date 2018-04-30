import Util.Circuit;
import Util.Graphe;
import Util.Client;
import org.graphstream.graph.Graph;

import java.util.*;
import java.util.stream.Collectors;

public class testGraphe {

    public static void main(String[] args) {
//        littleTestRandom();
//        testGenerateRandomGraph();
//        testDisplay();
        testRandomSwap();
//        testConstructeurAvecSommet();
    }

    public static void testGenerateRandomGraph() {
        Graphe graphe = Graphe.generateRandomGraph("data01");
        graphe.getCircuits().stream().forEach(circuit -> {
            System.out.println("cout de circuit: " + circuit.cout());
        });
        System.out.println("cout du graphe" + graphe.cout());
    }
    public static void testRandomSwap() {
        Graphe graphe = Graphe.generateRandomGraph("data01");
        graphe.getCircuits().stream().forEach(circuit -> {
            System.out.println("cout de circuit: " + circuit.cout());
        });
        System.out.println("cout du graphe" + graphe.cout());
        Graphe swapped = Graphe.swapRandomSommet(graphe);
        System.out.println(graphe.cout());
        Graph graph = Graphe.adaptGraphe(graphe);
        graph.display();
        Graph graph1 = Graphe.adaptGraphe(swapped);
        graph1.display();

    }

    public static void testConstructeurAvecSommet() {
        Graphe graphe = Graphe.generateRandomGraph("data01");
        System.out.println(graphe.equals(graphe));
        List<Client>  sommets = graphe.getSommets();
        System.out.println(sommets.size());
        Graphe ne = new Graphe(sommets, true);
        System.out.println(graphe.equals(ne));
    }

    public static void testDisplay() {
        Graphe graphe = Graphe.generateRandomGraph("data01");
        Graph graph = Graphe.adaptGraphe(graphe);
        graph.display();
    }


}
