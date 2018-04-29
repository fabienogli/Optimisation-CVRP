package Util;

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

    public Circuit() {
        this.arcs = new HashMap<>();
    }

    public Circuit(HashMap<Integer, Arc> arcs) {
        this.arcs = arcs;
    }

    public double cout() {
        double result = 0;
        Iterator it = this.arcs.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            result += ((Arc) pair.getValue()).cout();
            it.remove();
        }
        return result;
    }

    public double getLongueur() {
        return this.arcs.size();
    }
}
