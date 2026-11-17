package heyitsap.AlgData.Preprocessing;

import heyitsap.AlgData.Main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Preprosessing {
    public static void readNodes() throws IOException {
        String txtFile = "island/noder.text";
        System.out.println("Reading nodes from " + txtFile);
        Path outputFolder = Path.of("Resources");
        Files.createDirectories(outputFolder);
        try (BufferedReader br = new BufferedReader(new FileReader(String.valueOf(Main.class.getResource(txtFile))))){
            br.readLine(); // To skip first line about amount of edges and nodes.

            String line;
            while ((line = br.readLine()) != null){
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] splittedline = hsplit(line, 3);
                if (splittedline.length != 3){
                    continue;
                }

                int nodeId = Integer.parseInt(splittedline[0]);
                double latitude = Integer.parseInt(splittedline[1]);
                int longitude = Integer.parseInt(splittedline[2]);

                Node node = new Node(nodeId, latitude, longitude);

            }
            System.out.println("Reading Success!!");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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


