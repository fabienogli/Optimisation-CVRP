package Util;

import java.util.Objects;

abstract class Sommet {
    private int idSommet;
    private Coordonnee coordonnee;

    public int getSommetAdjacent1() {
        return sommetAdjacent1;
    }

    public void setSommetAdjacent1(int sommetAdjacent1) {
        this.sommetAdjacent1 = sommetAdjacent1;
    }

    public int getSommetAdjacent2() {
        return sommetAdjacent2;
    }

    public void setSommetAdjacent2(int sommetAdjacent2) {
        this.sommetAdjacent2 = sommetAdjacent2;
    }

    public int sommetAdjacent1;
    public int sommetAdjacent2;

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
        if (this == o) {
            return true;
        }
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
