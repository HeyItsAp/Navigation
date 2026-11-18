package heyitsap.AlgData.Navigation;

import heyitsap.AlgData.Objects.Edge;
import heyitsap.AlgData.Objects.Graph;
import heyitsap.AlgData.Objects.Node;

import java.util.*;

public class RecordPath {
    public Graph graph;
    public String name;
    public int start;
    public int end;
    public final List<Integer> nodeIds;
    public int processedNodes;

    // To create a weighted path of a graph; A -> B -> C -> D -> E -> F -> G
    public RecordPath(Map<Integer, Integer> previous, int start, int end, Graph graph, String name, int processedNodes){
        this.name = name;
        this.graph = new Graph();
        this.start = start;
        this.end = end;
        List<Integer> linkedPath = reconstructPathEndToStart(previous, start, end);
        this.nodeIds = linkedPath;
        this.processedNodes = processedNodes;

        ListIterator<Integer> NodeIterator = linkedPath.listIterator();
        Integer previousid = null;
        while (NodeIterator.hasNext()){
            int current = NodeIterator.next();
            Node recraetedNode = graph.getNode(current);
            this.graph.addNodeWithPOI(recraetedNode.id, recraetedNode.latitude, recraetedNode.longitude, recraetedNode.POICode, recraetedNode.POI);

            if (previousid != null){
                Node from = graph.getNode(previousid);
                Edge dedicatedEdge = from.edges.stream().filter(e -> e.to.id == current).findFirst().orElse(null);
                this.graph.addEdge(dedicatedEdge.from.id, dedicatedEdge.to.id, dedicatedEdge.drivingTime, dedicatedEdge.length, dedicatedEdge.speedLimit);
            }
            previousid = current;
        }
    }
    public static List<Integer> reconstructPathEndToStart(
            Map<Integer, Integer> previous,
            int start,
            int end) {

        LinkedList<Integer> path = new LinkedList<>();

        int current = end;

        while (current != start) {
            path.addFirst(current);

            Integer predecessor = previous.get(current);

            if (predecessor == null) {
                return Collections.emptyList();
            }

            current = predecessor;
        }

        path.addFirst(start);

        return path;
    }
}
