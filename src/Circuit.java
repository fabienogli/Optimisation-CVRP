import java.util.Map;
import java.util.Objects;

public class Circuit {

    public Map<Integer, Sommet> sommets;

    public Circuit() {
    }

    public Circuit(Map<Integer, Sommet> sommets) {
        this.sommets = sommets;
    }

    public Map<Integer, Sommet> getSommets() {
        return sommets;
    }

    public void setSommets(Map<Integer, Sommet> sommets) {
        this.sommets = sommets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Circuit circuit = (Circuit) o;
        return Objects.equals(sommets, circuit.sommets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sommets.hashCode());
    }

    public double getLongueurItineraire() {
        this.sommets.values().stream().mapToDouble(Number::intValue).sum();
    }
}
