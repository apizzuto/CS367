import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;

//////////////////////////////////////////////////////////////////////////////
//ALL STUDENTS COMPLETE THESE SECTIONS
//Title:            HashTable
//Files:            HashTable.java, TestHash.java, Questions.txt
//Semester:         CS 367 Spring 2018
//
//Author:           Alex Pizzuto (pizzuto@wisc.edu)
//CS Login:         pizzuto@cs.wisc.edu
//Lecturer's Name:  Charles Fischer
//
////////////////////STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//
//Persons:          N/A
//
//Online sources:   CS367 Class notes
//
////////////////////////////80 columns wide //////////////////////////////////


/**
 * This class implements a hashtable that using chaining for collision handling.
 * Any non-<tt>null</tt> item may be added to a hashtable.  Chains are 
 * implemented using <tt>LinkedList</tt>s.  When a hashtable is created, its 
 * initial size, maximum load factor, and (optionally) maximum chain length are 
 * specified.  The hashtable can hold arbitrarily many items and resizes itself 
 * whenever it reaches its maximum load factor or whenever it reaches its 
 * maximum chain length (if a maximum chain length has been specified).
 * 
 * Note that the hashtable allows duplicate entries.
 */
public class HashTable<T> {
	//FIELDS
	private double MAX_LOAD_FACTOR = 0;
	private int INIT_SIZE = 0;
	private int MAX_LENGTH = 0;
	
	private int itemCount = 0;
	private int longestLink = 0;
	private double loadFactor = 0.0;
	private LinkedList<T>[] ht; 
	
    
    /**
     * Constructs an empty hashtable with the given initial size, maximum load
     * factor, and no maximum chain length.  The load factor should be a real 
     * number greater than 0.0 (not a percentage).  For example, to create a 
     * hash table with an initial size of 10 and a load factor of 0.85, one 
     * would use:
     * 
     * <dir><tt>HashTable ht = new HashTable(10, 0.85);</tt></dir>
     *
     * @param initSize the initial size of the hashtable.
     * @param loadFactor the load factor expressed as a real number.
     * @throws IllegalArgumentException if <tt>initSize</tt> is less than or 
     *         equal to 0 or if <tt>loadFactor</tt> is less than or equal to 0.0
     **/
    public HashTable(int initSize, double loadFactor) {
    		if (initSize <= 0 || loadFactor <= 0.0) {
    			throw new IllegalArgumentException();
    		} else {
	    		this.INIT_SIZE = initSize;
	    		this.MAX_LOAD_FACTOR = loadFactor;
	    		this.MAX_LENGTH = 0;
	    		this.ht = (LinkedList<T>[])(new LinkedList[this.INIT_SIZE]);
    		}
    }
    
    
    /**
     * Constructs an empty hashtable with the given initial size, maximum load
     * factor, and maximum chain length.  The load factor should be a real 
     * number greater than 0.0 (and not a percentage).  For example, to create 
     * a hash table with an initial size of 10, a load factor of 0.85, and a 
     * maximum chain length of 20, one would use:
     * 
     * <dir><tt>HashTable ht = new HashTable(10, 0.85, 20);</tt></dir>
     *
     * @param initSize the initial size of the hashtable.
     * @param loadFactor the load factor expressed as a real number.
     * @param maxChainLength the maximum chain length.
     * @throws IllegalArgumentException if <tt>initSize</tt> is less than or 
     *         equal to 0 or if <tt>loadFactor</tt> is less than or equal to 0.0 
     *         or if <tt>maxChainLength</tt> is less than or equal to 0.
     **/
    public HashTable(int initSize, double loadFactor, int maxChainLength) {
        if (initSize <= 0 || loadFactor <= 0.0 || maxChainLength <=0 ) {
        		throw new IllegalArgumentException();
        } else {
        		this.INIT_SIZE = initSize;
        		this.MAX_LOAD_FACTOR = loadFactor;
        		this.MAX_LENGTH = maxChainLength;
        		this.ht = (LinkedList<T>[])(new LinkedList[this.INIT_SIZE]);
        }
    }
    
    /**
     * Private helper function to return a properly formatted hash value
     * @param item
     * @return hash value compatible with hash table
     */
    private int hash(T item, int size) {
    		int value = item.hashCode();
    		if(value >= 0 ) {
    			value = value % size;
    		} 
    		while (value < 0 ) {
    			//If negative, add size until we are in proper codomain
    			value = value + size;
    		}
    		return value;
    }
    
    /**
     * Private helper function to resize the hashtable when the max load factor
     * is exceeded or if the max chain length is exceeded. Resizes to twice
     * the previous size plus 1
     */
    private void resize() {
    		int oldSize = this.INIT_SIZE;
    		int newSize = (2*this.INIT_SIZE) + 1;
    		LinkedList<T>[] tmp = (LinkedList<T>[])(new LinkedList[newSize]);
    		
    		for(int i =0; i < newSize; i++) {
    			tmp[i] = new LinkedList<T>();
    		}
    		
    		//When we loop through the old array, we need to rehash 
    		//to the new array
    		LinkedList<T> tmpList = null;
    		int tmpHash = 0;
    		for(int i =0; i< oldSize; i++) {
    			//Loop over the old hashtable
    			tmpList = this.ht[i];
    			if(tmpList != null && tmpList.size() != 0) {
    				Iterator<T> itrt = tmpList.iterator(); 
    				//Iterate through each index, and rehash each item
    				while(itrt.hasNext()) {
    					T addItem = itrt.next();
    					tmpHash = hash(addItem, newSize);
    					tmp[tmpHash].add(addItem);
    					
    				}
    				
    			}
    		}
    		this.ht = tmp;
    		this.INIT_SIZE = newSize;
    		this.loadFactor = ((double)itemCount)/newSize;
    		int newlongest = 0;
    		for(int i =0; i < newSize; i++) {
    			if (this.ht[i].size() > newlongest ) {
    				newlongest = this.ht[i].size();
    			}
    		}
    		this.longestLink = newlongest;
    		
    		//Check to make sure that we don't need to resize again
    		if( ((longestLink > MAX_LENGTH) && (MAX_LENGTH != 0)) || loadFactor > MAX_LOAD_FACTOR) {
        		resize();
        }
    }
    
    
    /**
     * Determines if the given item is in the hashtable and returns it if 
     * present.  If more than one copy of the item is in the hashtable, the 
     * first copy encountered is returned.
     *
     * @param item the item to search for in the hashtable.
     * @return the item if it is found and <tt>null</tt> if not found.
     **/
    public T lookup(T item) {
    		if(this.itemCount == 0) {
    			return null;
    		}
    		int myHash = hash(item, this.INIT_SIZE);
    		LinkedList<T> tstList = this.ht[myHash];
    		if (tstList == null) {
    			return null;
    		}
    		if (tstList.contains(item)) {
    			return item;
    		}
    		return null;
    }
    
    
    /**
     * Inserts the given item into the hashtable.  The item cannot be 
     * <tt>null</tt>.  If there is a collision, the item is added to the end of
     * the chain.
     * <p>
     * If the load factor of the hashtable after the insert would exceed 
     * (not equal) the maximum load factor (given in the constructor), then the 
     * hashtable is resized.  
     * 
     * If the maximum chain length of the hashtable after insert would exceed
     * (not equal) the maximum chain length (given in the constructor), then the
     * hashtable is resized.
     * 
     * When resizing, to make sure the size of the table is reasonable, the new 
     * size is always 2 x <i>old size</i> + 1.  For example, size 101 would 
     * become 203.  (This guarantees that it will be an odd size.)
     * </p>
     * <p>Note that duplicates <b>are</b> allowed.</p>
     *
     * @param item the item to add to the hashtable.
     * @throws NullPointerException if <tt>item</tt> is <tt>null</tt>.
     **/
    public void insert(T item) {
        if (item == null ) {
        		throw new NullPointerException();
        }
        
        //Call private helper function to calculate the hash value of item
        int myHash = hash(item, this.INIT_SIZE);
        if (ht[myHash] == null) {
        		ht[myHash] = new LinkedList<T>();
        }
        ht[myHash].add(item);
        this.itemCount++;
        this.loadFactor = ((double)itemCount)/ht.length;
        if( ht[myHash].size() > longestLink) {
        		this.longestLink = ht[myHash].size();
        }
        
        //Make sure we have not overloaded the hashtable
        if( ((longestLink > MAX_LENGTH) && (MAX_LENGTH != 0)) || loadFactor > MAX_LOAD_FACTOR) {
        		resize();
        }
        
    }
    
    
    /**
     * Removes and returns the given item from the hashtable.  If the item is 
     * not in the hashtable, <tt>null</tt> is returned.  If more than one copy 
     * of the item is in the hashtable, only the first copy encountered is 
     * removed and returned.
     *
     * @param item the item to delete in the hashtable.
     * @return the removed item if it was found and <tt>null</tt> if not found.
     **/
    public T delete(T item) {
    		int myHash = hash(item, this.INIT_SIZE);
    		if(this.ht[myHash].contains(item)) {
    			this.ht[myHash].remove(item);
    			return item;
    		} else {
    			return null; 
    		}
    }
    
    
    /**
     * Prints all the items in the hashtable to the <tt>PrintStream</tt> 
     * supplied.  The items are printed in the order determined by the index of
     * the hashtable where they are stored (starting at 0 and going to 
     * (table size - 1)).  The values at each index are printed according 
     * to the order in the <tt>LinkedList</tt> starting from the beginning. 
     *
     * @param out the place to print all the output.
     **/
    public void dump(PrintStream out) {
    		out.println("Hashtable Contents:");
    		for (int i = 0; i < this.INIT_SIZE; i++ ) {
    			if (ht[i] != null ) {
    				if(ht[i].size() > 0) {
	    				int size = ht[i].size();
	    				out.print(i + ": [");
	    				for(int j = 0; j < size - 1 ; j++) {
	    					out.print(ht[i].get(j).toString() + ", ");
	    				}
	    				out.print(ht[i].get(size -1));
	    				out.print("]\n");
    				}
    			}
    		}
    }
    
  
    /**
     * Prints statistics about the hashtable to the <tt>PrintStream</tt> 
     * supplied.  The statistics displayed are: 
     * <ul>
     * <li>the current table size
     * <li>the number of items currently in the table 
     * <li>the current load factor
     * <li>the length of the largest chain
     * <li>the number of chains of length 0
     * <li>the average length of the chains of length > 0
     * </ul>
     *
     * @param out the place to print all the output.
     **/
    public void displayStats(PrintStream out) {
    		int zeroLengths = 0;
    		for (int i = 0; i < INIT_SIZE; i++) {
    			if(ht[i] == null || ht[i].size() == 0) {
    				zeroLengths += 1;
    			}
    		}
    		double avlength = (double)(this.itemCount)/(this.INIT_SIZE-zeroLengths);
    		out.println("Hashtable statistics:");
    		out.println("  current table size:\t\t"+ this.INIT_SIZE);
    		out.println("  # items in table:\t\t" + this.itemCount);
    		out.println("  current load factor:\t\t" + this.loadFactor);
    		out.println("  longest chain length:\t\t" + this.longestLink);
    		out.println("  # 0-length chains:\t\t" + zeroLengths);
    		out.println("  avg (non-0) chain length:\t" + avlength);
    }
}
