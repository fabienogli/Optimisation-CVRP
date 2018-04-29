package Util;

public class Depot extends Client {

    public static int DEPOT = 0;

    public Depot() {
    }

    public Depot(Coordonnee coordonnee) {
        super(DEPOT, coordonnee);
    }

    @Override
    public String toString() {
        return "Util.Depot{" + super.toString() + "}";
    }
}
