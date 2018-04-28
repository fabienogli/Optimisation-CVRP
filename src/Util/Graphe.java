package Util;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

public class Graphe {

    private  Depot depot;
    public static void main(String args[]) {
        Graph graph = new SingleGraph("Tutorial 1");
        graph.addNode("A" );
        graph.addNode("B" );
        graph.addNode("C" );
        graph.addEdge("AB", "A", "B");
        graph.addEdge("BC", "B", "C");
        graph.addEdge("CA", "C", "A");
        graph.display();
    }
}