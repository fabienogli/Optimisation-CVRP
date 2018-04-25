public class Depot extends Sommet {

    public static int DEPOT = 0;

    public Depot() {
    }

    public Depot(Coordonnee coordonnee) {
        super(DEPOT, coordonnee);
    }

    @Override
    public String toString() {
        return "Depot{" + super.toString() + "}";
    }
}
