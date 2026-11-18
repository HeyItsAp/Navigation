package heyitsap.AlgData;


import heyitsap.AlgData.Navigation.Dijkstras;
import heyitsap.AlgData.Navigation.RecordPath;
import heyitsap.AlgData.Objects.Graph;
import heyitsap.AlgData.Preprocessing.Preprosessing;

import java.io.IOException;
import java.util.ArrayList;

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
            ArrayList<RecordPath> paths = Dijkstras.DijsktrasUntilXAmountPOI(graph, 50000, 8, 5);
            Map.showPath(paths.getFirst());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
