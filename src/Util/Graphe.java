package Util;

import Algos.RecuitSimule;
import Algos.Genetique;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
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
    private List<Client> sommets;
    private Map<Integer, Client> clients;
    private ArrayList<Circuit> circuits;

    public Graphe() {
        circuits = new ArrayList<>();
    }

    public Graphe(List<Client> sommets) {    //Pour pouvoir faire un constructeur avec les sommets
//        System.out.println("dans le constructeur graphe");
        this.sommets = sommets;
        this.circuits = new ArrayList<>();
        Client tmp = sommets.get(0);
        Depot depot = new Depot(tmp.getCoordonnee());
        if (depot.getIdSommet() != 0) {
            return;
        }
        Circuit circuit = new Circuit();
        Client lastSommet = depot;
        int i_arc = 0;
        HashMap<Integer, Arc> arcs = new HashMap<>();
        for (int i =1; i < sommets.size(); i++) {
            Client sommet = sommets.get(i);
//            System.out.println(sommet);
            if (lastSommet == depot && sommet.getIdSommet() == 0) {
                continue;
            }
            if (sommet.getQuantite() + circuit.getC() > 100) {
//                System.out.println("premier if: " + circuit.getC());
                arcs.put(i_arc, new Arc(lastSommet, depot));
                circuit.setArcs(arcs);
                this.circuits.add(circuit);
                circuit = new Circuit();
                arcs = new HashMap<>();
                lastSommet = depot;
                i_arc = 0;
            }
            if (sommet.getIdSommet() == 0) {
                sommet = depot;
            }
            circuit.addSommet(sommet);
            arcs.put(i_arc, new Arc(lastSommet, sommet));
            lastSommet = sommet;
            if (lastSommet == depot) {
                circuit.setArcs(arcs);
                this.circuits.add(circuit);
                circuit = new Circuit();
                arcs = new HashMap<>();
                i_arc = -1;
            }
            i_arc++;
        }
        arcs.put(i_arc, new Arc(lastSommet, depot));
        circuit.setArcs(arcs);
        this.circuits.add(circuit);
//        System.out.println("dans le constructeur graphe");
    }

    public Graphe(String dataset) {
        this(SommetFactory.getDataFromDb(dataset));
    }
    public Graphe(Map<Integer,Client> map){
        this();
        this.clients = map;
        depot = (Depot) this.clients.get(0);
        int i = 1;
        int nbClients = this.clients.size() - 1;
        int j = 1;
        while (i <= nbClients) {
            System.out.println("Circuit " + i);
            int Cmax = 0;
            Circuit circuit = new Circuit();
            HashMap<Integer, Arc> arcs = new HashMap<>();
            if (i < nbClients) {
                Arc arc1 = new Arc(this.clients.get(0), this.clients.get(i));
                arcs.put(j, arc1);
                arc1.setArcAdjacent1(0);
                arc1.setArcAdjacent2(j+1);
                j++;
                circuit.getClients().add(this.clients.get(i));
                Cmax = this.clients.get(i).getQuantite();
                i++;
                while (i <= nbClients && (Cmax + this.clients.get(i).getQuantite()) <= 100) {
                    //  while ((Cmax + this.clients.get(i).getQuantite()) <= 100 ) {
                    Cmax += this.clients.get(i).getQuantite();
                    Arc arc = new Arc(this.clients.get(i - 1), this.clients.get(i));
                    arcs.put(j, arc);
                    arc1.setArcAdjacent1(j-1);
                    if(i<nbClients) arc1.setArcAdjacent2(j+1);
                    j++;
                    circuit.getClients().add(this.clients.get(i));
                    i++;
                    System.out.println("Cmax=" + Cmax);
                }
                Arc arc2 = new Arc(this.clients.get(i - 1), this.clients.get(0));
                arcs.put(j, arc2);
                arc2.setArcAdjacent1(j-1);
                arc2.setArcAdjacent2(0);
                j++;

            } else if (i == nbClients) {
                Arc arc1 = new Arc(this.clients.get(0), this.clients.get(i));
                arcs.put(j, arc1);
                j++;
                Arc arc2 = new Arc(this.clients.get(i), this.clients.get(0));
                arcs.put(j, arc2);
                j++;
                circuit.getClients().add(this.clients.get(i));
                this.clients.get(i).setSommetAdjacent1(0);
                this.clients.get(i).setSommetAdjacent2(0);
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
                graph.addEdge(s2 + s3, s2, s3);
                System.out.println(s2 + s3 + "-" + s2 + "-" + s3);
            }
        }
        for (Node node : graph) {
            node.addAttribute("ui.label", node.getId());
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
        graph.getNodeSet().forEach(node -> {
            node.addAttribute("ui.label", node.getId());
        });
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
        Client lastClient = depot;
        int i_arc = 0;

        while (!clients.isEmpty()) {
            Random random = new Random();
            List<Integer> keys = new ArrayList<>(clients.keySet());
            int randomKey = keys.get(random.nextInt(keys.size()));
            Client client = clients.get(randomKey);
            if (client.getIdSommet() == 0) {
                clients.remove(randomKey, client);
                randomKey = keys.get(random.nextInt(keys.size()));
                client = clients.get(randomKey);
            }
            if (client.getQuantite() + cout >= cmax) {   //C'est un nouveau circuit, on doit l'intialiser
                arcs.put(i_arc, new Arc(lastClient, depot));
                circuit.setArcs(arcs);
                circuits.add(circuit);
                circuit = new Circuit();
                arcs = new HashMap<>();
                cout = 0;
                i_arc = 0;
                lastClient = depot;
            }
                arcs.put(i_arc, new Arc(lastClient, client));
                cout += client.getQuantite();
                lastClient = client;
                clients.remove(randomKey, client);
            i_arc++;
        }
        arcs.put(i_arc, new Arc(lastClient, depot));
        circuit.setArcs(arcs);
        circuits.add(circuit);
        graphe.setCircuits(circuits);
        List<Client> sommets = new ArrayList<>();
        circuits.stream().forEach(circ -> {
            sommets.addAll(circ.getSommets());
        });
        graphe.setSommets((ArrayList)sommets);
        return graphe;
    }
    public static Graphe swapRandomClient(Graphe graphe) {
        Map<Integer, Client> map = Genetique.convertListToMapClient(graphe.getSommets());
        Random random = new Random();
        List<Integer> keys = new ArrayList<>(map.keySet());
        int firstRandomKey = keys.get(random.nextInt(keys.size()));
        int secondRandomKey =  keys.get(random.nextInt(keys.size()));
        while (map.get(firstRandomKey).getIdSommet() == 0) {
            firstRandomKey = keys.get(random.nextInt(keys.size()));
        }
        while (map.get(secondRandomKey).getIdSommet() == 0 || secondRandomKey == firstRandomKey) {
            secondRandomKey = keys.get(random.nextInt(keys.size()));
        }
        Client toSwap = map.get(firstRandomKey);
        Client toSwap1 = map.get(secondRandomKey);
        map.remove(toSwap);
        map.remove(toSwap1);
        map.put(secondRandomKey, toSwap);
        map.put(firstRandomKey, toSwap1);
        return new Graphe(new ArrayList<>(map.values()));
    }

    public static Graphe swapRandomSommet(Graphe graphe) {
        //Map<Integer, Client> map = Genetique.convertListToMapClient(graphe.getSommets());
        Map<Integer, Client> map =graphe.getClients();
        Random random = new Random();
        List<Integer> keys = new ArrayList<>(map.keySet());
        int firstRandomKey = keys.get(random.nextInt(keys.size()));
        int secondRandomKey =  keys.get(random.nextInt(keys.size()));
        System.out.println(map.get(firstRandomKey));
        while (map.get(firstRandomKey).getIdSommet() == 0) {
            firstRandomKey = keys.get(random.nextInt(keys.size()));
        }
        while (map.get(secondRandomKey).getIdSommet() == 0 || secondRandomKey == firstRandomKey) {
            secondRandomKey = keys.get(random.nextInt(keys.size()));
        }
        Client toSwap = map.get(firstRandomKey);
        Client toSwap1 = map.get(secondRandomKey);
        map.remove(toSwap);
        map.remove(toSwap1);
        map.put(secondRandomKey, toSwap);
        map.put(firstRandomKey, toSwap1);
        graphe.setClients(map);
        //System.out.println(map.values().stream().collect(Collectors.toList()));
        //return new Graphe(map.values().stream().collect(Collectors.toList()));
        return  new Graphe(graphe.clients);
    }

    @Override
    public boolean equals(Object obj) {
        ArrayList<Circuit> circuits = ((Graphe) obj).getCircuits();
        if (circuits.size() != this.circuits.size()) {
            System.out.println("Les deux graphes ont des tailles différentes:");
            System.out.println(this.circuits.size() +" et " + circuits.size());
            return false;
        }
        for (int i = 0; i < this.circuits.size(); i++) {
            if (! circuits.get(i).equals(this.circuits.get(i))) {
                System.out.println("le circuit numero "+ i + " est different");
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "Graphe avec au max C =" + getCtotal() + ", un distance total de " + cout() + " et " + circuits.size() + " circuits";
    }

    public Depot getDepot() {
        return depot;
    }

    // * Getteur et Setteur

    public void setDepot(Depot depot) {
        this.depot = depot;
    }

    public Map<Integer, Client> getClients() {
        return clients;
    }

    public void setClients(Map<Integer, Client> clients) {
        this.clients = clients;
    }

    public ArrayList<Circuit> getCircuits() {
        return circuits;
    }

    public void setCircuits(ArrayList<Circuit> circuits) {
        this.circuits = circuits;
    }

    public void setSommets(ArrayList<Client> sommets) {
        this.sommets = sommets;
    }
    public double cout() {
//        double result =0;
//        for (int i = 0; i < this.sommets.size() -1; i ++) {
//            Coordonnee a = sommets.get(i).getCoordonnee();
//            Coordonnee b = sommets.get(i + 1).getCoordonnee();
//            result += Math.sqrt(Math.pow(b.getY() - a.getY(), 2) + Math.pow(b.getX() - a.getY(), 2));
//        }
//        return result;
        return circuits.stream().mapToDouble(Circuit::cout).sum();
        }

    public int getCtotal() {
        return this.circuits.stream().mapToInt(Circuit::getC).max().orElse(-1);
    }

    public List<Client> getSommets() {
        return this.sommets;
    }

    public boolean isValid() {
        for (int i = 0; i < circuits.size(); i++) {
            if (circuits.get(i).getC() > 100) {
                System.out.println("Un circuit "+ i + " n'est pas valide: C= " +circuits.get(i).getC());
                return false;
            }
        }
        return true;
    }

    public static Graphe addCircuit(Graphe graphe, Circuit circuit) {
        List<Client> clientsCircuit = circuit.getSommets();
        List<Client> clients = graphe.getSommets();
//        System.out.println(circuit.getSommets());
        for (Client tmp : clientsCircuit) {
            if (tmp.getIdSommet() == 0) {
                continue;
            }
            clients.remove(tmp);
            if (clients.indexOf(tmp) != -1) {
                return null;
            }
        }
        int size = clients.size();

        clients.add(size, graphe.getDepot());
        clients.addAll(size+1, clientsCircuit);
        List<Client> clean = new ArrayList<>();
        int i = 0;
        for (Client tmp : clients) {
            if (tmp == null) {
                continue;
            }
            if (i > 0 && tmp.getIdSommet() == 0 && clean.get(i-1).getIdSommet() == 0) {
                continue;
            }
//            System.out.println(tmp.getIdSommet());
            clean.add(i, tmp);
            i++;
        }
//        System.out.println("i =" + i);
//        System.out.println();
//        Genetique.debugListSommet(clean);
        clean.size();
        return new Graphe(clean);
    }
}
