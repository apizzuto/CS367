///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            WordCloudGenerator
// File:             ArrayHeap.java
// Semester:         CS 367 Spring 2018
//
// Author:           Alex Pizzuto (pizzuto@wisc.edu)
// CS Login:         pizzuto@cs.wisc.edu
// Lecturer's Name:  Charles Fischer
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//                   
// Persons:          N/A
//
// Online sources:   CS367 Class notes
//
//////////////////////////// 80 columns wide //////////////////////////////////

import java.util.NoSuchElementException;

/**
 * An implementation of the Priority Queue ADT using an array-based
 * implementation of max heap.
 * 
 * @author APizzuto
 *
 * <p> Bugs: none Known
 */
public class ArrayHeap<E extends Prioritizable> implements PriorityQueueADT<E> {

    // default number of items the heap can hold before expanding
    private static final int INIT_SIZE = 100;
    private E[] data; //The actual data heap is an array
    private int numItems; //Number of items in the ArrayHeap
    private int allottedSize; //Current number of items that can be stored
    
    
    /**
     * No argument constructor that constructs a heap whose
     * underlying array has enough space to store INIT_SIZE items 
     * before needing to expand.
     */
    public ArrayHeap() {
    		this(INIT_SIZE); 
    		//Calls the one-argument constructor with size INIT_SIZE
    }
    
   /**
    * One argument constructor that takes an integer parameter and 
    * constructs a heap whose underlying array has enough space to
    * store the number of items given in the parameter before needing
    * to expand 
    * 
    * @param space
    */
    public ArrayHeap(int space) {
    		if (space < 0 ) {
    			throw new IllegalArgumentException();
    		}
    		this.data = (E[])(new Prioritizable[space + 1]); 
    		//Array is of size space + 1 because ArrayHeaps start indexing at 1
    		this.numItems = 0;
    		this.allottedSize = space;
    }


    /**
     *
     * Returns true if this ArrayHeap priority queue contains no items.
     * @return true if this priority queue contains no items, false otherwise
     */
    public boolean isEmpty() {
        return (size() == 0);  // replace this stub with your code
    }

    /**
     * Adds the given item to the ArrayHeap priority queue.
     * @param item the item to insert into the ArrayHeap priority queue
     */
    public void insert(E item) {
    		if(item == null) {
    			throw new IllegalArgumentException();
    			//Don't allow null to be added to the Heap
    		}
        if(numItems == allottedSize ) {
        		//If ArrayHeap is full, we must resize before inserting
        		reSize(); 
        }
        
        int index = numItems+1;
        data[index] = item; 
        int parentInd = index/2;
        //Start by making new item the last in the array heap
        
        while (parentInd > 0 && data[index].getPriority() > data[parentInd].getPriority()) {
        	//Continue to swap added item with parent until it is less than its parent
        	
        		swapItems(index, index/2); //Call helper function to swap entries
        		index = parentInd; //Change index to where the parent was
        		parentInd = index/2;
        }
        numItems++;
        
    }
    
    /**
     * Private helper function to double the size of the ArrayHeap
     */
    private void reSize() {
    		E[] bigArray = (E[])(new Prioritizable[(numItems * 2) +1]);
    		System.arraycopy(data, 1, bigArray, 1, numItems);
    		data = bigArray;
    		allottedSize = allottedSize*2;
    }
    
    /**
     * Private helper function to swap two items in the array heap
     * located at childIndex and parentIndex
     * 
     * @param childIndex location of item to be moved higher in ArrayHeap
     * @param parentIndex location of item to be swapped with its child
     */
    private void swapItems(int childIndex, int parentIndex) {
    		E tmp = data[childIndex];
    		data[childIndex] = data[parentIndex];
    		data[parentIndex] = tmp;
    }

    /**
     * Removes and returns the item with the highest priority.
     * @return the item with the highest priority
     * @throws NoSuchElementException if the ArrayHeap priority queue is empty
     */
    public E removeMax() {
        if(numItems == 0) {
        		throw new NoSuchElementException();
        }
        
        E outData = data[1];
        //Record the max priority item before reconfiguring arrayHeap
        data[1] = data[numItems];
        //Replace root with last leaf
        data[numItems] = null;
        //Remove the last leaf from the ArrayHeap
        numItems--;
        //Decrement the Size of the ArrayHeap
        int index = 1;
        boolean inOrder = false;
        
        while(!inOrder && index < numItems) {
        		if(index*2 > numItems ) {
        			//If data has no child, we are in order
        			inOrder = true;
        			
        		}else if((index*2) == numItems) {
        			//If only one child, it will be a left child
        			
        			if (data[index].getPriority() < data[numItems].getPriority() ) {
        				//If parent is less than child, swap
        				swapItems(index, index*2);
        				inOrder = true;
        			} else {
        				//If parent is larger than only child, ArrayHeap is in order
        				inOrder = true;
        			}
        			
        		} else if( data[index*2].getPriority() > data[(index*2) + 1].getPriority()) {
        			//If left child is larger, compare to it
        			if (data[index].getPriority() < data[index*2].getPriority()) {
        				//If parent is less than child, swap
        				swapItems(index, index*2);
        				index = index*2;
        			} else {
	        			//If parent larger than or equal to largest child, 
        				//Then the ArrayHeap is in order
	        			inOrder = true;
        			}
        			
        		} else {
        			//If right child is equal or larger, compare to it
        			if (data[index].getPriority() < data[(index*2)+1].getPriority()) {
        				//If parent is less than child, swap
        				swapItems(index, (index*2)+1);
        				index = (index*2)+1;
        			} else {
	        			//If parent equal to or larger than largest child, 
        				//Then the ArrayHeap is in order
	        			inOrder = true;
        			}
        		}
        }
        
        return outData; //Returns the max item from earlier
    }

    /**
     * Returns the item with the highest priority.
     * @return the item with the highest priority
     * @throws NoSuchElementException if the priority queue is empty
     */
    public E getMax() {
    		if (size() == 0) {
    			throw new NoSuchElementException();
    		} 
        return data[1];  //Max priority item is the first item in the ArrayHeap
    }

    /**
     * Returns the number of items in this ArrayHeap priority queue.
     * @return the number of items in this ArrayHeap priority queue
     */
    public int size() {
        return numItems;  // replace this stub with your code
    }
}
