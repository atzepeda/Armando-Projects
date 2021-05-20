package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * This class acts as a validator for the course names passed in by the user.
 * 
 * @author Rolf Lewis
 */
public class CourseNameValidator {

	/** Valid States **/
	// State at which no letters have been identified
	private final State iState = new InitialState();

	// State at which a letter has been identified
	private final State lState =  new LetterState();

	// State at which a digit has been identified
	private final State dState = new DigitState();

	// State at which a suffix has been identified
	private final State sufState = new SuffixState();

	/** Current State **/
	private State state = iState;

	// Index of Character being inspected
	private int charIndex;
	// Number of digits processed so far
	int digitIndex = 0;
	
	// Max number of starting chars
	private static final int MAX_LEAD_CHARS = 4;
	
	// Max number of digits
	private static final int MAX_DIGITS = 3;
	
	// Minimum name length
	private static final int MIN_LENGTH = 4;

	/**
	 * Returns true if the passed in course name is valid, throws a labeled
	 * InvalidTransitionException if not.
	 * @param courseName to be evaluated
	 * @return true if courseName is valid
	 * @throws InvalidTransitionException if courseName is invalid
	 */
	public boolean isValid(String courseName) throws InvalidTransitionException {

		// Initial State
		state = iState;
		// reset Index of char being observed
		charIndex = 0;
		// reset number of digits processed
		digitIndex = 0;
		// Char being observed
		char c;

		try {
			while (charIndex < courseName.length()) {
				c = courseName.charAt(charIndex);

				if (!Character.isDigit(c) && !Character.isLetter(c)) {
					state.onOther();
				}

				if (Character.isLetter(c) ) {
					state.onLetter();
				}

				if (Character.isDigit(c)) {
					digitIndex++;
					state.onDigit();
				}

				charIndex++;
			}
		} catch (InvalidTransitionException e) {
			throw new InvalidTransitionException(e.getMessage());
		}
		
		if (charIndex < MIN_LENGTH) {
			throw new InvalidTransitionException("Course name must be at least 4 characters.");
		}
		
		return true;
	}

	/**
	 * Parent class to all of the states that the FSM can have. Unified
	 * onOther class throws IT exception.
	 * @author Rolf Lewis
	 */
	public abstract class State {

		/**
		 * What the FSM should do when it encounters a letter
		 * @throws InvalidTransitionException if letter transition is invalid
		 */
		public abstract void onLetter() throws InvalidTransitionException ;
		
		/**
		 * What the FSM should do when it encounters a digit
		 * @throws InvalidTransitionException if digit transition is invalid
		 */
		public abstract void onDigit() throws InvalidTransitionException;

		/**
		 * What the FSM should do when it encounters an invalid character
		 * @throws InvalidTransitionException if the next character is invalid
		 */
		public void onOther() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name can only contain letters and digits.");
		}
	}

	/**
	 * State that the FSM starts in
	 * @author Rolf Lewis
	 */
	public class InitialState extends State {

		@Override
		public void onLetter() {
			state = lState;
		}

		@Override
		public void onDigit() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name must start with a letter.");
		}
	}

	/**
	 * State for when the FSM found a letter in the last loop.
	 * @author Rolf Lewis
	 */
	public class LetterState extends State {

		@Override
		public void onLetter() throws InvalidTransitionException {
			if (charIndex >= MAX_LEAD_CHARS) {
				throw new InvalidTransitionException("Course name cannot start with more than 4 letters.");
			}
		}

		@Override
		public void onDigit() throws InvalidTransitionException {
				state = dState;
		}
	}

	/**
	 * State for when the FSM found a digit in the last loop.
	 * @author Rolf Lewis
	 */
	public class DigitState extends State {

		@Override
		public void onLetter() throws InvalidTransitionException {
			if (digitIndex == MAX_DIGITS) {
				state = sufState;
			} else if (digitIndex < MAX_DIGITS) {
				throw new InvalidTransitionException("Course name must have 3 digits.");
			} else if (digitIndex > MAX_DIGITS) {
				throw new InvalidTransitionException("Course name can only have 3 digits.");
			}
		}

		@Override
		public void onDigit() throws InvalidTransitionException {
			if (digitIndex > MAX_DIGITS) {
				throw new InvalidTransitionException("Course name can only have 3 digits.");
			}
		}
	}

	/**
	 * State for when the FSM found a suffix in the last loop.
	 * @author Rolf Lewis
	 */
	public class SuffixState  extends State {

		@Override
		public void onLetter() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name can only have a 1 letter suffix.");
		}

		@Override
		public void onDigit() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name cannot contain digits after the suffix.");
		}
	}
}
