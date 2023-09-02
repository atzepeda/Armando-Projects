package edu.ncsu.csc216.turn_taker.model.turns;

import static org.junit.Assert.*;

import java.util.Observable;
import java.util.Observer;

import org.junit.Before;
import org.junit.Test;

/**
 * Turn Taker Test.  Demonstrates testing an Observable object.
 * @author SarahHeckman
 */
public class TurnTakerTest {
	
	/** TurnTaker that is observed by the test class. */
	private TurnTaker t;
	/** Boolean flag to let us know if update() has been called */
	private boolean updated;
	/** 
	 * Reference to Observable passed to update().  That way we can check that
	 * the right object was passed to update().
	 */
	private Object updatedObject;
	
	/**
	 * Set up method. Adds an observer to the instance under test.
	 */
	@Before
	public void setUp() throws Exception {
		//Create a new TurnTaker
		t = new TurnTaker("Mary");
		
		//Adds a new Observer to the TurnTaker
		t.addObserver(new Observer() {

			/**
			 * If the Observable object calls notifyObservers(), this update()
			 * method will be called.  It will set the updated flag to true
			 * and assign the second argument, obj, to the updatedObject.  This
			 * is the Object that was passed into the notifyObservers() call 
			 * by the Observable object.
			 * 
			 * For this project, every call to notifyObservers() should pass in
			 * the current instance.  Then we can check that the current instance
			 * is correct!
			 */
			@Override
			public void update(Observable o, Object obj) {
				updated = true;
				updatedObject = obj;
			}
			
		});
	}
	
	/**
	 * Tests increment turn count.
	 */
	@Test
	public void testIncrementTurnCount() {
		//Always set updated/updatedObject to false/null BEFORE calling a method that should
		//notifyObservers().  This way you can make sure that the checks are testing the 
		//right thing!
		updated = false;
		updatedObject = null;
		
		//TurnTaker.incrementTurnCount() should call setChanged; notifiyObservers(); if the count is incremented.
		t.incrementTurnCount();
		//Check that the turnCount has been updated
		assertEquals("Incrementing turn count from zero to 1", 1, t.getTurnCount());
		//Check that the Observer.update() method defined in the test has been called.  If so, updated is true!
		assertTrue("Observers should be notified after incrementing turn count, but they were not.", updated);
		//Check that the correct object was passed to Observer.update()
		assertEquals("Observers should be notified after incrementing turn count, but they were not.", "Mary", ((TurnTaker)updatedObject).getName());
		//Check that the correct object was passed to Observer.update()
		assertEquals("Observers should be notified after incrementing turn count, but they were not.", 1, ((TurnTaker)updatedObject).getTurnCount());
		
		//Reset our flags
		updated = false;
		updatedObject = null;
		
		//Ensure that second call to incrementTurnCount also works
		t.incrementTurnCount();
		//Check that the turnCount has been updated
		assertEquals("Incrementing turn count from 1 to 2", 2, t.getTurnCount());
		//Check that the Observer.update() method defined in the test has been called.  If so, updated is true!
		assertTrue("Observers should be notified after incrementing turn count, but they were not.", updated);
		//Check that the correct object was passed to Observer.update()
		assertEquals("Observers should be notified after incrementing turn count, but they were not.", "Mary", ((TurnTaker)updatedObject).getName());
		//Check that the correct object was passed to Observer.update()
		assertEquals("Observers should be notified after incrementing turn count, but they were not.", 2, ((TurnTaker)updatedObject).getTurnCount());
	}

}
