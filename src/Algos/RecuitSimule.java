package Algos;

import Util.Solution;

import java.util.List;
import java.util.Random;

public class RecuitSimule {

    //paramètres de l'algorithme de recuit simulé
    private Solution solutionInitiale;
    private float tempInitiale;
    //fonction de decroissance de la temperature
    public float decreaseFunction(float mu){


        return 0;
    };
    //fonction f(x)
    public float evaluateFunction(Solution solution){
    //Le coût total d’une solution est la somme des coût de chaque Ri.
        return 0;
    };
    //generer voisinage
    public List<Solution> generateNeighbors(){

    }
    //executer le recuit simulé
    public Solution executeAlgo(Float tempMin,Float mu){
        //a initialiser
        int nb_essais=0;
        Random r = new Random();
        float delta =0;
        float temperatureCourante=tempInitiale;
        Solution solutionCourante =solutionInitiale;
        while (temperatureCourante>tempMin){
                for(int nb_etape=0;nb_etape<nb_essais;nb_etape++){
                    List<Solution>  neighbors = generateNeighbors();
                    int nbAlea =r.nextInt();
                    Solution solutionVoisine= neighbors.get(nbAlea);
                    // a revoir
                    delta = evaluateFunction(solutionVoisine)-evaluateFunction(solutionCourante);
                    if(delta < 0){

                    }else{
                        //critère de Metropolis
                    }


                }
              temperatureCourante=decreaseFunction(mu);
        }

        return null;
    }

}
