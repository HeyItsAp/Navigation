package heyitsap.AlgData;

import javax.swing.*;

import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;

public class Map {
    public static void test(){
        SwingUtilities.invokeLater(() -> {

            // Create the window
            JFrame frame = new JFrame("My OpenStreetMap");
            frame.setSize(800, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Create the map
            JMapViewer map = new JMapViewer();

            // Add the map to the window
            frame.add(map);
            frame.setVisible(true);

            // Set location (Trondheim, Norway)
            Coordinate location = new Coordinate(63.4305, 10.3951);

            // Center the map and choose zoom level
            map.setDisplayPosition(location, 12);

            // Add a marker
            map.addMapMarker(
                    new MapMarkerDot("Trondheim", location)
            );
        });
    }
}

