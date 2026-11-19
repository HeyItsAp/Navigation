package heyitsap.AlgData.Navigation;

import heyitsap.AlgData.Objects.Graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import static heyitsap.AlgData.DatasettManager.hsplit;


public class ALT {
    public static int[] islandLandmarks = {22957, 31542};
    public static int[] nordenLandmarks;

    public static int[][] PreprossesingFromLandmarks(Graph graph, int nodeNr, int[] landmarks){
        File FromLandmarks = new File("Resources/FromLandmarks.txt");
        boolean check = false;
        int countLandmarks = landmarks.length;

        int[][] distancesFrom;
        if (FromLandmarks.exists()){
            try (BufferedReader br = new BufferedReader(new FileReader(FromLandmarks))){
                String countLine = br.readLine();
                countLine.trim();
                int nodeCount = Integer.parseInt(countLine);
                if (nodeCount != graph.getNodes().size()){
                    throw new IllegalArgumentException("Not coherent Landmark files");
                }

                distancesFrom = new int[nodeCount][countLandmarks];
                Arrays.fill(distancesFrom, Integer.MAX_VALUE);

                check = true;
                String line;
                while ((line = br.readLine()) != null){
                    line = line.trim();
                    if (line.isEmpty()) continue;

                    String[] splittedLine = hsplit(line, countLandmarks);
                    if (splittedLine.length-1 != countLandmarks){
                        throw new IllegalArgumentException("Not choerent Landmark files");
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
            } catch (IllegalArgumentException _){
                check = false;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (!check){
            int nodeCount = graph.getNodes().size();

            // Assumes node IDs range from 0 to nodeCount - 1
            distancesFrom = new int[nodeCount][countLandmarks];

            // Initialize all distances to infinity
            for (int[] row : distancesFrom) {
                Arrays.fill(row, Integer.MAX_VALUE);
            }

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
