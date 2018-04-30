package Algos;

import Util.Graphe;
import org.graphstream.graph.Graph;
import Util.Client;

import java.sql.ClientInfoStatus;
import java.util.*;
import java.util.stream.Collectors;

public class Genetique {

    /**
     * Entrées: Fonction d'évaluation f, nombre d'itérations maximum n
     * Sorties: Meilleur individu trouvé
     * Générer aléatoirement la population initiale
     * i ← 0
     * tantque i < n et Condition d'arrêt non satisfaite faire
     * Évaluer chaque individu de la population selon f
     * Sélectionner les parents dans la population
     * Produire les enfants des parents sélectionnés par croisement
     * Muter les individus
     * Étendre la population en y ajoutant les enfants
     * Réduire la population
     * i ← i + 1
     * n tantque
     * Retourner le meilleur individu trouvé.
     */

    public void algo(ArrayList<Graphe> graphes) {

    }

    public static HashMap<Integer, Graphe> reproduction(HashMap<Integer, Graphe> graphes) {
        HashMap<Integer, Graphe> kiddo = new HashMap<Integer, Graphe>();
        Double sum = graphes.values().stream().mapToDouble(Graphe::cout).sum();
        HashMap<Integer, Double> freq = new HashMap<>();
        graphes.forEach((integer, graphe) -> {
            double result = graphe.cout() / sum;
            freq.put(integer, result);
        });

        Random _random = new Random();
        for (int i = 1; i <= graphes.size(); i++) {
            double random = _random.nextDouble();
            HashMap<Integer, Double> freq_sorted =
                    freq.entrySet()
                            .stream()
                            .sorted(Map.Entry.comparingByValue())
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
            Map.Entry<Integer, Double> result = freq_sorted.entrySet().stream().filter(integerDoubleEntry ->{
                if (integerDoubleEntry.getValue() < random) {
                    return true;
                }
                return false;
            }).reduce((a,b) -> b).orElse(null);
            if (result == null) {
                result = freq_sorted.entrySet().iterator().next();
            }
            kiddo.put(i, graphes.get(result.getKey()));
        }
        return kiddo;
    }

    public Graphe crossover(Graphe first, Graphe second, int indice) {
        List<Client> list2 =  second.getSommets();
        List<Client> list1 =  first.getSommets();
        if (list1.size() <= indice) {
            return null;
        }
        //enfant de la première liste
        List<Client> child_1 = list1.subList(0, indice);
        List<Client> child_2 = list2.subList(0, indice);
        child_1.addAll(list2.subList(indice + 1 , list1.size()));
        child_2.addAll(list1.subList(indice + 1 , list1.size()));
        List<Map<Integer, Client>> rearangeChildren = rearangeChild(convertListToMapPosition(child_1), convertListToMapPosition(child_2));
//        child_1 = rearangeChildren.get(0).values();
//        child_2 = rearangeChildren.get(1);
        Graphe graphe = new Graphe((ArrayList)child_1, true);
        Graphe graphe2 = new Graphe((ArrayList)child_2, true);
        if (graphe.cout() > graphe2.cout()) {
            return graphe2;
        }
        return graphe;
    }

    public static List<Map<Integer, Client>> rearangeChild(Map<Integer, Client> child_1, Map<Integer, Client> child_2) {
        List<Map<Integer, Client>> result = new ArrayList<>();
        HashMap<Integer, Client> missing_child1 = new HashMap<>();
        HashMap<Integer, Client> missing_child2 = new HashMap<>();
        extractDoublon(child_1, missing_child2);
        extractDoublon(child_2, missing_child1);
        missing_child1.forEach(child_1::put);
        missing_child1.forEach(child_2::put);
        result.add(0, child_1);
        result.add(1, child_2);
        return result;
    }

    private static void extractDoublon(Map<Integer, Client> child_2, HashMap<Integer, Client> missing_child1) {
        for (int i = 0; i < child_2.size(); i++) {
            Client client = child_2.get(i);
            if (client.getIdSommet() == 0) {
                continue;
            }
            for (int j = i + 1; j < child_2.size(); j++) {
                Client toCompare = child_2.get(j);
                if (client.equals(toCompare)) {
                    missing_child1.put(j,toCompare);
                    child_2.remove(toCompare);
                }
            }
        }
    }

    public static Map<Integer, Client> convertListToMapPosition(List<Client> clients) {
        Map<Integer, Client> map = new HashMap<>();
        System.out.println(clients.size());
        for (int i = 0; i < clients.size(); i++) {
            System.out.println(clients.get(i));
            map.put(i, clients.get(i));
        }
        return map;
    }
}
