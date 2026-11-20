package heyitsap.AlgData;

import heyitsap.AlgData.Objects.Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Files have exact name as in shown in assignment paper.
 *      - Test files (Island) lie in resources folder.
 *      - The assigned files just lie in the resources folder.
 */
public class DatasettManager {
    static Path txtFileNodes = Path.of("src/main/resources/noder.txt");
    static Path txtFileKanter = Path.of("src/main/resources/kanter.txt");
    static Path txtFilePOI = Path.of("src/main/resources/interessepkt.txt");


    public static Graph populateWithNodes(Graph graph) throws IOException {
        // ===========================
        Path txtFile = txtFileNodes;
        // ===========================

        System.out.println("Reading nodes from " + txtFile);

        try (BufferedReader br = new BufferedReader(Files.newBufferedReader(txtFile))){
            br.readLine(); // To skip first line
            String line;
            while ((line = br.readLine()) != null){
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] splittedLine = hsplit(line, 3);
                int nodeId = Integer.parseInt(splittedLine[0]);
                double latitude = Double.parseDouble(splittedLine[1]);
                double longitude = Double.parseDouble(splittedLine[2]);

                graph.addNode(nodeId,latitude,longitude);
            }
            System.out.println("Reading Success!!");

        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return graph;
    }

    public static Graph populateNodesWithEdges(Graph graph) throws IOException {
        if (graph.getNodes().isEmpty()){
            throw new IllegalArgumentException("Graph does not have any nodes");
        }

        // ===========================
        Path txtFile = txtFileKanter;
        // ===========================

        System.out.println("Reading Edges from " + txtFile);
        try (BufferedReader br = new BufferedReader(Files.newBufferedReader(txtFile))){
            br.readLine(); // To skip first line

            String line;
            while ((line = br.readLine()) != null){
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] splittedLine = hsplit(line, 5);

                int from = Integer.parseInt(splittedLine[0]);
                int to = Integer.parseInt(splittedLine[1]);
                int drivingTime = Integer.parseInt(splittedLine[2]);
                int length = Integer.parseInt(splittedLine[3]);
                int speedLimit = Integer.parseInt(splittedLine[4]);
                graph.addEdge(from,to, drivingTime, length, speedLimit);
            }
            System.out.println("Reading Success!!");

        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return graph;
    }

    public static Graph populateNodesWithPOI(Graph graph) throws IOException {
        if (graph.getNodes().isEmpty()){
            throw new IllegalArgumentException("Graph does not have any nodes");
        }
        // ===========================
        Path txtFile = txtFilePOI;
        // ===========================

        System.out.println("Reading POI from " + txtFile);
        try (BufferedReader br = new BufferedReader(Files.newBufferedReader(txtFile))){
                br.readLine(); // To skip first line

                String line;
                while ((line = br.readLine()) != null){
                    line = line.trim();
                    if (line.isEmpty()) continue;

                    String[] splittedLine = hsplit(line, 3);

                    int nodeId = Integer.parseInt(splittedLine[0]);
                    byte codePOI = Byte.parseByte(splittedLine[1]);
                    String namePOI = splittedLine[2];
                    graph.getNode(nodeId).setPOI(codePOI, namePOI);
                }
                System.out.println("Reading Success!!");

            } catch (IOException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }
        return graph;
    }



    public static String[] hsplit (String line, int amount){
        // Unngå dødstreg String.split()
        // finner ordene i "linje", oppdelingen havner i "felt[]"
        // Gir "index out of bounds" hvis linja ikke har så mange ord, eller >10 ord.
        // unngår regex og unøding bruk av "new"
        // Alle ascii-verdier <= mellomrom er blanke/skilletegn
        // Alle ascii-verdier > mellomrom former ord.

        String[] felt = new String[amount]; // Max 10 felt i en linje
        int j = 0;
        int length = line.length();
        for (int i = 0; i < amount; ++i) {
            //Hopp over innledende blanke, finn starten på ordet
            while (j < length && line.charAt(j) <= ' ') ++j;
            int ordstart = j;
            //Finn slutten på ordet, hopp over ikke-blanke
            while (j < length && line.charAt(j) > ' ') ++j;
            felt[i] = line.substring(ordstart, j);
        }
        return felt;
    }
}


