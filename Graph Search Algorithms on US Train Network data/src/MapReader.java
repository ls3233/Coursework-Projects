import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

<<<<<<< HEAD
public class MapReader {
	
  public static Graph readGraph(String vertexfile, String edgefile) throws FileNotFoundException {
    try{
    	Graph graph = new Graph();
        readVertices(graph, vertexfile);
        readEdges(graph, edgefile);
        return graph;
    }
    catch(FileNotFoundException exception){
    	System.out.println("File Not Found");
    }
	return null;
=======
  public static Graph readGraph(String vertexfile, String edgefile) {
    return null; // TODO 
>>>>>>> 593aabe696ce3be5c65304e43ccc9c1ebe0b41ac
  }

  public static void readVertices(Graph graph, String vertexfile) throws FileNotFoundException{
	 try{
		  Scanner input = new Scanner(new File(vertexfile));
		  while(input.hasNextLine()){
			  String inputLine = input.nextLine();
			  String[] tokens = inputLine.split(",");
			  int x = Integer.parseInt(tokens[1]);
			  int y = Integer.parseInt(tokens[2]);
			  Vertex v = new Vertex(tokens[0], x, y);
			  graph.addVertex(v);
		  }
	 }  
	 catch(FileNotFoundException exception){
		 System.out.println("File Not Found.");
	 } 
  }
  
  public static void readEdges(Graph graph, String edgefile) throws FileNotFoundException{
	 try{
		  Scanner input = new Scanner(new File(edgefile));
		  while(input.hasNextLine()){
			  String inputLine = input.nextLine();
			  String[] tokens = inputLine.split(",");
			  graph.addUndirectedEdge(tokens[0], tokens[1], 1.0);
		  }
	 }  
	 catch(FileNotFoundException exception){
		 System.out.println("File Not Found.");
	 } 
  }
  
  
  
  public void main(String[] args) throws FileNotFoundException {
    Graph graph = new Graph();
    graph = readGraph(args[0], args[1]); 
    DisplayGraph display = new DisplayGraph(graph);
    display.setVisible(true);
  }

}
