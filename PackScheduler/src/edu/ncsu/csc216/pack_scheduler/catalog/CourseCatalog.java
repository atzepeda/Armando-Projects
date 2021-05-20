package edu.ncsu.csc216.pack_scheduler.catalog;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.io.CourseRecordIO;

/**
 *  Creates the Course Catalog
 * @author Shalini Balagopal Nick Macon
 */
public class CourseCatalog {
	
	/** Catalog of Course objects */
	private SortedList<Course> catalog;
	
	/** 
	 * Constructs an empty catalog
	 */
	public CourseCatalog(){
        this.catalog = new SortedList<Course>();
        
    }
	/**
	 * Creates an empty catalog
	 */
	public void newCourseCatalog(){
        this.catalog = new SortedList<Course>();
        
    }
	
	/**
	 * Loads the course from the listed file
	 * @param file the filename
	 */
	public void loadCoursesFromFile(String file){
        try {
            catalog = CourseRecordIO.readCourseRecords(file);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Can not read file " + file);
        }
    }
	
	/**
	 * Adds a course to the course catalog
	 * @param name course name
	 * @param title course title
	 * @param section course section number
	 * @param credits course credits
	 * @param instructorId course instructor id
	 * @param enrollmentCap max number of students in class
	 * @param meetingDays course meeting days
	 * @param startTime course start time
	 * @param endTime course end time
	 * @return if the course was added
	 */
	public boolean addCourseToCatalog(String name, String title, String section, int credits, String instructorId, int enrollmentCap, String meetingDays, int startTime, int endTime){
        for (int i = 0; i < catalog.size(); i++) {
            if (catalog.get(i).getName().equals(name) && catalog.get(i).getSection().equals(section)) {
                return false;
                
            }
        }
        
        try {
        		catalog.add(new Course(name, title, section, credits, instructorId, enrollmentCap, meetingDays, startTime, endTime));
        } catch (IllegalArgumentException e) {
        		throw new IllegalArgumentException(e.getMessage());
        }
        
        return true;
       
    }
	
	/**
	 * Removes a selected course from the catalog
	 * @param name course name
	 * @param section course section
	 * @return if the course was removed
	 */
	public boolean removeCourseFromCatalog(String name, String section){
        for (int i = 0; i < catalog.size(); i++) {
            if (catalog.get(i).getName().equals(name) && catalog.get(i).getSection().equals(section)) {
                catalog.remove(i);
                return true;              
            } 
        }   
        return false;
       
    }
	
	/**
	 * Retrieves a course from the catalog
	 * @param name course name
	 * @param section course section
	 * @return the courses in the catalog
	 */
	public Course getCourseFromCatalog(String name, String section) {
		int cat = catalog.size();
		for (int i = 0; i < cat; i++) {
			if (catalog.get(i).getName().equals(name) && catalog.get(i).getSection().equals(section)) {
				return catalog.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Retrieves the entire course catalog
	 * @return the catalog of courses
	 */
	public String[][] getCourseCatalog() {
		int cat = catalog.size();
		String[][] x = new String[cat][5];
		for (int i = 0; i < cat; i++) {
			x[i][0] = catalog.get(i).getName();
			x[i][1] = catalog.get(i).getSection();
			x[i][2] = catalog.get(i).getTitle();
			x[i][3] = catalog.get(i).getMeetingString();
			x[i][4] = Integer.toString(catalog.get(i).getCourseRoll().getOpenSeats());
		}
		return x;
	}
	
	/**
	 * Saves the course catalog to a text file
	 * @param fileName the file name
	 */
	public void saveCourseCatalog(String fileName) {
		try {
			CourseRecordIO.writeCourseRecords(fileName, catalog);
		} catch (IOException e) {
			throw new IllegalArgumentException("The file cannot be saved.");
		}
	}
}