import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class Graph {

  // Keep a fast index to nodes in the map
  protected Map<String, Vertex> vertices;

  /**
   * Construct an empty Graph.
   */
  public Graph() {
    vertices = new HashMap<String, Vertex>();
  }

  public void addVertex(String name) {
    Vertex v = new Vertex(name);
    addVertex(v);
  }

  public void addVertex(Vertex v) {
    if (vertices.containsKey(v.name))
      throw new IllegalArgumentException(
          "Cannot create new vertex with existing name.");
    vertices.put(v.name, v);
  }

  public Collection<Vertex> getVertices() {
    return vertices.values();
  }

  public Vertex getVertex(String s) {
    return vertices.get(s);
  }

  /**
   * Add a new edge from u to v. Create new nodes if these nodes don't exist
   * yet. This method permits adding multiple edges between the same nodes.
   * 
   * @param u
   *          the source vertex.
   * @param w
   *          the target vertex.
   */
  public void addEdge(String nameU, String nameV, Double cost) {
    if (!vertices.containsKey(nameU))
      addVertex(nameU);
    if (!vertices.containsKey(nameV))
      addVertex(nameV);
    Vertex sourceVertex = vertices.get(nameU);
    Vertex targetVertex = vertices.get(nameV);
    Edge newEdge = new Edge(sourceVertex, targetVertex, cost);
    sourceVertex.addEdge(newEdge);
  }

  /**
   * Add a new edge from u to v. Create new nodes if these nodes don't exist
   * yet. This method permits adding multiple edges between the same nodes.
   * 
   * @param u
   *          unique name of the first vertex.
   * @param w
   *          unique name of the second vertex.
   */
  public void addEdge(String nameU, String nameV) {
    addEdge(nameU, nameV, 1.0);
  }
 

  public void addUndirectedEdge(String s, String t, double cost) {
    addEdge(s,t,cost);
    addEdge(t,s,cost);
  }

  public double computeEuclideanCost(double ux, double uy, double vx, double vy) {
	  return Math.sqrt((ux-vx)*(ux-vx) + (uy-vy)*(uy-vy));
  }

  public void computeAllEuclideanCosts() {
	  for(Vertex v: vertices.values()){
		  for(Edge e: v.getEdges()){
			  e.cost = computeEuclideanCost(e.sourceVertex.posX, e.sourceVertex.posY, e.targetVertex.posX, e.targetVertex.posY);
		  }
	  }
  }

  /** BFS */
  public void doBfs(String s) {
	  for (Vertex v :  vertices.values()){
		  v.cost = Double.POSITIVE_INFINITY;
		  v.visited = false;
	  }
	  Vertex v = getVertex(s);
	  v.cost = 0;
	  Queue<Vertex> q = new LinkedList<Vertex>();
	  q.add(v);
	  
	  while(!q.isEmpty()){
		  v = q.remove();
		  v.visited = true;
		  for (Edge e : v.getEdges()){
			  if(e.targetVertex.cost == Double.POSITIVE_INFINITY){
				  e.targetVertex.backpointer = e.sourceVertex;
				  e.targetVertex.cost = e.sourceVertex.cost + 1;
				  q.add(e.targetVertex);
			  }
		  }
	  }
		  
  }
  
  public Graph getUnweightedShortestPath(String s, String t) {
	  doBfs(s);
	  Graph g2 = new Graph();
	  for (Vertex v : vertices.values()){
		  g2.addVertex(v.name);;
		  if (v.backpointer != null)
			  g2.addEdge(v.backpointer.name, v.name, 1.0);
	  }
	  return g2; // TODO
  }

 
  /** Dijkstra's */
  /*
   * We make a Pair object to hold the vertex, and it's associated cost
   * We cannot use the "cost" field in vertex because of some reasons discussed in class
   * Basically, we must watch out for "duplicate" variables during Dijkstra's algorithm
   * i.e. if we add v3 once, then add it again with a lower cost, we must take care not to screw up the heap order priority
   */
  public class Pair implements Comparable<Pair>{ 
		 Vertex vertex;
		 Double cost;
		  
		  public Pair(Vertex vertex, Double cost){ //constructor
			  this.vertex = vertex;
			  this.cost = cost;
		  }
		  
		  public int compareTo(Pair other){ //Crucial, we need to create a CompareTo method, so our heap knows how to order Pair objects
			  return cost.compareTo(other.cost); 
		  }
		  public void setCost(Double newCost){
			  this.cost = newCost;
			  this.vertex.cost = newCost;
		  }
		  
	  }
  
  public void doDijkstra(String s) { 
	  for (Vertex v: vertices.values()){ //set all vertices to false & infinity
		  v.visited = false;
		  v.cost = Double.POSITIVE_INFINITY;
	  }
	  vertices.get(s).cost = 0.0; //our initial vertex has cost = 0
	  Pair first = new Pair(vertices.get(s),0.0); //create a Pair object for the initial vertex
	  PriorityQueue<Pair> q = new PriorityQueue<>(); //insert to heap
	  q.add(first);
	  
	  while(!q.isEmpty()){
		  Pair u = q.remove();
		  //System.out.println("name:" + u.vertex.name + " cost: " + u.vertex.cost); --> tester
		  if (!u.vertex.visited){
			  Vertex source = u.vertex;
			  source.visited = true;
			  /* for every adjacent vertex, update its cost based on its edge, 
			   * then create a Pair object and add it to the queue.
			   */
			  for (Edge e: source.getEdges()){
				  if (!e.targetVertex.visited){
					  if (e.sourceVertex.cost + e.cost < e.targetVertex.cost){ //update the cost
						  e.targetVertex.cost = e.sourceVertex.cost + e.cost;
						  e.targetVertex.backpointer = e.sourceVertex;
						  Pair p = new Pair(e.targetVertex, e.targetVertex.cost);
						  q.add(p);
					  }
				  }
			  }	  
		  }	    
	  }  
  }
  
  public Graph getWeightedShortestPath(String s, String t){
	  doDijkstra(s);
	  Graph g2 = new Graph();
	  for(Vertex v: vertices.values()){
		  g2.addVertex(v.name);//just add the vertex name, don't add the vertex itself
	  }
	  Vertex v = getVertex(t);
	  while(v != null && v.backpointer!= null){ //must check that backpointer also not null
		  g2.addEdge(v.backpointer.name, v.name, v.cost - v.backpointer.cost ); //add the names of vertices only, not the vertex itself
		  v = v.backpointer; //update v
	  }
	  return g2;
  }

  /** Prim's */
  public void doPrim(String s) {
	  for (Vertex v: vertices.values()){ 
		  v.visited = false;
		  v.cost = Double.POSITIVE_INFINITY;
	  }
	  Vertex v = getVertex(s);
	  v.cost = 0;
	  Pair p = new Pair(v, v.cost);
	  PriorityQueue<Pair> q = new PriorityQueue<>();
	  q.add(p);
	  
	  while (!q.isEmpty()){
		  Pair u = q.remove();
		  if (!u.vertex.visited){
			  Vertex source = u.vertex;
			  source.visited = true;
			  System.out.println("name: " + u.vertex.name + " cost: " + u.vertex.cost);
			  for (Edge e: source.getEdges()){
				  if (!e.targetVertex.visited){
					  if(e.cost < e.targetVertex.cost){ //edge cost, not source cost
						  e.targetVertex.cost = e.cost; //the cost of that vertex will be the cost to GET TO that vertex. 
						  e.targetVertex.backpointer = e.sourceVertex;
						  Pair pair = new Pair(e.targetVertex, e.cost);
						  q.add(pair);
					  }
				  }
			  }
			  
		  }
	  }
	  
  }

  public Graph getMinimumSpanningTree(String s) { //since each node can be the parent to several other nodes. How?
	  doPrim(s);
	  Graph g2 = new Graph();
	  for(Vertex v: vertices.values())
		  g2.addVertex(v.name);
	  for (Vertex v: vertices.values()){
		  if (v.backpointer != null){
			  g2.addEdge(v.backpointer.name, v.name, v.cost);
		  }  
	  }
	  return g2;
  }


  public void printAdjacencyList() {
    for (String u : vertices.keySet()) {
      StringBuilder sb = new StringBuilder();
      sb.append(u);
      sb.append(" -> [ ");
      for (Edge e : vertices.get(u).getEdges()) {
        sb.append(e.targetVertex.name);
        sb.append("(");
        sb.append(e.cost);
        sb.append(") ");
      }
      sb.append("]");
      System.out.println(sb.toString());
    }
  }

  public static void main(String[] args) throws FileNotFoundException {
	    Graph g = new Graph();
	    g.addVertex(new Vertex("v0", 0, 0));
	    g.addVertex(new Vertex("v1", 1, 1));
	    g.addVertex(new Vertex("v2", 4, 3));
	    g.addVertex(new Vertex("v3", 2, 2));

	    g.addEdge("v0", "v1");
	    g.addEdge("v1", "v2");
	    g.addEdge("v2", "v3");
	    g.addEdge("v3", "v0");
	    g.addEdge("v0", "v2");
	    g.addEdge("v1", "v3");
	    g.computeAllEuclideanCosts();
	    //g.printAdjacencyList();
	    
	    Graph g2 = new Graph();
	    g2 = g.getMinimumSpanningTree("v0");
	    g2.printAdjacencyList();
	    DisplayGraph display = new DisplayGraph(g2);
	    display.setVisible(true);
	  
	  /*Graph g = new Graph();
    g = MapReader.readGraph(args[0], args[1]);
    g.computeAllEuclideanCosts();
    System.out.println("LA:" + g.vertices.get("LosAngeles").cost);
    
    Graph g2 = new Graph();
    g2 = g.getWeightedShortestPath("NewYork", "LosAngeles");
    System.out.println("LA:" + g2.vertices.get("LosAngeles").cost);
    DisplayGraph display = new DisplayGraph(g2);
    display.setVisible(true);
    */
  }
}
