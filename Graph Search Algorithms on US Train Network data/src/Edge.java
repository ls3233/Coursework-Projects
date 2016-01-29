//An Edge object that include a cost variable, we need this to ensure that when we update the heap in Dijkstra's algorithm, the ordering of edges is not disrupted

public class Edge implements Comparable<Edge> {
  public Vertex sourceVertex;
  public Vertex targetVertex;
  public Double cost;

  public Edge(Vertex source, Vertex target, Double theCost) {
    sourceVertex = source;
    targetVertex = target;
    cost = theCost;
  }

  public Edge(Vertex source, Vertex target) {
    this(source, target, 1.0);
  }

  public int compareTo(Edge other) {
    return cost.compareTo(other.cost);
  }

}
