package heyitsap.AlgData.Preprocessing;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public List<Edge> edges;
    public final int id;
    public final double latitude;
    public final double longitude;
    public byte POICode;
    public String POI = "";
    /* POI codes:
    Bit     Betyr           Eksempler
    1       Stedsnavn       Trondheim, Moholt, …
    2       Bensinstasjon   Shell Herlev
    4       Ladestasjon     Ionity Klett
    8       Spisested       Restauranter, kafeer, puber
    16      Drikkested      Barer, puber, nattklubber
    32      Overnattings    Hoteller, moteller, gjestehu
     */

    public Node(int id, double latitude, double longitude){
        this.edges = new ArrayList<>();
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setPOI(byte POICode, String POI){
        this.POI += (" " + POI);
        this.POICode += POICode;
    }
}

