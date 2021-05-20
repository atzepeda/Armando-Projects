package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Interface for Conflict 
 * @author Shalini Balagopal
 */
public interface Conflict {
	
	/**
	 * Checks for conflict and throws a ConflictException if the Activity conflicts with
	 * an Activity that already exists. Conflict exists if they share at least one day with
	 * start time and end time values overlapping.
	 * @param possibleConflictingActivity that may conflict with another Activity
	 * @throws ConflictException if the two Activity Objects conflict
	 */
	void checkConflict(Activity possibleConflictingActivity) throws ConflictException;

}
