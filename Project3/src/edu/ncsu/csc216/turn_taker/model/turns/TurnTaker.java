/**
 * 
 */
package edu.ncsu.csc216.turn_taker.model.turns;

import java.util.Observable;

/**
 * TurnTaker class extends Observable and can be used to create a TurnTaker
 * object that has the field of both name and turnCount.
 * 
 * @author wnwang
 *
 */
public class TurnTaker extends Observable {

	/**
	 * String variable holding the name of the TurnTaker object.
	 */
	private String name;
	/**
	 * Integer value holding the turnCount of the TurnTaker object.
	 */
	private int turnCount;

	/**
	 * Constructs a TurnTaker object by calling the constructor with zero as the
	 * numTurns parameter value.
	 * 
	 * @param name
	 *            the name of the TurnTaker object
	 */
	public TurnTaker(String name) {
		this(name, 0);
	}

	/**
	 * Constructs a TurnTaker with given state of the fields.
	 * 
	 * @param name
	 *            the name of the TurnTaker object
	 * @param numTurns
	 *            the numTurns of the TurnTaker object
	 * @throws IllegalArgumentException
	 *             if name is null or the empty string or numTurns is less than zero
	 */
	public TurnTaker(String name, int numTurns) {
		if (name == null || name.isEmpty() || name.trim().length() == 0 || numTurns < 0)
			throw new IllegalArgumentException();
		setName(name);
		setTurnCount(numTurns);
		setChanged();
		notifyObservers(this);
	}

	/**
	 * Sets the name field of the TurnTaker object.
	 * 
	 * @param name
	 *            the name to set
	 */
	private void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the turnCount field of the TurnTaker object.
	 * 
	 * @param turnCount
	 *            the turnCount to set
	 */
	private void setTurnCount(int turnCount) {
		this.turnCount = turnCount;
	}

	/**
	 * Retruns the name of the TurnTaker object.
	 * 
	 * @return the name of the TurnTaker object
	 */
	public String getName() {
		return name;
	}

	/**
	 * Retruns the turnCount of the TurnTaker object.
	 * 
	 * @return the turnCount of the TurnTaker object
	 */
	public int getTurnCount() {
		return turnCount;
	}

	/**
	 * Increments the turnCount field and notifies observers of the change.
	 */
	public void incrementTurnCount() {
		turnCount++;
		setChanged();
		notifyObservers(this);
	}

	/**
	 * Returns a hash code value for the object. This method is supported for the
	 * benefit of hash tables such as those provided by {@link java.util.HashMap}.
	 * <p>
	 * The general contract of {@code hashCode} is:
	 * <ul>
	 * <li>Whenever it is invoked on the same object more than once during an
	 * execution of a Java application, the {@code hashCode} method must
	 * consistently return the same integer, provided no information used in
	 * {@code equals} comparisons on the object is modified. This integer need not
	 * remain consistent from one execution of an application to another execution
	 * of the same application.
	 * <li>If two objects are equal according to the {@code equals(Object)} method,
	 * then calling the {@code hashCode} method on each of the two objects must
	 * produce the same integer result.
	 * <li>It is <em>not</em> required that if two objects are unequal according to
	 * the {@link java.lang.Object#equals(java.lang.Object)} method, then calling
	 * the {@code hashCode} method on each of the two objects must produce distinct
	 * integer results. However, the programmer should be aware that producing
	 * distinct integer results for unequal objects may improve the performance of
	 * hash tables.
	 * </ul>
	 * <p>
	 * As much as is reasonably practical, the hashCode method defined by class
	 * {@code Object} does return distinct integers for distinct objects. (This is
	 * typically implemented by converting the internal address of the object into
	 * an integer, but this implementation technique is not required by the
	 * Java&trade; programming language.)
	 *
	 * @return a hash code value for this object.
	 * @see java.lang.Object#equals(java.lang.Object)
	 * @see java.lang.System#identityHashCode
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * Returns true if the names of TurnTaker objects are an exact match.
	 * 
	 * @return true if the names of TurnTaker objects are an exact match, false if
	 *         otherwise
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		// if (this == obj)
		// return true;
		if (obj == null)
			return false;
		// if (!(obj instanceof TurnTaker))
		// return false;
		TurnTaker other = (TurnTaker) obj;
		// if (name == null) {
		// if (other.name != null)
		// return false;
		// } else
		if (!name.equals(other.name))
			return false;
		return true;
	}

}