package Algos;

import Util.Solution;

import java.util.List;
import java.util.Random;

public class RecuitSimule {

    //paramètres de l'algorithme de recuit simulé
    private Solution solutionInitiale;
    private Double tempInitiale;
    //fonction de decroissance de la temperature
    public Double decreaseFunction(Double mu,Double temperature){
        return temperature*mu;
    };
    //fonction f(x)
    public Double evaluateFunction(Solution solution){
    //Le coût total d’une solution est la somme des coût de chaque Ri.

        return null;
    };
    //generer voisinage
    public List<Solution> generateNeighbors(){

        return null;
    }
    //executer le recuit simulé
    public Solution executeAlgo(Double tempMin,Double mu){
        //a initialiser
        int nb_essais=0;
        Random r = new Random();
        Double delta = 0.0;
        Double temperatureCourante=tempInitiale;
        Solution solutionCourante =solutionInitiale;
        while (temperatureCourante>tempMin){
                for(int nb_etape=0;nb_etape<nb_essais;nb_etape++){
                    //ou intervertir directement des aretes
                    List<Solution>  neighbors = generateNeighbors();
                    int nbAlea =r.nextInt();
                    Solution solutionVoisine= neighbors.get(nbAlea);
                    // a revoir
                    delta = evaluateFunction(solutionVoisine)-evaluateFunction(solutionCourante);
                    if(delta < 0){

                    }else{
                        //critère de Metropolis
                        Double pAlea =r.nextDouble();
                        if(pAlea<=Math.exp(-delta/temperatureCourante)){
                            solutionCourante = solutionVoisine;
                        }
                    }


                }
              temperatureCourante=decreaseFunction(mu,temperatureCourante);
        }

        return solutionCourante;
    }

}
