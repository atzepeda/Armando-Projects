/**
 * 
 */
package edu.ncsu.csc216.turn_taker.model.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import edu.ncsu.csc216.turn_taker.model.turns.TurnActivity;
import edu.ncsu.csc216.turn_taker.model.turns.TurnTaker;
import edu.ncsu.csc216.turn_taker.model.util.SortedArrayList;

/**
 * Test method for
 * {@link edu.ncsu.csc216.turn_taker.model.manager.TurnTakerManager}.
 * 
 * @author wnwang
 *
 */
public class TurnTakerManagerTest {

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.turn_taker.model.manager.TurnTakerManager#getInstance()}.
	 */
	@Test
	public void testGetInstance() {
		TurnTakerManager turnTakerManager = TurnTakerManager.getInstance();
		turnTakerManager.loadFile("test-files/turn_activities.md");
		TurnTaker turnTaker = new TurnTaker("turnTaker");
		assertEquals(-134958483, turnTaker.hashCode());
		turnTaker.equals(new TurnTaker("turnTaker"));
		try {
			turnTaker = new TurnTaker(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(null, e.getMessage());
		}
		try {
			turnTaker = new TurnTaker("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(null, e.getMessage());
		}
		try {
			turnTaker = new TurnTaker("turnTaker", -1);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(null, e.getMessage());
		}
		assertFalse(turnTakerManager.isChanged());
		turnTakerManager = TurnTakerManager.getInstance();
		assertEquals("test-files/turn_activities.md", turnTakerManager.getFilename());
		try {
			turnTakerManager.setFilename(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(null, e.getMessage());
		}
		try {
			turnTakerManager.setFilename("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(null, e.getMessage());
		}
		try {
			turnTakerManager.setFilename(" ");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(null, e.getMessage());
		}
		try {
			turnTakerManager.saveFile(" ");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(null, e.getMessage());
		}
		turnTakerManager.saveFile("test-files/act_turn_activities.md");
		assertEquals(3, turnTakerManager.getTurnActivityList().size());
		turnTakerManager.getTurnActivityList().addTurnActivity("name", "description");
		assertEquals(4, turnTakerManager.getTurnActivityList().size());
		turnTakerManager.getTurnActivityList().removeTurnActivity(3);
		assertEquals(3, turnTakerManager.getTurnActivityList().size());
		assertEquals(turnTakerManager.getTurnActivityList().getTurnActivity(0).toString(),
				turnTakerManager.getTurnActivityList().getTurnActivity(0).getTurnActivityName());
		try {
			turnTakerManager.getTurnActivityList().getTurnActivity(0).editTurnActivity("", "");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(null, e.getMessage());
		}
		try {
			turnTakerManager.getTurnActivityList().getTurnActivity(0).editTurnActivity(null, "");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(null, e.getMessage());
		}
		try {
			turnTakerManager.getTurnActivityList().getTurnActivity(0).editTurnActivity("name", null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(null, e.getMessage());
		}
		try {
			turnTakerManager.getTurnActivityList().getTurnActivity(0).editTurnActivity("name", "");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(null, e.getMessage());
		}
		turnTakerManager.getTurnActivityList().getTurnActivity(0).editTurnActivity("name", "description");
		try {
			turnTakerManager.getTurnActivityList().getTurnActivity(0).addTurnTaker(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(null, e.getMessage());
		}
		turnTakerManager.getTurnActivityList().getTurnActivity(0).getTurnTakers().addTurnTaker(new TurnTaker("null"));
		assertEquals(6, turnTakerManager.getTurnActivityList().getTurnActivity(0).getTurnTakers()
				.getTurnTakersAsArray().length);
		turnTakerManager.getTurnActivityList().getTurnActivity(0).removeTurnTaker("null");
		assertEquals(5, turnTakerManager.getTurnActivityList().getTurnActivity(0).getTurnTakers()
				.getTurnTakersAsArray().length);
		assertEquals("Mary", turnTakerManager.getTurnActivityList().getTurnActivity(0).getTurnTakers().nextTurnTaker());
		turnTakerManager.getTurnActivityList().getTurnActivity(0).takeTurn();
		assertEquals("Sue", turnTakerManager.getTurnActivityList().getTurnActivity(0).getTurnTakers().nextTurnTaker());
		assertEquals(3373738, turnTakerManager.getTurnActivityList().getTurnActivity(0).hashCode());
		// assertTrue(new TurnActivity("null", "null").equals(new TurnActivity("null",
		// "null")));
		// assertFalse(new TurnActivity("null", "null").equals(new
		// TurnActivity("TurnActivity", "null")));
		try {
			turnTakerManager.getTurnActivityList().getTurnActivity(1).getTurnTakers().moveUp(10);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(null, e.getMessage());
		}
		try {
			turnTakerManager.getTurnActivityList().getTurnActivity(1).getTurnTakers().moveUp(-1);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(null, e.getMessage());
		}
		try {
			turnTakerManager.getTurnActivityList().getTurnActivity(1).getTurnTakers().moveDown(10);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(null, e.getMessage());
		}
		try {
			turnTakerManager.getTurnActivityList().getTurnActivity(1).getTurnTakers().moveDown(-1);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(null, e.getMessage());
		}
		try {
			turnTakerManager.getTurnActivityList().getTurnActivity(1).getTurnTakers().moveUp(1);
		} catch (IllegalArgumentException e) {
			fail();
		}
		try {
			turnTakerManager.getTurnActivityList().getTurnActivity(1).getTurnTakers().moveDown(1);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertEquals("Maureen",
				turnTakerManager.getTurnActivityList().getTurnActivity(2).getTurnTakers().getTurnTakersAsArray()[0][0]);
		assertEquals("Mark",
				turnTakerManager.getTurnActivityList().getTurnActivity(2).getTurnTakers().getTurnTakersAsArray()[1][0]);
		assertEquals("Jeff",
				turnTakerManager.getTurnActivityList().getTurnActivity(2).getTurnTakers().getTurnTakersAsArray()[2][0]);
		assertEquals("Cecily",
				turnTakerManager.getTurnActivityList().getTurnActivity(2).getTurnTakers().getTurnTakersAsArray()[3][0]);
		assertEquals("Larry",
				turnTakerManager.getTurnActivityList().getTurnActivity(2).getTurnTakers().getTurnTakersAsArray()[4][0]);
		turnTakerManager.getTurnActivityList().getTurnActivity(2).getTurnTakers().removeTurnTaker("Jeff");
		assertEquals("Maureen",
				turnTakerManager.getTurnActivityList().getTurnActivity(2).getTurnTakers().getTurnTakersAsArray()[0][0]);
		assertEquals("Mark",
				turnTakerManager.getTurnActivityList().getTurnActivity(2).getTurnTakers().getTurnTakersAsArray()[1][0]);
		assertEquals("Cecily",
				turnTakerManager.getTurnActivityList().getTurnActivity(2).getTurnTakers().getTurnTakersAsArray()[2][0]);
		assertEquals("Larry",
				turnTakerManager.getTurnActivityList().getTurnActivity(2).getTurnTakers().getTurnTakersAsArray()[3][0]);
		try {
			turnTakerManager.getTurnActivityList().getTurnActivity(2).getTurnTakers().moveUp(0);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Maureen", turnTakerManager.getTurnActivityList().getTurnActivity(2).getTurnTakers()
					.getTurnTakersAsArray()[0][0]);
			assertEquals("Mark", turnTakerManager.getTurnActivityList().getTurnActivity(2).getTurnTakers()
					.getTurnTakersAsArray()[1][0]);
			assertEquals("Cecily", turnTakerManager.getTurnActivityList().getTurnActivity(2).getTurnTakers()
					.getTurnTakersAsArray()[2][0]);
			assertEquals("Larry", turnTakerManager.getTurnActivityList().getTurnActivity(2).getTurnTakers()
					.getTurnTakersAsArray()[3][0]);
		}
		try {
			turnTakerManager.getTurnActivityList().getTurnActivity(2).getTurnTakers().moveDown(0);
			assertEquals("Mark", turnTakerManager.getTurnActivityList().getTurnActivity(2).getTurnTakers()
					.getTurnTakersAsArray()[0][0]);
			assertEquals("Maureen", turnTakerManager.getTurnActivityList().getTurnActivity(2).getTurnTakers()
					.getTurnTakersAsArray()[1][0]);
			assertEquals("Cecily", turnTakerManager.getTurnActivityList().getTurnActivity(2).getTurnTakers()
					.getTurnTakersAsArray()[2][0]);
			assertEquals("Larry", turnTakerManager.getTurnActivityList().getTurnActivity(2).getTurnTakers()
					.getTurnTakersAsArray()[3][0]);
		} catch (IllegalArgumentException e) {
			fail();
		}

		SortedArrayList<TurnActivity> sortedArrayList = new SortedArrayList<TurnActivity>();
		assertTrue(sortedArrayList.isEmpty());
		assertFalse(turnTakerManager.getTurnActivityList().getTurnActivity(2)
				.equals(turnTakerManager.getTurnActivityList().getTurnActivity(0)));
		assertEquals("Mark",
				turnTakerManager.getTurnActivityList().getTurnActivity(2).getTurnTakers().getTurnTakersAsArray()[0][0]);

		try {
			turnTakerManager.getTurnActivityList().getTurnActivity(2).getTurnTakers().moveDown(3);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Mark", turnTakerManager.getTurnActivityList().getTurnActivity(2).getTurnTakers()
					.getTurnTakersAsArray()[0][0]);
			assertEquals("Maureen", turnTakerManager.getTurnActivityList().getTurnActivity(2).getTurnTakers()
					.getTurnTakersAsArray()[1][0]);
			assertEquals("Cecily", turnTakerManager.getTurnActivityList().getTurnActivity(2).getTurnTakers()
					.getTurnTakersAsArray()[2][0]);
			assertEquals("Larry", turnTakerManager.getTurnActivityList().getTurnActivity(2).getTurnTakers()
					.getTurnTakersAsArray()[3][0]);
		}
		try {
			turnTakerManager.getTurnActivityList().getTurnActivity(2).getTurnTakers().moveUp(3);
			assertEquals("Mark", turnTakerManager.getTurnActivityList().getTurnActivity(2).getTurnTakers()
					.getTurnTakersAsArray()[0][0]);
			assertEquals("Maureen", turnTakerManager.getTurnActivityList().getTurnActivity(2).getTurnTakers()
					.getTurnTakersAsArray()[1][0]);
			assertEquals("Larry", turnTakerManager.getTurnActivityList().getTurnActivity(2).getTurnTakers()
					.getTurnTakersAsArray()[2][0]);
			assertEquals("Cecily", turnTakerManager.getTurnActivityList().getTurnActivity(2).getTurnTakers()
					.getTurnTakersAsArray()[3][0]);
		} catch (IllegalArgumentException e) {
			fail();
		}
	}

}
