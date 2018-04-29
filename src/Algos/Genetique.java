package Algos;

import Util.Graphe;
import org.graphstream.graph.Graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;
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

    public HashMap<Integer, Graphe> reproduction(HashMap<Integer, Graphe> graphes) {
        HashMap<Integer, Graphe> kiddo = new HashMap<Integer, Graphe>();
        Double sum = graphes.values().stream().mapToDouble(Graphe::cout).sum();
        HashMap<Integer, Double> freq = new HashMap<>();
        graphes.forEach((integer, graphe) -> {
            double result = graphe.cout() /sum;
            freq.put(integer, result);
        });

        Random _random = new Random();
        for (int i = 1; i <= graphes.size(); i++) {
            double random = _random.nextDouble();
            Integer key = freq.entrySet().stream().min(Comparator.comparingDouble(f -> Math.abs(f.getValue() - random))).get().getKey(); //get the key of the closest value
            kiddo.put(i, graphes.get(key));
        }
        return kiddo;
    }
}
