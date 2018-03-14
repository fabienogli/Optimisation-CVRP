import java.util.Objects;

abstract class Sommet {
    private int idSommet;
    private Coordonnee coordonnee;

    public Sommet() {
    }

    public Sommet(int idClient, Coordonnee coordonnee) {
        this.idSommet = idClient;
        this.coordonnee = coordonnee;
    }

    public int getIdSommet() {
        return idSommet;
    }

    public void setIdSommet(int idSommet) {
        this.idSommet = idSommet;
    }

    public Coordonnee getCoordonnee() {
        return coordonnee;
    }

    public void setCoordonnee(Coordonnee coordonnee) {
        this.coordonnee = coordonnee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){ return true;}
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Sommet sommet = (Sommet) o;
        return idSommet == sommet.idSommet &&
                Objects.equals(coordonnee, sommet.coordonnee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSommet, coordonnee.hashCode());
    }

    @Override
    public String toString() {
        return "idSommet=" + idSommet +
                ", coordonnee=" + coordonnee.toString();
    }
}
