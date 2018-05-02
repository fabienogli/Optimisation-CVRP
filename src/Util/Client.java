package Util;

public class Client extends Sommet implements Cloneable{

    private int quantite;

    public Client(int idClient, Coordonnee coordonnee, int quantite) {
        super(idClient, coordonnee);
        this.quantite = quantite;
    }

    public Client() {
    }

    @Override
    public Client clone() throws CloneNotSupportedException {
        return (Client)super.clone();
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
