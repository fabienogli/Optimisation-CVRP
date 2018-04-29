package Util;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import scala.Int;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graphe {

    private Depot depot;
    private Map<Integer, Client> clients;
    private ArrayList<Arc> aretes = new ArrayList<>();
    private ArrayList<Circuit> circuits = new ArrayList<>();


    public Graphe(String dataset) {
        this.clients = SommetFactory.getDataFromDb("data01");
        depot = (Depot) this.clients.get(0);

        int nbClients = this.clients.size() - 1;
        int i = 1;
        int j = 1;
        while (i < nbClients || i == nbClients) {
            int Cmax = 0;
            Circuit circuit = new Circuit();
            HashMap<Integer, Arc> arcs = new HashMap<>();
            while (Cmax + this.clients.get(i).getQuantite() <= 100) {
                Cmax += this.clients.get(i).getQuantite();
                Arc arc = new Arc(this.clients.get(i - 1), this.clients.get(i));
                arcs.put(j, arc);
                j++;
                i++;
            }
            Arc arc = new Arc(this.clients.get(i), this.clients.get(0));
            arcs.put(j, arc);
            j++;
            circuit.setArcs(arcs);
            circuits.add(circuit);
        }

    }
    private Graph adaptGraphe(Graphe g){


        return null;
    }

    public static void main(String args[]) {
        Graphe graphe = new Graphe("data01");
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
