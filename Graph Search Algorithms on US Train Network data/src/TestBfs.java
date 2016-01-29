import java.io.FileNotFoundException;

public class TestBfs {

  public static void main(String[] args) throws FileNotFoundException {
	  Graph g = new Graph();
	    g = MapReader.readGraph(args[0], args[1]);
	    Graph g2 = new Graph();
	    g2 = g.getUnweightedShortestPath(args[2], args[3]);
	    
	    DisplayGraph display = new DisplayGraph(g2);
	    display.setVisible(true);
  }

}
