package heyitsap.AlgData.Preprocessing;


public class Edge {
    private final Node from;
    private final Node to;
    private final int drivingTime; // 1/100 of a second
    private final int length; // Meters
    private final int speedLimit; // Km/h

    public Edge(Node from, Node to, int drivingTime, int length, int speedLimit){
        this.drivingTime = drivingTime;
        this.length = length;
        this.speedLimit = speedLimit;
        this.from = from;
        this.to = to;
    }
}
