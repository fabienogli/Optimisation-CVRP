package algo;

import Algos.Genetique;
import Util.Coordonnee;
import Util.Graphe;
import Util.Client;
import org.graphstream.graph.Graph;

import java.util.*;
import java.util.stream.Collectors;

public class testGenetique {

    public static void main(String[] args) {
//        littleTestRandom();
//        testReproduction(4);
//        testRearangeChildren();
//        testExtractDoublon();
//        for (int i = 0; i < 500; i++) {
//
//            testCrossover();
//        }
        testAlgoGenetique();
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
        List<Graphe> grapheHashMap1 = Genetique.reproduction(new ArrayList<Graphe>(grapheHashMap.values()));
        grapheHashMap.entrySet().stream().forEach(integerGrapheEntry -> {
            System.out.println("cout du graphe " + integerGrapheEntry.getKey() + ": " + integerGrapheEntry.getValue().cout());
        });
        grapheHashMap1.forEach(integerGrapheEntry -> {
            System.out.println("cout du graphe reproduit: " + integerGrapheEntry.cout());
        });
//        System.out.println(grapheHashMap);
    }

    public static void testRearangeChildren() {
        ArrayList<Client> client_1 = new ArrayList<>();
        ArrayList<Client> client_2 = new ArrayList<>();
        Client a = new Client(0, new Coordonnee(0, 0), 0);
        Client b = new Client(1, new Coordonnee(0, 0), 0);
        Client c = new Client(2, new Coordonnee(0, 0), 0);
        Client d = new Client(3, new Coordonnee(0, 0), 0);
        Client e = new Client(4, new Coordonnee(0, 0), 0);
        Client f = new Client(5, new Coordonnee(0, 0), 0);
        Client g = new Client(6, new Coordonnee(0, 0), 0);
        client_1.add(a);
        client_1.add(b);
        client_1.add(a);
        client_1.add(c);
        client_1.add(d);
        client_1.add(e);
        client_1.add(a);
        client_1.add(b);
        client_1.add(c);

        client_2.add(a);
        client_2.add(g);
        client_2.add(f);
        client_2.add(g);
        client_2.add(a);
        client_2.add(d);
        client_2.add(e);
        client_2.add(a);
        client_2.add(f);
        System.out.println("avant transformation");
        Map<Integer, Client> mapClient1 = Genetique.convertListToMapClient(client_1);
        Map<Integer, Client> mapClient2 = Genetique.convertListToMapClient(client_2);
        System.out.println("premiere liste");
        mapClient1.forEach((key, value) -> {
            System.out.println("Client " + key + ": " + value.getIdSommet());
        });
        System.out.println("seconde liste");
        mapClient2.forEach((key, value) -> {
            System.out.println("Client " + key + ": " + value.getIdSommet());
        });
        List<Map<Integer, Client>> result =  Genetique.rearangeChild(mapClient1, mapClient2);
        Map<Integer, Client> child1 = result.get(0);
        Map<Integer, Client> child2 = result.get(1);
        System.out.println("premier child:");
        child1.forEach((key, value) -> {
            System.out.println("key: " + key+": " + value.getIdSommet());
        });
        System.out.println("second child:");
        child1.forEach((key, value) -> {
            System.out.println("key: " + key +": " + value.getIdSommet());
        });
    }

    public static void testExtractDoublon() {
        ArrayList<Client> client_1 = new ArrayList<>();
        Map<Integer, Client> missingChild = new HashMap<>();
        Client a = new Client(0, new Coordonnee(0, 0), 0);
        Client b = new Client(1, new Coordonnee(0, 0), 0);
        Client c = new Client(2, new Coordonnee(0, 0), 0);
        Client d = new Client(3, new Coordonnee(0, 0), 0);
        Client e = new Client(4, new Coordonnee(0, 0), 0);
        Client f = new Client(5, new Coordonnee(0, 0), 0);
        Client g = new Client(6, new Coordonnee(0, 0), 0);
        client_1.add(a);
        client_1.add(b);
        client_1.add(a);
        client_1.add(c);
        client_1.add(d);
        client_1.add(e);
        client_1.add(a);
        client_1.add(b);
        client_1.add(c);
        Map<Integer, Client> client = Genetique.convertListToMapClient(client_1);
        System.out.println("premiere liste");
        client.forEach((key, value) -> {
            System.out.println("valeur à la " + key + "position = " + value.getIdSommet());
        });
        missingChild = Genetique.extractDoublon(client);
        System.out.println("après algo");
        client.forEach((key, value) -> {
            System.out.println("valeur à la " + key + " position = " + value.getIdSommet());
        });
        missingChild.forEach((key, value) -> {
            System.out.println("valeur manquante à la " + key + "position = " + value.getIdSommet());
        });
    }

    public static void testCrossover() {
        Graphe graphe1 = Graphe.generateRandomGraph("data01");
        Graphe graphe2 = Graphe.generateRandomGraph("data01");
        Graphe result = Genetique.crossover(graphe1, graphe2, graphe1.getSommets().size() / 2);
        System.out.println("cout des deux premiers graphes: " + graphe1.cout() + " et " + graphe2.cout());
        System.out.println("cout du croisement: " +result.cout());
        System.out.println("le graphe est il valide ? " + result.isValid());
        System.out.println("le graphe possède-t-il tous les sommets ? " + checkIfAllSommetsHere(result));
        Graph visu1 = Graphe.adaptGraphe(graphe1);
        Graph visu2 = Graphe.adaptGraphe(graphe2);
        Graph visuResult = Graphe.adaptGraphe(result);
//        visu1.display();
//        visu2.display();
//        visuResult.display();
    }

    public static void testAlgoGenetique() {
        Graphe graphe = Genetique.algo(150, 100, "data01", 0.05);
        System.out.println("Le cout final est " + graphe.cout());
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
}
