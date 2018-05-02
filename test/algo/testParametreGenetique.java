package algo;

import Algos.Genetique;
import Util.Graphe;
import org.graphstream.graph.Graph;

import java.util.ArrayList;
import java.util.List;

public class testParametreGenetique {

    public static void main(String[] args) {
        for (int i = 1; i < 6; i++) {
            testMutation(i);
            testNbGen(i);
            testMutation(i);
        }

    }

    public static int optimisationCrossover(int dataset) {
        Graphe minGraphRandom = null;
        int nbPopOptimaleRandom =0;
        for (int i = 100; i < 150; i ++) {
            List<Graphe> pop = Genetique.generatePop("data0" + dataset, i);
            minGraphRandom = Genetique.evaluatePop(minGraphRandom, pop);
            for (int m = 0; m < 10; m++) {
                List<Graphe> children = Genetique.crossOverWithBestSolution(pop, minGraphRandom);
                Graphe tmp = minGraphRandom;
                minGraphRandom = Genetique.evaluatePop(minGraphRandom, children);
                if (tmp == minGraphRandom) {
                    nbPopOptimaleRandom = i;
                }
            }
        }
        //System.out.println("Population optimale random crossover: " + nbPopOptimaleRandom);
        //System.out.println("Population optimale random crossover: cout  " + minGraphRandom.cout());

        int nbPopOptimale =0;
        Graphe minGraph = null;
        for (int i = 100; i < 150; i ++) {
            List<Graphe> pop = Genetique.generatePop("data01", i);
            minGraph = Genetique.evaluatePop(minGraph, pop);
            for (int m = 0; m < 10; m++) {
                List<Graphe> children = Genetique.crossOverByOrder(pop);
                Graphe tmp = minGraph;
                minGraph = Genetique.evaluatePop(minGraph, children);
                if (tmp == minGraph) {
                    nbPopOptimale = i;
                }
            }
        }
        //System.out.println("Population optimale crossover: " + nbPopOptimale);
        //System.out.println("Population optimale crossover: cout  " + minGraph.cout());
        /**
         * Result:
         *
         Population optimale random crossover: 100000
         Population optimale random crossover: cout  1770.3564077262113
         Population optimale random crossover: 100000
         Population optimale random crossover: cout  1813.833239960234
         */
        return nbPopOptimale;
    }

    public static Double optimisationMutation(int dataset) {
        Graphe init = Graphe.generateRandomGraph("data0" + dataset);
        Graphe minGraph = null;
        Double bestProb = 0.0;
        for (double prob = 0; prob < 1; prob += 0.001) {
            for (int i = 0; i < 1000; i++) {
                Graphe result = Genetique.mutation(init, prob);
                if (minGraph == null || result.cout() < minGraph.cout()) {
                    minGraph = result;
                    bestProb = prob;
                }
            }
        }
        //System.out.println("La meilleur proba est " + bestProb + " et le min cout est " + minGraph.cout());
        /**
         * result
         * La meilleur proba est 0.014000000000000005 et le min cout est 2066.67599441071
         */
        return bestProb;
    }

    public static int optimisationReproduction(int dataset) {
        System.out.println("test");
        Graphe minGraph = null;
        int nbPopOptimale =0;
        for (int i = 10; i < 1000; i++) {
            System.out.println(i);
            List<Graphe> pop = Genetique.generatePop("data0" + dataset, 10);
            minGraph = Genetique.evaluatePop(minGraph, pop);
            List<Graphe> children = Genetique.reproduction(pop);
            Graphe tmp = minGraph;
            minGraph = Genetique.evaluatePop(minGraph, children);
            if (tmp == minGraph) {
                nbPopOptimale = i;
            }
            System.out.println("la meilleur trajet est " + minGraph.cout() + "pour une population de " + i);
        }
        //System.out.println("Population optimale random crossover: " + nbPopOptimale);
        //System.out.println("Population optimale random crossover: cout  " + minGraph.cout());
        return nbPopOptimale;
        /**
         * result
         * Population optimale random crossover: 100000
         * Population optimale random crossover: cout  1717.52579576748
         */
    }

    public static int optimisationNbGen(int dataset) {
        int nbGen = 0;
        Graphe minGraphRandom = null;
        int nbPopOptimaleRandom =0;
        List<Graphe> pop = Genetique.generatePop("data0"+dataset, 150);
        for (int i = 1; i < 100; i ++) {
            minGraphRandom = Genetique.evaluatePop(minGraphRandom, pop);
            //System.out.println(pop.size());
            List<Graphe> child = Genetique.crossOverWithBestSolution(pop,minGraphRandom);
            child.addAll(Genetique.crossOverWithBestSolution(pop, minGraphRandom));
            if (pop.stream().mapToDouble(Graphe::cout).sum() < child.stream().mapToDouble(Graphe::cout).sum()) {

                pop = child;
            }
            //System.out.println("meilleur enfant " + Genetique.evaluatePop(null, pop).cout());
//            Genetique.debugListSommet(Genetique.evaluatePop(null, pop).getSommets());
            minGraphRandom = Genetique.evaluatePop(minGraphRandom, pop);
            System.out.println("la meilleur trajet est " + minGraphRandom.cout());
//            if (!testGenetique.checkIfAllSommetsHere(minGraphRandom)) {
//                //System.out.println(testGenetique.checkIfAllSommetsHere(minGraphRandom));
//                //System.out.println("gen  " + i + " cout " + minGraphRandom.cout());
//                break;
//            }
            //System.out.println("gen  " + i + " cout " + minGraphRandom.cout());
            //System.out.println(minGraphRandom);
            //System.out.println(minGraphRandom.isValid());

        }
//        Graph graph = Graphe.adaptGraphe(minGraphRandom);
//        graph.display();
        //System.out.println("Population optimale random crossover: " + nbPopOptimaleRandom);
        return nbGen;
    }

    public static void testNbPop(int dataset) {
        List<Graphe> graphes = new ArrayList<>();
        System.out.println("Test du nombre de population pour la dataset " + dataset);
        String data = "data0" + dataset;
        for (int i = 10; i < 500; i++) {
            Graphe graphe = Genetique.algo(i, 100, data, 0.14);
            System.out.println("Meilleur solution: " + graphe.cout() + " pour une population de " + i);
            graphes.add(graphe);
        }
        Graphe best = Genetique.evaluatePop(null, graphes);
        System.out.println("Meilleur solution: " + best.cout() );
    }

    public static void testNbGen(int dataset) {
        List<Graphe> graphes = new ArrayList<>();
        System.out.println("Test du nombre de génération pour la dataset " + dataset);
        String data = "data0" + dataset;
        for (int i = 10; i < 1000; i++) {
            Graphe graphe = Genetique.algo(150, i, data, 0.14);
            System.out.println("Meilleur solution: " + graphe.cout() + " pour " + i + " génération");
            graphes.add(graphe);
        }
        Graphe best = Genetique.evaluatePop(null, graphes);
        System.out.println("Meilleur solution: " + best.cout() );
    }

    public static void testMutation(int dataset) {
        List<Graphe> graphes = new ArrayList<>();
        System.out.println("Test de la probabilité de mutation pour la dataset " + dataset);
        String data = "data0" + dataset;
        for (double i = 0; i < 1; i=0.001) {
            Graphe graphe = Genetique.algo(150, 100, data, i);
            System.out.println("Meilleur solution: " + graphe.cout() + " pour " + i + " comme probabilité de mutation");
            graphes.add(graphe);
        }
        Graphe best = Genetique.evaluatePop(null, graphes);
        System.out.println("Meilleur solution: " + best.cout() );
    }


}
