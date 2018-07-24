///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            WordCloudGenerator
// File:             BSTDictionary.java
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

import java.util.Iterator;

/**
* The BSTDictionary class is an implementation of the DictionaryADT that
* uses a binary search tree.
* 
* <p> Bugs: none Known
* 
* @author APizzuto
*
*/
public class BSTDictionary<K extends Comparable<K>> implements DictionaryADT<K> {
    private BSTnode<K> root;  // the root node
    private int numItems;     // the number of items in the dictionary

    /**
     * Constructor: Creates an empty BST, 
     * sets the root equal to null and the numItems equal to zero
     */
    public BSTDictionary() {
    		this.root = null; //constructor points to null
    		this.numItems = 0; //Dictionary empty to start
    }
    
    /**
     * Inserts the given key into the Dictionary if the key is not already in 
     * the Dictionary.  If the key is already in the Dictionary, a
     * DuplicatException is thrown.
     * @param key the key to insert into the Dictionary
     * @throws DuplicateException if the key is already in the Dictionary
     * @throws IllegalArgumentException if the key is null
     */    
    public void insert(K key) throws DuplicateException {
        if (key == null ) {
        	
        		throw new IllegalArgumentException();
        		
        } else {
        	
        		root = insert(root,key);
        		//Make use of helper method to handle recursive cases
        		numItems++; //helper method does not increment numItems
        	
        }
    }
    
    /**
     * Helper method to recursively traverse the dictionary
     * until it finds a spot to insert key.
     * @param r the root of the tree/subtree
     * @param key the key to be inserted
     * @return root
     * @throws DuplicateException if key already in BSTDictionary
     */
    private BSTnode<K> insert(BSTnode<K> r, K key) throws DuplicateException {
    		if(r == null){
        	
	    		return new BSTnode<K>(key,null,null); 
	    		//If tree was empty, make tree point to key
    		
    		} 
    		if(r.getKey().equals(key)) {
    			//key is present, so we throw exception
    			throw new DuplicateException();
    			
    		} else if ( key.compareTo(r.getKey()) < 0 ) {
    			//if less, add to left subtree
    			r.setLeft(insert(r.getLeft(), key)); 
    			//Recursive call to navigate to lower levels of tree
    			return r;
    			
    		} else {
    			//Otherwise, add to right subtree
    			r.setRight(insert(r.getRight(), key));
    			//Recursive call to navigate to lower levels of tree
    			return r;
    		}
    }

    /**
     * Deletes the given key from the Dictionary.  If the key is in the 
     * Dictionary, the key is deleted and true is returned.  If the key is not 
     * in the Dictionary, the Dictionary is unchanged and false is returned. 
     * @param key the key  to delete from the Dictionary
     * @return true if the deletion is successful (i.e., the key was in the 
     * Dictionary and has been removed), false otherwise (i.e., the key was 
     * not in the Dictionary)
     */
    public boolean delete(K key) {
    		if (key == null) {
    			return false; //return false if key is null
    		} else if (lookup(key) == null ) {
    			//Note: calling function of O(height of tree) does 
    			//nothing to the asymptotic complexity of this 
    			//function being O(height of tree)
    			return false;
    		}
    		else {
    			root = delete(root, key); 
    			//Make use of helper method to handle recursive cases
    			//with knowledge that key is in tree
    			numItems--; //decrement number of items
    			return true;
    		}  
    }
    
    /**
     * Helper method for delete. Deletes key from given tree
     * or subtree
     * 
     * @param r root of tree or subtree
     * @param key the Key to delete
     */
    private BSTnode<K> delete(BSTnode<K> r, K key) {
    		if(key.equals(r.getKey())) {
    			//if we have found key, we must delete if
    			if(r.getLeft() == null && r.getRight()==null) {
    				return null;//if r is a leaf, set it equal to null
    			} else if (r.getLeft() == null ) {
    				//if left is null, make right child the new root
    				return r.getRight();
    			} else if (r.getRight() == null) {
    				//if right is null, make left the new root
    				return r.getLeft();
    			}
    			else {
    				//if r has two children, we choose to replace r
    				//by the smallest value in the right subtree
    				//and recursively delete that value
    				K smallVal = smallest(r.getRight());
    				r.setKey(smallVal);
    				r.setRight( delete(r.getRight(), smallVal) );
    				return r;
    			}
    		} else if (key.compareTo(r.getKey()) < 0 ) {
    			r.setLeft( delete(r.getLeft(), key) );
    			return r;
    		} else {
    			r.setRight( delete(r.getRight(), key) );
    			return r;
    		}
    }
    
    /**
     * Helper method for delete. Returns smallest value in
     * the subtree rooted at n
     * 
     * @param n root of tree/subtree
     * @return smallest value in subtree
     */
    private K smallest(BSTnode<K> n) {
	    	if (n.getLeft() == null) {
	            return n.getKey();
	        } else {
	            return smallest(n.getLeft());
	        }
    }

    /**
     * Searches for the given key in the Dictionary and returns the key stored
     * in the Dictionary. If the key is not in the Dictionary, null is 
     * returned.
     * @param key the key to search for
     * @return the key from the Dictionary, if the key is in the Dictionary; 
     *         null if the key is not in the Dictionary
     */
    public K lookup(K key) {
    		if (key == null ) {
    			return null; //If key is null, return null
    		} else {
    			return lookup(root, key); 
    			//Helper function to deal with all recursive cases
    		}
    }
    
    /**
     * Helper function to handle all recursive cases for lookup. 
     * If found, returns key, otherwise returns null.
     * 
     * @param r root of the tree/subtree
     * @param key the key to find
     * @return key if found, null otherwise
     */
    private K lookup(BSTnode<K> r, K key) {
    		if (r == null) {
    			return null; //if tree is empty, return null
    		} else if (r.getKey().equals(key)) {
    			return r.getKey(); //If found, return key
    		} else if ( key.compareTo(r.getKey()) < 0) {
    			//If key is less than root of current tree/subtree,
    			//navigate to left subtree and perform lookup
    			return lookup(r.getLeft(), key);
    		} else {
    			//key is greater than root of current tree/subtree,
    			//navigate to right subtree and perform lookup
    			return lookup(r.getRight(), key);
    		}
    }

    /**
     * Returns true if and only if the Dictionary is empty.
     * @return true if the Dictionary is empty, false otherwise
     */
    public boolean isEmpty() {
        return (numItems==0);
    }

    /**
     * Returns the number of keys in the Dictionary.
     * @return the number of keys in the Dictionary
     */
    public int size() {
        return numItems;  
    }
    
    /**
     * Returns the total path length.  The total path length is the sum of  
     * the lengths of the paths to each (key, value) pair.
     * @return the total path length
     */
    public int totalPathLength() {
        return totalPathLength(root, 1);  //Call recursive helper method
    }
    
    /**
     * Helper methods to calculate the total path length by adding the current 
     * node's path length to the total length of the sum of its right and left
     * subtree
     *  
     * @param r root of the tree/subtree currently being looked at
     * @param level height of node r
     * @return total path length for r and all of its children
     */
    private int totalPathLength(BSTnode<K> r, int level) {
    		if (r == null) {
    			return 0;
    		} else if (r.getLeft()==null && r.getRight()==null) {
    			return level;
    			//if r is a leaf, don't add to the length
    		} else {
    			return level + totalPathLength(r.getLeft(), level + 1) +
    				totalPathLength(r.getRight(), level+1);
    			//recursively add the lengths of all subtrees of r
    		}
    }
    
    /**
     * Returns an iterator over the Dictionary that iterates over the keys 
     * in the Dictionary in order from smallest to largest.
     * @return an iterator over the keys in the Dictionary in order
     */
    public Iterator<K> iterator() {
        return new BSTDictionaryIterator<K>(root); 
    }
}
