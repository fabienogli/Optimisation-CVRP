package Util;

import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Circuit {

    private ArrayList<Client> sommets;
    private HashMap<Integer, Arc> arcs;

    public HashMap<Integer, Arc> getArcs() {
        return arcs;
    }

    public void setArcs(HashMap<Integer, Arc> arcs) {
        this.arcs = arcs;
        this.arcs.forEach((key, value) -> this.sommets.add((Client) value.getSommets()[Arc.start]));
    }


    public ArrayList<Client> getClients() {
        return clients;
    }

    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
    }

    private ArrayList<Client> clients = new ArrayList<>();


    public Circuit() {
        this.arcs = new HashMap<>();
        this.sommets = new ArrayList<>();
    }

    public Circuit(HashMap<Integer, Arc> arcs) {
        setArcs(arcs);
    }

    public double cout() {
        return this.arcs.values().stream().mapToDouble(Arc::cout).sum();
    }

    public double getLongueur() {
        return this.arcs.size();
    }

    public ArrayList<Client> getSommets() {
        return this.sommets;
    }

    public int getC() {
        return this.sommets.stream().mapToInt(Client::getQuantite).sum();
    }

    @Override
    public boolean equals(Object obj) {
        Circuit circuit = (Circuit) obj;
        Map<Integer, Arc> arcs = circuit.getArcs();
        if (arcs.size() != this.arcs.size()) {
            System.out.println("Ils n'y pas le même nombre d'arc");
            return false;
        }
        for (int i = 0; i < this.arcs.size(); i++) {
            if (! this.arcs.get(i).equals(arcs.get(i))) {
                System.out.println("Ces deux arcs à l'indice: "+ i + " sont différents");
                System.out.println(arcs.get(i));
                System.out.println(this.arcs.get(i));
                return false;
            }
        }
        return true;
    }
}
