package heyitsap.AlgData;

import javax.swing.*;

import heyitsap.AlgData.Navigation.RecordPath;
import heyitsap.AlgData.Objects.Node;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Map {
    public static void showPath(RecordPath path) {

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(path.name);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(1000, 700);
            frame.setLocationRelativeTo(null);

            JMapViewer map = new JMapViewer();
            frame.add(map);

            List<Coordinate> coordinates = new ArrayList<>();

            // 1. Get all node coordinates in path order
            for (int id : path.nodeIds) {

                Node node = path.graph.getNode(id);

                if (node == null) {
                    throw new IllegalStateException(
                            "Missing node: " + id
                    );
                }

                coordinates.add(new Coordinate(
                        node.latitude,
                        node.longitude
                ));
            }

            // 2. Draw the edges between consecutive nodes
            for (int i = 0; i < coordinates.size() - 1; i++) {

                Coordinate from = coordinates.get(i);
                Coordinate to = coordinates.get(i + 1);

                MapPolygonImpl line = new MapPolygonImpl(
                        Arrays.asList(from, to, to)
                );

                line.setColor(Color.RED);
                line.setBackColor(new Color(0, 0, 0, 0));
                line.setStroke(new BasicStroke(4f));

                map.addMapPolygon(line);
            }

            // 3. Draw all nodes
            for (int i = 0; i < coordinates.size(); i++) {

                int nodeId = path.nodeIds.get(i);

                MapMarkerDot marker = new MapMarkerDot(
                        "Node " + nodeId,
                        coordinates.get(i)
                );

                if (i == 0) {
                    marker.setBackColor(Color.GREEN);
                } else if (i == coordinates.size() - 1) {
                    marker.setBackColor(Color.RED);
                } else {
                    marker.setBackColor(Color.BLUE);
                }

                map.addMapMarker(marker);
            }

            // 4. Display map
            frame.setVisible(true);

            if (coordinates.size() > 1) {
                map.setDisplayToFitMapMarkers();
                map.setZoom(Math.max(0, map.getZoom() - 1));
            } else if (coordinates.size() == 1) {
                map.setDisplayPosition(coordinates.get(0), 15);
            }
        });
    }
}

