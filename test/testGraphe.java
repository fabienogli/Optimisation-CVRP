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
//        testRandomSwap();
        //test pour le set sommet
        Graphe graphe = Graphe.generateRandomGraph("data01");
        ArrayList<Circuit>circuits = graphe.getCircuits();
        List<Client> sommets = new ArrayList<>();
        circuits.stream().forEach(circuit -> {
            System.out.println("dans un circuit");
            System.out.println(circuit.getSommets());
            System.out.println(circuit.getSommets().size());
            sommets.addAll(circuit.getSommets());
        });
        System.out.println("sortie");
        System.out.println(sommets);
        System.out.println(sommets.size());
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

    public static void testDisplay() {
        Graphe graphe = Graphe.generateRandomGraph("data01");
        Graph graph = Graphe.adaptGraphe(graphe);
        graph.display();
    }


}
