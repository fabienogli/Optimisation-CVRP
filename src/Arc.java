
public class Arc {

    public static int start = 0;
    public static int end = 1;

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

    public double cout() {
        double result = longueur();
        return result;
    }

    private double longueur() {
        double result = 0;
        return result;
    }
}
