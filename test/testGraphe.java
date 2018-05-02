import Util.Circuit;
import Util.Graphe;
import Util.Client;
import org.graphstream.graph.Graph;

import java.util.*;
import java.util.stream.Collectors;

public class testGraphe {

    public static void main(String[] args) {
//        littleTestRandom();
        testGenerateRandomGraph();
//        testDisplay();
//        testRandomSwap();
//        testConstructeurAvecSommet();
    }

    public static void testGenerateRandomGraph() {
        Graphe graphe = Graphe.generateRandomGraph("data01");
        checkIfAllSommetsHere(graphe);
        graphe.getCircuits().stream().forEach(circuit -> {
            System.out.println("cout de circuit: " + circuit.cout());
        });
        System.out.println("cout du graphe" + graphe.cout());
        Graph visu = Graphe.adaptGraphe(graphe);
        visu.display();
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
        Graphe ne = new Graphe(sommets);
        System.out.println(graphe.equals(ne));
        Graph visu1 = Graphe.adaptGraphe(graphe);
        Graph visu2 = Graphe.adaptGraphe(ne);
        visu1.display();
        visu2.display();
    }

    public static void testDisplay() {
        Graphe graphe = Graphe.generateRandomGraph("data01");
        Graph graph = Graphe.adaptGraphe(graphe);
        graph.display();
    }

    public static boolean checkIfAllSommetsHere(Graphe result) {
        for (int i = 0; i < 32; i++) {
            boolean found = false;
            for (int j = 0; j < result.getSommets().size(); j++) {
                if (result.getSommets().get(j).getIdSommet() == i) {
                    found = true;
                }
            }
            if (!found) {
                System.out.println("Le sommets "+ i +" n'a pas été trouvé");
                return false;
            }
        }
        return true;
    }

//    public static void testSwapRandomClient() {
//        Graphe graphe
//    }


}
