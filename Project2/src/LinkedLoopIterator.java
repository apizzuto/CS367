import java.util.*;

/**
 * The LinkedLoopIterator class creates an iterator
 * that can be usd for the LoopADT<Image> implementation
 * of a loop
 *
 * <p>Bugs: None known
 *
 * @author Alex Pizzuto
 */



public class LinkedLoopIterator<E> implements Iterator<E> {
	/*
	 * Fields
	 */
	
	//int to determine if the entire loop has been iterated over
	private int itCount; 
	//Size of loop to iterate over
	private int numItems;
	//Current node
	private DblListnode<E> itNode;
	
	
	/**
	 * Constructor for iterator of a circular doubly linked list
	 */
	public LinkedLoopIterator(DblListnode<E> item, int size) {
		this.itNode = item;
		this.numItems = size;
		this.itCount = 0;
	}
	
	
	/**
	 * Checks to see if the entire loop has been iterated over
	 * @return true if we have not yet iterated over the entire loop
	 */
	public boolean hasNext() {
		return (this.itCount < this.numItems);
	}
	
	/**
	 * @return the data value of the next node in the loop
	 * @throws EmptyLoopException
	 */
	public E next()  {
		if (this.itCount == this.numItems ) {
			
			//If we have traversed the loop in its entirety,
			//throw exception
			throw new NoSuchElementException();
			
		} else {
			
			E nextNode = itNode.getNext().getData();
			this.itNode = this.itNode.getNext();
			itCount++;
			return nextNode;
			
		}
		
	}
	
	
	/*
	 * We are not implementing this method
	 */
	public void remove (E item) {
		throw new UnsupportedOperationException();
	}
	
}
