import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class BwogBot {
	AvlMap<String,Integer> bwog;
	private Scanner s;

	ArrayList<Pair<Integer,String>> popularityRanking = new ArrayList<>();
	
  public BwogBot() {
	  bwog = new AvlMap<>();
  }

  /*
   * I use an AvlMap in this case
   * an AvlMap is great because it avoids collisions i.e. every word will evaluate to a unique node for sure
   * versus if I'd use separate chaining, I'd have to deal with collisions
   * it's also much faster to utilize the methods in AvlTree.java and AvlMap.java
   * Lastly, We can find/get elements much faster, as opposed to looking through the separatechainingMap
   */
  
  public void readFile(String fileName) throws IOException {
	 //initialize scanner and arraylist
	  s = new Scanner(new File(fileName));
	  ArrayList<String> allWords = new ArrayList<>();
	  
	  //read words into arraylist
	  while (s.hasNext())
	  	allWords.add(s.next());
	  
	  //iterate through the list, look at each word and determine how many times it appears
	  System.out.println(allWords.size());
	  for(int i = 0; i<allWords.size(); i++){
		  Pair<String, Integer> dummy = new Pair(allWords.get(i), 1);
		  while (!bwog.contains(dummy)){
			  int counter = 1;
			  for (int j = i+1; j<allWords.size();j++){
				  if (allWords.get(i).equals(allWords.get(j)))
					  counter++;
			  }
			  put(allWords.get(i),counter);
			  ranking(counter, allWords.get(i));
		  }
	  }
  }
  
  public void ranking(Integer key, String word){
	  Pair<Integer, String> popularity = new Pair(key,word);
	  popularityRanking.add(popularity);
	  Collections.sort(popularityRanking);
  }
  
  //insert a pair. If the key exists, do nothing.
  public void put(String key, Integer value){
	  Pair<String, Integer> input = new Pair(key, value);
	  if (bwog.contains(input))
		  return;
	  bwog.put(key, value);  
  }

  public int getCount(String word) {
    Pair<String, Integer> input = new Pair(word, 1);
    return bwog.get(word);
  }


  public Map<String, Integer> getMap() {
    return bwog;
  }
  
  public List<String> getNMostPopularWords(int n) {
	  List<String> topWords = new ArrayList<>();
	  for(Pair<Integer, String> all : popularityRanking.subList(popularityRanking.size()-n+1, popularityRanking.size())){
		  topWords.add(all.value);
	  }
	  return topWords;

  }

  public static void main(String[] args) throws IOException {
    BwogBot bot = new BwogBot();
    bot.readFile("comments.txt");
    System.out.println(bot.getCount("hodor")); // because linan's hungry now
    System.out.println(bot.getNMostPopularWords(100));
  }
}
