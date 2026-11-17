package heyitsap.AlgData.Objects;


public class Edge {
    public final Node from;
    public final Node to;
    public final int drivingTime; // In 1/100 of a second i dont knwo why
    public final int length; // The main weight. Also, 1/100 of a second.
    public final int speedLimit; // Km/h

    public Edge(Node from, Node to, int drivingTime, int length, int speedLimit){
        this.drivingTime = drivingTime;
        this.length = length;
        this.speedLimit = speedLimit;
        this.from = from;
        this.to = to;
    }

}
