package algo;

import Algos.Genetique;
import Util.Graphe;
import org.graphstream.graph.Graph;

import java.util.List;

public class testParametreGenetique {

    public static void main(String[] args) {

//        optimisationCrossover();
//        optimisationMutation();
//        optimisationReproduction();
        optimisationNbGen();
    }

    public static int optimisationCrossover() {
        Graphe minGraphRandom = null;
        int nbPopOptimaleRandom =0;
        for (int i = 100; i < 150; i ++) {
            List<Graphe> pop = Genetique.generatePop("data01", i);
            minGraphRandom = Genetique.evaluatePop(minGraphRandom, pop);
            for (int m = 0; m < 10; m++) {
                List<Graphe> children = Genetique.randomCrossover(pop);
                Graphe tmp = minGraphRandom;
                minGraphRandom = Genetique.evaluatePop(minGraphRandom, children);
                if (tmp == minGraphRandom) {
                    nbPopOptimaleRandom = i;
                }
            }
        }
        System.out.println("Population optimale random crossover: " + nbPopOptimaleRandom);
        System.out.println("Population optimale random crossover: cout  " + minGraphRandom.cout());

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
        System.out.println("Population optimale crossover: " + nbPopOptimale);
        System.out.println("Population optimale crossover: cout  " + minGraph.cout());
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

    public static Double optimisationMutation() {
        Graphe init = Graphe.generateRandomGraph("data01");
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
        System.out.println("La meilleur proba est " + bestProb + " et le min cout est " + minGraph.cout());
        /**
         * result
         * La meilleur proba est 0.014000000000000005 et le min cout est 2066.67599441071
         */
        return bestProb;
    }

    public static int optimisationReproduction() {
        Graphe minGraph = null;
        int nbPopOptimale =0;
        for (int i = 1000; i < 1000000; i *= 10) {
            List<Graphe> pop = Genetique.generatePop("data01", 10);
            minGraph = Genetique.evaluatePop(minGraph, pop);
            for (int m = 0; m < 10; m++) {
                List<Graphe> children = Genetique.reproduction(pop);
                Graphe tmp = minGraph;
                minGraph = Genetique.evaluatePop(minGraph, children);
                if (tmp == minGraph) {
                    nbPopOptimale = i;
                }
            }
        }
        System.out.println("Population optimale random crossover: " + nbPopOptimale);
        System.out.println("Population optimale random crossover: cout  " + minGraph.cout());
        return nbPopOptimale;
        /**
         * result
         * Population optimale random crossover: 100000
         * Population optimale random crossover: cout  1717.52579576748
         */
    }

    public static int optimisationNbGen() {
        int nbGen = 0;
        Graphe minGraphRandom = null;
        int nbPopOptimaleRandom =0;
        List<Graphe> pop = Genetique.generatePop("data01", 150);
        for (int i = 1; i < 10; i ++) {
            minGraphRandom = Genetique.evaluatePop(minGraphRandom, pop);
            System.out.println(pop.size());
            List<Graphe> child = Genetique.randomCrossover(pop);
            child.addAll(Genetique.randomCrossover(pop));
            if (pop.stream().mapToDouble(Graphe::cout).sum() < child.stream().mapToDouble(Graphe::cout).sum()) {

                pop = child;
            }
            System.out.println("meilleur enfant " + Genetique.evaluatePop(null, pop).cout());
            Genetique.debugListSommet(Genetique.evaluatePop(null, pop).getSommets());
            minGraphRandom = Genetique.evaluatePop(minGraphRandom, pop);
            if (!testGenetique.checkIfAllSommetsHere(minGraphRandom)) {
                System.out.println(testGenetique.checkIfAllSommetsHere(minGraphRandom));
                System.out.println("gen  " + i + " cout " + minGraphRandom.cout());
                break;
            }
            System.out.println("gen  " + i + " cout " + minGraphRandom.cout());
            System.out.println(minGraphRandom);
            System.out.println(minGraphRandom.isValid());

        }
        Graph graph = Graphe.adaptGraphe(minGraphRandom);
        graph.display();
        System.out.println("Population optimale random crossover: " + nbPopOptimaleRandom);
        return nbGen;
    }

}
