package edu.ncsu.csc216.pack_scheduler.course.roll;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.util.ArrayQueue;
import edu.ncsu.csc216.pack_scheduler.util.LinkedAbstractList;
/**
 * Creates the roll for a course
 * @author Rolf Lewis
 * @author Nick Macon
 * @author yueyu
 *
 */
public class CourseRoll {
	
	/** list containing students on the course roll */
	private LinkedAbstractList<Student> roll;

	/** capacity of the course */
	private int enrollmentCap;
	
	/** course in the student's schedule */
	private Course course;

	/** minimum enrollment for the course */
	private final static int MIN_ENROLLMENT = 10;

	/** maximum enrollment for the course */
	private final static int MAX_ENROLLMENT = 250;
	
	/** waitlist for courses */
	private ArrayQueue<Student> waitlist;
	
	/** max size for waitlist */
	private final static int MAX_WAITLIST = 10;
	
	

	/**
	 * Constructor for CourseRoll Object, with a parameter representing max class size
	 * @param cap maximum students allowed in class
	 * @param currentCourse the current course courseRoll is a part of 
	 */
	public CourseRoll(int cap, Course currentCourse) {
		roll = new LinkedAbstractList<Student>(cap);
		setEnrollmentCap(cap);
		waitlist = new ArrayQueue<Student>(MAX_WAITLIST);
		if(currentCourse == null) {
			throw new IllegalArgumentException();
		}
		course = currentCourse;
	}

	/**
	 * Returns this class' enrollment cap
	 * @return this class' enrollment cap
	 */
	public int getEnrollmentCap() {
		return enrollmentCap;
	}

	/**
	 * Sets this class' enrollment cap
	 * @param cap this class' enrollment cap
	 */
	public void setEnrollmentCap(int cap) {
		if (cap < MIN_ENROLLMENT || cap > MAX_ENROLLMENT) {
			throw new IllegalArgumentException();
		}
		
		if (cap < roll.size()) {
			throw new IllegalArgumentException();
		}
		
		enrollmentCap = cap;
		roll.setCapacity(cap);
	}

	/**
	 * Adds the mentioned student to the class roll and gets one student closer to cap
	 * @param student to add to class roll
	 */
	public void enroll(Student student) {
		if (canEnroll(student)) {
			if(roll.size() < enrollmentCap) {
				roll.add(student);
			} else {
				try {
					waitlist.enqueue(student);
				} catch (IllegalArgumentException e) {
					throw new IllegalArgumentException();
				}
			}
		} else {
			throw new IllegalArgumentException();
		}

	}

	/**
	 * Removes the mentioned student from the class roll and frees one space
	 * @param student to remove from the class roll
	 */
	public void drop(Student student) {
		if (student == null) {
			throw new IllegalArgumentException();
		}
		else {
			if(roll.remove(student)) {
				if(!(waitlist.isEmpty())) {
					Student newStu = waitlist.dequeue();
					roll.add(newStu);
					newStu.getSchedule().addCourseToSchedule(course);
				}
			} else {
				waitlist.remove(student);
			}
		}
	}

	/**
	 * Returns the number of open seats in a class
	 * @return the number of open seats in a class
	 */
	public int getOpenSeats() {
		return enrollmentCap - roll.size();
	}

	/**
	 * Returns true if the student would be able to enroll in this class if they wanted
	 * @param student to reference in status check
	 * @return true if the student would be able to enroll in this class if they wanted
	 */
	public boolean canEnroll(Student student) {
		if (student == null || (roll.size() >= enrollmentCap && waitlist.size() >= MAX_WAITLIST)) {
			return false;
		}
		
		for (int i = 0; i < roll.size(); i++) {
			if (roll.get(i).getId().equals(student.getId())) {
				return false;
			}
		}
		
		boolean found = true;
		Student currentStudent = null;
		for (int i = 0; i < waitlist.size(); i++) {
			currentStudent = waitlist.dequeue();
			if (currentStudent.getId().equals(student.getId())) {
				found = false;
			}
			waitlist.enqueue(currentStudent);
		}
		return found;
	}
	
	/**
	 * A method to return the number of students waitlisted
	 * @return an integer representing the number of students on the waitlist
	 */
	public int getNumberOnWaitlist() {
		return waitlist.size();
	}
	
	/**
	 * Gets the roll as an array
	 * @return the 2d array
	 */
	public String[][] get2DArrayRoll() {
		String[][] array = new String[roll.size()][3];
	    for(int i = 0; i < array.length; i++){
	        array[i][0] = roll.get(i).getFirstName();
	        array[i][1] = roll.get(i).getLastName();
	        array[i][2] = roll.get(i).getId();
	    }
	    return array;
	}
}
