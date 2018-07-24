import java.util.*;
import java.io.*;

///////////////////////////////////////////////////////////////////////////////
//Main Class File:  Texttester.java or GUItester.java
//File:             InteractiveDBTester.java
//Semester:         CS 367 Spring 2018
//
//Author:           Alex Pizzuto (pizzuto@wisc.edu)
//CS Login:         pizzuto@cs.wisc.edu
//Lecturer's Name:  Charles Fischer
//
///////////////////////////////////////////////////////////////////////////////

/**
 * The InteractiveDBTester class creates and tests an EmployeeDatabase
 * that represents information about employees and wish lists.
 * Employee and wish list information is read from a text file.
 * 
 * @author Alex Pizzuto
 *
 */
public class InteractiveDBTester {
	
	// make the Employee database a static data member so it does not have to 
	// be passed to each help method
    // It is protected so that subclasses representing particular testers can
    // access it

	protected static EmployeeDatabase EmployeeDB = new EmployeeDatabase();
	
    /**
     * Initialize DB from external text file. Makes sure file is
     * readable. 
     * @param args Expects exactly one argument (name of file)
     */
    protected static void populateDB(String [] args) {
    	
    	// Step 1: check whether exactly one command-line argument is given
	  if(args.length != 1) {
		  String message = "Please provide input file as command-line argument";
		  System.out.println(message);
		  System.exit(1);
	  }
    	
    	// Step 2: check whether the input file exists and is readable
	   File file = new File( args[0] );
	   Scanner inFile = null;
	   try {
		   inFile = new Scanner(file);
	   } catch (FileNotFoundException e) {
		   //ensure that file exists
		   System.out.println("Error: Cannot access input file");
		   System.exit(1);
	   }
    	
    	// Step 3: load the data from the input file and use it to construct a
    	//         Employee database
	   while( inFile.hasNext() ) {
		   String line = inFile.nextLine();
		   List<String> empInfo = new ArrayList<String>(Arrays.asList(line.split(",")));
		   //creates list of strings for each line of file
		   String empName = empInfo.get(0);
		   EmployeeDB.addEmployee(empName); //Adds new employee to DB
		   List<String> destInfo = new ArrayList<String>(empInfo.subList(1, empInfo.size()));
		   Iterator<String> itrt = destInfo.iterator();
		   while(itrt.hasNext()) {
			   //All entries after the first on a line are stored as destinations
			   EmployeeDB.addDestination(empName, itrt.next()); 
		   }
	   }
	   inFile.close();
    }

     static boolean GUIactive;  //is GUI tester active?

// Methods that implement GUI buttons or testing actions

    /**
     * If employee not in DB, return "employee not found". Otherwise, find
     * employee and return employee formatted as:
     * employee:destination1,destination2,...
     * @param employee String employee username
     * @return employee not found or employee information
     */
     protected static String pushFind(String employee){
    	 	if(!EmployeeDB.containsEmployee(employee)) {
    	 		String failedMessage = "Employee not found \n";
    	 		return failedMessage; //return "employee not found" if employee not in DB 
    	 	} else {
    	 		List<String> destList = EmployeeDB.getDestinations(employee);
    	 		String info = employee.toLowerCase() + ":" 
    	 				+ String.join(",", destList) + "\n";
    	 		return info; //return formatted string with employee's wishlist
    	 	}
    }

     /**
      * Discontinues destination if it is to be found in DB
      * 
      * @param destination String to be removed
      * @return "destination not found" if not in DB. 
      * @return "destination discontinued" if destination was in DB
      */
     protected static String pushDiscontinue(String destination) {
    	 	String failedMessage = "destination not found \n";
    	 	String successMessage = "destination discontinued \n";
    	 	boolean bool = EmployeeDB.removeDestination(destination); 
    	 	//this sets bool to true if destination was in DB, false otherwise
    	 	if(bool) {
    	 		return successMessage; //if true, return "destination discontinued"
    	 	} else {
    	 		return failedMessage; //if false, return "destination not found"
    	 	}
     }

    /**
     * Search for destination and return all employees that list
     * that destination on their wishlist
     * 
     * @param destination
     * @return string "destination not found" if destination not in DB
     * @return string "destination:employee1,employee2,..." otherwise
     */
     protected static String pushSearch(String destination){
    	 	if(!EmployeeDB.containsDestination(destination)) {
 	 		String failedMessage = "destination not found \n";
 	 		return failedMessage;
 	 	} else {
 	 		List<String> eList = new ArrayList<String>();
 	 		eList.addAll(EmployeeDB.getEmployees(destination));
 	 		String message = destination.toLowerCase() 
 	 					+ ":" + String.join(",", eList) + "\n";
 	 		return message;
 	 	}
     }


     /**
      * Remove the supplied employee from the employee DB if 
      * employee is in DB to start
      * 
      * @param employee String employee to be removed
      * @return "employee not found" if employee not in DB to start
      * @return "employee removed" otherwise
      */
     protected static String pushRemove(String employee){
    	   String failedMessage = "Employee not found \n";
    	   String successMessage = "Employee removed \n";
    	   boolean bool = EmployeeDB.removeEmployee(employee);
    	   if (bool) {
    		   	return successMessage;
       } else {
    	   		return failedMessage;
       }
     }

     /**
      * Returns information about the current state of the DB, including:
      * number of employees and destinations, distribution of 
      * destinations per employee, distribution of employees per destination, 
      * and most popular destination.
      * 
      * @return formatted string with all of the information listed above
      */
     protected static String pushInformation(){
    	 	Integer empNum = EmployeeDB.size();
    	 	Integer destNum = destList().size();
    	 	String numberInfo = "Employees: " + String.format("%d", empNum) 
    	 			+ ", Destinations: " + String.format("%d", destNum);
    	 	Integer[] numDPerE = destPerEmp(); 
    	 	//returns array of [mostDestinations, leastDestinations, total]
    	 	Float aveD = (float) numDPerE[2]/empNum;
    	 	//divide total number of entries on wishlists by number of employees
    	 	String destInfo = String.format("# of destinations/employee: most %d, "
    	 			+ "least %d, average %.1f", numDPerE[0], numDPerE[1], aveD);
    	 	Integer[] numEPerD = empPerDest();
    	 	//returns array of [mostEmployees, leastEmployees, total]
    	 	Float aveE = (float) numEPerD[2]/destNum;
    	 	//divide total number of employees per destination by destinations
    	 	String empInfo = String.format("# of employees/destination: most %d, "
    	 			+ "least %d, average %.1f", numEPerD[0], numEPerD[1], aveE);
    	 	List<String> modeList = destMode();
    	 	//returns List<String> of all destinations that appear the most
    	 	String popInfo = String.format("Most popular destination: %s [%d]",
    	 			String.join(",", modeList), numEPerD[0]);
    	 	String totalInfo = numberInfo + "\n" + destInfo + "\n" + empInfo 
    	 			+ "\n" + popInfo + "\n";
    	 	return totalInfo;
     }

     /**
      * Returns a list of the contents of the entire DB, one employee per line
      * 
      * @return String list of contents of DB, each line formatted as 
      * employee:destination1,destination2,...
      */
 	protected static String pushList(){
 		Iterator<Employee> itrt = EmployeeDB.iterator();
 		StringBuilder message = new StringBuilder();
 		while (itrt.hasNext()) {
 			String name = itrt.next().getUsername();
    	   		List<String> dList = EmployeeDB.getDestinations(name);
    	   		String myStr = String.join(",", dList);
    	   		message = message.append(String.format("%s:%s \n", name, myStr));
 		}
 		String strMessage = message.toString() + "\n";
 		return strMessage;
     }
     
 	/**
 	 * Lists all destinations that appear in the DB exactly once
 	 * 
 	 * @return dList List<String> of all destinations
 	 */
     private static List<String> destList(){
    	 	List<String> dList = new ArrayList<String>();
    	 	Iterator<Employee> itrt = EmployeeDB.iterator();
    	 	while (itrt.hasNext()) {
    	 		String name = itrt.next().getUsername();
    	 		List<String> wList = EmployeeDB.getDestinations(name);
    	 		Iterator<String> wItrt = wList.iterator();
    	 			while (wItrt.hasNext()) {
    	 				String location = wItrt.next();
    	 				if (!dList.contains(location)) {
    	 					//make sure destination not already on list
    	 					dList.add(location);
    	 				}
    	 			}
    	 	}
    	 	return dList;
     }
     
     /**
      * Calculates the maximum and minimum number of destinations
      * to be found on a wishlist. It also returns the total number
      * of elements on all wishlists
      * 
      * @return myDestArray Integer[] including [most destinations, 
      * 		least destinations, total number of entries]
      */
     private static Integer[] destPerEmp() {
    	 	Integer[] myDestArray = new Integer[] {0,10000,0};
    	 	Iterator<Employee> itrt = EmployeeDB.iterator();
    	 	Integer total = 0;
    	 	while (itrt.hasNext()) {
    	 		Integer wListSize = itrt.next().getWishlist().size();
    	 		if (wListSize > myDestArray[0]) {
    	 			myDestArray[0] = wListSize;
    	 		} else if (wListSize < myDestArray[1]) {
    	 			myDestArray[1] = wListSize;
    	 		}
    	 		total = total + wListSize;
    	 	}
    	 	myDestArray[2] = total;
    	 	return myDestArray;
     }
     
     /**
      * Calculates the maximum and minumum number of wishlists in which any
      * destination appears. Also returns the total number of entries
      * 
      * @return myEmpArray Integer[] including [destination with most employees, 
      * 		destination with least employees, total number of entries]
      */
     private static Integer[] empPerDest() {
    	 	List<String> dList = destList();
    	 	Integer total = 0;
    	 	Integer[] myEmpArray = new Integer[] {0,100000,0};
    	 	Iterator<String> itrt = dList.iterator();
    	 	while (itrt.hasNext()) {
    	 		Integer eListSize = EmployeeDB.getEmployees(itrt.next()).size();
    	 		if (eListSize > myEmpArray[0] ) {
    	 			myEmpArray[0] = eListSize;
    	 		} else if (eListSize < myEmpArray[1]) {
    	 			myEmpArray[1] = eListSize;
    	 		}
    	 		total = total + eListSize;
    	 	}
    	 	myEmpArray[2] = total;
    	 	return myEmpArray;
     }
     
     /**
      * Finds the most frequently appearing destinations, and returns
      * all of the destinations with the most appearances on the DB.
      * 
      * @return modeList List<String> of all of the destinations that appear
      * 		most frequently
      */
     private static List<String> destMode () {
    	 	Integer[] myEmpArray = empPerDest();
    	 	Integer modeFreq = myEmpArray[0];
    	 	List<String> dList = destList();
    	 	Iterator<String> itrt = dList.iterator();
    	 	List<String> modeList = new ArrayList<String>();
    	 	while (itrt.hasNext()) {
    	 		String dest = itrt.next();
    	 		Integer eListSize = EmployeeDB.getEmployees(dest).size();
    	 		if (eListSize == modeFreq) {
    	 			modeList.add(dest);
    	 		}
    	 	}
    	 	return modeList;
     }

    // The pushHelp method may be used as is:

    protected static String pushHelp(){
        String cmds = "";
	if (GUIactive) {
          cmds +="Available commands:\n" +
                "\tFind employee\n" +
                "\tDiscontinue destination\n" +
                "\tSearch destination\n" +
                "\tRemove employee\n" +
                "\tInformation on database\n" +
                "\tList contents of database\n" +
                "\tText interface activated\n" +
                "\tHelp on available commands\n" +
                "\tQuit database testing\n" ;
       }else {
          cmds +=
        	("discontinue/d <destination> - discontinue the given <destination>\n") +
        	("find/f <Employee> - find the given <Employee>\n") +
        	("gui/g Switch to GUI testing interface\n") +
        	("help/h - display this help menu\n") +
        	("information/i - display information about this Employee database\n") +
        	("list/l - list contents of Employee database\n") +
        	("search/s <destination> - search for the given <destination>\n") +
        	("quit/q - quit\n") +
        	("remove/r <Employee> - remove the given <Employee>\n");
         
       }
            return cmds;
    }

    // The pushQuit method may be used as is:

    protected static String pushQuit(){
        System.exit(0);
        return "";
    }
}