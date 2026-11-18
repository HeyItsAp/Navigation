
package heyitsap.AlgData.Maps;

import heyitsap.AlgData.Navigation.RecordPath;
import heyitsap.AlgData.Objects.Node;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class DualMaps {

    private static final Color[] COLORS = {
            Color.RED,
            Color.BLUE,
            Color.GREEN,
            Color.MAGENTA,
            Color.ORANGE,
            Color.CYAN,
            Color.PINK
    };

    public static void showTwoPathLists(
            List<RecordPath> listA,
            List<RecordPath> listB) {

        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame("Path Comparison");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(1400, 750);
            frame.setLocationRelativeTo(null);

            // Two independent maps
            JMapViewer mapA = new JMapViewer();
            JMapViewer mapB = new JMapViewer();

            // Populate each map using its own list
            drawPaths(mapA, listA);
            drawPaths(mapB, listB);

            // Panel A
            JPanel panelA = new JPanel(new BorderLayout());

            JPanel labelsA = new JPanel();
            labelsA.setLayout(new BoxLayout(labelsA, BoxLayout.Y_AXIS));

            JLabel titleA = new JLabel("List A - " + listA.size() + " paths",SwingConstants.CENTER);
            titleA.setAlignmentX(Component.CENTER_ALIGNMENT);
            labelsA.add(titleA);

            int j = 0;
            for (RecordPath A : listA) {
                j++;
                JLabel label = new JLabel(j + ": " + A.processedNodes);
                label.setAlignmentX(Component.CENTER_ALIGNMENT);
                labelsA.add(label);
            }

            panelA.add(labelsA, BorderLayout.NORTH);
            panelA.add(mapA, BorderLayout.CENTER);


            // Panel B
            JPanel panelB = new JPanel(new BorderLayout());

            JPanel labelsB = new JPanel();
            labelsB.setLayout(new BoxLayout(labelsB, BoxLayout.Y_AXIS));

            JLabel titleB = new JLabel("List B - " + listB.size() + " paths",SwingConstants.CENTER);
            titleB.setAlignmentX(Component.CENTER_ALIGNMENT);
            labelsB.add(titleB);
            int i = 0;
            for (RecordPath B : listB){
                i++;
                JLabel label = new JLabel(i + ": " + B.processedNodes);
                label.setAlignmentX(Component.CENTER_ALIGNMENT);
                labelsB.add(label);
            }

            panelB.add(labelsB, BorderLayout.NORTH);
            panelB.add(mapB, BorderLayout.CENTER);

            // Place maps side by side
            JPanel mainPanel = new JPanel(
                    new GridLayout(1, 2, 10, 0)
            );

            mainPanel.add(panelA);
            mainPanel.add(panelB);

            frame.add(mainPanel);
            frame.setVisible(true);

            // Fit each map independently
            fitMap(mapA);
            fitMap(mapB);
        });
    }

    private static void drawPaths(
            JMapViewer map,
            List<RecordPath> paths) {

        for (int pathIndex = 0;
             pathIndex < paths.size();
             pathIndex++) {

            RecordPath path = paths.get(pathIndex);

            Color color = COLORS[pathIndex % COLORS.length];

            Coordinate previous = null;

            for (int nodeId : path.nodeIds) {

                Node node = path.graph.getNode(nodeId);

                if (node == null) {
                    throw new IllegalStateException(
                            "Missing node: " + nodeId
                    );
                }

                Coordinate current = new Coordinate(
                        node.latitude,
                        node.longitude
                );

                // Draw edge from previous node
                if (previous != null) {

                    MapPolygonImpl edge = new MapPolygonImpl(
                            Arrays.asList(
                                    previous,
                                    current,
                                    current
                            )
                    );

                    edge.setColor(color);
                    edge.setBackColor(
                            new Color(0, 0, 0, 0)
                    );
                    edge.setStroke(new BasicStroke(4f));

                    map.addMapPolygon(edge);
                }

                // Draw node
                MapMarkerDot marker = new MapMarkerDot(
                        path.name + " - Node " + nodeId,
                        current
                );

                marker.setBackColor(color);
                marker.setColor(Color.BLACK);

                map.addMapMarker(marker);

                previous = current;
            }
        }
    }

    private static void fitMap(JMapViewer map) {

        int count = map.getMapMarkerList().size();

        if (count == 1) {
            map.setDisplayPosition(
                    map.getMapMarkerList()
                            .get(0)
                            .getCoordinate(),
                    13
            );
        } else if (count > 1) {
            map.setDisplayToFitMapMarkers();
        }
    }
}
