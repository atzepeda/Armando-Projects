/**
 * 
 */
package edu.ncsu.csc216.turn_taker.model.turns;

import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.Observer;

/**
 * TurnActivity class can be used to create a TurnActivity object that has name,
 * description, and a list of turn takers.
 * 
 * @author wnwang
 *
 */
public class TurnActivity extends Observable implements Observer, Comparable<TurnActivity> {

	/**
	 * String variable holding the name of the TurnActivity object.
	 */
	private String turnActivityName;
	/**
	 * String variable holding the description of the TurnActivity object.
	 */
	private String turnActivityDescription;
	/**
	 * TurnTakerQueue object that has a list of turnTakers in TurnActivity.
	 */
	private TurnTakerQueue turnTakers;

	/**
	 * Constructs a TurnActivity with the given parameters.
	 * 
	 * @param name
	 *            the name of the TurnActivity object
	 * @param description
	 *            the description of the TurnActivity object
	 * @throws IllegalArgumentException
	 *             if name or description is either null or an empty string
	 */
	public TurnActivity(String name, String description) throws IllegalArgumentException {
		setTurnActivityName(name);
		setTurnActivityDescription(description);
		turnTakers = new TurnTakerQueue();
		turnTakers.addObserver(this);
	}

	/**
	 * Sets the turnActivityName field of the TurnActivity object.
	 * 
	 * @param turnActivityName
	 *            the turnActivityName to set
	 * @throws IllegalArgumentException
	 *             if turnActivityName is either null or an empty string
	 */
	private void setTurnActivityName(String turnActivityName) {
		if (turnActivityName == null || turnActivityName.isEmpty())
			throw new IllegalArgumentException();
		this.turnActivityName = turnActivityName;
	}

	/**
	 * Sets the turnActivityDescription field of the TurnActivity object.
	 * 
	 * @param turnActivityDescription
	 *            the turnActivityDescription to set
	 * @throws IllegalArgumentException
	 *             if turnActivityDescription is either null or an empty string
	 */
	private void setTurnActivityDescription(String turnActivityDescription) {
		if (turnActivityDescription == null || turnActivityDescription.isEmpty())
			throw new IllegalArgumentException();
		this.turnActivityDescription = turnActivityDescription;
	}

	/**
	 * Sets the fields to the parameter values.
	 * 
	 * @param name
	 *            the name of the TurnActivity object
	 * @param description
	 *            the description of the TurnActivity object
	 * @throws IllegalArgumentException
	 *             if either parameter is null or an empty field
	 */
	public void editTurnActivity(String name, String description) throws IllegalArgumentException {
		setTurnActivityName(name);
		setTurnActivityDescription(description);
		// Notify observers of TurnActivity that the collection has changed.
		setChanged();
		notifyObservers(this);
	}

	/**
	 * Retruns the turnActivityName of the TurnActivity object.
	 * 
	 * @return the turnActivityName of the TurnActivity object
	 */
	public String getTurnActivityName() {
		return turnActivityName;
	}

	/**
	 * Retruns the turnActivityDescription of the TurnActivity object.
	 * 
	 * @return the turnActivityDescription of the TurnActivity object
	 */
	public String getTurnActivityDescription() {
		return turnActivityDescription;
	}

	/**
	 * Delegates to the TurnTakerQueue to take the turn.
	 * 
	 * @throws NoSuchElementException
	 *             if the queue is empty
	 */
	public void takeTurn() throws NoSuchElementException {
		turnTakers.takeTurn();
		// Notify observers of TurnActivity that the collection has changed.
		setChanged();
		notifyObservers(this);
	}

	/**
	 * Retruns the turnTakers of the TurnActivity object.
	 * 
	 * @return the turnTakers of the TurnActivity object
	 */
	public TurnTakerQueue getTurnTakers() {
		return turnTakers;
	}

	/**
	 * Adds the given TurnTaker to the TurnTakerQueue.
	 * 
	 * @param t
	 *            the TurnTaker to be added
	 * @throws IllegalArgumentException
	 *             if the TurnTaker is null or a duplicate
	 */
	public void addTurnTaker(TurnTaker t) throws IllegalArgumentException {
		try {
			turnTakers.addTurnTaker(t);
		} catch (NullPointerException e) {
			throw new IllegalArgumentException();
		}
		// Notify observers of TurnActivity that the collection has changed.
		setChanged();
		notifyObservers(this);
	}

	/**
	 * Removes the TurnTaker with the given name from the TurnTakerQueue.
	 * 
	 * @param name
	 *            the name of the TurnTaker to be removed
	 * @throws NoSuchElementException
	 *             if the queue is empty
	 * @throws IllegalArgumentException
	 *             if the TurnTaker is not in the queue
	 */
	public void removeTurnTaker(String name) throws NoSuchElementException, IllegalArgumentException {
		turnTakers.removeTurnTaker(name);
		// Notify observers of TurnActivity that the collection has changed.
		setChanged();
		notifyObservers(this);
	}

	/**
	 * TurnActivities are compared by their name ignoring case.
	 * 
	 * @param activity
	 *            the TurnActivity to be compared to
	 * @return a negative integer, zero, or a positive integer as the specified
	 *         String is greater than, equal to, or less than this String, ignoring
	 *         case considerations.
	 */
	@Override
	public int compareTo(TurnActivity activity) {
		return this.getTurnActivityName().compareToIgnoreCase(activity.getTurnActivityName());
	}

	/**
	 * This method is called whenever the observed object is changed. An application
	 * calls an <tt>Observable</tt> object's <code>notifyObservers</code> method to
	 * have all the object's observers notified of the change. If the Observable is
	 * an instance of the TurnTakerQueue, then the observers of the TurnActivity
	 * should be updated. The current instance is passed to notifyObservers().
	 *
	 * @param o
	 *            the observable object.
	 * @param arg
	 *            an argument passed to the <code>notifyObservers</code> method.
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof TurnTakerQueue) {
			setChanged();
			notifyObservers(this);
		}
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
		result = prime * result + ((turnActivityName == null) ? 0 : turnActivityName.hashCode());
		return result;
	}

	/**
	 * Indicates whether some other object is "equal to" this one.
	 * <p>
	 * The {@code equals} method implements an equivalence relation on non-null
	 * object references:
	 * <ul>
	 * <li>It is <i>reflexive</i>: for any non-null reference value {@code x},
	 * {@code x.equals(x)} should return {@code true}.
	 * <li>It is <i>symmetric</i>: for any non-null reference values {@code x} and
	 * {@code y}, {@code x.equals(y)} should return {@code true} if and only if
	 * {@code y.equals(x)} returns {@code true}.
	 * <li>It is <i>transitive</i>: for any non-null reference values {@code x},
	 * {@code y}, and {@code z}, if {@code x.equals(y)} returns {@code true} and
	 * {@code y.equals(z)} returns {@code true}, then {@code x.equals(z)} should
	 * return {@code true}.
	 * <li>It is <i>consistent</i>: for any non-null reference values {@code x} and
	 * {@code y}, multiple invocations of {@code x.equals(y)} consistently return
	 * {@code true} or consistently return {@code false}, provided no information
	 * used in {@code equals} comparisons on the objects is modified.
	 * <li>For any non-null reference value {@code x}, {@code x.equals(null)} should
	 * return {@code false}.
	 * </ul>
	 * <p>
	 * The {@code equals} method for class {@code Object} implements the most
	 * discriminating possible equivalence relation on objects; that is, for any
	 * non-null reference values {@code x} and {@code y}, this method returns
	 * {@code true} if and only if {@code x} and {@code y} refer to the same object
	 * ({@code x == y} has the value {@code true}).
	 * <p>
	 * Note that it is generally necessary to override the {@code hashCode} method
	 * whenever this method is overridden, so as to maintain the general contract
	 * for the {@code hashCode} method, which states that equal objects must have
	 * equal hash codes.
	 *
	 * @param obj
	 *            the reference object with which to compare.
	 * @return {@code true} if this object is the same as the obj argument;
	 *         {@code false} otherwise.
	 * @see #hashCode()
	 * @see java.util.HashMap
	 */
	@Override
	public boolean equals(Object obj) {
		// if (this == obj)
		// return true;
		if (obj == null)
			return false;
		// if (!(obj instanceof TurnActivity))
		// return false;
		TurnActivity other = (TurnActivity) obj;
		// if (turnActivityName == null) {
		// if (other.turnActivityName != null)
		// return false;
		// } else
		if (!turnActivityName.equals(other.turnActivityName))
			return false;
		return true;
	}

	/**
	 * Returns the TurnActivity name.
	 * 
	 * @return the TurnActivity name
	 */
	@Override
	public String toString() {
		return getTurnActivityName();
	}

}