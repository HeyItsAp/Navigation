package heyitsap.AlgData;


import heyitsap.AlgData.Navigation.ALT;
import heyitsap.AlgData.Objects.Graph;

import java.io.IOException;

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
            //ArrayList<RecordPath> paths = Dijkstras.DijsktrasUntilXAmountPOI(graph, 205, 8, 5);
            //DualMaps.showTwoPathLists(paths, paths);
            ALT.PreprossesingLandmarks(graph,333, ALT.islandLandmarks, true);
            ALT.PreprossesingLandmarks(graph.reverseGraph(),333, ALT.islandLandmarks, false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
