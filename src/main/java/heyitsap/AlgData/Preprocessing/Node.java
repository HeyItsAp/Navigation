package heyitsap.AlgData.Preprocessing;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public List<Edge> edges;
    public final int id;
    public final double latitude;
    public final double longitude;
    public byte POICode;
    public String POI;

    public Node(int id, double latitude, double longitude){
        this.edges = new ArrayList<>();
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setPOI(byte POICode, String POI){
        this.POI = POI;
        this.POICode = POICode;
    }
}

