package Util;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.ArrayList;
import java.util.Map;

public class Graphe {

    private Depot depot;
    private Map<Integer, Client> clients;
    private ArrayList<Arc> aretes = new ArrayList<>();
    private Circuit[] circuits = new Circuit[4];


    public Graphe(String dataset) {
        this.clients = SommetFactory.getDataFromDb("data01");
        depot = (Depot) this.clients.get(0);
        for(int i =0;i<this.clients.size()-1;i++){
            int Cmax = 0;
            int j = 1;
            while (Cmax <=100){
                Arc arc = new Arc(depot,this.clients.get(j));
                Cmax += this.clients.get(j).getQuantite();
            }
        }
    }

    public static void main(String args[]) {
        Graph graph = new SingleGraph("Tutorial 1");
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addEdge("AB", "A", "B");
        graph.addEdge("BC", "B", "C");
        graph.addEdge("CA", "C", "A");
        graph.display();
    }
}
