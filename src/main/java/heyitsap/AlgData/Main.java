package heyitsap.AlgData;


import heyitsap.AlgData.Navigation.Dijkstras;
import heyitsap.AlgData.Objects.Graph;
import heyitsap.AlgData.Preprocessing.Preprosessing;

import java.io.IOException;

public class Main {
    static void main() {
        Graph graph = new Graph();
        try {
            Preprosessing.populateWithNodes(graph);
            Preprosessing.populateNodesWithEdges(graph);
            Preprosessing.populateNodesWithPOI(graph);
            System.out.println(graph);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            System.out.println(Dijkstras.DijsktrasUntilXAmountPOI(graph, 50000, 8, 5).getFirst());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
