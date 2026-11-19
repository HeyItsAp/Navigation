package heyitsap.AlgData.Navigation;

import heyitsap.AlgData.Objects.Edge;
import heyitsap.AlgData.Objects.Graph;
import heyitsap.AlgData.Objects.Node;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static heyitsap.AlgData.DatasettManager.hsplit;

/**
 * For best calculation and shi remove everything in both txt files. So that it can properly write and stuff. Unexpected lines might bug the system.
 */


public class ALT {
    public static int[] islandLandmarks = {22957, 31542};
    public static int[] nordenLandmarks;
    public static Path outputFolder = Path.of("Resources");
    public static File FromLandmarks = new File("Resources/FromLandmarks.txt");
    public static File ToLandmarks = new File("Resources/ToLandmarks.txt");

    // Node, g(v), g(v) + h(v)
    record QueueEntry(Node node, int distance, int priority){}

    /* if From is true; read and write to FromLandsmarks. If not; read and write to ToLandsmarks.txt */
    public static int[][] PreprossesingLandmarks(Graph graph, int nodeNr, int[] landmarks, boolean From){
        boolean check = false;
        File dedicatedFile = From ? FromLandmarks : ToLandmarks;
        int countLandmarks = landmarks.length;

        int[][] distancesFrom;
        if (dedicatedFile.exists()){
            try (BufferedReader br = new BufferedReader(new FileReader(dedicatedFile))){
                System.out.println("== Reading throught From landsmarks file: " + (From ? "From Landmark Distances" : "To Landmark Distances ==") );
                String infoLine = br.readLine();
                if (infoLine == null){
                    throw new IllegalArgumentException("File exists but might be empty");
                }
                infoLine.trim();
                String[] splittedInfoLine = hsplit(infoLine, 2);
                int nodeCount = Integer.parseInt(splittedInfoLine[0]);
                int startNodeId = Integer.parseInt(splittedInfoLine[1]);
                if (startNodeId != nodeNr){
                    throw new IllegalArgumentException("Not coherent Landmark files (1, startNode): Files: " + startNodeId + ", Input: " + nodeNr);
                }

                distancesFrom = new int[nodeCount][countLandmarks];
                for (int[] row : distancesFrom) {
                    Arrays.fill(row, Integer.MAX_VALUE);
                }

                check = true;
                String line;
                System.out.println("Looping through each line");
                while ((line = br.readLine()) != null){
                    line = line.trim();
                    if (line.isEmpty()) continue;

                    String[] splittedLine = hsplit(line, countLandmarks);
                    if (splittedLine.length != countLandmarks){
                        throw new IllegalArgumentException("Not coherent Landmark files (3, landmark count): Files: " + (splittedLine.length-1) + ", Input: " + countLandmarks);

                    }

                    int nodeId = Integer.parseInt(splittedLine[0]);

                    if (nodeId < 0 || nodeId >= nodeCount) {
                        throw new IllegalArgumentException("Node ID out of range");
                    }

                    for (int i = 0; i < countLandmarks; i++) {
                        distancesFrom[nodeId][i] = Integer.parseInt(splittedLine[i]);
                    }
                }
                System.out.println("Reading Success!!");
                return distancesFrom;
            } catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
                check = false;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (!check){
            // Calculating Shi
            System.out.println("= No file with content, calculating new distances... = ");
            int nodeCount = graph.getNodes().size();

            // Assumes node IDs range from 0 to nodeCount - 1
            distancesFrom = new int[nodeCount][countLandmarks];

            // Initialize all distances to infinity
            for (int[] row : distancesFrom) {
                Arrays.fill(row, Integer.MAX_VALUE);
            }

            System.out.println("Running Dijkstra...");

            // Run Dijkstra once for each landmark
            for (int i = 0; i < countLandmarks; i++) {
                int landmarkId = landmarks[i];

                System.out.println(
                        "Processing landmark nr: " + landmarkId
                );

                Dijkstras.DijsktrasFromLandsmarks(
                        graph, landmarkId, i, distancesFrom
                );
            }
            System.out.println("Finished Dijkstra");
            System.out.println("Attempting to write...");

            // Writing to file.
            try {
                Files.createDirectories(outputFolder);

                try (BufferedWriter bw = new BufferedWriter(
                        new FileWriter(dedicatedFile))) {

                    // First line: number of nodes
                    bw.write(distancesFrom.length + " " + nodeNr);
                    bw.newLine();

                    // Write each node and its landmark distances
                    System.out.println("Writing to write nodes...");
                    for (int nodeId = 0; nodeId < distancesFrom.length; nodeId++) {


                        StringBuilder sb = new StringBuilder();
                        sb.append(nodeId);

                        for (int landmark = 0;
                             landmark < distancesFrom[nodeId].length;
                             landmark++) {

                            sb.append(" ")
                                    .append(distancesFrom[nodeId][landmark]);
                        }

                        bw.write(sb.toString());
                        bw.newLine();
                    }
                }

                System.out.println("Distances from landmark file saved");

            } catch (IOException e) {
                throw new RuntimeException(
                        "Failed to write landmark file", e
                );
            }


            return distancesFrom;
        }
        return null;

    }

    private record POIBound(int[] minFrom, int[] maxTo){ }
    private static POIBound calculatePOIBounds(Graph graph, int POICode, int[][] distancesFrom, int[][] distancesTo) {
        int landmarkCount = distancesFrom[0].length;
        int INF = Integer.MAX_VALUE;

        int[] minFrom = new int[landmarkCount];
        int[] maxTo = new int[landmarkCount];

        Arrays.fill(minFrom, INF);

        for (Node node : graph.getNodes().values()) {
            if (!matchesPOI(node, POICode)) {
                continue;
            }

            for (int i = 0; i < landmarkCount; i++) {
                int from = distancesFrom[node.id][i];
                int to = distancesTo[node.id][i];

                if (from != INF) {
                    minFrom[i] = Math.min(minFrom[i], from);
                }

                if (to == INF) {
                    maxTo[i] = INF;
                } else if (maxTo[i] != INF) {
                    maxTo[i] = Math.max(maxTo[i], to);
                }
            }
        }
        return new POIBound(minFrom, maxTo);
    }

    private static boolean matchesPOI(Node node, int poiCode) {
        return (node.POICode & poiCode) == 0;
    }


    private static int POIHeuristic(int nodeId, int[][] distancesFrom, int[][] distancesTo, POIBound bounds) {
        int h = 0;
        int INF = Integer.MAX_VALUE;

        for (int i = 0; i < bounds.minFrom.length; i++) {
            int fromNode = distancesFrom[nodeId][i];
            int toNode = distancesTo[nodeId][i];

            // Lower bound using FROM landmarks
            if (fromNode != INF && bounds.minFrom[i] != INF) {
                int estimate = bounds.minFrom[i] - fromNode;
                h = Math.max(h, estimate);
            }

            // Lower bound using TO landmarks
            if (toNode != INF && bounds.maxTo[i] != INF) {
                int estimate = toNode - bounds.maxTo[i];
                h = Math.max(h, estimate);
            }
        }
        return h;
    }


    public static ArrayList<RecordPath> ALTUntilXAmountPOI(Graph graph, int start, int POICode, int amount, int[] landmarks){
        Node startNode = graph.getNode(start);
        ArrayList<RecordPath> listOfPaths = new ArrayList<>();
        if (amount <= 0) return listOfPaths;
        int processednodes = 0;

        // id of node, node
        Map<Integer, Integer> previous = new HashMap<>();
        // Open list:
        PriorityQueue<QueueEntry> Pq = new PriorityQueue<QueueEntry>(Comparator.comparingInt(QueueEntry::priority));
        // Closed list: id of node, distance
        Map<Integer, Integer> distances = new HashMap<>();

        // Get preprossesed tables
        int[][] distancesFrom = PreprossesingLandmarks(graph, start, landmarks, true);
        int[][] distancesTo = PreprossesingLandmarks(graph.reverseGraph(), start, landmarks, false);


        // Calculate heuristic bounds for this POI category
        POIBound bounds = calculatePOIBounds(
                graph, POICode, distancesFrom, distancesTo
        );

        distances.put(start, 0);
        int startH = POIHeuristic(
                start, distancesFrom, distancesTo, bounds
        );

        Pq.offer(new QueueEntry(startNode, 0, startH));

        while (!Pq.isEmpty()){
            QueueEntry entry = Pq.poll();
            Node currentNode = entry.node();
            int currentLength = entry.distance();

            // Skip outdated entries
            if (currentLength != distances.getOrDefault(currentNode.id, Integer.MAX_VALUE)) {
                continue;
            }
            processednodes++;

            // Check whether this is a matching POI
            if (matchesPOI(currentNode, POICode)) {
                RecordPath path = new RecordPath(previous, start, currentNode.id,graph, "ALT", processednodes);
                listOfPaths.add(path);

                // Stop after finding enough POIs
                if (listOfPaths.size() >= amount) {
                    break;
                }
            }

            // Explore neighbors
            for (Edge edge : currentNode.edges) {
                int neighborId = edge.to.id;
                int weight= edge.length;

                if (weight < 0) {
                    throw new IllegalArgumentException("Negative edge weight");
                }

                int newDist = currentLength + weight;
                int oldDist = distances.getOrDefault(neighborId, Integer.MAX_VALUE);

                if (newDist < oldDist) {
                    distances.put(neighborId, newDist);
                    previous.put(neighborId, currentNode.id);
                    Node neighbor = graph.getNode(neighborId);

                    // Calculate heuristic for this neighbor
                    int h = POIHeuristic(neighborId, distancesFrom, distancesTo, bounds);

                    // ALT priority
                    int priority = newDist + h;

                    Pq.offer(new QueueEntry(
                            neighbor,
                            newDist,
                            priority
                    ));
                }
            }
        }
        return listOfPaths;
    }


    public static int EstimatedDistance(int nodeId, int targetId, int[][] from, int[][] to){
        // prioirty thing f
        int h = 0;
        for (int i = 0; i < from[nodeId].length; i++) {
            int landmarkToNode = from[nodeId][i];
            int landmarkToTarget = from[targetId][i];

            int nodeToLandmark = to[nodeId][i];
            int targetToLandmark = to[targetId][i];

            // Ignore unreachable distances
            if (landmarkToNode != Integer.MAX_VALUE &&
                    landmarkToTarget != Integer.MAX_VALUE) {

                h = Math.max(h,
                        landmarkToTarget - landmarkToNode);
            }

            if (nodeToLandmark != Integer.MAX_VALUE &&
                    targetToLandmark != Integer.MAX_VALUE) {

                h = Math.max(h,
                        (nodeToLandmark - targetToLandmark));
            }
        }
        return h;
    }
    public static RecordPath RegularALT(Graph graph,int start, int end, int[] landmarks) {
        Node startNode = graph.getNode(start);
        Node endNode = graph.getNode(end);

        if (startNode == null || endNode == null) {
            throw new IllegalArgumentException("Invalid node ID");
        }

        // id of node, distance
        Map<Integer, Integer> distances = new HashMap<>();
        // id of node, node
        Map<Integer, Integer> previous = new HashMap<>();

        // ALT sorts by g(v) + h(v)
        PriorityQueue<QueueEntry> pq = new PriorityQueue<>(
                Comparator.comparingInt(QueueEntry::priority)
        );

        int[][] distancesFrom = PreprossesingLandmarks(graph,start,landmarks,true);
        int[][] distancesTo = PreprossesingLandmarks(graph.reverseGraph(),start,landmarks,false);
        int processednodes = 0;

        distances.put(start, 0);

        int startHeuristic = EstimatedDistance(start, end, distancesFrom, distancesTo);

        pq.offer(new QueueEntry(startNode, 0, startHeuristic));

        while (!pq.isEmpty()) {
            QueueEntry entry = pq.poll();
            Node currentNode = entry.node();
            int currentDist = entry.distance();

            // Ignore outdated queue entries
            if (currentDist != distances.getOrDefault(currentNode.id, Integer.MAX_VALUE)) {
                continue;
            }

            processednodes++;

            // Destination found
            if (currentNode.id == end) {
                return new RecordPath(previous, start, end, graph, "ALT", processednodes);
            }

            // Explore outgoing edges
            for (Edge edge : currentNode.edges) {
                int neighborId = edge.to.id;
                int length = edge.length;

                if (length < 0) {
                    throw new IllegalArgumentException("Negative edge weight");
                }

                if (currentDist > Integer.MAX_VALUE - length) {
                    throw new ArithmeticException("Distance overflow");
                }

                int newDist = currentDist + length;

                int oldDist = distances.getOrDefault(
                        neighborId, Integer.MAX_VALUE
                );

                // Found a shorter path
                if (newDist < oldDist) {
                    distances.put(neighborId, newDist);
                    previous.put(neighborId, currentNode.id);

                    Node neighbor = graph.getNode(neighborId);

                    // Calculate ALT heuristic
                    int h = EstimatedDistance(neighborId, end, distancesFrom, distancesTo);

                    // f(v) = g(v) + h(v)
                    int priority = newDist + h;

                    pq.offer(new QueueEntry(neighbor, newDist, priority));
                }
            }
        }

        // Destination unreachable
        return null;
    }
}
