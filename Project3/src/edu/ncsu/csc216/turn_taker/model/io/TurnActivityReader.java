/**
 * 
 */
package edu.ncsu.csc216.turn_taker.model.io;

import java.io.File;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.turn_taker.model.turns.TurnActivity;
import edu.ncsu.csc216.turn_taker.model.turns.TurnActivityList;
import edu.ncsu.csc216.turn_taker.model.turns.TurnTaker;

/**
 * TurnActivityReader reads a TurnActivityList from the given file.
 * 
 * @author wnwang
 *
 */
public class TurnActivityReader {

	/**
	 * Static instance of the TurnActivityList object.
	 */
	private static TurnActivityList turnActivityList;
	/**
	 * String variable holding the name of the TurnActivity object.
	 */
	private static String turnActivityName;
	/**
	 * String variable holding the description of the TurnActivity object.
	 */
	private static String turnActivityDescription;
	/**
	 * Field holding the TurnActivity object.
	 */
	private static TurnActivity turnActivity;

	/**
	 * Returns a TurnActivityList from the given file.
	 * 
	 * @param filename
	 *            the filename of the file to be read
	 * @return a TurnActivityList from the given file
	 * @throws IllegalArgumentException
	 *             if any error or incorrect formatting encountered
	 */
	public static TurnActivityList readTurnActivitiesFile(String filename) {
		turnActivityList = new TurnActivityList();
		turnActivity = null;
		turnActivityName = null;
		turnActivityDescription = null;
		try {
			Scanner scanner = new Scanner(new File(filename));
			setTurnActivity(scanner);
			scanner.close();
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
		return turnActivityList;
	}

	/**
	 * Sets TurnActivity with underlying logic.
	 * 
	 * @param scanner
	 *            the Scanner object as parameter to be process
	 */
	private static void setTurnActivity(Scanner scanner) {
		String nextLine = scanner.nextLine();
		if (turnActivityName == null && nextLine.startsWith("# ")) {
			Scanner line = new Scanner(nextLine);
			Scanner next = line.useDelimiter("# ");
			turnActivityName = next.next();
			next.close();
			line.close();
			setTurnActivity(scanner);
		} else if (turnActivityName != null && nextLine.isEmpty())
			setTurnActivityDescription(scanner);
		else
			throw new IllegalArgumentException();
	}

	/**
	 * Sets TurnActivityDescription.
	 * 
	 * @param scanner
	 *            the Scanner object as parameter to be process
	 */
	private static void setTurnActivityDescription(Scanner scanner) {
		String nextLine = scanner.nextLine();
		if (turnActivityDescription == null && !nextLine.isEmpty()) {
			turnActivityDescription = nextLine;
			setTurnActivityDescription(scanner);
		} else if (turnActivityDescription != null && !nextLine.isEmpty()) {
			turnActivityDescription += "\n" + nextLine;
			setTurnActivityDescription(scanner);
		} else if (turnActivityDescription != null && nextLine.isEmpty()) {
			turnActivity = new TurnActivity(turnActivityName, turnActivityDescription);
			setTurnTakers(scanner);
		} else
			throw new IllegalArgumentException();
	}

	/**
	 * Sets TurnTakers.
	 * 
	 * @param scanner
	 *            the Scanner object as parameter to be process
	 */
	private static void setTurnTakers(Scanner scanner) {
		String nextLine = null;
		try {
			nextLine = scanner.nextLine();
			if (nextLine.startsWith("* ")) {
				Scanner line = new Scanner(nextLine);
				nextLine = line.useDelimiter("\\* ").next();
				Scanner delimiter = new Scanner(nextLine);
				Scanner next = delimiter.useDelimiter("\t");
				try {
					turnActivity.addTurnTaker(new TurnTaker(next.next(), next.nextInt()));
				} catch (NoSuchElementException e) {
					line.close();
					delimiter.close();
					throw new IllegalArgumentException();
				}
				line.close();
				delimiter.close();
				setTurnTakers(scanner);
			} else if (nextLine.isEmpty()) {
				if (turnActivity.getTurnTakers().getTurnTakersAsArray().length >= 2) {
					turnActivityList.addTurnActivity(turnActivity);
					turnActivityName = null;
					turnActivityDescription = null;
					turnActivity = null;
				} else
					throw new IllegalArgumentException();
				setTurnActivity(scanner);
			} else
				throw new IllegalArgumentException();
		} catch (NoSuchElementException e) {
			if (turnActivity.getTurnTakers().getTurnTakersAsArray().length >= 2) {
				turnActivityList.addTurnActivity(turnActivity);
				turnActivityName = null;
				turnActivityDescription = null;
				turnActivity = null;
			} else
				throw new IllegalArgumentException();
		}
	}

}