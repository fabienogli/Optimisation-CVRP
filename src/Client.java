import java.util.Objects;

public class Client {

    private int idClient;
    private Coordonnee coordonnee;
    private int quantite;

    public Client(int idClient, Coordonnee coordonnee, int quantite) {
        this.idClient = idClient;
        this.coordonnee = coordonnee;
        this.quantite = quantite;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public Coordonnee getCoordonnee() {
        return coordonnee;
    }

    public void setCoordonnee(Coordonnee coordonnee) {
        this.coordonnee = coordonnee;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){ return true;}
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Client client = (Client) o;
        return idClient == client.idClient &&
                quantite == client.quantite &&
                Objects.equals(coordonnee, client.coordonnee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idClient, coordonnee.hashCode(), quantite);
    }
}
