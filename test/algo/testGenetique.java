//package algo;
//
//import Algos.Genetique;
//import Util.Coordonnee;
//import Util.Graphe;
//import Util.Client;
//import scala.Int;
//
//import javax.swing.text.html.Option;
//import java.sql.ClientInfoStatus;
//import java.util.*;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
//public class testGenetique {
//
//    public static void main(String[] args) {
////        littleTestRandom();
////        testReproduction(4);
//        testRearangeChildren();
//    }
//
//    public static void littleTestRandom() {
//        HashMap<Integer, Double> tests = new HashMap<>();
//        tests.put(1,0.25);
//        tests.put(2,0.34);
//        tests.put(3,0.75);
//        tests.put(4,0.01);
//        HashMap<Integer, Double> testss =
//                tests.entrySet()
//                .stream()
//                .sorted(Map.Entry.comparingByValue())
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
//
//        Random random = new Random();
//        double result = random.nextDouble();
//        System.out.println("le tirage: "+ result);
//        Map.Entry<Integer, Double> sa = testss.entrySet().stream().filter(integerDoubleEntry ->{
//                if (integerDoubleEntry.getValue() < result) {
//                    return true;
//                }
//                return false;
//            }).reduce((a,b) -> b).orElse(null);
//        System.out.println(sa.getKey());
//        System.out.println(sa.getValue());
//
//    }
//
//    public static void testReproduction(int size) {
//        HashMap<Integer, Graphe> grapheHashMap = new HashMap<>();
//        for (int i = 0; i < size; i++) {
//            Graphe graphe = Graphe.generateRandomGraph("data01");
//            grapheHashMap.put(i, graphe);
//        }
//        HashMap<Integer, Graphe> grapheHashMap1 = Genetique.reproduction(grapheHashMap);
//        grapheHashMap.entrySet().stream().forEach(integerGrapheEntry -> {
//            System.out.println("cout du graphe " + integerGrapheEntry.getKey() + ": " + integerGrapheEntry.getValue().cout());
//        });
//        grapheHashMap1.entrySet().stream().forEach(integerGrapheEntry -> {
//            System.out.println("cout du graphe reproduit " + integerGrapheEntry.getKey() + ": " + integerGrapheEntry.getValue().cout());
//        });
////        System.out.println(grapheHashMap);
//    }
//
//    public static void testRearangeChildren() {
//        ArrayList<Client> client_1 = new ArrayList<>();
//        ArrayList<Client> client_2 = new ArrayList<>();
//        Client a = new Client(0, new Coordonnee(0, 0), 0);
//        Client b = new Client(1, new Coordonnee(0, 0), 0);
//        Client c = new Client(2, new Coordonnee(0, 0), 0);
//        Client d = new Client(3, new Coordonnee(0, 0), 0);
//        Client e = new Client(4, new Coordonnee(0, 0), 0);
//        Client f = new Client(5, new Coordonnee(0, 0), 0);
//        Client g = new Client(6, new Coordonnee(0, 0), 0);
//        client_1.add(a);
//        client_1.add(b);
//        client_1.add(a);
//        client_1.add(c);
//        client_1.add(d);
//        client_1.add(e);
//        client_1.add(a);
//        client_1.add(b);
//        client_1.add(c);
//
//        client_2.add(a);
//        client_2.add(f);
//        client_2.add(g);
//        client_2.add(a);
//        client_2.add(d);
//        client_2.add(e);
//        client_2.add(g);
//        client_2.add(a);
//        client_2.add(f);
//
//        System.out.println("premiere liste");
//        client_1.forEach(System.out::println);
//        System.out.println("seconde liste");
//        client_2.forEach(System.out::println);
//
//        List<List<Client>> result =  Genetique.rearangeChild(client_1, client_2);
//        List<Client> child1 = result.get(0);
//        List<Client> child2 = result.get(1);
//        System.out.println("premier child:");
//        child1.forEach(System.out::println);
//        System.out.println("second child:");
//        child1.forEach(System.out::println);
//    }
//}
