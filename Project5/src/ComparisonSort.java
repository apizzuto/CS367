//////////////////////////////////////////////////////////////////////////////
//ALL STUDENTS COMPLETE THESE SECTIONS
//Title:            ComparisonSort
//Files:            ComparisonSort.java, SortObject.java, Questions.txt
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
 * This class implements six different comparison sorts (and an optional
 * seventh sort for extra credit):
 * <ul>
 * <li>selection sort</li>
 * <li>insertion sort</li>
 * <li>merge sort</li>
 * <li>quick sort</li>
 * <li>heap sort</li>
 * <li>selection2 sort</li>
 * <li>(extra credit) insertion2 sort</li>
 * </ul>
 * It also has a method that runs all the sorts on the same input array and
 * prints out statistics.
 */

public class ComparisonSort {
	//Store the data moves in a private field
	private static int moves = 0;
	

    /**
     * Sorts the given array using the selection sort algorithm. You may use
     * either the algorithm discussed in the on-line reading or the algorithm
     * discussed in lecture (which does fewer data moves than the one from the
     * on-line reading). Note: after this method finishes the array is in sorted
     * order.
     * 
     * @param <E>  the type of values to be sorted
     * @param A    the array to sort
     */
    public static <E extends Comparable<E>> void selectionSort(E[] A) {
    		if(A == null || A.length == 0) return;
    		int j, k, minIndex;
    		E min;
        int N = A.length;

        for (k = 0; k < N; k++) {
            min = A[k]; moves++;
            minIndex = k;
            for (j = k+1; j < N; j++) {
                if (A[j].compareTo(min) < 0) {
                    min = A[j]; moves++;
                    minIndex = j;
                }
            }
            A[minIndex] = A[k]; moves++;
            A[k] = min; moves++;
        }
    }

    /**
     * Sorts the given array using the insertion sort algorithm. Note: after
     * this method finishes the array is in sorted order.
     * 
     * @param <E>  the type of values to be sorted
     * @param A    the array to sort
     */
    public static <E extends Comparable<E>> void insertionSort(E[] A) {
    		if(A == null || A.length == 0) return;
    		int k, j;
    		E tmp;
    		int N = A.length;
          
        for (k = 1; k < N; k++) {
            tmp = A[k]; moves++;
            j = k - 1;
            while ((j >= 0) && (A[j].compareTo(tmp) > 0)) {
            	// move one value over one place to the right
            		A[j+1] = A[j]; moves++;
                j--;
            }
            A[j+1] = tmp;    // insert kth value in correct place relative 
                               // to previous values
            moves++;
        }
    }

    /**
     * Sorts the given array using the merge sort algorithm. Note: after this
     * method finishes the array is in sorted order.
     * 
     * @param <E>  the type of values to be sorted
     * @param A    the array to sort
     */
    public static <E extends Comparable<E>> void mergeSort(E[] A) {
    		if (A == null || A.length == 0 ) {
    		}
    		else {
    			mergeAux(A, 0, A.length - 1); // call the aux. function to do all the work
    		}
    }
    
    /**
     * Private aux. helper function to handle recursive calls for 
     * mergeSort 
     * 
     * @param A
     * @param low
     * @param high
     */
    private static <E extends Comparable<E>> void mergeAux(E[] A, int low, int high) {
    	// base case
        if (low == high) return;
     
        // recursive case
        
     // Step 1: Find the middle of the array (conceptually, divide it in half)
        int mid = (low + high) / 2;
         
     // Steps 2 and 3: Sort the 2 halves of A
        mergeAux(A, low, mid);
        mergeAux(A, mid+1, high);
     
     // Step 4: Merge sorted halves into an auxiliary array
        E[] tmp = (E[])(new Comparable[high-low+1]);
        int left = low;    // index into left half
        int right = mid+1; // index into right half
        int pos = 0;       // index into tmp
         
        while ((left <= mid) && (right <= high)) {
        // choose the smaller of the two values "pointed to" by left, right
        // copy that value into tmp[pos]
        // increment either left or right as appropriate
        // increment pos
        		if (A[left].compareTo(A[right]) <= 0) {
                tmp[pos] = A[left]; moves++;
                left++; 
        		}
            else {
                tmp[pos] = A[right]; moves++;
                right++;
            	}
            pos++;
            
        }
        
        // when one of the two sorted halves has "run out" of values, but
        // there are still some in the other half, copy all the remaining 
        // values to tmp
        // Note: only 1 of the next 2 loops will actually execute
        while (left <= mid) {  
        		tmp[pos] = A[left]; moves++;
            left++;
            pos++;
        }
        while (right <= high) {  
        		tmp[pos] = A[right]; moves++;
        		right++;
        		pos++;
        }
     
        // all values are in tmp; copy them back into A
        System.arraycopy(tmp, 0, A, low, tmp.length);
        moves += tmp.length;
    }

    /**
     * Sorts the given array using the quick sort algorithm, using the median of
     * the first, last, and middle values in each segment of the array as the
     * pivot value. Note: after this method finishes the array is in sorted
     * order.
     * 
     * @param <E>  the type of values to be sorted
     * @param A   the array to sort
     */
    public static <E extends Comparable<E>> void quickSort(E[] A) {
    		if(A == null || A.length == 0) {
    			return;
    		}
    		quickAux(A, 0, A.length-1); //Call aux function
    }
    
    /**
     * Private aux. helper function to handle recursive calls for 
     * quickSort 
     * @param A
     * @param low
     * @param high
     */
    private static <E extends Comparable<E>> void quickAux(E[] A, int low, int high) {
        if (high-low < 2) {
        		if (high - low == 1 ) {
        			if ( A[low].compareTo(A[high]) > 0) {
        				swap(A, low, high);
        			}
        		}
        }
        else {
            int right = partition(A, low, high);
            	quickAux(A, low, right);
            	quickAux(A, right+2, high);
        }
    }
    
   /**
    * Private helper function to create the partition in a quicksort
    * @param A
    * @param low
    * @param high
    * @return
    */
    private static <E extends Comparable<E>> int partition(E[] A, int low, int high) {
    	
    	    E pivot = medianOfThree(A, low, high); moves++; // this does step 1
    	    int left = low+1; 
    	    int right = high-2;
    	    while ( left <= right ) {
    	        while (A[left].compareTo(pivot) < 0) left++;
    	        while (right > 0 && A[right].compareTo(pivot) > 0) {
    	        		right--;
    	        }
    	        if (left <= right) {
    	            swap(A, left, right); //moves is incremented in swap
    	            left++;
    	            right--;
    	        }
    	    }

    	    swap(A, right+1, high-1); // step 4
    	    return right;
    	}

    /**
     * Private helper function to swap elements of an array
     * 
     * @param A, the array of interest
     * @param ind1, the first index
     * @param ind2, the second index
     * 
     */
    private static <E extends Comparable<E>> void swap(E[] A, int ind1, int ind2) {
		E tmp = A[ind1]; moves++;
		A[ind1] = A[ind2]; moves++;
		A[ind2] = tmp; moves++;
	}
    
    /**
     * Private helper function to prepare an array for quicksort
     * 
     * @param A, the array of interest
     * @param low, the location of the low index
     * @param high, the location of the high index
     */
    private static <E extends Comparable<E>> E medianOfThree(E[] A, int low, int high) {
    		int avg = (low + high) / 2;
    		if (A[low].compareTo(A[avg]) > 0) { 
    			swap(A, low, avg);
    		}
    		if ( A[avg].compareTo(A[high]) > 0){
    			swap(A, avg, high);
    			if(A[low].compareTo(A[avg]) > 0 ) {
    				swap(A, low, avg);
    			}
    		}
    		
    		swap(A, avg, high-1);
    		return A[high - 1];
    }

    /**
     * Sorts the given array using the heap sort algorithm outlined below. Note:
     * after this method finishes the array is in sorted order.
     * <p>
     * The heap sort algorithm is:
     * </p>
     * 
     * <pre>
     * for each i from 1 to the end of the array
     *     insert A[i] into the heap (contained in A[0]...A[i-1])
     *     
     * for each i from the end of the array up to 1
     *     remove the max element from the heap and put it in A[i]
     * </pre>
     * 
     * @param <E>  the type of values to be sorted
     * @param A    the array to sort
     */
    public static <E extends Comparable<E>> void heapSort(E[] A) {
        if(A == null || A.length == 0) return;
        //First, we need to create a heap
        int length = A.length;
        
        for (int i = (length / 2) - 1; i>=0; i--) {
        		heapify(A, length, i);
        }
        
        //Now, extract the max element of the heap
        for(int i = length - 1; i>=0; i--) {
        		swap(A, 0, i);
        		
        		//After removing max, make the new remaining array a heap
        		heapify(A, i, 0);
        }
        
    }
    
    /**
     * Private helper function to turn an array into a heap
     * @param A the array
     * @param length the number of elements of interest
     * @param i the initial index
     */
    private static <E extends Comparable<E>> void heapify(E[] A, int length, int i) {
    		int big = i;
    		int leftChild = 2*i + 1;
    		int rightChild = 2*i + 2;
    		
    		if(leftChild < length && A[leftChild].compareTo(A[big]) > 0) {
    			big = leftChild;
    		}
    		if(rightChild < length && A[rightChild].compareTo(A[big]) > 0) {
    			big = rightChild;
    		}
    		if(big != i) {
    			swap(A, big, i);
    			heapify(A, length, big);
    		}
    }

    /**
     * Sorts the given array using the selection2 sort algorithm outlined
     * below. Note: after this method finishes the array is in sorted order.
     * <p>
     * The selection2 sort is a bi-directional selection sort that sorts
     * the array from the two ends towards the center. The selection2 sort
     * algorithm is:
     * </p>
     * 
     * <pre>
     * begin = 0, end = A.length-1
     * 
     * // At the beginning of every iteration of this loop, we know that the 
     * // elements in A are in their final sorted positions from A[0] to A[begin-1]
     * // and from A[end+1] to the end of A.  That means that A[begin] to A[end] are
     * // still to be sorted.
     * do
     *     use the MinMax algorithm (described below) to find the minimum and maximum 
     *     values between A[begin] and A[end]
     *     
     *     swap the maximum value and A[end]
     *     swap the minimum value and A[begin]
     *     
     *     ++begin, --end
     * until the middle of the array is reached
     * </pre>
     * <p>
     * The MinMax algorithm allows you to find the minimum and maximum of N
     * elements in 3N/2 comparisons (instead of 2N comparisons). The way to do
     * this is to keep the current min and max; then
     * </p>
     * <ul>
     * <li>take two more elements and compare them against each other</li>
     * <li>compare the current max and the larger of the two elements</li>
     * <li>compare the current min and the smaller of the two elements</li>
     * </ul>
     * 
     * @param <E>  the type of values to be sorted
     * @param A    the array to sort
     */
    public static <E extends Comparable<E>> void selection2Sort(E[] A) {
        if(A == null || A.length == 0) return;
        int minIndex, maxIndex;
        for(int begin = 0, end = A.length - 1; begin < A.length/2; begin++, end-- ) {
        		minIndex = begin;
        		maxIndex = end;
        		//Each time, loop through to find Min and Max using
        		//the MinMax algorithm
        		for(int i = begin; i <= (begin + end) / 2; i++) {
        			if(A[i].compareTo(A[end-(i-begin)]) < 0) {
        				if(A[i].compareTo(A[minIndex]) < 0) {
        					minIndex = i;
        				}
        				if(A[end-(i-begin)].compareTo(A[maxIndex]) > 0 ) {
        					maxIndex = end - (i-begin);
        				}
        			} else {
        				if(A[i].compareTo(A[maxIndex]) > 0) {
        					maxIndex = i;
        				}
        				if(A[end-(i-begin)].compareTo(A[minIndex]) < 0 ) {
        					minIndex = end - (i-begin);
        				}
        			}
    			}
        		//Do the swapping, checking for some corner cases to spare some
        		//data moves
        		if(minIndex == end && maxIndex == begin) {
        			swap(A, begin, end);
        		} else if(begin == maxIndex) {
        			swap(A, begin, end);
        			swap(A, begin, minIndex );
        		} else if(end == minIndex) {
        			swap(A, begin, end);
        			swap(A, end, maxIndex);
        		} else {
	        		if(minIndex != begin) {
	        			swap(A, minIndex, begin);
	        		}
	        		if(maxIndex != end) {
	        			swap(A, maxIndex, end);
	        		}
        		}
    		}	
    }

    
    /**
     * <b>Extra Credit:</b> Sorts the given array using the insertion2 sort 
     * algorithm outlined below.  Note: after this method finishes the array 
     * is in sorted order.
     * <p>
     * The insertion2 sort is a bi-directional insertion sort that sorts the 
     * array from the center out towards the ends.  The insertion2 sort 
     * algorithm is:
     * </p>
     * <pre>
     * precondition: A has an even length
     * left = element immediately to the left of the center of A
     * right = element immediately to the right of the center of A
     * if A[left] > A[right]
     *     swap A[left] and A[right]
     * left--, right++ 
     *  
     * // At the beginning of every iteration of this loop, we know that the elements
     * // in A from A[left+1] to A[right-1] are in relative sorted order.
     * do
     *     if (A[left] > A[right])
     *         swap A[left] and A[right]
     *  
     *     starting with with A[right] and moving to the left, use insertion sort 
     *     algorithm to insert the element at A[right] into the correct location 
     *     between A[left+1] and A[right-1]
     *     
     *     starting with A[left] and moving to the right, use the insertion sort 
     *     algorithm to insert the element at A[left] into the correct location 
     *     between A[left+1] and A[right-1]
     *  
     *     left--, right++
     * until left has gone off the left edge of A and right has gone off the right 
     *       edge of A
     * </pre>
     * <p>
     * This sorting algorithm described above only works on arrays of even 
     * length.  If the array passed in as a parameter is not even, the method 
     * throws an IllegalArgumentException
     * </p>
     *
     * @param  A the array to sort
     * @throws IllegalArgumentException if the length or A is not even
     */    
    public static <E extends Comparable<E>> void insertion2Sort(E[] A) { 
    		if(A == null || A.length == 0) return;
    		if(A.length % 2 == 1) {
    			throw new IllegalArgumentException();
    		}
    		int left = A.length/2 -1;
    		int right = A.length/2;
    		if(A[left].compareTo(A[right]) > 0) {
    			swap(A, left, right);
    		}
    		left--; right++;
    		while(left >= 0) {
    			if(A[left].compareTo(A[right]) > 0) {
        			swap(A, left, right);
        		}
    			//Now, we do the two insertion sorts
    			int i = right;
    			E tmp = A[right]; moves++;
    			while(i > left && tmp.compareTo(A[i-1]) < 0) {
    				A[i] = A[i-1]; moves++;
    				i--;
    			}
    			if(!A[right].equals(tmp)) {
    				A[i] = tmp; moves++;
    			}
    			
    			i = left;
    			tmp = A[left]; moves++;
    			while(i < right && tmp.compareTo(A[i+1]) > 0) {
    				A[i] = A[i+1]; moves++;
    				i++;
    			}
    			if(!A[left].equals(tmp)) {
    				A[i] = tmp; moves++;
    			}
    			
    			//increment
    			left--; right++;
    		}
    }

    /**
     * Internal helper for printing rows of the output table.
     * 
     * @param sort          name of the sorting algorithm
     * @param compares      number of comparisons performed during sort
     * @param moves         number of data moves performed during sort
     * @param milliseconds  time taken to sort, in milliseconds
     */
    private static void printStatistics(String sort, int compares, int moves,
                                        long milliseconds) {
        System.out.format("%-23s%,15d%,15d%,15d\n", sort, compares, moves, 
                          milliseconds);
    }

    /**
     * Sorts the given array using the six (seven with the extra credit)
     * different sorting algorithms and prints out statistics. The sorts 
     * performed are:
     * <ul>
     * <li>selection sort</li>
     * <li>insertion sort</li>
     * <li>merge sort</li>
     * <li>quick sort</li>
     * <li>heap sort</li>
     * <li>selection2 sort</li>
     * <li>(extra credit) insertion2 sort</li>
     * </ul>
     * <p>
     * The statistics displayed for each sort are: number of comparisons, 
     * number of data moves, and time (in milliseconds).
     * </p>
     * <p>
     * Note: each sort is given the same array (i.e., in the original order) 
     * and the input array A is not changed by this method.
     * </p>
     * 
     * @param A  the array to sort
     */
    static public void runAllSorts(SortObject[] A) {
        System.out.format("%-23s%15s%15s%15s\n", "algorithm", "data compares", 
                          "data moves", "milliseconds");
        System.out.format("%-23s%15s%15s%15s\n", "---------", "-------------", 
                          "----------", "------------");
        SortObject[] B;
        
        //selection sort
        B = A.clone(); //So that we can store the initial array
        SortObject.resetCompares();
        ComparisonSort.moveReset();
        long startTime = System.currentTimeMillis();
        selectionSort(B);
        long endTime = System.currentTimeMillis();
        long totTime = endTime - startTime;
        boolean sorted = CheckSorted(B);
        int compares = SortObject.getCompares();
        /*
        if (!sorted) {
        		System.out.print(sorted);
        }
        */
        printStatistics("selection", compares, moves, totTime);
        
        //Insertion Sort
        B = A.clone(); //So that we can store the initial array
        SortObject.resetCompares();
        ComparisonSort.moveReset();
        startTime = System.currentTimeMillis();
        insertionSort(B);
        endTime = System.currentTimeMillis();
        totTime = endTime - startTime;
        sorted = CheckSorted(B);
        compares = SortObject.getCompares();
        /*
        if (!sorted) {
        		System.out.print(sorted);
        }
        */
        printStatistics("insertion", compares, moves, totTime);
        
        
        //Merge Sort
        B = A.clone(); //So that we can store the initial array
        SortObject.resetCompares();
        ComparisonSort.moveReset();
        startTime = System.currentTimeMillis();
        mergeSort(B);
        endTime = System.currentTimeMillis();
        totTime = endTime - startTime;
        sorted = CheckSorted(B);
        compares = SortObject.getCompares();
        /*
        if (!sorted) {
        		System.out.print(sorted);
        }
        */
        printStatistics("merge", compares, moves, totTime);
        
        //quick sort
        B = A.clone(); //So that we can store the initial array
        SortObject.resetCompares();
        ComparisonSort.moveReset();
        startTime = System.currentTimeMillis();
        quickSort(B);
        endTime = System.currentTimeMillis();
        totTime = endTime - startTime;
        sorted = CheckSorted(B);
        compares = SortObject.getCompares();
        /*
        if (!sorted) {
        		System.out.print(sorted);
        }
        */
        printStatistics("quick", compares, moves, totTime);
        
        //heap sort
        B = A.clone(); //So that we can store the initial array
        SortObject.resetCompares();
        ComparisonSort.moveReset();
        startTime = System.currentTimeMillis();
        heapSort(B);
        endTime = System.currentTimeMillis();
        totTime = endTime - startTime;
        sorted = CheckSorted(B);
        compares = SortObject.getCompares();
        /*
        if (!sorted) {
        		System.out.print(sorted);
        }
        */
        printStatistics("heap", compares, moves, totTime);
        
        //selection2 sort
        B = A.clone(); //So that we can store the initial array
        SortObject.resetCompares();
        ComparisonSort.moveReset();
        startTime = System.currentTimeMillis();
        selection2Sort(B);
        endTime = System.currentTimeMillis();
        totTime = endTime - startTime;
        sorted = CheckSorted(B);
        compares = SortObject.getCompares();
        /*
        if (!sorted) {
        		System.out.print(sorted);
        }
        */
        printStatistics("selection2", compares, moves, totTime);
        
        //insertion2 sort
        B = A.clone(); //So that we can store the initial array
        SortObject.resetCompares();
        ComparisonSort.moveReset();
        startTime = System.currentTimeMillis();
        insertion2Sort(B);
        endTime = System.currentTimeMillis();
        totTime = endTime - startTime;
        sorted = CheckSorted(B);
        compares = SortObject.getCompares();
        /*
        if (!sorted) {
        		System.out.print(sorted);
        }
        */
        printStatistics("insertion2", compares, moves, totTime);
    }
    
    /**
     * Resets the number of moves
     */
    private static void moveReset() {
    		moves = 0;
    }
    
    /**
     * A check to make sure the array is sorted
     * 
     * @param A the array to check
     * @return true if sorted, false if not
     */
    private static <E extends Comparable<E>> boolean CheckSorted(E[] A) {
    		boolean retBool = true;
    		for (int i = 1; i < A.length; i++) {
    			if (A[i].compareTo(A[i-1]) < 0) {
    				retBool = false;
    				//System.out.print(i + " ");
    			}
    		}
    		/* Removed. Just used for testing
    		if(!retBool) {
    			if( A.length > 15) {
    				for(int i = 0; i<=15; i++) {
    					System.out.print(A[i]);
    					System.out.print("\t");
    				}
    			} else {
    				for(int i = 0; i < A.length; i++) {
    					System.out.print(A[i]);
    					System.out.print("\t");
    				}
    			}
    		}
    		*/
    		
    		
    		return retBool;
    }
    
}
