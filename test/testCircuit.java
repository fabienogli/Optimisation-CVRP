import Util.Circuit;
import Util.Graphe;
import org.graphstream.graph.Graph;

import java.util.ArrayList;

public class testCircuit {

    public static void main(String[] args) {
        ArrayList<Circuit> circuits = Graphe.generateRandomGraph("data01");
        Graph graph = Graphe.adaptGraphe(circuits);
        graph.display();
    }
}
