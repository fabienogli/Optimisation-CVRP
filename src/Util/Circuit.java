package Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Circuit {

    public HashMap<Integer, Arc> getArcs() {
        return arcs;
    }

    public void setArcs(HashMap<Integer, Arc> arcs) {
        this.arcs = arcs;
    }

    private HashMap<Integer, Arc> arcs;

    public ArrayList<Client> getClients() {
        return clients;
    }

    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
    }

    private ArrayList<Client> clients = new ArrayList<>();

    public Circuit() {
        this.arcs = new HashMap<>();
    }

    public Circuit(HashMap<Integer, Arc> arcs) {
        this.arcs = arcs;
    }

    public double cout() {
        return this.arcs.values().stream().mapToDouble(Arc::cout).sum();
    }

    public double getLongueur() {
        return this.arcs.size();
    }
}
