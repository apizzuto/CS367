///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            WordCloudGenerator
// File:             BSTDictionaryIterator.java
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

import java.util.*;

/**
 * BSTDictionaryIterator implements an iterator for a binary search tree (BST)
 * implementation of a Dictionary.  The iterator iterates over the tree in 
 * order of the key values (from smallest to largest). We will do this
 * by essentially doing an in order traversal of the tree and 
 * populating the stack with certain amounts of this in order traversal
 * 
 * <p> Bugs: none Known
 * 
 * @author APizzuto
 */
public class BSTDictionaryIterator<K> implements Iterator<K> {
	private Stack<BSTnode<K>> itStack; 
	//stack to store elements of tree to be iterated over 
	
	
	/**
	 * Constructor for the iterator
	 * @param root the Root of the BSTDictionary to iterate over
	 */
	public BSTDictionaryIterator(BSTnode<K> root) {
		
		itStack = new Stack<BSTnode<K>>();
		
		//At first, push nodes starting at root and 
		//going down to the leftmost (minimum) node
		BSTnode<K> node = root;
		while(node != null ) {
			itStack.push(node);
			node = node.getLeft();
		}
	}

    /**
     * Returns true if there is another element to iterate over.
     */
	public boolean hasNext() {
        return (!itStack.isEmpty());  
    }

    /**
     * Returns the next element in the iteration of the BSTDictionary
     * @return next element in iteration
     */
	public K next() {
		if (this.hasNext() == false ) {
			
			throw new NoSuchElementException();
			
		}
		
		BSTnode<K> next = itStack.pop(); 
		//Get the next item and remove it from the stack
		if (next.getRight() != null) {
			//Add items to the stack as you would in an in order traversal
			BSTnode<K> addNode = next.getRight();
			while (addNode != null) {
				itStack.push(addNode); 
				addNode = addNode.getLeft();
			}
		}
		//Return the key of the popped node
        return next.getKey();  
    }

    public void remove() {
        // DO NOT CHANGE: you do not need to implement this method
        throw new UnsupportedOperationException();
    }    
}
