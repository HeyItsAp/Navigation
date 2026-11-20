package heyitsap.AlgData;


import heyitsap.AlgData.Maps.DualMaps;
import heyitsap.AlgData.Navigation.ALT;
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        // Long ahh Path (island): 88494 -> 26684.
        try {
            RecordPath path = Dijkstras.DijsktrasStartEnd(graph, 88494, 26684);
            ArrayList<RecordPath> paths1 = new ArrayList<>();
            paths1.add(path);

            RecordPath path2 = ALT.RegularALT(graph, 88494, 26684, ALT.islandLandmarks);
            ArrayList<RecordPath> paths2 = new ArrayList<>();
            paths2.add(path2);
            DualMaps.showTwoPathLists(paths1, paths2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
