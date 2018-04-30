package Util;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.graphstream.ui.view.Viewer;
import scala.Int;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Graphe {

    private Depot depot;
    private ArrayList<Client> sommets;
    private Map<Integer, Client> clients;
    private ArrayList<Arc> aretes;
    private ArrayList<Circuit> circuits;

    public Graphe() {
        aretes = new ArrayList<>();
        circuits = new ArrayList<>();
    }

    public Graphe(ArrayList<Circuit> circuits) {
        this.circuits = circuits;
        this.setSommets((ArrayList) circuits.stream().map(Circuit::getSommets).collect(Collectors.toList()));
    }

    public Graphe(String dataset) {
        this();
        this.clients = SommetFactory.getDataFromDb("data01");
        depot = (Depot) this.clients.get(0);
        int i = 1;
        int nbClients = this.clients.size() - 1;
        int j = 1;
        while (i < nbClients) {
            int Cmax = 0;
            Circuit circuit = new Circuit();
            HashMap<Integer, Arc> arcs = new HashMap<>();
            Arc arc1 = new Arc(this.clients.get(0), this.clients.get(i));
            arcs.put(j, arc1);
            j++;
            i++;
            while (Cmax + this.clients.get(i).getQuantite() <= 100) {
                Cmax += this.clients.get(i).getQuantite();
                Arc arc = new Arc(this.clients.get(i - 1), this.clients.get(i));
                arcs.put(j, arc);
                j++;
                i++;
            }
            Arc arc2 = new Arc(this.clients.get(i), this.clients.get(0));
            arcs.put(j, arc2);
            j++;
            circuit.setArcs(arcs);
            circuits.add(circuit);

        }

    }

    private Graph adaptGraphe() {
        Graph graph = new SingleGraph("Graphe");
        graph.setStrict(false);
        graph.setAutoCreate(true);
        SpriteManager sman = new SpriteManager(graph);
        int h = 1;
        for (int i = 0; i < circuits.size(); i++) {
            for (int j = 1; j < circuits.get(i).getArcs().size(); j++) {

                graph.addEdge(Integer.toString(h), Integer.toString(circuits.get(i).getArcs().get(j).getSommets()[0].getIdSommet()), Integer.toString(circuits.get(i).getArcs().get(j).getSommets()[1].getIdSommet()), true);
                h++;
            }
        }
        return graph;
    }

    public static Graph adaptGraphe(Graphe graphe) {
        Graph graph = new SingleGraph("Graphe");
        graph.setStrict(false);
        graph.setAutoCreate(true);
        SpriteManager sman = new SpriteManager(graph);
        int h = 1;
        ArrayList<Circuit> circuits = graphe.getCircuits();
        for (int i = 0; i < circuits.size(); i++) {
            for (int j = 0; j < circuits.get(i).getArcs().size(); j++) {

                graph.addEdge(Integer.toString(h), Integer.toString(circuits.get(i).getArcs().get(j).getSommets()[0].getIdSommet()), Integer.toString(circuits.get(i).getArcs().get(j).getSommets()[1].getIdSommet()), true);
                h++;
            }
        }
        return graph;
    }

    public static Graphe generateRandomGraph(String dataset) {
        Map<Integer, Client> clients = SommetFactory.getDataFromDb(dataset);
        return generateRandomGrapheFromSommet(clients);
    }

    private static Graphe generateRandomGrapheFromSommet(Map<Integer, Client> clients) {
        int cmax = 100;
        ArrayList<Circuit> circuits = new ArrayList<Circuit>();
        Graphe graphe = new Graphe();
        Depot depot = (Depot) clients.get(0);
        clients.remove(depot.getIdSommet(), depot);
        graphe.setDepot(depot);
        graphe.setClients(clients);
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
            if (client.getIdSommet() == 0) {
                clients.remove(client);
                randomKey = keys.get(random.nextInt(keys.size()));
                client = clients.get(randomKey);
            }
            if (lastClient == null) {   //C'est un nouveau circuit, on doit l'intialiser
                circuit = new Circuit();
                arcs = new HashMap<>();
                cout = 0;
                i_arc = 0;
                lastClient = depot;
            }
            if (client.getQuantite() + cout >= cmax && lastClient != null) {
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
        graphe.setCircuits(circuits);
        graphe.setSommets((ArrayList) circuits.stream().map(Circuit::getSommets).collect(Collectors.toList()));
        return graphe;
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

    public Depot getDepot() {
        return depot;
    }

    public void setDepot(Depot depot) {
        this.depot = depot;
    }

    public Map<Integer, Client> getClients() {
        return clients;
    }

    public void setClients(Map<Integer, Client> clients) {
        this.clients = clients;
    }

    public ArrayList<Arc> getAretes() {
        return aretes;
    }

    public void setAretes(ArrayList<Arc> aretes) {
        this.aretes = aretes;
    }

    public ArrayList<Circuit> getCircuits() {
        return circuits;
    }

    public void setCircuits(ArrayList<Circuit> circuits) {
        this.circuits = circuits;
    }

    public ArrayList<Client> getSommets() {
        return this.sommets;
    }

    public Graphe(ArrayList<Client> sommets, boolean test) {    //Pour pouvoir faire un constructeur avec les sommets
        this.sommets = sommets;
        Depot depot = (Depot) sommets.get(0);
        if (depot.getIdSommet() != 0) {
            return;
        }
        sommets.remove(depot);
        Circuit circuit = new Circuit();
        Client lastSommet = depot;
        int i_arc = 0;
        HashMap<Integer, Arc> arcs = new HashMap<>();
        for (Client sommet : sommets) {
            if (sommet.getIdSommet() == 0) {
                arcs.put(i_arc, new Arc(lastSommet, depot));
                circuit.setArcs(arcs);
                this.circuits.add(circuit);
                circuit = new Circuit();
                arcs = new HashMap<>();
                lastSommet = depot;
                i_arc = 0;
                continue;
            }
            lastSommet = sommet;
            arcs.put(i_arc, new Arc(lastSommet, sommet));
            i_arc++;
        }
    }

    public void setSommets(ArrayList<Client> sommets) {
        this.sommets = sommets;
    }
}
