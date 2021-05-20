package edu.ncsu.csc216.pack_scheduler.user.schedule;

import edu.ncsu.csc216.pack_scheduler.course.ConflictException;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.util.ArrayList;

/**
 * The class schedule
 * @author Nick Macon
 * @author Rolf Lewis
 *
 */
public class Schedule {


	/** schedule title */
	private String title;

	/** an array of courses */
	private ArrayList<Course> schedule;

	/**
	 * Creates the schedule of courses with the title
	 */
	public Schedule() {
		schedule = new ArrayList<Course>();
		this.title = "My Schedule";
	}

	/**
	 * Adds a new course to the schedule
	 * @param course the course to be added
	 * @return the updated schedule
	 */
	public boolean addCourseToSchedule(Course course) {

		for (int i = 0; i < schedule.size(); i++) {
			if (!course.isDuplicate(schedule.get(i))) {
				try {
					course.checkConflict(schedule.get(i));
				} catch (ConflictException e) {
					throw new IllegalArgumentException(
							"The course cannot be added due to a conflict.");
				}
			}
			if (course.isDuplicate(schedule.get(i))) {
				throw new IllegalArgumentException(
						"You are already enrolled in " + course.getName());
			}
		}
		return schedule.add(course);
	}

	/**
	 * Removes a course from the schedule
	 * @param course the course to be removed
	 * @return the updated schedule
	 */
	public boolean removeCourseFromSchedule(Course course) {
		for (int i = 0; i < schedule.size(); i++) {
			if(course != null && course.isDuplicate(schedule.get(i))){
				schedule.remove(i);
				return true;
			}
			/**if (course != null && schedule.get(i) != null) {
				schedule.remove(i);
				return true;
			}*/
		}
		return false;
	}

	/**
	 * Resets the schedule
	 */
	public void resetSchedule() {
		schedule = new ArrayList<Course>();
	}

	/**
	 * Gets the schedule of courses
	 * @return the schedule
	 */
	public String[][] getScheduledCourses(){
		String[][] courses = new String[schedule.size()][4];
		for (int i = 0; i < schedule.size(); i++) {
			courses[i] = schedule.get(i).getShortDisplayArray();
		}
		return courses;
	}

	/**
	 * Sets the title for the schedule
	 * @param title the title of the schedule
	 */
	public void setTitle(String title) {
		if (title == null) {
			throw new IllegalArgumentException("Invalid title.");
		} else if (title.equals("")) {
			this.title = "My Schedule";
		} else {
			this.title = title;
		}
	}

	/**
	 * Gets the title for the schedule
	 * @return the title of the schedule
	 */
	public String getTitle() {
		return title;

	}

	/**
	 * Returns the number of credit hours in the student's schedule
	 * @return the number of credit hours in the student's schedule
	 */
	public int getScheduleCredits() {
		int credits = 0;
		for (int i = 0; i < schedule.size(); i++) {
			credits += schedule.get(i).getCredits();
		}

		return credits;
	}

	/**
	 * Returns true if this student could add the parameter course
	 * @param course to reference in status check
	 * @return true if this student could add the parameter course
	 */
	public boolean canAdd(Course course) {
		if (course == null) {
			return false;
		}
		for (int i = 0; i < schedule.size(); i++) {
			if (schedule.get(i).isDuplicate(course)) {
				return false;
			}
			try {
				schedule.get(i).checkConflict(course);
			} catch (ConflictException e) {
				return false;
			}
		}
		return true;
	}
}