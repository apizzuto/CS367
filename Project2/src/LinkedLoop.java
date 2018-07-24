import java.util.*;

/**
 * Implementation of a loop as a doubly linked chain of nodes
 *
 * <p>Bugs: None known
 *
 * @author Alex Pizzuto
 */

public class LinkedLoop<E> implements LoopADT<E> {
	/* Fields */ 
	private DblListnode<E> items; //Doubly-linked node
	private int numItems; // Total number of nodes in the Loop
	
	
	/*
	 * Constructs an empty LinkedLoop
	 */
	public LinkedLoop() {
		items = null;
		numItems = 0;
	}
	
	/**
     * Adds the given item immediately <em>before</em> the current 
     * item.  After the new item has been added, the new item becomes the 
     * current item.
     * 
     * @param item the item to add
     */
    public void add(E item) {
    		DblListnode<E> newNode = new DblListnode<E>(item);
    		if ( numItems == 0 ) { 
    			//If Loop is empty, make the first node
    			items = newNode;
    			items.setNext(newNode); //Have the first node point to itself
    			items.setPrev(newNode); 
    			
    		} else {
    			DblListnode<E> prevNode = items.getPrev(); 
    			//Figures out which node we are inserting after
    			
    			newNode.setNext(items);
    			newNode.setPrev(prevNode); 
    			//Now our newNode is pointing to the right things
    			//But we still need to point the old nodes to the newNode
    			items.setPrev(newNode);
    			prevNode.setNext(newNode);
    			
    			//Change newNode to be the current node
    			items = newNode;
    		}
    		numItems++; //Add one to the total number of nodes in Loop
    }
    
    /**
     * Returns the current item.  If the Loop is empty, an 
     * <tt>EmptyLoopException</tt> is thrown.
     * 
     * @return the current item
     * @throws EmptyLoopException if the Loop is empty
     */
    	public E getCurrent() throws EmptyLoopException {
    		if(numItems == 0) {
    			throw new EmptyLoopException();
    		} else {
    			return items.getData();
    		}
    	}
    
    	
    /**
     * Removes and returns the current item.  The item immediately 
     * <em>after</em> the removed item then becomes the  current item.  
     * If the Loop is empty initially, an <tt>EmptyLoopException</tt> 
     * is thrown.
     * 
     * @return the removed item
     * @throws EmptyLoopException if the Loop is empty
     */
    public E removeCurrent() throws EmptyLoopException {
	    	if(numItems == 0) {
	    		
				throw new EmptyLoopException();
				
		}
	    	E currDat = items.getData();
	    	if (numItems == 1) {
			items = null; //Now there is an empty loop
		} else {  
			//Set previous node's next pointer to the node after current
			//Set next node's previous pointer to node before current
			items.getPrev().setNext(items.getNext());
			items.getNext().setPrev(items.getPrev());
			items = items.getNext(); //Change current node to what was the next
			//This still works if numItems == 2
		}

	    	numItems--; //Account for losing access to removed node
	    	return currDat; //Returns the current node's data
    }
    
    /**
     * Advances current forward one item resulting in the item that is 
     * immediately <em>after</em> the current item becoming the current item.
     */
    public void next() {
    		
    		items = items.getNext();
    		
    }
    
    /**
     * Moves current backwards one item resulting in the item that is 
     * immediately <em>before</em> the current item becoming the current item.
     */
    public void previous() {
    	
    		items = items.getPrev();
    	
    }
    
    /**
     * Determines if this Loop is empty, i.e., contains no items.
     * @return true if the Loop is empty; false otherwise
     */
    public boolean isEmpty() {
    		return (numItems == 0);
    }
    
    /**
     * Returns the number of items in this Loop.
     * @return the number of items in this Loop
     */
    public int size() {
    		return numItems;
    }
    
    /**
     * Returns an iterator for this Loop.
     * @return an iterator for this Loop
     */
    public Iterator<E> iterator() {
    		return new LinkedLoopIterator<E>(items, numItems);
    }
	
	
}
