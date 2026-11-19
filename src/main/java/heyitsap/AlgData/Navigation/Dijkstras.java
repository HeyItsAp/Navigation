package heyitsap.AlgData.Navigation;

import heyitsap.AlgData.Objects.Edge;
import heyitsap.AlgData.Objects.Graph;
import heyitsap.AlgData.Objects.Node;

import java.util.*;

public class Dijkstras {

    record QueueEntry(Node node, int distance) {}
    /* Underdeveloped / Forgetton*/
    public static void DijsktrasFromLandsmarks(Graph graph, int start, int landmarkIndex, int[][] distancesFrom) {
        Node startNode = graph.getNode(start);
        if (startNode == null) {
            throw new IllegalArgumentException(
                    "Landmark does not exist: " + start
            );
        }

        PriorityQueue<QueueEntry> Pq = new PriorityQueue<>(Comparator.comparingInt(QueueEntry::distance));

        // Distance from landmark to itself = 0
        distancesFrom[start][landmarkIndex] = 0;
        Pq.add(new QueueEntry(startNode, 0));

        while (!Pq.isEmpty()) {
            QueueEntry entry = Pq.poll();

            Node currentNode = entry.node;
            int nodeId = currentNode.id;
            int currentDistance = entry.distance;

            // Skip outdated queue entries
            if (currentDistance !=
                    distancesFrom[nodeId][landmarkIndex]) {
                continue;
            }

            // Gotta do this till all nodes explore, so no if to stop.

            // Explore
            for (Edge edge : currentNode.edges) {
                int neigboringId = edge.to.id;
                int newDistance = currentDistance + edge.length;

                // All unvisted nodes have infinity distances:
                int oldDist = distancesFrom[neigboringId][landmarkIndex];

                // Unvisted nodes have inf distance, so it updates. If the new calculated distance is smaller than old, replace it with new distance.
                if (newDistance < oldDist) {
                    distancesFrom[neigboringId][landmarkIndex] = newDistance;
                    Node neighbor = graph.getNode(neigboringId);
                    Pq.add(new QueueEntry(neighbor, newDistance));
                }
            }
        }
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
    public static ArrayList<RecordPath> DijsktrasUntilXAmountPOI(Graph graph, int start, int POICode, int amount) {
        Node startNode = graph.getNode(start);

        int length = graph.getNodes().size();

        // id of node, distance
        Map<Integer, Integer> distances = new HashMap<>();
        // id of node, node
        Map<Integer, Integer> previous = new HashMap<>();

        PriorityQueue<QueueEntry> Pq = new PriorityQueue<>(Comparator.comparingInt(QueueEntry::distance));
        ArrayList<RecordPath> listOfPaths = new ArrayList<>();
        int processednodes = 0;

        distances.put(start, 0);
        Pq.add(new QueueEntry(startNode, 0));

        while (!Pq.isEmpty()) {
            QueueEntry entry = Pq.poll();
            Node currentNode = entry.node();
            int currentDistance = entry.distance();

            // Ignore outdated entries
            if (currentDistance != distances.get(currentNode.id)) {
                continue;
            }

            processednodes++;

            // Destination reached
            if (listOfPaths.size() == amount){
                return listOfPaths;
            } else {
                if ((currentNode.POICode & POICode) == POICode) {
                    listOfPaths.add(new RecordPath(previous,start,currentNode.id,graph," ",processednodes));
                }
            }


            // Explore
            for (Edge edge : currentNode.edges) {
                int neigboringId = edge.to.id;
                int newDistance = currentDistance + edge.length;

                // All unvisted nodes have infinity distances:
                int oldDist = distances.getOrDefault(neigboringId, Integer.MAX_VALUE);

                // Unvisted nodes have inf distance, so it updates. If the new calculated distance is smaller than old, replace it with new distance.
                if (newDistance < oldDist) {
                    distances.put(neigboringId, newDistance);
                    previous.put(neigboringId, currentNode.id);
                    Node neighbor = graph.getNode(neigboringId);
                    Pq.add(new QueueEntry(neighbor, newDistance));
                }
            }
        }
        return null;
    }
}
