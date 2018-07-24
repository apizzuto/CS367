///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            WordCloudGenerator
// File:             KeyWord.java
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

/**
* The KeyWord Class contains a word and a non-negative integer representing the
* number of times the word occurs in an input file
* 
* <p> Bugs: none Known
* 
* @author APizzuto
*
*/
public class KeyWord implements Comparable<KeyWord>, Prioritizable{
	//Fields
	public int occur;  //Number of occurrences
	public String word; //lowercase key word
	
	/**
	 * Constructor: Constructs a KeyWord with the given word (converted to lower-case)
	 *  and zero occurences. If the word is null or an empty string, 
	 *  an IllegalArgumentException is thrown
	 * 
	 * @param word String to be saved as key word
	 */
	public KeyWord(String word) {
		
		if( word == null || word.equals("") ) {
			throw new IllegalArgumentException();
		}
		this.word = word.toLowerCase(); //Set word to be the provided word
		this.occur = 0; //Set default to 0 
		
	}
	
	/**
	 * Returns the word for this KeyWord.
	 * 
	 * @return the word of the KeyWord
	 */
	public String getWord() {
		return word;
	}
	
	/**
	 * Returns the number of occurrences for this KeyWord.
	 * 
	 * @return number of occurrences
	 */
	public int getOccurrences() {
		return occur;
	}
	
	/**
	 * Adds one to the number of occurrences for this KeyWord.
	 */
	public void increment() {
		occur++;
	}
	
	/**
	 * Returns the priority for this KeyWord. 
	 * The priority of a KeyWord is the number of occurrences it has.
	 * 
	 * @return priority of KeyWord
	 */
	public int getPriority() {
		return occur;
	}
	
	/**
	 * Compares the KeyWord with the one given. 
	 * Two KeyWords are compared by comparing the word associated with 
	 * the two KeyWords, ignoring case differences in the names.
	 * 
	 * @param other the KeyWord with which to compare this KeyWord
	 * @return int corresponding to comparison using Comparable convention
	 */
	public int compareTo(KeyWord other) {
		return word.compareToIgnoreCase(other.getWord());
	}
	
	/**
	 * Compares this KeyWord to the specified object. 
	 * The result is true if and only if the argument is not null 
	 * and is a KeyWord object whose word is the same as the word 
	 * of this KeyWord, ignoring case differences.
	 * 
	 * @overrides equals in java.lang.Object
	 * @param the object with which to compare this KeyWord
	 * @return true iff argument is KeyWord object whose word
	 * is the same as the word of thie keyWord
	 */
	public boolean equals(Object other) {
		if(!(other instanceof KeyWord) || other==null ) {
			return false;
		}
		KeyWord testKeyWord = (KeyWord) other;
		return word.equalsIgnoreCase(testKeyWord.getWord());
	}
	
	
}
