package Algos;

import Util.Arc;
import Util.Circuit;
import Util.Graphe;

import java.util.List;
import java.util.Random;

public class RecuitSimule {


    //fonction de decroissance de la temperature
    public static Double decreaseFunction(Double mu, Double temperature) {
        return temperature * mu;
    }

    ;

    //fonction f(x)
    public static Double evaluateFunction(Graphe solution) {
        //Le coût total d’une solution est la somme des coût de chaque Ri.
        return solution.cout();
    }

    ;

    //generer voisinage
    public static Graphe generateRandomNeighbors(Graphe solution) {
        List<Circuit> circuits = solution.getCircuits();
        Random r = new Random();
        int circuitAlea1 = r.nextInt(circuits.size() - 1);
        int arcAlea1 = r.nextInt(circuits.get(circuitAlea1).getArcs().size() - 1) + 1;
        Arc arctmp = circuits.get(circuitAlea1).getArcs().get(arcAlea1);

        int circuitAlea2 = 0;
        int arcAlea2 = 0;
        do {
            circuitAlea2 = r.nextInt(circuits.size() - 1);
        }
        while (circuitAlea1 == circuitAlea2);
        do {
            arcAlea2 = r.nextInt(circuits.size() - 1);
        }
        while (arcAlea1 == arcAlea2);


        return null;
    }

    //executer le recuit simulé
    public static Graphe executeAlgo(Double tempMin, Double mu, Graphe solutionInitiale, Double tempInitiale) {
        //a initialiser
        //equilibre statique = nbre de clients
        int nb_essais = solutionInitiale.getClients().size() - 1;
        Random r = new Random();
        Double delta = 0.0;
        Double temperatureCourante = tempInitiale;
        Graphe solutionCourante = solutionInitiale;
        Graphe solutionOptimale = solutionCourante;
        while (temperatureCourante > tempMin) {
            for (int nb_etape = 0; nb_etape < nb_essais; nb_etape++) {
                //ou intervertir directement des aretes
                //List<Solution>  neighbors = generateNeighbors();
                int nbAlea = r.nextInt();
                Graphe solutionVoisine = generateRandomNeighbors(solutionCourante);
                // a revoir
                delta = evaluateFunction(solutionVoisine) - evaluateFunction(solutionCourante);
                if (delta < 0) {
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
            temperatureCourante = decreaseFunction(mu, temperatureCourante);
        }

        return solutionOptimale;
    }

}
