/**
 * 
 */
package edu.ncsu.csc216.turn_taker.model.manager;

import java.util.Observable;
import java.util.Observer;

import edu.ncsu.csc216.turn_taker.model.io.TurnActivityReader;
import edu.ncsu.csc216.turn_taker.model.io.TurnActivityWriter;
import edu.ncsu.csc216.turn_taker.model.turns.TurnActivityList;

/**
 * TurnTakerManager is the manager class for the TurnTaker application.
 * 
 * @author wnwang
 *
 */
public class TurnTakerManager extends Observable implements Observer {

	/**
	 * Single TurnTakerManager instance.
	 */
	private static TurnTakerManager instance;
	/**
	 * TurnActivityList of TurnTakerManager.
	 */
	private TurnActivityList list;
	/**
	 * Last used filename of TurnTakerManager.
	 */
	private String filename;
	/**
	 * Flag indicates if the list has changed.
	 */
	private boolean changed;

	/**
	 * Returns the single TurnTakerManager instance.
	 * 
	 * @return the single TurnTakerManager instance
	 */
	public static TurnTakerManager getInstance() {
		if (instance == null)
			instance = new TurnTakerManager();
		return instance;
	}

	/**
	 * Constructs the TurnTakerManager by creating an empty TurnActivityList and
	 * registering as an Observer of the list.
	 */
	private TurnTakerManager() {
		newList();
	}

	/**
	 * Creates a new empty TurnActivityList and registers the TurnTakerManager as an
	 * Observer.
	 */
	public void newList() {
		list = new TurnActivityList();
		list.addObserver(this);
		// Notify observers of TurnTakerManager that the collection has changed.
		setChanged();
		notifyObservers(this);
	}

	/**
	 * Returns the value stored in changed.
	 * 
	 * @return the value stored in changed
	 */
	public boolean isChanged() {
		return changed;
	}

	/**
	 * Sets the value stored in changed.
	 * 
	 * @param changed
	 *            the boolean to be set
	 */
	private void setChanged(boolean changed) {
		this.changed = changed;
	}

	/**
	 * Returns the filename.
	 * 
	 * @return the filename of TurnTakerManager
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * Sets the filename.
	 * 
	 * @param filename
	 *            the filename to set
	 * @throws IllegalArgumentException
	 *             if the filename parameter is null, the empty string, or string
	 *             with whitespace only
	 */
	public void setFilename(String filename) {
		if (filename == null || filename.isEmpty() || filename.trim().length() == 0)
			throw new IllegalArgumentException();
		this.filename = filename.trim();
	}

	/**
	 * Reads the TurnActivities from the given filename and adds the
	 * TurnTakerManager as an observer to the created TurnActivityList.
	 * 
	 * @param filename
	 *            the filename to load file from
	 */
	public void loadFile(String filename) {
		list = TurnActivityReader.readTurnActivitiesFile(filename);
		list.addObserver(this);
		setFilename(filename);
		setChanged(false);
		// Notify observers of TurnTakerManager that the collection has changed.
		setChanged();
		notifyObservers(this);
	}

	/**
	 * Saves the TurnActivityList to the given filename.
	 * 
	 * @param filename
	 *            the filename to save file to
	 */
	public void saveFile(String filename) {
		TurnActivityWriter.writeTurnActivitiesFile(filename, list);
		setChanged(false);
	}

	/**
	 * Returns the TurnActivityList.
	 * 
	 * @return the list as TurnActivityList object
	 */
	public TurnActivityList getTurnActivityList() {
		return list;
	}

	/**
	 * This method is called whenever the observed object is changed. An application
	 * calls an <tt>Observable</tt> object's <code>notifyObservers</code> method to
	 * have all the object's observers notified of the change. TurnTakerManager
	 * should propagate the notification of the change to the Observers and changed
	 * should be updated to true.
	 *
	 * @param o
	 *            the observable object.
	 * @param arg
	 *            an argument passed to the <code>notifyObservers</code> method.
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof TurnActivityList) {
			setChanged();
			notifyObservers(this);
			setChanged(true);
		}
	}

}