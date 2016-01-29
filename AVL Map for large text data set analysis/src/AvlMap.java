

public class AvlMap<K extends Comparable<? super K>, V> implements Map<K, V>  {
	private AvlTree<Pair<K,V>> map;

	
	
  public AvlMap() {
	  map = new AvlTree<>();
  }

  
  public void put(K key, V value) {
    Pair<K,V> input = new Pair<K,V>(key, value);
    map.insert(input);
  }

  
  public V get(K key) {   
    Pair<K,V> input = new Pair(key, 1);
    if (!map.contains(input))
    	throw new NullPointerException("that key doesn't exist.");
    return map.get(input).value;
  }
  
  public boolean contains(Pair<K,V> input){
	  return map.contains(input);
  }
  


  
  public static void main(String[] args){
	  AvlMap<Integer, Integer> map = new AvlMap<>();
	  map.put(1,3);
	  System.out.println(map.get(1));
  }
  

	  
  

  
  
  
}
