package heyitsap.AlgData;


import heyitsap.AlgData.Map;
import heyitsap.AlgData.Maps.DualMaps;
import heyitsap.AlgData.Navigation.ALT;
import heyitsap.AlgData.Navigation.Dijkstras;
import heyitsap.AlgData.Objects.RecordPath;
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


        try {
            RecordPath path = Dijkstras.DijsktrasStartEnd(graph, 2001238, 2763740);
            ArrayList<RecordPath> paths1 = new ArrayList<>();
            paths1.add(path);

            RecordPath path2 = ALT.RegularALT(graph, 2001238, 2763740, ALT.islandLandmarks);
            ArrayList<RecordPath> paths2 = new ArrayList<>();
            paths2.add(path2);
            DualMaps.showTwoPathLists(paths1, paths2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
          /* POI codes:
            Bit     Betyr           Eksempler
            1       Stedsnavn       Trondheim, Moholt, …
            2       Bensinstasjon   Shell Herlev
            4       Ladestasjon     Ionity Klett
            8       Spisested       Restauranter, kafeer, puber
            16      Drikkested      Barer, puber, nattklubber
            32      Overnattings    Hoteller, moteller, gjestehu
             */

        /*
        try {
            ArrayList<RecordPath> Dij = Dijkstras.DijsktrasUntilXAmountPOI(graph, 2001238, 8, 4);
            ArrayList<RecordPath> ALT = heyitsap.AlgData.Navigation.ALT.ALTUntilXAmountPOI(graph, 2001238, 8,4,  heyitsap.AlgData.Navigation.ALT.islandLandmarks);
            DualMaps.showTwoPathLists(Dij, ALT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        */
    }
}
