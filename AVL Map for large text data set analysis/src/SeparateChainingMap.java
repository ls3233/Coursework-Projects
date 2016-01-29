import java.util.LinkedList;
import java.util.ListIterator;


public class SeparateChainingMap<K extends Comparable<? super K>, V> implements Map<K, V> {

  public static final int SCALE_FACTOR = 2;
  public static final int INITIAL_TABLE_SIZE = 8;
  public static final double MAX_LOAD_FACTOR = 1.0;
  LinkedList<Pair<K,V>>[] array;
  private int size;
  private int tableSize;

  
  public SeparateChainingMap() {
	  array = new LinkedList[INITIAL_TABLE_SIZE];
	  for (int i = 0; i<array.length;i++)
		  array[i]= new LinkedList<Pair<K,V>>();
	  size = 0;
	  tableSize = 8;
  }

  public int getSize() {
    return size;
  }

  public int getTableSize() {
    return tableSize;
  }

  private int hash(K key){
	  int hashValue = key.hashCode() * 37;
	  hashValue = hashValue % tableSize;
	  return hashValue;
  }
  

  public void put(K key, V value) {
	 Pair<K,V> input = new Pair<>(key, value);
	 double loadFactor = size/tableSize;
	 if (loadFactor>MAX_LOAD_FACTOR)
		 upsize();
	 int hashValue = hash(key);
	 System.out.println("value: " + hashValue);
	 array[hashValue].addFirst(input);
	 size++;
  }

  public V get(K key) {
    int hashValue = hash(key);
    LinkedList<Pair<K,V>> list = array[hashValue];
    return find(key, list);
  }
  
  private V find(K key, LinkedList<Pair<K,V>> list){
	  ListIterator<Pair<K,V>> iter = list.listIterator();
	  Pair<K,V> dummy = new Pair(key, 1);
	  while (iter.hasNext()){
		  Pair<K,V> next = iter.next();
		  if (dummy.compareTo(next)==0)
			  return next.value;
	  }
	  return null;
  }
 

  public void upsize() {
	  int newTableSize = tableSize * SCALE_FACTOR;
	  LinkedList<Pair<K,V>>[] newArray;
	  newArray = new LinkedList[newTableSize];
	  for (int i = 0; i<tableSize; i++)
		  newArray[i] = array[i];
	  for (int i = tableSize; i<newArray.length; i++)
		  newArray[i] = new LinkedList<Pair<K,V>>();
	  array = newArray;
	  tableSize = newTableSize;
  }
  
  public static void main(String[] args){
	  SeparateChainingMap<Integer, Integer> map = new SeparateChainingMap<>();
	  Pair<Integer, Integer> test = new Pair(3,4);
	  map.put(6,5);
	  System.out.println(map.get(6));
	  map.upsize();
	  map.upsize();
	  
  }
  
}
