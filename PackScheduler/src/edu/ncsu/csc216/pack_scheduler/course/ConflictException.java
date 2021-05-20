package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Creates the ConflictException Object.
 * @author Shalini Balagopal
 */
public class ConflictException extends Exception {

	/** ID used for serialization. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates a ConflictException with an error message passed through
	 * to the parent constructor.
	 * @param message error message to pass to Exception
	 */
	public ConflictException(String message) {
		super(message);
	}
	
	/**
	 * Creates a ConflictException with a specific message passed to the
	 * parameterized constructor.
	 */
	public ConflictException() {
		this("Schedule conflict.");
	}

}
