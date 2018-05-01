package Algos;

import Util.Circuit;
import Util.Graphe;
import Util.Client;
import org.graphstream.graph.Graph;

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

    public static Graphe algo(int nbPopulation, int nbGen, String dataset, double probaMutation) {
        Graphe bestSolution;
        List<Graphe> population = new ArrayList<>();
        List<Graphe> populationBis = new ArrayList<>();
        Double minCout = -1.0;
        Graphe minGraph = null;
        for (int i = 0; i < nbPopulation; i++) {
            //Initialisation de POP
            Graphe graphe = Graphe.generateRandomGraph(dataset);
            //Affectation des minimum
            if (minGraph == null) {
                minCout = graphe.cout();
                minGraph = graphe;
            }
            population.add(graphe);
        }
        System.out.println(minCout+ " " + minGraph);
        for (int gen = 0; gen < nbGen; gen++) {
            System.out.println("la fitness = "+ minCout);
            while (populationBis.size() <= population.size()) {
                //Phase de reproduction
                List<Graphe> popReproduite = reproduction(population);
                //Phase de croisement
                List<Graphe> children = new ArrayList<>();
                for (int i = 0; i < popReproduite.size()-1; i += 2) {
                    children.add(crossover(popReproduite.get(i), popReproduite.get(i + 1), popReproduite.get(i).getSommets().size() / 2));
                }
                //Phase de mutation
                populationBis.addAll(children);
//                for (int i = 0; i < children.size(); i++) {
//                    populationBis.add(mutation(children.get(i), probaMutation));
//                }

            }
//            System.out.println(population.size());
//            System.out.println(populationBis.size());
            if (populationBis != null) {
                population = populationBis;
                populationBis = new ArrayList<>();
            }
            evaluatePop(minCout, minGraph, population);
        }
        return minGraph;
    }

    public static void evaluatePop(Double minCout, Graphe minGraph, List<Graphe> graphes) {
        System.out.println(graphes);
        for (Graphe graphe : graphes) {
            if (minGraph == null || minCout > graphe.cout()) {
                minCout = graphe.cout();
                minGraph = graphe;
            }
        }
    }

    /**
     *
     * @param _graphes List population initiale
     * @return graphe List population reproduite
     */
    public static List<Graphe> reproduction(List<Graphe> _graphes) {
        Map<Integer, Graphe> graphes = convertListToMapGraphe(_graphes);
        HashMap<Integer, Graphe> kiddo = new HashMap<Integer, Graphe>();
        Double sum = graphes.values().stream().mapToDouble(graphe -> {
            return 1 / graphe.cout();
        }).sum();
        HashMap<Integer, Double> freq = new HashMap<>();
        graphes.forEach((integer, graphe) -> {
            double result =1 / (graphe.cout() *sum);
            freq.put(integer, result);
        });
//        System.out.println(freq);

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
        return new ArrayList<>(kiddo.values());
    }

    public static Graphe crossover(Graphe first, Graphe second, int indice) {
        List<Client> mom =  first.getSommets();
        List<Client> dad =  second.getSommets();
//        System.out.println("taille de la mere:" + mom.size());
//        System.out.println("taille du pere:" + dad.size());
        if (mom.size() <= indice) {
            System.out.println("mom size: "+mom.size() + " < indice:" + indice );
            return first;
        }
//        System.out.println("indice= " + indice);
//        System.out.println("valeur qui séprare mere " + mom.get(indice));
//        System.out.println("mom :");
//        mom.forEach(System.out::println);
//        System.out.println("valeur qui séprare pere " + dad.get(indice));
        while (dad.get(indice).getIdSommet() == 0 || mom.get(indice).getIdSommet() == 0) {
            indice++;
        }
//        System.out.println("dad :");
//        dad.forEach(System.out::println);
        //enfant de la première liste
        List<Client> child_1 = new ArrayList<>(mom.subList(0, indice));
        List<Client> child_2 = new ArrayList<>(dad.subList(0, indice));
//        System.out.println("moit taille child 1 " + child_1.size());
//        System.out.println("moit taille child 2 " + child_2.size());
        child_1.addAll(indice, new ArrayList<>(dad.subList(indice, dad.size())));
//        System.out.println("mom sublist casse les couilles " + mom.subList(indice, mom.size()).size());
//        System.out.println("sq taille child 1 " + child_1.size());
        child_2.addAll(indice, new ArrayList<>(mom.subList(indice, mom.size())));
//        System.out.println("taille child 1 " + child_1.size());
//        System.out.println("taille child 2 " + child_2.size());

        Map<Integer, Client> mapChild1 = convertListToMapClient(child_1);
        Map<Integer, Client> mapChild2 = convertListToMapClient(child_2);
        List<Map<Integer, Client>> rearangeChildren = rearangeChild(mapChild1, mapChild2);
        child_1 = new ArrayList<>(rearangeChildren.get(0).values());
        child_2 = new ArrayList<>(rearangeChildren.get(1).values());
//        System.out.println("child 1");
//        child_1.forEach(System.out::println);
//        System.out.println("child 2");
//        child_2.forEach(System.out::println);
        Graphe graphe = new Graphe(child_1);
        Graphe graphe2 = new Graphe(child_2);
        graphe.isValid();
        graphe2.isValid();
        if (graphe.cout() > graphe2.cout()) {
//            System.out.println(" on a choisi child 2");
            return graphe2;
        }
//        System.out.println(" on a choisi child 1");
        return graphe;
    }

    public static List<Map<Integer, Client>> rearangeChild(Map<Integer, Client> child_1, Map<Integer, Client> child_2) {
        List<Map<Integer, Client>> result = new ArrayList<>();
//        System.out.println("child 2");
        Map<Integer, Client> missing_child1 = extractDoublon(child_2);
//        System.out.println("child 1");
        Map<Integer, Client> missing_child2 = extractDoublon(child_1);
//        System.out.println(missing_child1.size());
//        System.out.println(missing_child2.size());
//        System.out.println("missing_child1");
//        missing_child1.forEach((key, value) -> {
//            System.out.println(key +" : " + value.getIdSommet());
//        });
//        System.out.println("missing_child2");
//        missing_child2.forEach((key, value) -> {
//            System.out.println(key +" : " + value.getIdSommet());
//        });
//        System.out.println("chil1"+ child_1.size());
//        child_1.forEach((key, value) -> {
//            System.out.println(key +" : " + value.getIdSommet());
//        });
//        System.out.println("chil2 de taille " + child_2.size());
//        child_2.forEach((key, value) -> {
//            System.out.println(key +" : " + value.getIdSommet());
//        });
        missing_child1.forEach((key, value) -> {
            if (missing_child2.isEmpty()) {
                child_1.put(child_1.size(), value);
            } else {
                Map.Entry<Integer, Client> entry = missing_child2.entrySet().iterator().next();
                child_1.put(entry.getKey(), value);
                child_2.put(key, entry.getValue());
                missing_child2.remove(entry.getKey(), entry.getValue());
            }
        });
        if (!missing_child2.isEmpty()) {
            child_2.put(child_2.size(), missing_child2.entrySet().iterator().next().getValue());
        }
        result.add(0, child_1);
        result.add(1, child_2);
        return result;
    }

    public static Map<Integer, Client> extractDoublon(Map<Integer, Client> child) {
        Map<Integer, Client> missing_child1 = new HashMap<>();
//        System.out.println("taille=" +child.size());
        int size = child.size();
        for (int i = 0; i < size; i++) {
            Client client = child.get(i);
            if (client == null || client.getIdSommet() == 0) {
                continue;
            }
            for (int j = i + 1; j < size; j++) {
                Client toCompare = child.get(j);
                if (client.equals(toCompare)) {
                    missing_child1.put(j,toCompare);
                    child.remove(j, toCompare);
                }
            }

        }
        return missing_child1;
    }

    public static Map<Integer, Client> convertListToMapClient(List<Client> clients) {
        Map<Integer, Client> map = new HashMap<>();
        for (int i = 0; i < clients.size(); i++) {
            map.put(i, clients.get(i));
        }
        return map;
    }

    public static Map<Integer, Graphe> convertListToMapGraphe(List<Graphe> clients) {
        Map<Integer, Graphe> map = new HashMap<>();
        for (int i = 0; i < clients.size(); i++) {
            map.put(i, clients.get(i));
        }
        return map;
    }

    public static Graphe mutation(Graphe graphe, double prob) {
        Random random = new Random();
        if (random.nextDouble() >= prob) {
            return graphe;
        }
        return Graphe.swapRandomSommet(graphe);
    }

    public static void crossCircuit(Graphe mom, Graphe dad) {
        Random random = new Random();
        int i_momPath = random.nextInt(mom.getCircuits().size());
        int i_dadPath = random.nextInt(dad.getCircuits().size());
        Circuit momPath = mom.getCircuits().get(i_momPath);
        Circuit dadPath = mom.getCircuits().get(i_dadPath);
    }
}
