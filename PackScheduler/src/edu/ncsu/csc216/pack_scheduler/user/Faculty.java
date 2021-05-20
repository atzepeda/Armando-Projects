package edu.ncsu.csc216.pack_scheduler.user;

import edu.ncsu.csc216.pack_scheduler.user.schedule.FacultySchedule;

/**
 * Creates the faculty object
 * @author nmaco
 * @author atzep
 */
public class Faculty extends User implements Comparable<Faculty> {

	/** number of courses a faculty member is teaching */
	private int maxCourses;
	/** max number of classes a faculty member can teach */
	public static final int MAX_COURSES = 3;
	/** min number of classes a faculty member can teach */
	public static final int MIN_COURSES = 1;
	/** Schedule of faculty member */
	private FacultySchedule schedule;
	
	/**
	 * Constructor for Student Object with ALL parameters
	 * 
	 * @param firstName first name of faculty
	 * @param lastName last name of faculty
	 * @param id unique ID of faculty
	 * @param email of faculty account
	 * @param password of faculty account
	 * @param maxCourses max amount of courses for a given faculty
	 */
	public Faculty(String firstName, String lastName, String id, String email, String password, int maxCourses) {
		super(firstName, lastName, id, email, password);
		setMaxCourses(maxCourses);
		schedule = new FacultySchedule(id);
	}
	
	/**
	 * Constructor for Student Object with all parameters EXCEPT maxCourses
	 * 
	 * @param firstName first name of faculty
	 * @param lastName last name of faculty
	 * @param id unique ID of faculty
	 * @param email of faculty account
	 * @param password of faculty account
	 */
	public Faculty(String firstName, String lastName, String id, String email, String password) {
	    this(firstName, lastName, id, email, password, MAX_COURSES);
	}
	
	/**
	 * Returns amount of max courses for a faculty
	 * 
	 * @return int of max courses for a faculty
	 */
	public int getMaxCourses() {
		return maxCourses;
	}
	
	/**
	 * Sets max amount of courses for student account
	 * 
	 * @param maxCourses max courses for student account
	 * @throws IllegalArgumentException if input is less than 3 or greater than 18
	 */
	public void setMaxCourses(int maxCourses) {
		if(maxCourses < MIN_COURSES || maxCourses > MAX_COURSES) {
			throw new IllegalArgumentException("Invalid max courses");
		}
		this.maxCourses = maxCourses;
	}
	
	/**
	 * Return faculty member's schedule
	 * @return faculty member's schedule
	 */
	public FacultySchedule getSchedule() {
		return schedule;
	}
	
	/**
	 * Checks to see if faculty member is teaching more classes than the max amount
	 * of classes they are allowed to teach
	 * @return boolean value representing if faculty member is overloaded
	 */
	public boolean isOverloaded() {
		if(schedule.getNumScheduledCourses() > maxCourses) {
			return true;
		}
		return false;
	}
	
	@Override
	/**
	 * Returns a String of the fields as a comma separated line
	 * 
	 * @return String value of the object
	 */
	public String toString() {
		return this.getFirstName() + "," + this.getLastName() + "," + this.getId() + "," + this.getEmail() + "," + this.getPassword() + "," + maxCourses;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + maxCourses;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Faculty other = (Faculty) obj;
		if (maxCourses != other.maxCourses)
			return false;
		return true;
	}

	@Override
	public int compareTo(Faculty f) {
		int compareLastName = getLastName().compareTo(f.getLastName());
		if (compareLastName != 0) {
			return compareLastName;
		}
		
		int compareFirstName = getFirstName().compareTo(f.getFirstName());
		if (compareFirstName != 0) {
			return compareFirstName;
		}
		
		int compareID = getId().compareTo(f.getId());
		if (compareID != 0) {
			return compareID;
		}
		return 0;
	}
}
