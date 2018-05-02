package Util;

public class Arc {

    public static int start = 0;
    public static int end = 1;

    public int getArcAdjacent1() {
        return arcAdjacent1;
    }

    public void setArcAdjacent1(int arcAdjacent1) {
        this.arcAdjacent1 = arcAdjacent1;
    }

    public int getArcAdjacent2() {
        return arcAdjacent2;
    }

    public void setArcAdjacent2(int arcAdjacent2) {
        this.arcAdjacent2 = arcAdjacent2;
    }

    private int arcAdjacent1;
    private int arcAdjacent2;

    private Sommet[] sommets;

    private boolean oriented;

    public Arc() {
        this.oriented = false;
        this.sommets = new Sommet[2];
    }

    public Arc(Sommet sommet1, Sommet sommet2) {
        this();
        this.sommets[start] = sommet1;
        this.sommets[end] = sommet2;
    }

    public Arc(Sommet[] sommets) {
        this.setSommets(sommets);
    }

    public Sommet[] getSommets() {
        return sommets;
    }

    public void setSommets(Sommet[] sommets) {
        this.sommets = sommets;
    }
    public void setSommet1(Sommet sommet1) {
        this.sommets[0] = sommet1;
    }
    public void setSommet2(Sommet sommet2) {
        this.sommets[1] = sommet2;
    }

    public double cout() {
        double result = distance();
        return result;
    }

    private double distance() {
        if (this.sommets != null && this.sommets[start] != null && this.sommets[end] != null) {
            return Coordonnee.distance(this.sommets[start].getCoordonnee(), this.sommets[end].getCoordonnee());
        }
        return 0;
    }

    @Override
    public String toString() {
        return this.sommets[start].toString() + "----" + this.sommets[end].toString();
    }

    @Override
    public boolean equals(Object obj) {
        Arc arc = (Arc)obj;
        return this.sommets[start] == arc.getSommets()[start] && this.sommets[end] == arc.getSommets()[end];
    }
}
