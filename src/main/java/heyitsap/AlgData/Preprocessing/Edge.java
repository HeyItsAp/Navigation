package heyitsap.AlgData.Preprocessing;


public class Edge {
    private final Node from;
    private final Node to;
    private final int drivingTime; // In 1/100 of a second i dont knwo why
    private final int length; // The main weight. Also, 1/100 of a second.
    private final int speedLimit; // Km/h

    public Edge(Node from, Node to, int drivingTime, int length, int speedLimit){
        this.drivingTime = drivingTime;
        this.length = length;
        this.speedLimit = speedLimit;
        this.from = from;
        this.to = to;
    }

}
