package heyitsap.AlgData.Objects;

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
    public void addNodeWithPOI(int id, double latitude, double longitude, byte POICode, String POI){
        if (nodes.containsKey(id)){
            throw new IllegalArgumentException(id + ", Node already exists");
        }
        nodes.put(id, new Node(id, latitude, longitude, POICode, POI));
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

    public Map<Integer, Node> getNodes() {
        return nodes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Graph with ")
                .append(nodes.size())
                .append(" nodes\n");

        for (Node node : nodes.values()) {
            sb.append("Node ")
                    .append(node.id)
                    .append(" (lat: ")
                    .append(node.latitude)
                    .append(", lon: ")
                    .append(node.longitude)
                    .append(", POI: ")
                    .append(node.POI)
                    .append(", POI_Code: ")
                    .append(node.POICode)
                    .append(")\n");

            for (Edge edge : node.edges) {
                sb.append("  -> Node ")
                        .append(edge.to.id)
                        .append(" (weight: ")
                        .append(edge.length)
                        .append(")\n");
            }
        }

        return sb.toString();
    }
}
