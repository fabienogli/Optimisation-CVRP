package Algos;

import Util.*;

import java.util.List;
import java.util.Random;

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
        List<Circuit> circuits = solution.getCircuits();
        Random r = new Random();
        //someHashMap.keySet().toArray()[new Random().nextInt(someHashMap.keySet().toArray().length)];
        int circuitAlea1 = r.nextInt(circuits.size() - 1);
        //int arcAlea1 = r.nextInt(circuits.get(circuitAlea1).getArcs().size() - 2) + 1;
        int arcAlea1 = (int) circuits.get(circuitAlea1).getArcs().keySet().toArray()[new Random().nextInt(circuits.get(circuitAlea1).getArcs().keySet().toArray().length)];
        //System.out.println(circuits.size()+""+circuits.get(circuitAlea1).getArcs().size());
        Arc arctmp1 = circuits.get(circuitAlea1).getArcs().get(arcAlea1);

        int circuitAlea2 = 0;
        int arcAlea2 = 0;
        do {
            circuitAlea2 = r.nextInt(circuits.size() - 1);
        }
        while (circuitAlea1 == circuitAlea2);
        do {

            arcAlea2 = (int) circuits.get(circuitAlea2).getArcs().keySet().toArray()[new Random().nextInt(circuits.get(circuitAlea2).getArcs().keySet().toArray().length)];
        }
        while (arcAlea1 == arcAlea2);
        Arc arctmp2=circuits.get(circuitAlea2).getArcs().get(arcAlea2);
        //System.out.println(circuitAlea1 + ""+arcAlea1);
        //System.out.println(circuitAlea2 + ""+arcAlea2);
        circuits.get(circuitAlea1).getArcs().get(arcAlea1).setSommets(circuits.get(circuitAlea2).getArcs().get(arcAlea2).getSommets());
        //pour les arcs ajacents on update les extremités;
        int arcAdjacent1=circuits.get(circuitAlea1).getArcs().get(arcAlea1).getArcAdjacent1();
        int arcAdjacent2=circuits.get(circuitAlea1).getArcs().get(arcAlea1).getArcAdjacent2();
        //TODO pour les arcs adjacents =0,on fait rien et modifier aussi pour l'autre circuit
        Client sommet1= (Client)circuits.get(circuitAlea1).getArcs().get(arcAdjacent1).getSommets()[0];
        Client sommet2= (Client)circuits.get(circuitAlea1).getArcs().get(arcAdjacent2).getSommets()[1];
        Client sommet3= (Client)circuits.get(circuitAlea1).getArcs().get(arcAdjacent1).getSommets()[0];
        Client sommet4= (Client)circuits.get(circuitAlea1).getArcs().get(arcAdjacent2).getSommets()[1];
        int i=0;
        for(Client client:(Client[])arctmp1.getSommets()){

            if(client.getIdSommet() == sommet1.getIdSommet()){
                circuits.get(circuitAlea1).getArcs().get(arcAdjacent1).setSommet1(arctmp2.getSommets()[i]);
            }else if(client.getIdSommet()== sommet2.getIdSommet()){
                circuits.get(circuitAlea1).getArcs().get(arcAdjacent1).setSommet2(arctmp2.getSommets()[i]);
            }else if(client.getIdSommet()== sommet3.getIdSommet()){
                circuits.get(circuitAlea1).getArcs().get(arcAdjacent2).setSommet1(arctmp2.getSommets()[i]);
            }else if(client.getIdSommet()== sommet4.getIdSommet()){
                circuits.get(circuitAlea1).getArcs().get(arcAdjacent2).setSommet2(arctmp2.getSommets()[i]);
            }
            i++;
        }

        return solution;
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
