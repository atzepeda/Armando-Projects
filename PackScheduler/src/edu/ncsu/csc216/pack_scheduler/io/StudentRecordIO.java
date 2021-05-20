package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;

/**
 * Reads Student records from text files and writes a set of Student records to a file.
 * 
 * @author Shalini Balagopal Nick Macon
 */
public class StudentRecordIO {
	/**
	 * Creates a Student object from a line
	 * @param line containing fields for Student object
	 * @return Student object
	 */
	private static Student processStudent(String line) {
		Scanner lineScanner = new Scanner(line);
		try {
			lineScanner.useDelimiter(",");

			String firstName = lineScanner.next();
			String lastName = lineScanner.next();
			String id = lineScanner.next();
			String email = lineScanner.next();
			String password = lineScanner.next();
			int maxCredits = lineScanner.nextInt();

			Student x = new Student(firstName, lastName, id, email, password, maxCredits);

			lineScanner.close();
			return x;

		} catch (NoSuchElementException e) {
			lineScanner.close();
			throw new IllegalArgumentException();
		}

	}

	/**
	 * Reads Student records from a file and generates a list of valid Student records.
	 * Any duplicate objects with the same unique Student ID will be thrown out.
	 * If the file cannot be found or read a FileNotFoundException is thrown.
	 * 
	 * @param fileName file to read Student records from
	 * @return list of valid Student records
	 * @throws FileNotFoundException if the file cannot be found or read
	 */
	public static SortedList<Student> readStudentRecords(String fileName) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(fileName));
		SortedList<Student> students = new SortedList<Student>();
		while (fileReader.hasNextLine()) {
			try {
				Student student = processStudent(fileReader.nextLine());
				boolean duplicate = false;
				for (int i = 0; i < students.size(); i++) {
					User s = students.get(i);
					if (student.getId().equals(s.getId())) {
						duplicate = true; 
					}
				}
				if (!duplicate) {
					students.add(student);
				}
			} catch (IllegalArgumentException e) {
				// skips line
			}
		}
		fileReader.close();
		return students;
	}

	/**
	 * Writes the valid list of Student records to a new file
	 * 
	 * @param fileName file to write valid Student records to
	 * @param studentDirectory the array that stores the list of students
	 * @throws IOException if the output for the file cannot be interpreted 
	 */
	public static void writeStudentRecords(String fileName, SortedList<Student> studentDirectory) throws IOException {
        try{
            PrintStream fileWriter = new PrintStream(new File(fileName));
            for (int i = 0; i < studentDirectory.size(); i++) {
                fileWriter.println(studentDirectory.get(i).toString());
            }
            fileWriter.close();
            
        } catch(IOException e){
            throw new IOException(fileName + " (Permission denied)");
        }
    }

}
