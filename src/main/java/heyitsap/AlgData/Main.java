package heyitsap.AlgData;


import heyitsap.AlgData.Preprocessing.Graph;
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

    }
}
