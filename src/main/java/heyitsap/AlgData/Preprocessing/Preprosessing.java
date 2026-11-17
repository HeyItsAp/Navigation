package heyitsap.AlgData.Preprocessing;

import heyitsap.AlgData.Main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class Preprosessing {
    public static Graph populateWithNodes(Graph graph) throws IOException {
        String txtFile = "/island/noder.text";
        System.out.println("Reading nodes from " + txtFile);
        Path outputFolder = Path.of("Resources");
        Files.createDirectories(outputFolder);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream(txtFile)))){
            br.readLine(); // To skip first line

            String line;
            while ((line = br.readLine()) != null){
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] splittedLine = hsplit(line, 3);
                if (splittedLine.length != 3){
                    continue;
                }

                int nodeId = Integer.parseInt(splittedLine[0]);
                double latitude = Integer.parseInt(splittedLine[1]);
                double longitude = Integer.parseInt(splittedLine[2]);

                graph.addNode(nodeId,latitude,longitude);
            }
            System.out.println("Reading Success!!");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return graph;
    }

    public static Graph populateNodesWithEdges(Graph graph) throws IOException {
        if (graph.getNodes().isEmpty()){
            throw new IllegalArgumentException("Graph does not have any nodes");
        }
        String txtFile = "/island/kanter.txt";
        System.out.println("Reading nodes from " + txtFile);
        Path outputFolder = Path.of("Resources");
        Files.createDirectories(outputFolder);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream(txtFile)))){
            br.readLine(); // To skip first line

            String line;
            while ((line = br.readLine()) != null){
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] splittedLine = hsplit(line, 5);
                if (splittedLine.length != 5){
                    continue;
                }
                int from = Integer.parseInt(splittedLine[0]);
                int to = Integer.parseInt(splittedLine[1]);
                int drivingTime = Integer.parseInt(splittedLine[2]);
                int length = Integer.parseInt(splittedLine[3]);
                int speedLimit = Integer.parseInt(splittedLine[4]);
                graph.addEdge(from, to, drivingTime, length, speedLimit);
            }
            System.out.println("Reading Success!!");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return graph;
    }

    public static Graph populateNodesWithPOI(Graph graph) throws IOException {
        if (graph.getNodes().isEmpty()){
            throw new IllegalArgumentException("Graph does not have any nodes");
        }

        String txtFile = "island/interessepkt.txt";
        System.out.println("Reading nodes from " + txtFile);
        Path outputFolder = Path.of("Resources");
        Files.createDirectories(outputFolder);
        try (BufferedReader br = new BufferedReader(new FileReader(String.valueOf(Main.class.getResource(txtFile))))){

                br.readLine(); // To skip first line

                String line;
                while ((line = br.readLine()) != null){
                    line = line.trim();
                    if (line.isEmpty()) continue;

                    String[] splittedLine = hsplit(line, 3);
                    if (splittedLine.length != 3){
                        continue;
                    }
                    int nodeId = Integer.parseInt(splittedLine[0]);
                    byte codePOI = Byte.parseByte(splittedLine[1]);
                    String namePOI = splittedLine[2];
                    graph.getNode(nodeId).setPOI(codePOI, namePOI);
                }
                System.out.println("Reading Success!!");

            } catch (IOException e) {
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

        String[] felt = new String[10]; // Max 10 felt i en linje
        int j = 0;
        int length = line.length();
        for (int i = 0; i < amount; ++i) {
            //Hopp over innledende blanke, finn starten på ordet
            while (line.charAt(j) <= ' ') ++j;
            int ordstart = j;
            //Finn slutten på ordet, hopp over ikke-blanke
            while (j < length && line.charAt(j) > ' ') ++j;
            felt[i] = line.substring(ordstart, j);
        }
        return felt;
    }
}


