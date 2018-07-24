import java.util.*;

///////////////////////////////////////////////////////////////////////////////
//Main Class File:  Texttester.java or GUItester.java
//File:             EmployeeDatabase.java
//Semester:         CS 367 Spring 2018
//
//Author:           Alex Pizzuto (pizzuto@wisc.edu)
//CS Login:         pizzuto@cs.wisc.edu
//Lecturer's Name:  Charles Fischer
//
///////////////////////////////////////////////////////////////////////////////

/**
 * The EmployeeDatabase class stores the employees, 
 * to be tested by the InteractiveDBTester class. The class
 * consists of a list of instances of Employee objects
 * 
 * @author - Alex Pizzuto, CS367, Spring 2018
 * 
 */
public class EmployeeDatabase {
	private static List<Employee> employees; //List of employees in the DB
	
	/**
	 * Constructor for the EmployeeDatabase class
	 * constructs an empty List to start
	 * 
	 */
	public EmployeeDatabase() {
		employees = new ArrayList<Employee>();
	}
	
	/**
	 * Add an employee with the given username e to the end of the database.
	 * If an employee with username e is already in the database, just return.
	 * 
	 * @param e: the username of the Employee to be added
	 * @return if given employee is already in the DB
	 */
	public void addEmployee(String e) {
		if (e == null) {
			throw new IllegalArgumentException(); //Exception if param is null
		} else if (!containsEmployee(e.toLowerCase())) { //make sure employee not in DB
			Employee newGuy = new Employee(e.toLowerCase()); 
			//creates new instance of Employee, formats username to lowercase
			employees.add(newGuy); //Adds newGuy to DB
		} else {
			return; //return if employee already in DB
		}
	}
	
	/**
	 * Add destination d to employee username e in DB. If e 
	 * is not in DB, throw an exception, if d is already on the
	 * wishlist for d, return.
	 * 
	 * @param e tells which wishlist to add d to
	 * @param d new destination to add
	 * @return if d already on wishlist of e
	 */
	public void addDestination(String e, String d) {
		if (e == null || !containsEmployee(e) || d == null) {
			throw new IllegalArgumentException(); 
			//throws exception if e is null, d is null, or e not in DB already
		} else if (hasDestination(e,d)){
			return; //returns if d already on wishlist of e
		} else {
			Iterator<Employee> itrt = iterator(); //creates new iterator
			String usrName = e.toLowerCase();
			while(itrt.hasNext()) {
				Employee emp = itrt.next();
				if(usrName.equals(emp.getUsername())) { //find location of e in DB
					emp.getWishlist().add(d.toLowerCase()); 
					//adds formatted destination to correct user's wishlist
				}
			}
		}
	}
	
	/**
	 * Returns true iff employee e is already
	 * in DB
	 * 
	 * @param e username of employee
	 * @return true if e already in DB, false otherwise
	 */
	public boolean containsEmployee(String e) {
		if(e == null) { //throw exception if e is null
			throw new IllegalArgumentException();
		}
		Iterator<Employee> itrt = iterator();
		boolean bool = false;
		while(itrt.hasNext()) {
			String empName = itrt.next().getUsername(); //employee to test against
			if(e.toLowerCase().equals(empName)) {
				bool = true;
			}
		}
		return bool;
	}
	
	/**
	 * Return true iff d appears in at least one employee's
	 * wishlist in the DB
	 * 
	 * @param d destination to check
	 * @return true iff d on someone's wishlist
	 */
	public boolean containsDestination(String d) {
		if (d == null) { //throw exception if d is null
			throw new IllegalArgumentException();
		}
		Iterator<Employee> itrt = iterator(); //create employee iterator
		while (itrt.hasNext()) {
			List<String> wList = itrt.next().getWishlist();
			Iterator<String> wItrt = wList.iterator();
			// iterate over each employee's wishlist
			while(wItrt.hasNext()) {
				if (wItrt.next().equals(d.toLowerCase())) {
					//if d is on wishlist, return true
					return true;
				}
			}
		} // if exited all while loops without returning, return false
		return false;
	}
	
	/**
	 * Checks to see if a certain user already has 
	 * a specific destination on their wishlist
	 * 
	 * @param e username of employee
	 * @param d destination to check
	 * @return true if wishlist of e already contains d
	 */
	public boolean hasDestination(String e, String d) {
		if (e == null || d == null) { //throw exception if e or d is null
			throw new IllegalArgumentException();
		}
		if(!containsEmployee(e)) {
			return false; //return false if e is not in the DB
		} else {
			Iterator<Employee> itrt = iterator(); //create employee iterator
			while(itrt.hasNext()) {
				Employee emp = itrt.next(); 
				String usrName = emp.getUsername();
				if (usrName.equals(e.toLowerCase())) { //find correct user
					List<String> wList = emp.getWishlist(); 
					//creates list of out of user's wishlist
					Iterator<String> wItrt = wList.iterator();
					while(wItrt.hasNext()) {
						if (wItrt.next().equals(d.toLowerCase())){
							return true;
						} //if an item on emp's wishlist is d, return true
					}
				}
			} //if exited the while loop, then d not on wishlist
		}
		return false;
	}
	
	/**
	 * Return the list of employees who have d on their 
	 * wishlist. If d is not in DB, return a null list
	 * 
	 * @param d String destination to check
	 * @return List<String> of employees with d on wishlist
	 */
	public List<String> getEmployees(String d) { 
		List<String> eList = new ArrayList<String>(); //List to return
		if (d == null) {  //throw null exception if d null
			throw new IllegalArgumentException();
		} else if (!containsDestination(d)) {
			return null; //return null list if d not anywhere in DB
		} else {
			Iterator<Employee> itrt = iterator();
			while(itrt.hasNext()) { //loop over all employees
				Employee emp = itrt.next();
				if (hasDestination(emp.getUsername(),d.toLowerCase())) {
					//check each employee to see if d on their wishlist
					eList.add(emp.getUsername()); //if d on wishlist of emp, add emp
				}
			}
		}
		return eList; 
	}
	
	/**
	 * Return the wishlist for the employee e. If e is not
	 * in DB, return null.
	 * 
	 * @param e String employee of interest
	 * @return wishlist 
	 */
	public List<String> getDestinations(String e) {
		if (e == null) { //throw exception if e is null
			throw new IllegalArgumentException();
		} else {
			Iterator<Employee> itrt = iterator();
			while (itrt.hasNext()) { //iterate over employees
				Employee emp = itrt.next();
				if (emp.getUsername().equals(e.toLowerCase())) {
					return emp.getWishlist(); //return wishlist of Employee emp
				}
			}
		}
		return null; //returns null if e not in DB
	}
	
	/**
	 * Iterator of list for EmployeeDatabase class
	 * 
	 * @return Iterator of List<Employee>
	 */
	public Iterator<Employee> iterator() {
		return employees.iterator();
	}
	
	/**
	 * Remove employee e from DB. If e not in DB, return false,
	 * otherwise, return true.
	 * 
	 * @param e String employee to be removed
	 * @return true if e was in DB, false, otherwise
	 */
	public boolean removeEmployee(String e) {
		if (e == null) { //throw exception if argument is null
			throw new IllegalArgumentException();
		} else if (!containsEmployee(e)) {
			return false; //return false if e not in DB
		} else {
			Iterator<Employee> itrt = iterator();
			while(itrt.hasNext()) { //iterate over employees
				Employee emp = itrt.next(); 
				if (e.toLowerCase().equals(emp.getUsername())) {
					itrt.remove(); //remove all instances of e in employee DB
				}
			}
			return true;
		}
	}
	
	/**
	 * Remove destination d from DB (ie from every wish list in which
	 * d appears). If d nowhere in the DB, return false, otherwise 
	 * return true
	 * 
	 * @param d String destination to check
	 * @return false if d not in DB, true otherwise
	 */
	public boolean removeDestination(String d) {
		if (d == null) { //throw exception if d is null
			throw new IllegalArgumentException();
		} else if (!containsDestination(d)) {
			return false; // return false if d nowhere in DB
		} else {
			Iterator<Employee> itrt = iterator();
			while(itrt.hasNext()) { //iterate over employees
				Employee emp = itrt.next();
				if (hasDestination(emp.getUsername(), d)) {
					//check to see if each employee has d on their wishlist
					List<String> wList = emp.getWishlist();
					Iterator<String> wItrt = wList.iterator();
					while(wItrt.hasNext()) { //find location of d in a wishlist
						String dest = wItrt.next();
						if (dest.equals(d.toLowerCase())) {
							wItrt.remove(); //remove d from wishlist
						}
					}
				}
			}
			return true;
		}
	}
	
	/**
	 * Returns the number of employees currently stored in DB
	 * 
	 * @return Integer number of employees in DB
	 */
	public int size() {
		return employees.size();
	}
	
	
}
