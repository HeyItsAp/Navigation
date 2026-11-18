package heyitsap.AlgData;


import heyitsap.AlgData.Maps.DualMaps;
import heyitsap.AlgData.Navigation.Dijkstras;
import heyitsap.AlgData.Navigation.RecordPath;
import heyitsap.AlgData.Objects.Graph;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    static void main() {
        Graph graph = new Graph();
        try {
            DatasettManager.populateWithNodes(graph);
            DatasettManager.populateNodesWithEdges(graph);
            DatasettManager.populateNodesWithPOI(graph);
            System.out.println(graph);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            ArrayList<RecordPath> paths = Dijkstras.DijsktrasUntilXAmountPOI(graph, 205, 8, 5);
            DualMaps.showTwoPathLists(paths, paths);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
