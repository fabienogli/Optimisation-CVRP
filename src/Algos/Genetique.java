package Algos;

import Util.Graphe;
import org.graphstream.graph.Graph;
import Util.Client;
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
        ArrayList<Client> list2 =  second.getSommets();
        ArrayList<Client> list1 =  first.getSommets();
        if (list1.size() <= indice) {
            return null;
        }
        //enfant de la première liste
        List<Client> child_11 = list1.subList(0, indice);
        List<Client> child_12 = list1.subList(indice + 1 , list1.size());
        //enfant de la première liste
        List<Client> child_21 = list1.subList(0, indice);
        List<Client> child_22 = list1.subList(indice + 1 , list1.size());


        return kid;src/Util/Graphe.java
    }
}
