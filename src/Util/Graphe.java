package Util;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.spriteManager.SpriteManager;
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
        int i = 1;
        int nbClients = this.clients.size() - 1;
        int j = 1;
        while (i < nbClients || i==nbClients) {
            System.out.println("Circuit " + i);
            int Cmax = 0;
            Circuit circuit = new Circuit();
            HashMap<Integer, Arc> arcs = new HashMap<>();
            if (i < nbClients) {
                Arc arc1 = new Arc(this.clients.get(0), this.clients.get(i));
                arcs.put(j, arc1);
                j++;
                i++;
                while ((Cmax + this.clients.get(i).getQuantite()) <= 100 && i != nbClients) {
                    //  while ((Cmax + this.clients.get(i).getQuantite()) <= 100 ) {
                    Cmax += this.clients.get(i).getQuantite();
                    Arc arc = new Arc(this.clients.get(i - 1), this.clients.get(i));
                    arcs.put(j, arc);
                    j++;
                    i++;
                    System.out.println("Cmax=" + Cmax);
                }
                Arc arc2 = new Arc(this.clients.get(i), this.clients.get(0));
                arcs.put(j, arc2);
                j++;
            }else if(i==nbClients){
                Arc arc1 = new Arc(this.clients.get(0), this.clients.get(i));
                arcs.put(j, arc1);
                j++;
                Arc arc2 = new Arc(this.clients.get(i), this.clients.get(0));
                arcs.put(j, arc2);
                j++;
                i++;//pour sortir
            }
            circuit.setArcs(arcs);
            circuits.add(circuit);
        }
        System.out.println("Fin Construction");
    }

    private Graph adaptGraphe() {
        Graph graph = new SingleGraph("Graphe");
        graph.setStrict(false);
        graph.setAutoCreate(true);
        SpriteManager sman = new SpriteManager(graph);
        int h = 1;
        for (int i = 0; i < circuits.size(); i++) {
            System.out.println("Circuit " + i);
            for (int keySet: circuits.get(i).getArcs().keySet()) {
                String s1 = Integer.toString(h);
                String s2 = Integer.toString(circuits.get(i).getArcs().get(keySet).getSommets()[0].getIdSommet());
                String s3 = Integer.toString(circuits.get(i).getArcs().get(keySet).getSommets()[1].getIdSommet());
                graph.addEdge(s1, s2, s3);
                System.out.println(s1 + "-" + s2 + "-" + s3);
                h++;
            }
        }
        return graph;
    }

    public static void main(String args[]) {
        Graphe graphe = new Graphe("data01");
       /* Graph graph = new MultiGraph("Tutorial 1");
        SpriteManager sman = new SpriteManager(graph);
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addEdge("AB", "A", "B");
        graph.addEdge("BC", "B", "C");
        graph.addEdge("CA", "C", "A");
        graph.display();*/
        Graph graph = graphe.adaptGraphe();
        graph.display();
    }

    public double cout() {
        return circuits.stream().mapToDouble(Circuit::cout).sum();
    }
}
