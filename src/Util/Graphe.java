package Util;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.spriteManager.SpriteManager;
import scala.Int;

import java.util.*;

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
                Arc arc2 = new Arc(this.clients.get(i-1), this.clients.get(0));
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
        Graph graph = new MultiGraph("Graphe");
       graph.setStrict(false);
       graph.setAutoCreate(true);
        graph.addAttribute("ui.stylesheet", "node { fill-color: red; }");
        SpriteManager sman = new SpriteManager(graph);
        this.clients.forEach((integer, client) -> {
            graph.addNode(Integer.toString(client.getIdSommet()));
        });
        for (int i = 0; i < circuits.size(); i++) {
            System.out.println("Circuit " + i);
           for (int keySet : circuits.get(i).getArcs().keySet()) {
                String s2 = Integer.toString(circuits.get(i).getArcs().get(keySet).getSommets()[0].getIdSommet());
                String s3 = Integer.toString(circuits.get(i).getArcs().get(keySet).getSommets()[1].getIdSommet());
                graph.addEdge(s2+s3, s2, s3);
                System.out.println(s2+s3 + "-" + s2 + "-" + s3);
            }
        }
        for (Node node : graph) {
            node.addAttribute("ui.label", node.getId());
        }
        return graph;
    }
    public static Graph adaptGraphe(ArrayList<Circuit> circuits) {
        Graph graph = new SingleGraph("Graphe");
        graph.setStrict(false);
        graph.setAutoCreate(true);
        SpriteManager sman = new SpriteManager(graph);
        int h = 1;
        for (int i = 0; i < circuits.size(); i++) {
            for (int j = 0; j < circuits.get(i).getArcs().size(); j++) {

                graph.addEdge(Integer.toString(h), Integer.toString(circuits.get(i).getArcs().get(j).getSommets()[0].getIdSommet()), Integer.toString(circuits.get(i).getArcs().get(j).getSommets()[1].getIdSommet()), true);
                h++;
            }
        }
        return graph;
    }
    public static ArrayList<Circuit> generateRandomGraph(String dataset) {
        int Cmax = 100;
        ArrayList<Circuit> circuits = new ArrayList<Circuit>();
        Map<Integer, Client> clients = SommetFactory.getDataFromDb(dataset);
        Depot depot = (Depot) clients.get(0);
        clients.remove(depot.getIdSommet(), depot);
        Circuit circuit = new Circuit();
        HashMap<Integer, Arc> arcs = new HashMap<>();
        int cout = 0;
        Client lastClient = null;
        int i_arc = 0;

        while (!clients.isEmpty()) {
            Random random = new Random();
            List<Integer> keys = new ArrayList<>(clients.keySet());
            int randomKey = keys.get(random.nextInt(keys.size()));
            Client client = clients.get(randomKey);
            if (lastClient == null) {   //C'est un nouveau circuit, on doit l'intialiser
                circuit = new Circuit();
                arcs = new HashMap<>();
                cout = 0;
                i_arc = 0;
                lastClient = depot;
            }
            if (client.getQuantite() + cout >= Cmax && lastClient != null) {
                arcs.put(i_arc, new Arc(lastClient, depot));
                circuit.setArcs(arcs);
                circuits.add(circuit);
                lastClient = null;
            } else {
                arcs.put(i_arc, new Arc(lastClient, client));
                cout += client.getQuantite();
                lastClient = client;
            }
            i_arc++;
            clients.remove(randomKey, client);
        }
        return circuits;
    }

    public static void main(String args[]) {
        Graphe graphe = new Graphe("data01");
        /*Graph graph = new MultiGraph("Tutorial 1");
        SpriteManager sman = new SpriteManager(graph);
        graph.addNode("C");
        graph.addNode("A");
        graph.addNode("B");

        graph.addEdge("AB", "A", "B");
        graph.addEdge("CA", "C", "A");
        graph.addEdge("BC", "B", "C");

        graph.display();*/
        Graph graph = graphe.adaptGraphe();
        graph.display();
    }

    public double cout() {
        return circuits.stream().mapToDouble(Circuit::cout).sum();
    }
}
