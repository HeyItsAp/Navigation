package heyitsap.AlgData.Preprocessing;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public List<Edge> edges;
    public final int id;
    public double latitdue;
    public double longitude;
    public byte POICode;
    public String POI;

    public Node(int id, double latitdue, double longitude){
        this.edges = new ArrayList<>();
        this.id = id;
        this.latitdue = latitdue;
        this.longitude = longitude;
    }

    public void setPOI(byte POICode, String POI){
        this.POI = POI;
        this.POICode = POICode;
    }
}

