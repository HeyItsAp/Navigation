package heyitsap.AlgData.Navigation;

import heyitsap.AlgData.Objects.Graph;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static heyitsap.AlgData.DatasettManager.hsplit;


public class ALT {
    public static int[] islandLandmarks = {22957, 31542};
    public static int[] nordenLandmarks;
    public static Path outputFolder = Path.of("Resources");
    public static File FromLandmarks = new File("Resources/FromLandmarks.txt");


    public static int[][] PreprossesingFromLandmarks(Graph graph, int nodeNr, int[] landmarks){
        boolean check = false;
        int countLandmarks = landmarks.length;

        int[][] distancesFrom;
        if (FromLandmarks.exists()){
            try (BufferedReader br = new BufferedReader(new FileReader(FromLandmarks))){
                System.out.println("Reading throught From landsmarks file");
                String countLine = br.readLine();
                if (countLine == null){
                    throw new IllegalArgumentException("File exists but might be empty");
                }
                countLine.trim();
                int nodeCount = Integer.parseInt(countLine);
                if (nodeCount != graph.getNodes().size()){
                    throw new IllegalArgumentException("Not coherent Landmark files (1, Nodecount): Files: " + nodeCount + ", Graph: " + graph.getNodes().size());
                }

                distancesFrom = new int[nodeCount][countLandmarks];
                for (int[] row : distancesFrom) {
                    Arrays.fill(row, Integer.MAX_VALUE);
                }

                check = true;
                String line;
                System.out.println("Looping through each line:");
                while ((line = br.readLine()) != null){
                    line = line.trim();
                    if (line.isEmpty()) continue;

                    String[] splittedLine = hsplit(line, countLandmarks);
                    if (splittedLine.length-1 != countLandmarks){
                        throw new IllegalArgumentException("Not coherent Landmark files (1, landmark count): Files: " + (splittedLine.length-1) + ", Input: " + countLandmarks);

                    }

                    int nodeId = Integer.parseInt(splittedLine[0]);

                    if (nodeId < 0 || nodeId >= nodeCount) {
                        throw new IllegalArgumentException("Node ID out of range");
                    }

                    for (int i = 0; i < countLandmarks; i++) {
                        distancesFrom[nodeId][i] = Integer.parseInt(splittedLine[i + 1]);
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
            System.out.println("No file with content, calculating new distances...");
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
                        "Processing landmark: " + landmarkId
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
                File FromLandmarks = new File("Resources/FromLandmarks.txt");

                try (BufferedWriter bw = new BufferedWriter(
                        new FileWriter(FromLandmarks))) {

                    // First line: number of nodes
                    bw.write(Integer.toString(distancesFrom.length));
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

    public static int[][] PreprossesingToLandmarks(){
        return null;
    }




    public static RecordPath ALTUntilXAmountPOI(Graph graph, int start, int POICode, int amount){
        return null;
    }
}
