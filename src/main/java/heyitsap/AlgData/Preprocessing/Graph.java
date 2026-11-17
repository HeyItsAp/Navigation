package heyitsap.AlgData.Preprocessing;

import java.util.HashMap;
import java.util.Map;

public class Graph {
    private final Map<Integer, Node> nodes = new HashMap<>();

    public void addNode(int id, double latitude, double longitude){
        if (nodes.containsKey(id)){
            throw new IllegalArgumentException(id + ", Node already exists");
        }
        nodes.put(id, new Node(id, latitude, longitude));
    }
    public void addEdge(int from, int to, int drivingTime, int length, int speedLimit){
        if (drivingTime < 0) {
            throw new IllegalArgumentException("Negative weight");
        }

        Node start = nodes.get(from);
        Node end = nodes.get(to);
        if (start == null || !nodes.containsKey(to)) {
            throw new IllegalArgumentException("Unknown node");
        }
        start.edges.add(new Edge(start,end,drivingTime,length,speedLimit));
    }

    public Node getNode(int id) {
        return nodes.get(id);
    }

    public int size() {
        return nodes.size();
    }
}
