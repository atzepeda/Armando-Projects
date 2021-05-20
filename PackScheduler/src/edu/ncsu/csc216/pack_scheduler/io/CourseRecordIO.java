package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory;
import edu.ncsu.csc216.pack_scheduler.manager.RegistrationManager;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;

/**
 * Reads Course records from text files.  Writes a set of CourseRecords to a file.
 * 
 * @author Sarah Heckman Shalini Balagopal
 */
public class CourseRecordIO {

	/**
	 * Reads in elements from a line and adding them to a new Course object. If
	 * there are no more elements to read in, an IllegalArgumentException is thrown
	 * to the readCourseRecords method.
	 * @param line line to create Course object from
	 * @return Course object to add to catalog
	 * @throws IllegalArgumentException if an element cannot be found in the lineScanner
	 */
    private static Course readCourse(String line) {
		Scanner lineScanner = new Scanner(line);
		Course c;
		try {
			lineScanner.useDelimiter(",");

			String name = lineScanner.next();
			String title = lineScanner.next();
			String section = lineScanner.next();
			int credits = lineScanner.nextInt();
			String instructorId = lineScanner.next();
			int enrollmentCap = lineScanner.nextInt();
			String meetingDays = lineScanner.next();
			int startTime;
			int endTime;
			if (!lineScanner.hasNext()) {
				c = new Course(name, title, section, credits, null, enrollmentCap, meetingDays);
				RegistrationManager regMan = RegistrationManager.getInstance();
				FacultyDirectory facDir = regMan.getFacultyDirectory();
				Faculty facultyMember = facDir.getFacultyById(instructorId);
				if (facultyMember != null) {
					facultyMember.getSchedule().addCourseToSchedule(c);
				}
				lineScanner.close();
				return c;
			} else {
				startTime = lineScanner.nextInt();
				endTime = lineScanner.nextInt();
			}
			
			c = new Course(name, title, section, credits, null, enrollmentCap, meetingDays, startTime, endTime);
			RegistrationManager regMan = RegistrationManager.getInstance();
			FacultyDirectory facDir = regMan.getFacultyDirectory();
			Faculty facultyMember = facDir.getFacultyById(instructorId);
			if (facultyMember != null) {
				facultyMember.getSchedule().addCourseToSchedule(c);
			}
			lineScanner.close();
			return c;

		} catch (NoSuchElementException e) {
			lineScanner.close();
			throw new IllegalArgumentException();
		}
	}

	/**
     * Reads course records from a file and generates a list of valid Courses.  Any invalid
     * Courses are ignored.  If the file to read cannot be found or the permissions are incorrect
     * a File NotFoundException is thrown.
     * @param fileName file to read Course records from
     * @return a list of valid Courses
     * @throws FileNotFoundException if the file cannot be found or read
     */
	public static SortedList<Course> readCourseRecords(String fileName) throws FileNotFoundException {
	    Scanner fileReader = new Scanner(new FileInputStream(fileName));
	    SortedList<Course> courses = new SortedList<Course>();
	    while (fileReader.hasNextLine()) {
	        try {
	            Course course = readCourse(fileReader.nextLine());
	            boolean duplicate = false;
	            for (int i = 0; i < courses.size(); i++) {
	                Course c = courses.get(i);
	                if (course.getName().equals(c.getName()) &&
	                        course.getSection().equals(c.getSection())) {
	                    duplicate = true;
	                }
	            }
	            if (!duplicate) {
	                courses.add(course);
	            }
	        } catch (IllegalArgumentException e) {
	        		// skips line
	        }
	    }
	    fileReader.close();
	    return courses;
	}

    /**
     * Writes the given list of Courses to an output file
     * @param fileName name of new file to write courses to
     * @param courses list of courses to be written to file
     * @throws IOException if unable to write to file
     */
	public static void writeCourseRecords(String fileName, SortedList<Course> courses) throws IOException {
        PrintStream fileWriter = new PrintStream(new File(fileName));
        
		for (int i = 0; i < courses.size(); i++) {
			fileWriter.println(courses.get(i).toString());
		}

        
        fileWriter.close();
    }
}