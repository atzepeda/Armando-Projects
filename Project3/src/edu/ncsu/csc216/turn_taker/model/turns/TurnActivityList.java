/**
 * 
 */
package edu.ncsu.csc216.turn_taker.model.turns;

import java.util.Observable;
import java.util.Observer;

import edu.ncsu.csc216.turn_taker.model.util.SortedArrayList;

/**
 * TurnActivityList class can be used to create a TurnActivityList that has a
 * SortedArrayList object with a list of TurnActivity objects.
 * 
 * @author wnwang
 *
 */
public class TurnActivityList extends Observable implements Observer {

	/**
	 * SortedArrayList of the TurnActivityList holding a list of TurnActivity.
	 */
	private SortedArrayList<TurnActivity> activities;

	/**
	 * Constructs an empty SortedArrayList.
	 */
	public TurnActivityList() {
		activities = new SortedArrayList<TurnActivity>();
	}

	/**
	 * Creates a TurnActivity from the given name and description and adds the
	 * TurnActivity to the SortedArrayList.
	 * 
	 * @param name
	 *            the name of the TurnActivity object
	 * @param description
	 *            the description of the TurnActivity object
	 * @throws IllegalArgumentException
	 *             if the name or description are null or the empty string or if the
	 *             name is a duplicate of another TurnActivity
	 */
	public void addTurnActivity(String name, String description) throws IllegalArgumentException {
		addTurnActivity(new TurnActivity(name, description));
	}

	/**
	 * Adds the TurnActivity to the SortedArrayList.
	 * 
	 * @param t
	 *            the TurnActivity object to be added
	 * @throws IllegalArgumentException
	 *             if the name is a duplicate of another TurnActivity
	 */
	public void addTurnActivity(TurnActivity t) throws IllegalArgumentException {
		t.addObserver(this);
		activities.add(t);
		// Notify observers of TurnActivityList that the collection has changed.
		setChanged();
		notifyObservers(this);
	}

	/**
	 * Removes the TurnActivity at the given index.
	 * 
	 * @param idx
	 *            the index of TurnActivity to be removed
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of bounds
	 */
	public void removeTurnActivity(int idx) throws IndexOutOfBoundsException {
		activities.remove(idx);
		// Notify observers of TurnActivityList that the collection has changed.
		setChanged();
		notifyObservers(this);
	}

	/**
	 * Returns the TurnActivity at the given index.
	 * 
	 * @param idx
	 *            the given index of the TurnActivity
	 * @return the TurnActivity at the given index
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of bounds
	 */
	public TurnActivity getTurnActivity(int idx) throws IndexOutOfBoundsException {
		return activities.get(idx);
	}

	/**
	 * Returns the number of elements in the SortedArrayList.
	 * 
	 * @return the number of elements in the SortedArrayList
	 */
	public int size() {
		return activities.size();
	}

	/**
	 * This method is called whenever the observed object is changed. An application
	 * calls an <tt>Observable</tt> object's <code>notifyObservers</code> method to
	 * have all the object's observers notified of the change. If the Observable is
	 * an instance of the TurnActivity, then the observers of the TurnActivityList
	 * should be updated. The current instance is passed to notifyObservers().
	 *
	 * 
	 * @param o
	 *            the observable object.
	 * @param arg
	 *            an argument passed to the <code>notifyObservers</code> method.
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof TurnActivity) {
			setChanged();
			notifyObservers(this);
		}
	}

}