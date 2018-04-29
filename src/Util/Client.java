package Util;

public class Client extends Sommet {

    private int quantite;

    public Client(int idClient, Coordonnee coordonnee, int quantite) {
        super(idClient, coordonnee);
        this.quantite = quantite;
    }

    public Client() {
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    @Override
    public String toString() {
        return "{Util.Client: " + super.toString() + '}';
    }

}
