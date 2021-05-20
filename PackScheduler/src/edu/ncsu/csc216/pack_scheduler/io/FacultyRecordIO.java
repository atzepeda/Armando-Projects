package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;


/**
 * Read and writes faculty info
 * @author nmaco
 * @author atzep
 */
public class FacultyRecordIO {

	/**
	 * Reads the faculty records
	 * @param fileName name of file to be read
	 * @return the faculty info
	 * @throws FileNotFoundException if the file is not found
	 */
	public static LinkedList<Faculty> readFacultyRecords(String fileName) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(fileName));
		LinkedList<Faculty> faculties = new LinkedList<Faculty>();
		while (fileReader.hasNextLine()) {
			try {
				Faculty faculty = processFaculty(fileReader.nextLine());
				faculties.add(faculty);
			} catch (IllegalArgumentException e) {
				// skips line
			}
		}
		fileReader.close();
		return faculties;
	}

	/**
	 * Creates a faculty object from a line
	 * @param line containing faculty field
	 * @return faculty object
	 */
	private static Faculty processFaculty(String line) {
		Scanner lineScanner = new Scanner(line);
		Faculty faculty = null;
		try {
			lineScanner.useDelimiter(",");
			String firstName = lineScanner.next();
			String lastName = lineScanner.next();
			String id = lineScanner.next();
			String email = lineScanner.next();
			String password = lineScanner.next();
			int maxCourses = lineScanner.nextInt();
			faculty = new Faculty(firstName, lastName, id, email, password, maxCourses);
		} catch (NoSuchElementException e) {
			lineScanner.close();
			throw new IllegalArgumentException();
		}
		lineScanner.close();
		return faculty;
	}
	/**
	 * Writes to a file the faculty records
	 * @param fileName the file to write too
	 * @param facultyDirectory the directory to write to
	 * @throws IOException unable to write to file
	 */
	public static void writeFacultyRecords(String fileName, LinkedList<Faculty> facultyDirectory) throws IOException{
		try{
            PrintStream fileWriter = new PrintStream(new File(fileName));
            for (int i = 0; i < facultyDirectory.size(); i++) {
                fileWriter.println(facultyDirectory.get(i).toString());
            }
            fileWriter.close();
            
        } catch(IOException e){
            throw new IOException(fileName + " (Permission denied)");
        }
	}
}
