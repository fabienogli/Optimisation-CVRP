package Algos;

import Util.*;
import org.graphstream.graph.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static Util.Graphe.swapRandomSommet;
import static Util.Graphe.swapRandomSommet2;

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

    public static Graphe generateRandomNeighbors(Graphe solution) throws CloneNotSupportedException {
        //return  Graphe.generateRandomGrapheFromSommet(solution.getClients());
        return  swapRandomSommet(solution).clone();
    }

    //executer le recuit simulé
    public static Graphe executeAlgo(Double tempMin, Double mu, Graphe solutionInitiale, Double tempInitiale,int essais) throws CloneNotSupportedException {
        //a initialiser
        //equilibre statique = nbre de clients
       // int nb_essais = (int) Math.pow(solutionInitiale.getClients().size()-1 ,2);
        int nb_essais =essais;
        Random r = new Random();
        Double delta = 0.0;
        Double temperatureCourante = tempInitiale;
        Graphe solutionCourante = solutionInitiale.clone();
        Graphe solutionOptimale = solutionCourante.clone();
        while (temperatureCourante > tempMin) {
            for (int nb_etape = 0; nb_etape < nb_essais; nb_etape++) {
                //ou intervertir directement des aretes
                //List<Solution>  neighbors = generateNeighbors();
                Graphe solutionVoisine = generateRandomNeighbors(solutionCourante).clone();
                // a revoir
                delta = solutionVoisine.cout() - solutionCourante.cout();
                if (delta <= 0) {
                    solutionCourante = new Graphe(solutionVoisine.clone().getClients());
                    if (solutionVoisine.cout() < solutionOptimale.cout()) {
                        solutionOptimale = new Graphe(solutionVoisine.clone().getClients());
                    }
                } else {
                    //critère de Metropolis
                    Double pAlea = r.nextDouble();
                    if (pAlea <= Math.exp(-delta / temperatureCourante)) {
                        solutionCourante = new Graphe(solutionVoisine.clone().getClients());
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
    //executer le recuit simulé
    public static Graphe executeAlgo2(Double mu, Graphe solutionInitiale, Double tempInitiale) throws CloneNotSupportedException {
        //a initialiser
        //equilibre statique = nbre de clients
        // int nb_essais = (int) Math.pow(solutionInitiale.getClients().size()-1 ,2);
        int nb_essais = 10000;
        Random r = new Random();
        Double delta = 0.0;
        Double temperatureCourante = tempInitiale;
        Graphe solutionCourante = solutionInitiale.clone();
        Graphe solutionOptimale = solutionCourante.clone();
        int kmax=10000;
        for (int k = 0; k < kmax; k++){
            for (int nb_etape = 0; nb_etape < nb_essais; nb_etape++) {
                //ou intervertir directement des aretes
                //List<Solution>  neighbors = generateNeighbors();
                Graphe solutionVoisine = generateRandomNeighbors(solutionCourante).clone();
                // a revoir
                delta = solutionVoisine.cout() - solutionCourante.cout();
                if (delta <= 0) {
                    solutionCourante = solutionVoisine.clone();
                    if (solutionVoisine.cout() < solutionOptimale.cout()) {
                        solutionOptimale = solutionVoisine.clone();
                    }
                } else {
                    //critère de Metropolis
                    Double pAlea = r.nextDouble();
                    if (pAlea <= Math.exp(-delta / temperatureCourante)) {
                        solutionCourante = solutionVoisine.clone();
                    }
                }
                //afficher graphe
            }

            temperatureCourante = decreaseFunction(mu, temperatureCourante);
            System.out.println(solutionOptimale.cout()+"-"+temperatureCourante+"-"+k+"-"+nb_essais);
         }
        Graph graph = solutionOptimale.adaptGraphe();
        graph.display();
        System.out.println(solutionOptimale.cout());
        return solutionOptimale;
    }



    /*public  static  void main(String args[]) throws CloneNotSupportedException {
        Graphe graphe = new Graphe("data01");
        executeAlgo(0.00001,0.99,graphe,100.0,10000);
        //executeAlgo2(0.01,0.4,graphe,100.0);
    }*/
}
