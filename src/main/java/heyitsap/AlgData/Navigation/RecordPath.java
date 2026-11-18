package heyitsap.AlgData.Navigation;

import heyitsap.AlgData.Objects.Edge;
import heyitsap.AlgData.Objects.Graph;
import heyitsap.AlgData.Objects.Node;

import java.util.*;

public class RecordPath {
    public Graph graph = new Graph();
    public String name;

    // To create a weighted path of a graph; A -> B -> C -> D -> E -> F -> G
    public RecordPath(Map<Integer, Integer> previous, int start, int end, Graph graph, String name){
        this.name = name;
        List<Integer> linkedPath = reconstructPathEndToStart(previous, start, end);
        ListIterator<Integer> iterator = linkedPath.listIterator();
        while (iterator.hasNext()){
            int current = iterator.next();
            Node recraetedNode = graph.getNode(current);
            graph.addNodeWithPOI(recraetedNode.id, recraetedNode.latitude, recraetedNode.longitude, recraetedNode.POICode, recraetedNode.POI);

            if (iterator.hasNext()){
                int next = iterator.next();
                iterator.previous();
                Edge dedicatedEdge = graph.getNode(current).edges.stream().filter(e -> e.to.id == next).findFirst().orElse(null);
                graph.addEdge(dedicatedEdge.from.id, dedicatedEdge.to.id, dedicatedEdge.drivingTime, dedicatedEdge.length, dedicatedEdge.speedLimit);
            }
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
