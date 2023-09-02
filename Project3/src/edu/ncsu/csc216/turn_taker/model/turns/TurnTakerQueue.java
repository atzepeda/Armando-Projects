/**
 * 
 */
package edu.ncsu.csc216.turn_taker.model.turns;

import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.Observer;

import edu.ncsu.csc216.turn_taker.model.util.SwapLinkedQueue;

/**
 * TurnTakerQueue class can be used to create a TurnTakerQueue that has a
 * SwapLinkedQueue object with a list of TurnTaker.
 * 
 * @author Sarah Heckman
 * @author Stuart Reges
 * @author wnwang
 *
 */
public class TurnTakerQueue extends Observable implements Observer {

	/**
	 * SwapLinkedQueue of the TurnTakerQueue holding a list of TurnTaker objects.
	 */
	private SwapLinkedQueue<TurnTaker> turnTakers;

	/**
	 * Constructor of TurnTakerQueue both creates an empty SwapLinkedQueue object
	 * and assigns the SwapLinkedQueue object to the turnTakers field.
	 */
	public TurnTakerQueue() {
		turnTakers = new SwapLinkedQueue<TurnTaker>();
	}

	/**
	 * Adds the TurnTaker to the end of the SwapLinkedQueue.
	 * 
	 * @param t
	 *            TurnTaker object to be added to turnTakers
	 * @throws NullPointerException
	 *             if the TurnTaker is null
	 * @throws IllegalArgumentException
	 *             if the TurnTaker is duplicate
	 */
	public void addTurnTaker(TurnTaker t) throws NullPointerException, IllegalArgumentException {
		t.addObserver(this);
		// Add the TurnTaker to the queue
		turnTakers.add(t);
		// Notify observers of TurnTakerQueue that the collection has changed.
		setChanged();
		notifyObservers(this);
	}

	/**
	 * Removes the TurnTaker with the given name from the queue.
	 * 
	 * @param name
	 *            the name of the TurnTaker to be removed
	 * @throws IllegalArgumentException
	 *             if the TurnTaker is not in the queue
	 * @throws NoSuchElementException
	 *             if the queue is empty
	 */
	public void removeTurnTaker(String name) throws NoSuchElementException {
		// Remove the TurnTaker from the queue
		int size = turnTakers.size();
		for (int i = 0; i < size; i++) {
			TurnTaker turnTaker = turnTakers.remove();
			if (!turnTaker.getName().equals(name))
				addTurnTaker(turnTaker);
		}
		if (turnTakers.size() == size)
			throw new IllegalArgumentException();
		// Notify observers of TurnTakerQueue that the collection has changed.
		setChanged();
		notifyObservers(this);
	}

	/**
	 * Returns the name of the next TurnTaker in the SwapLinkedQueue without
	 * updating the queue contents.
	 * 
	 * @return the name of the next TurnTaker in the SwapLinkedQueue
	 * @throws NoSuchElementException
	 *             if the queue is empty
	 */
	public String nextTurnTaker() throws NoSuchElementException {
		return turnTakers.peek().getName();
	}

	/**
	 * Increments the turnCount of the TurnTaker at the front of the queue and moves
	 * the TurnTaker to the back of the queue.
	 * 
	 * @throws NoSuchElementException
	 *             if the queue is empty
	 */
	public void takeTurn() throws NoSuchElementException {
		TurnTaker turnTaker = turnTakers.remove();
		turnTaker.incrementTurnCount();
		turnTakers.add(turnTaker);
		// Notify observers of TurnTakerQueue that the collection has changed.
		setChanged();
		notifyObservers(this);
	}

	/**
	 * Swaps the TurnTaker at the given idx with the TurnTaker at idx-1.
	 * 
	 * @param idx
	 *            the given idx with the TurnTaker
	 * @throws IllegalArgumentException
	 *             if the swap cannot be completed
	 */
	public void moveUp(int idx) throws IllegalArgumentException {
		turnTakers.swap(idx, true);
		// Notify observers of TurnTakerQueue that the collection has changed.
		setChanged();
		notifyObservers(this);
	}

	/**
	 * Swaps the TurnTaker at the given idx with the TurnTaker at idx+1.
	 * 
	 * @param idx
	 *            the given idx with the TurnTaker
	 * @throws IllegalArgumentException
	 *             if the swap cannot be completed
	 */
	public void moveDown(int idx) throws IllegalArgumentException {
		turnTakers.swap(idx, false);
		// Notify observers of TurnTakerQueue that the collection has changed.
		setChanged();
		notifyObservers(this);
	}

	/**
	 * Returns a 2D String array where the first column is the TurnTaker name and
	 * the second column is the turnCount. Each row represents a TurnTaker where the
	 * first row is the TurnTaker at the front of the queue. If the queue is empty,
	 * an empty 2D array is returned.
	 * 
	 * @return 2D String array of name and turnCount of the TurnTaker from the queue
	 */
	public String[][] getTurnTakersAsArray() {
		int size = turnTakers.size();
		String[][] turnTakersAsArray = new String[size][2];
		for (int i = 0; i < size; i++) {
			TurnTaker turnTaker = turnTakers.remove();
			turnTakersAsArray[i][0] = turnTaker.getName();
			turnTakersAsArray[i][1] = Integer.toString(turnTaker.getTurnCount());
			turnTakers.add(turnTaker);
		}
		return turnTakersAsArray;
	}

	/**
	 * This method is called whenever the observed object is changed. An application
	 * calls an <tt>Observable</tt> object's <code>notifyObservers</code> method to
	 * have all the object's observers notified of the change. If the given
	 * Observable is a TurnTaker in the SwapLinkedQueue, then the observers of the
	 * TurnTakerQueue should be updated. The current instance is passed to
	 * notifyObservers().
	 *
	 * 
	 * @param o
	 *            the observable object.
	 * @param arg
	 *            an argument passed to the <code>notifyObservers</code> method.
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof TurnTaker && turnTakers.contains((TurnTaker) o)) {
			setChanged();
			notifyObservers(this);
		}
	}

}