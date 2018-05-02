package Algos;

import Util.*;
import org.graphstream.graph.Graph;

import java.util.List;
import java.util.Random;

import static Util.Graphe.swapRandomSommet;

public class RecuitSimule {


    //fonction de decroissance de la temperature
    public static Double decreaseFunction(Double mu, Double temperature) {
        return temperature * mu;
    }

    //fonction f(x)
    public static Double evaluateFunction(Graphe solution) {
        //Le coût total d’une solution est la somme des coût de chaque Ri.
        return solution.cout();
    }

    //generer voisinage
    public static Graphe generateRandomNeighbors(Graphe solution) {
        return  swapRandomSommet(solution);
    }

    //executer le recuit simulé
    public static Graphe executeAlgo(Double tempMin, Double mu, Graphe solutionInitiale, Double tempInitiale) {
        //a initialiser
        //equilibre statique = nbre de clients
       // int nb_essais = (int) Math.pow(solutionInitiale.getClients().size()-1 ,2);
        int nb_essais =2000;
        Random r = new Random();
        Double delta = 0.0;
        Double temperatureCourante = tempInitiale;
        Graphe solutionCourante = solutionInitiale;
        Graphe solutionOptimale = solutionCourante;
        while (temperatureCourante > tempMin) {
            for (int nb_etape = 0; nb_etape < nb_essais; nb_etape++) {
                //ou intervertir directement des aretes
                //List<Solution>  neighbors = generateNeighbors();
                Graphe solutionVoisine = generateRandomNeighbors(solutionCourante);
                // a revoir
                delta = evaluateFunction(solutionVoisine) - evaluateFunction(solutionCourante);
                if (delta <= 0) {
                    solutionCourante = solutionVoisine;
                    if (evaluateFunction(solutionVoisine) < evaluateFunction(solutionOptimale)) {
                        solutionOptimale = solutionVoisine;
                    }
                } else {
                    //critère de Metropolis
                    Double pAlea = r.nextDouble();
                    if (pAlea <= Math.exp(-delta / temperatureCourante)) {
                        solutionCourante = solutionVoisine;
                    }
                }
                //afficher graphe

            }
            System.out.println(solutionOptimale.cout());
            temperatureCourante = decreaseFunction(mu, temperatureCourante);
        }
        Graph graph = solutionOptimale.adaptGraphe();
        graph.display();
        System.out.println(solutionOptimale.cout());
        return solutionOptimale;
    }

    public  static  void main(String args[]){
        Graphe graphe = new Graphe("data01");
        executeAlgo(0.0001,0.99999,graphe,1000.0);
    }
}
