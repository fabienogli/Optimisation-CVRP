public class Depot extends Sommet {

    public Depot(Coordonnee coordonnee) {
        super(0, coordonnee);
    }

    @Override
    public String toString() {
        return "Depot{" + super.toString() + "}";
    }
}
