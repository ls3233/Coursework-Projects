/*This FastBuffer class implements the Buffer interface with two stacks
*/ 

import java.util.Deque;
import java.util.LinkedList;

public class FastBuffer implements Buffer {

  private Deque<Character> left;
  private Deque<Character> right;

  public FastBuffer() {
    init();
  }

  private void init() {
    left = new LinkedList<Character>();
    right = new LinkedList<Character>();
  }

  
  public int size() {
    return left.size() + right.size();
  }

  public void load(char[] initial, int cursorPosition) {
    init();
    for (char c : initial) {
      left.addFirst(c);
    }
    setCursor(cursorPosition);
  }

  public char[] toArray() {
    char[] array = new char[size()];
    int i = 0;
    for (Character c : left) {
      array[left.size() - 1 - i] = c;
      i++;
    }
    for (Character c : right) {
      array[i] = c;
      i++;
    }
    return array;
  }

  
  public int getCursor() {
    return left.size();
  }

  public void setCursor(int j) {
    while (getCursor() != j) {
      if (j < getCursor()) {
        moveLeft();
      }
      if (j > getCursor()) {
        moveRight();
      }
    }
  }

  
  public void moveRight() {
    left.addFirst(right.removeFirst());
  }

  public void moveLeft() {
    right.addFirst(left.removeFirst());
  }

  public void insertLeft(char c) {
    left.addFirst(c);
  }

  public char deleteRight() {
    return right.removeFirst();
  }

  public char deleteLeft() {
    return left.removeFirst();
  }

    
    public static void main(String[] args) {
        
        FastBuffer myBuff = new FastBuffer();
        //System.out.println("Cursor at "+myBuff.getCursor());
        myBuff.insertLeft('1');
        //System.out.println("Cursor at "+myBuff.getCursor());
        myBuff.insertLeft('2');
        myBuff.insertLeft('3');
        myBuff.insertLeft('4');
   
        myBuff.setCursor(3);
        myBuff.insertLeft('5');
        myBuff.insertLeft('6');
        myBuff.insertLeft('7');
  
        
        char[] theArray = myBuff.toArray();

        
        for (int i = 0; i < theArray.length; i++) 
        {
            System.out.print(theArray[i]);
        }
        System.out.println();
        
        //Add "*$" at position 6
        myBuff.setCursor(6);
        myBuff.insertLeft('*');
        myBuff.insertLeft('$');
      
        // Print again: 
        theArray = myBuff.toArray();
        for (int i = 0; i < theArray.length; i++) 
        {
            System.out.print(theArray[i]);
        } 
        System.out.println();
        
        // delete the junk characters
        System.out.println("Deleted: " + myBuff.deleteLeft());
        System.out.println("Deleted: " + myBuff.deleteLeft());
        //System.out.println("Deleted: " + myBuff.deleteRight());
        
        theArray = myBuff.toArray();
        for (int i = 0; i < theArray.length; i++) {
            System.out.print(theArray[i]);
        }
        System.out.println();
        
    }
    
}