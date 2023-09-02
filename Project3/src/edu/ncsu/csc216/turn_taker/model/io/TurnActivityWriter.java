/**
 * 
 */
package edu.ncsu.csc216.turn_taker.model.io;

import java.io.File;
import java.io.PrintStream;

import edu.ncsu.csc216.turn_taker.model.turns.TurnActivityList;

/**
 * TurnActivityWriter writes the given TurnActivityList to the given file.
 * 
 * @author wnwang
 *
 */
public class TurnActivityWriter {

	/**
	 * Writes the given TurnActivityList to the given file.
	 * 
	 * @param filename
	 *            the filename of the file to be written
	 * @param list
	 *            the given TurnActivityList to be written to the given file
	 * @throws IllegalArgumentException
	 *             if any error occured
	 */
	public static void writeTurnActivitiesFile(String filename, TurnActivityList list) {
		if (filename.trim().length() == 0)
			throw new IllegalArgumentException();
		try {
			PrintStream printStream = new PrintStream(new File(filename));
			for (int i = 0; i < list.size(); i++) {
				printStream.println("# " + list.getTurnActivity(i).getTurnActivityName());
				printStream.print("\n");
				printStream.println(list.getTurnActivity(i).getTurnActivityDescription());
				printStream.print("\n");
				String[][] turnTakersArray = list.getTurnActivity(i).getTurnTakers().getTurnTakersAsArray();
				for (String[] turnTakers : turnTakersArray) {
					printStream.println("* " + turnTakers[0] + "\t" + turnTakers[1]);
				}
				printStream.print("\n");
			}
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
	}

}