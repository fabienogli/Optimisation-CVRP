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
        List<Graphe> populationBis = new ArrayList<>();
        //Generation de la population initiale
        List<Graphe> population = generatePop(dataset, nbPopulation);

        //Evaluation de la population initiale
        Graphe minGraph = evaluatePop(null, population);
        Double minCout = minGraph.cout();
        for (int gen = 0; gen < nbGen; gen++) {
            while (populationBis.size() < population.size()) {
//                //System.out.println("gen=" + gen + " fitness " + minCout);
                //Phase de reproduction
                List<Graphe> popReproduite = reproduction(population);
//                //System.out.println("sortie reproduction");
                //Phase de croisement
                List<Graphe> children = crossOverWithBestSolution(popReproduite, minGraph);
//                //System.out.println("sortie crossover");

                //Phase de mutation
                populationBis.addAll(children);
                children.clear();
                for (int i = 0; i < children.size(); i++) {
                    populationBis.add(mutation(children.get(i), probaMutation));
                }

            }
            if (population.stream().mapToDouble(Graphe::cout).sum() > populationBis.stream().mapToDouble(Graphe::cout).sum()) {
//                //System.out.println("ouhouh on a changé");
                population = populationBis;
            }
            populationBis = new ArrayList<>();
//            //System.out.println(populationBis.size());
            minGraph = evaluatePop(minGraph, population);
            minCout = minGraph.cout();
        }
        return minGraph;
    }

    /**
     *
     * @param graphes
     * @ int[] tableau ave l'indice
     */
    public static List<Graphe> randomCrossover(List<Graphe> graphes) {
        int sizeGraphe = graphes.get(0).getSommets().size();
        Map<Integer, Graphe> mapPopReproduite = convertListToMapGraphe(graphes);
        List<Graphe> children = new ArrayList<>();
        while (mapPopReproduite.size() > 2) {
            Random random = new Random();
            int i_1 = randomGraph(mapPopReproduite);
            int i_2 = randomGraph(mapPopReproduite);
            int i_middle = random.nextInt(sizeGraphe - 10);
            while (i_1 == -1 || i_1 == i_2) {
                i_1 = randomGraph(mapPopReproduite);
                i_2 = randomGraph(mapPopReproduite);
                i_middle = random.nextInt(sizeGraphe - 10);
            }

            children.add(crossover(mapPopReproduite.get(i_1), mapPopReproduite.get(i_2), i_middle));
            mapPopReproduite.remove(i_1);
            mapPopReproduite.remove(i_2);
        }
//        if (evaluatePop(null, children) == null) {
//            //System.out.println(graphes.size());
//            //System.out.println(children);
//        }
//        //System.out.println();
//        //System.out.println("best of children = " + evaluatePop(null, children).cout());
        return children;
    }

    public static List<Graphe> crossOverWithBestSolution(List<Graphe> graphes, Graphe best) {
        int sizeGraphe = graphes.get(0).getSommets().size();
        Map<Integer, Graphe> mapPopReproduite = convertListToMapGraphe(graphes);
        List<Graphe> children = new ArrayList<>();
        while (mapPopReproduite.size() > 2) {
            Random random = new Random();
            int i_1 = randomGraph(mapPopReproduite);
            int i_middle = random.nextInt(sizeGraphe - 10);

            children.add(crossover(mapPopReproduite.get(i_1), best, i_middle));
            mapPopReproduite.remove(i_1);
        }
//                //System.out.println("best of children = " + evaluatePop(null, children).cout());
//        //System.out.println("taille de children " + children.size());

//        if (evaluatePop(null, children) == null) {
//            //System.out.println(graphes.size());
//            //System.out.println(children);
//        }
//        //System.out.println();
//        //System.out.println("best of children = " + evaluatePop(null, children).cout());
        return children;
    }

    public static List<Graphe> crossOverByOrder(List<Graphe> graphes) {
        int sizeGraphe = graphes.get(0).getSommets().size();
        Map<Integer, Graphe> mapPopReproduite = convertListToMapGraphe(graphes);
        List<Graphe> children = new ArrayList<>();
        for (int i = 0; i < mapPopReproduite.size() -1 ; i += 2) {
            Random random = new Random();
            int i_middle = random.nextInt(sizeGraphe - 1);
            children.add(crossover(mapPopReproduite.get(i), mapPopReproduite.get(i + 1), i_middle));
        }
        return children;
    }


    public static Graphe evaluatePop(Graphe minGraph, List<Graphe> graphes) {
        for (Graphe graphe : graphes) {
            if (minGraph == null || minGraph.cout() > graphe.cout()) {
                minGraph = graphe;
            }
        }
        return minGraph;
    }

    /**
     * @param _graphes List population initiale
     * @return graphe List population reproduite
     */
    public static List<Graphe> reproduction(List<Graphe> _graphes) {
//        //System.out.println("dans graph");
        Map<Integer, Graphe> graphes = convertListToMapGraphe(_graphes);
//        //System.out.println("après graph converti");
        HashMap<Integer, Graphe> kiddo = new HashMap<Integer, Graphe>();
        double min = graphes.entrySet().stream().mapToDouble(entrey->{
            return entrey.getValue().cout();
        }).min().orElse(1);
//        //System.out.println("avant somme");
        Double sum = graphes.values().stream().mapToDouble(graphe -> {
            return graphe.cout() / min;
        }).sum();
        HashMap<Integer, Double> freq = new HashMap<>();

        graphes.forEach((integer, graphe) -> {
            double result = (graphe.cout() / sum);
            freq.put(integer, result);
        });

        Random _random = new Random();
        HashMap<Integer, Double> freq_sorted =
                freq.entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByValue())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        for (int i = 0; i < graphes.size(); i++) {
            double random = _random.nextDouble();
            kiddo.put(i, graphes.get(findInterval(random, freq_sorted)));
        }
        return new ArrayList<>(kiddo.values());
    }

    public static Graphe crossover(Graphe first, Graphe second, int indice) {
        /*Broken
        List<Client> mom = first.getSommets();
        List<Client> dad = second.getSommets();
//        //System.out.println("indice de changement " + indice);
//        //System.out.println("taille de la mere:" + mom.size());
//        //System.out.println("taille du pere:" + dad.size());
        if (mom.size() <= indice) {
            //System.out.println("mom size: " + mom.size() + " < indice:" + indice);
            return first;
        }

//        //System.out.println("indice= " + indice);
//        //System.out.println("valeur qui séprare mere " + mom.get(indice));
//        //System.out.println("mom :");
//        mom.forEach(System.out::println);
//        //System.out.println("valeur qui séprare pere " + dad.get(indice));
        while (dad.get(indice).getIdSommet() == 0 || mom.get(indice).getIdSommet() == 0) {
            //System.out.println("bloqué dans le while");
            //System.out.println(dad.get(indice));
            //System.out.println(mom.get(indice));
            indice++;
        }
        //debug
        Client pivot_mom = mom.get(indice);
        Client pivot_dad = dad.get(indice);

        //fin debug

//        //System.out.println("dad :");
//        dad.forEach(System.out::println);
        //enfant de la première liste
        List<Client> child_1 = new ArrayList<>(mom.subList(0, indice));
        List<Client> child_2 = new ArrayList<>(dad.subList(0, indice));
//        //System.out.println("moit taille child 1 " + child_1.size());
//        //System.out.println("moit taille child 2 " + child_2.size());
        child_1.addAll(indice, new ArrayList<>(dad.subList(indice, dad.size())));
//        //System.out.println("mom sublist casse les couilles " + mom.subList(indice, mom.size()).size());
//        //System.out.println("sq taille child 1 " + child_1.size());
        child_2.addAll(indice, new ArrayList<>(mom.subList(indice, mom.size())));
//        //System.out.println("taille child 1 " + child_1.size());
//        //System.out.println("taille child 2 " + child_2.size());

        Map<Integer, Client> mapChild1 = convertListToMapClient(child_1);
        Map<Integer, Client> mapChild2 = convertListToMapClient(child_2);
        List<Map<Integer, Client>> rearangeChildren = rearangeChild(mapChild1, mapChild2);
        child_1 = new ArrayList<>(rearangeChildren.get(0).values());
        child_2 = new ArrayList<>(rearangeChildren.get(1).values());
//        //System.out.println("child 1");
//        child_1.forEach(System.out::println);
//        //System.out.println("child 2");
//        child_2.forEach(System.out::println);
        Graphe graphe = new Graphe(child_1);
        Graphe graphe2 = new Graphe(child_2);
        if (!checkIfAllSommetsHere(graphe)){
            return debug(mom, dad, pivot_mom, pivot_dad, child_1, child_2, graphe, graphe2);
        }
        if (!checkIfAllSommetsHere(graphe2)){
            return debug(mom, dad, pivot_mom, pivot_dad, child_1, child_2, graphe, graphe2);
        }
//        //System.out.println("fils 1: "+ graphe.cout());
//        //System.out.println("fils 2: "+graphe2.cout());
        if (graphe.cout() > graphe2.cout()) {
//            //System.out.println(" on a choisi child 2");
            return graphe2;
        } else {
            return graphe;
        }
        */
//        //System.out.println(" on a choisi child 1");
        Graphe toChange;
        Graphe toInsert;
        if (first.cout() > second.cout()) {
            toChange = second;
            toInsert = first;
        } else {
            toChange = first;
            toInsert = second;
        }
        Random random = new Random();
        int tmp = random.nextInt(toInsert.getCircuits().size() -1);
        Circuit circuit = toInsert.getCircuits().stream().min(Comparator.comparingDouble(cxs->{
//            //System.out.println(cxs.cout());
            return cxs.cout();
        })).orElse(toInsert.getCircuits().get(0));
        Graphe graphe = Graphe.addCircuit(toChange, circuit);
//        //System.out.println("GRAPH: " +graphe);
        return graphe;
    }

    private static Graphe debug(List<Client> mom, List<Client> dad, Client pivot_mom, Client pivot_dad, List<Client> child_1, List<Client> child_2, Graphe graphe, Graphe graphe2) {
        //System.out.println("debug graphe");
        //System.out.println(graphe);
        //System.out.println("mom avec " + child_1.size() + " sommets");
        //System.out.println("pivot = " + pivot_mom.getIdSommet());
        //System.out.println("mom");
        debugListSommet(mom);
        //System.out.println(graphe2);
        //System.out.println("dad avec " + child_2.size() + " sommets");
        //System.out.println("pivot = " + pivot_dad.getIdSommet());
        //System.out.println("dad");
        debugListSommet(dad);
        //System.out.println("child1");
        debugListSommet(child_1);
        //System.out.println("child2");
        debugListSommet(child_2);
        //System.out.println("fin debug");
        return null;
    }

    public static void debugListSommet(List<Client> clients) {
//        clients.forEach(client -> {
//            if (client == null) {
//                //System.out.println("WTFTFTTFTFT");
//            }
//            System.out.print(client.getIdSommet()+", ");
//        });
        //System.out.println();
    }

    public static List<Map<Integer, Client>> rearangeChild(Map<Integer, Client> child_1, Map<Integer, Client> child_2) {
        List<Map<Integer, Client>> result = new ArrayList<>();
//        //System.out.println("child 2");
        Map<Integer, Client> missing_child1 = extractDoublon(child_2);
//        //System.out.println("child 1");
        Map<Integer, Client> missing_child2 = extractDoublon(child_1);
//        //System.out.println(missing_child1.size());
//        //System.out.println(missing_child2.size());
//        //System.out.println("missing_child1");
//        missing_child1.forEach((key, value) -> {
//            //System.out.println(key +" : " + value.getIdSommet());
//        });
//        //System.out.println("missing_child2");
//        missing_child2.forEach((key, value) -> {
//            //System.out.println(key +" : " + value.getIdSommet());
//        });
//        //System.out.println("chil1"+ child_1.size());
//        child_1.forEach((key, value) -> {
//            //System.out.println(key +" : " + value.getIdSommet());
//        });
//        //System.out.println("chil2 de taille " + child_2.size());
//        child_2.forEach((key, value) -> {
//            //System.out.println(key +" : " + value.getIdSommet());
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
//        //System.out.println("taille=" +child.size());
        int size = child.size();
        for (int i = 0; i < size; i++) {
            Client client = child.get(i);
            if (client == null || client.getIdSommet() == 0) {
                continue;
            }
            for (int j = i + 1; j < size; j++) {
                Client toCompare = child.get(j);
                if (client.equals(toCompare)) {
                    missing_child1.put(j, toCompare);
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
        return Graphe.swapRandomClient(graphe);
    }

    public static void crossCircuit(Graphe mom, Graphe dad) {
        Random random = new Random();
        int i_momPath = random.nextInt(mom.getCircuits().size());
        int i_dadPath = random.nextInt(dad.getCircuits().size());
        Circuit momPath = mom.getCircuits().get(i_momPath);
        Circuit dadPath = mom.getCircuits().get(i_dadPath);
    }

    public static int randomGraph(Map<Integer, Graphe> graphes) {
        Random random = new Random();
        List<Integer> keys = new ArrayList<>(graphes.keySet());
        int randomKey = keys.get(random.nextInt(keys.size()));
        return randomKey;
    }

    public static List<Graphe> generatePop(String dataset, int nbPopulation) {
        List<Graphe> population = new ArrayList<>();
        for (int i = 0; i < nbPopulation; i++) {
            //Initialisation de POP
            Graphe graphe = Graphe.generateRandomGraph(dataset);
            population.add(graphe);
        }
        return population;
    }

    public static int findInterval(double value, Map<Integer, Double> mapSorted) {
        for (int j = 0; j < mapSorted.size(); j++) {
            if (mapSorted.get(j) > value) {
                if (j == 0) {
                    return j;
                }
                return j-1;
            }
        }
        return 0;
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
//                //System.out.println("Le sommets "+ i +" n'a pas été trouvé");
                return false;
            }
        }
        return true;
    }


}
