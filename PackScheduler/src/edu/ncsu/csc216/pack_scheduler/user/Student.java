package edu.ncsu.csc216.pack_scheduler.user;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * Creates the Student Object 
 * 
 * @author Shalini Balagopal Nick Macon
 */
public class Student extends User implements Comparable<Student> {
	
	/** Max amount of credits for a given student */
	private int maxCredits;
	
	/** Max amount of credits for any student in system */
	public static final int MAX_CREDITS = 18;
	
	/** the schedule for the student */
	private Schedule schedule;
	
	/** Student's Class roll */

	/**
	 * Constructor for Student Object with ALL parameters
	 * 
	 * @param firstName first name of student
	 * @param lastName last name of student
	 * @param id unique ID of student
	 * @param email of student account
	 * @param password of student account
	 * @param maxCredits max amount of credits for a given student
	 */
	public Student(String firstName, String lastName, String id, String email, String password, int maxCredits) {
		super(firstName, lastName, id, email, password);
		setMaxCredits(maxCredits);
		schedule = new Schedule();
	}
	
	/**
	 * Constructor for Student Object with all parameters EXCEPT maxCredits
	 * 
	 * @param firstName first name of student
	 * @param lastName last name of student
	 * @param id unique ID of student
	 * @param email of student account
	 * @param password of student account
	 */
	public Student(String firstName, String lastName, String id, String email, String password) {
	    this(firstName, lastName, id, email, password, MAX_CREDITS);
	}

	/**
	 * Returns amount of max credits for a student
	 * 
	 * @return int of max credits for a student
	 */
	public int getMaxCredits() {
		return maxCredits;
	}

	/**
	 * Sets max amount of credits for student account
	 * 
	 * @param maxCredits max credits for student account
	 * @throws IllegalArgumentException if input is less than 3 or greater than 18
	 */
	public void setMaxCredits(int maxCredits) {
		if(maxCredits < 3 || maxCredits > 18) {
			throw new IllegalArgumentException("Invalid max credits");
		}
		this.maxCredits = maxCredits;
	}


	@Override
	/**
	 * Returns a String of the fields as a comma separated line
	 * 
	 * @return String value of the object
	 */
	public String toString() {
		return this.getFirstName() + "," + this.getLastName() + "," + this.getId() + "," + this.getEmail() + "," + this.getPassword() + "," + maxCredits;
	}
	
	/**
	 * Compares the Student first name against another Student first name, 
	 * their last names, and their student IDs and returns an integer corresponding 
	 * to the result of that comparison.
	 * @param s Student to compare against this Student (that calls this method)
	 * @return -1 if this Student is less than s
	 * 		    0 if this Student is equal to s
	 * 			1 if this Student is greater than s
	 */
	public int compareTo(Student s) {
		int compareLastName = getLastName().compareTo(s.getLastName());
		if (compareLastName != 0) {
			return compareLastName;
		}
		
		int compareFirstName = getFirstName().compareTo(s.getFirstName());
		if (compareFirstName != 0) {
			return compareFirstName;
		}
		
		int compareID = getId().compareTo(s.getId());
		if (compareID != 0) {
			return compareID;
		}
		
		return 0;
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + maxCredits;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (maxCredits != other.maxCredits)
			return false;
		return true;
	}
	
	/**
     * Gets the students schedule
     * @return students schedule
     */
    public Schedule getSchedule(){
        return schedule;
    }

    /**
     * Returns true if this student can add the selected course to their schedule
     * @param course to reference in status check
     * @return true if this student can add the selected course to their schedule
     */
    public boolean canAdd(Course course) {
        if (schedule.getScheduleCredits() + course.getCredits() > maxCredits) {
            return false;
        }
        if(!schedule.canAdd(course)) {
        	return false;
        }
        return true;
    }
	
	
	
	
}
