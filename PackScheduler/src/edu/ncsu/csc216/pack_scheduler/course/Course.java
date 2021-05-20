package edu.ncsu.csc216.pack_scheduler.course;

import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.course.validator.CourseNameValidator;
import edu.ncsu.csc216.pack_scheduler.course.validator.InvalidTransitionException;

/**
 * Creates the Course Object that contains information about a Course
 * including name, section, instructor, credit hours, meeting times, etc.
 * 
 * @author Shalini Balagopal
 * @author Rolf Lewis
 */
public class Course extends Activity implements Comparable<Course> {
	/** Length of section String */
	private static final int SECTION_LENGTH = 3;
	/** Course Name Validator */
	private final CourseNameValidator validator = new CourseNameValidator();
	/** Maximum value of creditHours field */
	private static final int MAX_CREDITS = 5;
	/** Minimum value for creditHours field */
	private static final int MIN_CREDITS = 1;
	
	/** Course's name. */
	private String name;
	/** Course's section. */
	private String section;
	/** Course's credit hours */
	private int credits;
	/** Course's instructor */
	private String instructorId;
	/** Course's enrollment cap */
	private int enrollmentCap;
	/** Course's roll */
	private CourseRoll roll;
	
	/**
	 * Creates a Course object with values for all fields
	 * @param name name of Course
	 * @param title title of Course
	 * @param section section number of Course
	 * @param credits amount of credit hours for Course
	 * @param instructorId ID of instructor for Course
	 * @param enrollmentCap max number of students in class
	 * @param meetingDays days of the week Course meets
	 * @param startTime start time of Course
	 * @param endTime end time of Course
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap, String meetingDays, int startTime, int endTime) {
		super(title, meetingDays, startTime, endTime);
		
		try {
			
			if (name == null || name.equals("")) {
				throw new InvalidTransitionException();
			}
			validator.isValid(name);
		} catch (InvalidTransitionException e) {
			throw new IllegalArgumentException("Invalid course name");
		}
		
		if (title == null || name.equals("")) { 
			throw new IllegalArgumentException("Invalid course title");
		}
		
	    setName(name);
	    setSection(section);
	    setCredits(credits);
	    setInstructorId(instructorId);
	    this.enrollmentCap = enrollmentCap;
		roll = new CourseRoll(enrollmentCap, this);
	}

	/**
	 * Creates a Course object with name, title, section, instructorID, and meetingDays
	 * @param name name of Course
	 * @param title title of Course
	 * @param section section number of Course
	 * @param credits amount of credit hours for Course
	 * @param instructorId ID of instructor for Course
	 * @param enrollmentCap max number of students in class
	 * @param meetingDays days of the week Course meets
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap, String meetingDays) {
		this(name, title, section, credits, instructorId, enrollmentCap, meetingDays, 0, 0);
	}

	/**
	 * Returns the Course's name 
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the Course's name. If the name is null or has
	 * a length less than 4 or greater than 6, then an
	 * IllegalArgumentException is thrown.
	 * @param name the name to set
	 * @throws IllegalArgumentException if name is null or
	 * length is less than 4 or greater than 6
	 */
	private void setName(String name) {
	    this.name = name;
	}
	
	/**
	 * Gets the Course's section
	 * @return the section
	 */
	public String getSection() {
		return section;
	}
	
	/**
	 * Sets the Course's section. If the section is null
	 * or not exactly 3 digits then an IllegalArgumentException
	 * is thrown.
	 * @param section the section to set
	 * @throws IllegalArgumentException if section is null or
	 * not 3 digits
	 */
	public void setSection(String section) {
	    if (section == null) {
	        throw new IllegalArgumentException("Invalid section number");
	    }
	    if (section.length() != SECTION_LENGTH) {
	        throw new IllegalArgumentException("Invalid section number");
	    }
	    for (int i = 0; i < section.length(); i++) {
	    		char ch = section.charAt(i);
	    		if (!Character.isDigit(ch)) {
	    			throw new IllegalArgumentException("Invalid section number");
	    		}
	    }
		this.section = section;
	}
	
	/**
	 * Gets the Course's credit hours
	 * @return the credit hours
	 */
	public int getCredits() {
		return credits;
	}
	
	/**
	 * Sets the Course's credit hours. If the credit hours are
	 * less than 1 or greater than 5 then an IllegalArgumentException
	 * is thrown.
	 * @param credits the credit hours to set
	 * @throws IllegalArgumentException if credits is less than 1 or
	 * greater than 5
	 */
	public void setCredits(int credits) {
		if (credits < MIN_CREDITS || credits > MAX_CREDITS) {
			throw new IllegalArgumentException();
		}
		this.credits = credits;
	}
	
	/**
	 * Gets the Course's instructor
	 * @return the instructorId
	 */
	public String getInstructorId() {
		return instructorId;
	}
	
	/**
	 * Sets the Course's instructor. If the instructor ID
	 * is null or empty, then an IllegalArgumentException is thrown.
	 * @param instructorId the instructorId to set
	 * @throws IllegalArgumentException if instructor is empty
	 * or null
	 */
	public void setInstructorId(String instructorId) {
	    if (instructorId == null) {
	    	this.instructorId = instructorId;
	        return;
	    }
	    if (instructorId.length() == 0) {
	        throw new IllegalArgumentException("Invalid instructor unity id");
	    }
		this.instructorId = instructorId;
	}
	
	/**
	 * Sets the Course's meeting days. If meetingDays is null or has any character
	 * other than M, T, W, H, F, or A or if A is present and is not the only 
	 * character, then an IllegalArgumentException is thrown.
	 * @param meetingDays the meetingDays to set
	 * @throws IllegalArgumentException if meetingDays is null or contains a char
	 * other than M, T, W, F, or A, or if A is present but not the only char
	 */
	@Override
	public void setMeetingDays(String meetingDays) {
	    if (meetingDays == null) {
	        throw new IllegalArgumentException("Invalid meeting days");
	    }
	    if (meetingDays.length() == 0) {
	        throw new IllegalArgumentException("Invalid meeting days");
	    }
	    for (int i = 0; i < meetingDays.length(); i++) {
	    		char c = meetingDays.charAt(i);
	    		if (c != 'M' && c != 'T' && c != 'W' && c != 'H' && c != 'F' && c != 'A') {
	    			throw new IllegalArgumentException("Invalid meeting days");
	    		}
	    		if (c == 'A' && meetingDays.indexOf(c) != 0) {
	    			throw new IllegalArgumentException("Invalid meeting days");
	    		}
	    }
		super.setMeetingDays(meetingDays);
	}

	/***
	 * Generates a hashCode for Course using all fields
	 * @return hashCode for Course
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + credits;
		result = prime * result + ((instructorId == null) ? 0 : instructorId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((section == null) ? 0 : section.hashCode());
		return result;
	}

	/**
	 * Compares a given object to this object for equality on all fields.
	 * @param obj the Object to compare
	 * @return true if objects are the same on all fields
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (credits != other.credits)
			return false;
		if (instructorId == null) {
			if (other.instructorId != null)
				return false;
		} else if (!instructorId.equals(other.instructorId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (section == null) {
			if (other.section != null)
				return false;
		} else if (!section.equals(other.section))
			return false;
		return true;
	}

	/**
	 * Returns a comma separated value String of all Course fields.
	 * @return String representation of Course
	 */
	@Override
	public String toString() {
		if (super.getMeetingDays().equals("A")) {
			return name + "," + super.getTitle() + "," + section + "," + credits + "," + instructorId + ","
					+ enrollmentCap + "," + super.getMeetingDays();
		}
		return name + "," + super.getTitle() + "," + section + "," + credits + "," + instructorId + "," + enrollmentCap
				+ "," + super.getMeetingDays() + "," + super.getStartTime() + "," + super.getEndTime();
	}

	/**
	 * Returns a String array containing the Course name, section, title, and meeting info
	 * @return String array of Course information
	 */
	@Override
	public String[] getShortDisplayArray() {
		String[] s = {getName(), getSection(), getTitle(), getMeetingString(), "" + roll.getOpenSeats()};
		return s;
	}

	/**
	 * Returns a String array containing the Course name, section, title,
	 * credits, instructor ID, meeting info, and an empty String
	 * @return String array of Course information
	 */
	@Override
	public String[] getLongDisplayArray() {
		String[] s = {getName(), getSection(), getTitle(), Integer.toString(getCredits()), getInstructorId(), getMeetingString(), ""};
		return s;
	}

	/**
	 * Checks the Activity passed as a parameter is an instance of Course,
	 * and casts it as a Course Object so that it can compare its name against the
	 * name of the Course that originally called this method. Returns true if this name
	 * matches the name of the Activity object. Returns false otherwise.
	 * @param a Activity Object to compare to this Course
	 * @return true if title of a equals title of this Course
	 * 		   false otherwise
	 */
	@Override
	public boolean isDuplicate(Activity a) {
        if (a instanceof Course) {
        		Course c = (Course) a;
        		if (c.getName().equals(this.getName())) {
        			return true;
        		}
        }
		return false;
	}
	
	/**
	 * Compares the Course names of two Courses, then their sections and returns an
	 * integer corresponding to the result of that comparison.
	 * @param c Course to compare against this Course (that calls this method)
	 * @return -1 if this Course is less than c
	 * 		    0 if this Course is equal to c
	 * 			1 if this Course is greater than c
	 */
	public int compareTo(Course c) {
		int compareName = getName().compareTo(c.getName());
		if (compareName != 0) { // if courses are not equal in name
			return compareName; // returns -1 or 1 if less than or equal
		}
		
		int compareSection = getSection().compareTo(c.getSection());
		if (compareSection != 0) { // If sections are not equal in section
			return compareSection; // returns -1 or 1 if less than or equal
		}
		
		return 0; // otherwise returns 0 as these are equal
	}
	
	/**
	 * Returns the current CourseRoll
	 * @return the current CourseRoll
	 */
	public CourseRoll getCourseRoll() {
        return roll;
    }
}
