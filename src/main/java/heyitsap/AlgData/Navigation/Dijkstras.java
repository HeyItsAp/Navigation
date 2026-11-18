package heyitsap.AlgData.Navigation;

import heyitsap.AlgData.Objects.Edge;
import heyitsap.AlgData.Objects.Graph;
import heyitsap.AlgData.Objects.Node;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Dijkstras {
    public static int DijsktrasStartEnd(Graph graph, int start, int end){
        Node startNode = graph.getNode(start);
        Node endNode = graph.getNode(end);

        int length = graph.getNodes().size();

        // id of node, distance
        Map<Integer, Integer> distances = new HashMap<>();
        // id of node, node
        Map<Integer, Integer> previous = new HashMap<>();
        record QueueEntry(Node node, int distance){}

        PriorityQueue<QueueEntry> Pq = new PriorityQueue<>(
                Comparator.comparingInt(QueueEntry::distance)
        );

        distances.put(start, 0);
        Pq.add(new QueueEntry(startNode, 0));

        while (!Pq.isEmpty()){
            QueueEntry entry = Pq.poll();
            Node currentNode = entry.node;
            int currentDistance = entry.distance;

            // Ignore outdated entries
            if (currentDistance != distances.get(currentNode.id)) {
                continue;
            }

            // Destination reached
            if (currentNode.id == end) {
                return currentDistance;
            }

            // Explore
            for (Edge edge : currentNode.edges){
                int neigboringId = edge.to.id;
                int newDistance = currentDistance + edge.length;

                // All unvisted nodes have infinity distances:
                int oldDist = distances.getOrDefault(
                        neigboringId,Integer.MAX_VALUE
                );

                // Unvisted nodes have inf distance, so it updates. If the new calculated distance is smaller than old, replace it with new distance.
                if (newDistance < oldDist) {
                    distances.put(neigboringId, newDistance);
                    previous.put(neigboringId, currentNode.id);
                    Node neighbor = graph.getNode(neigboringId);
                    Pq.add(new QueueEntry(neighbor, newDistance));
                }
            }
        }
        return new RecordPath();
    };
    public static void DijsktrasUntilXAmountPOI(Graph graph, int start){

    };
}
