package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * Creates an exception that gets thrown when the validation FSM hits an invalid state.
 * @author Rolf Lewis
 */
public class InvalidTransitionException extends Exception {

	/** ID used for serialization. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates an InvalidTransitionException with an error message passed through
	 * to the parent constructor.
	 * @param message error message to pass to Exception
	 */
	public InvalidTransitionException(String message) {
		super(message);
	}
	
	/**
	 * Creates an InvalidTransitionException with a specific message passed to the
	 * parameterized constructor.
	 */
	public InvalidTransitionException() {
		this("Invalid FSM Transition.");
	}
}
