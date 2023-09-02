/**
 * 
 */
package edu.ncsu.csc216.turn_taker.model.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Scanner;

import org.junit.Test;

/**
 * Test method for
 * {@link edu.ncsu.csc216.turn_taker.model.io.TurnActivityWriter}.
 * 
 * @author wnwang
 *
 */
public class TurnActivityWriterTest {

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.turn_taker.model.io.TurnActivityWriter#writeTurnActivitiesFile(java.lang.String, edu.ncsu.csc216.turn_taker.model.turns.TurnActivityList)}.
	 */
	@Test
	public void testWriteTurnActivitiesFile() {
		@SuppressWarnings("unused")
		TurnActivityReader turnActivityReader = new TurnActivityReader();
		@SuppressWarnings("unused")
		TurnActivityWriter turnActivityWriter = new TurnActivityWriter();

		TurnActivityWriter.writeTurnActivitiesFile("test-files/act_turn_activities.md",
				TurnActivityReader.readTurnActivitiesFile("test-files/turn_activities.md"));
		try {
			Scanner exp = new Scanner(new File("test-files/exp_turn_activities.md"));
			Scanner act = new Scanner(new File("test-files/act_turn_activities.md"));
			while (exp.hasNextLine())
				assertEquals(exp.nextLine(), act.nextLine());
			exp.close();
			act.close();
		} catch (Exception e) {
			fail();
		}
	}

}
