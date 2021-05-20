/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.catalog;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * Tests the course catalog class
 * @author Shalini Balagopal Nick Macon
 */
public class CourseCatalogTest {
	
	/** Valid course records */
	private final String validTestFile = "test-files/course_records.txt";
	/** Invalid course records */
	private final String invalidTestFile = "test-files/invalid_course_records.txt";
	
	/** Course name */
	private static final String NAME = "CSC216";
	/** Course title */
	private static final String TITLE = "Programming Concepts - Java";
	/** Course section */
	private static final String SECTION = "001";
	/** Course credits */
	private static final int CREDITS = 4;
	/** Course instructor id */
	private static final String INSTRUCTOR_ID = "sesmith5";
	/** Enrollment Cap */
	private static final int ENROLL_CAP = 150;
	/** Course meeting days */
	private static final String MEETING_DAYS = "TH";
	/** Course start time */
	private static final int START_TIME = 1330;
	/** Course end time */
	private static final int END_TIME = 1445;

	/**
	 * Resets course_records.txt for use in other tests.
	 * @throws IOException if unable to reset the files
	 */
	@Before
	public void setUp() throws Exception {
		// Reset course_records.txt so that it's fine for other needed tests
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "starter_course_records.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "course_records.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
	}

	/**
	 * Tests the creation of a course catalog
	 */
	@Test
	public void testCourseCatalog() {
		CourseCatalog c = new CourseCatalog();
        assertEquals(0, c.getCourseCatalog().length);
	}
	
	/**
	 * Tests the creation of a new course catalog
	 */
	@Test
	public void testNewCourseCatalog() {
		CourseCatalog c = new CourseCatalog();
		c.loadCoursesFromFile(validTestFile);
        assertEquals(8, c.getCourseCatalog().length);
        c.newCourseCatalog();
        assertEquals(0, c.getCourseCatalog().length);
	}
	
	/**
	 * Tests loading the courses from a file
	 */
	@Test
	public void testLoadCoursesFromFile() {
		//valid file
		CourseCatalog c = new CourseCatalog();
		c.loadCoursesFromFile(validTestFile);
        assertEquals(8, c.getCourseCatalog().length);
        
        //invalid file
        CourseCatalog c2 = new CourseCatalog();
        c2.loadCoursesFromFile(invalidTestFile);
        assertEquals(0, c2.getCourseCatalog().length);
        
        CourseCatalog c3 = new CourseCatalog();
        try{
            c3.loadCoursesFromFile("fakefile");
        } catch(IllegalArgumentException e){
            assertEquals(e.getMessage(), "Can not read file fakefile");
        }
	}
	
	/**
	 * Tests adding a course to the course catalog
	 */
	@Test
	public void testAddCourseToCatalog() {
		CourseCatalog c = new CourseCatalog();
		c.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLL_CAP, MEETING_DAYS, START_TIME, END_TIME);
		String[][] catalog = c.getCourseCatalog();
        assertEquals(1, catalog.length);
        assertEquals(NAME, catalog[0][0]);
        assertEquals(SECTION, catalog[0][1]);
        assertEquals(TITLE, catalog[0][2]);
        
        c = new CourseCatalog();
        try {
			c.addCourseToCatalog(null, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLL_CAP, MEETING_DAYS, START_TIME, END_TIME);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(0, c.getCourseCatalog().length); 
		}
	}
	
	/**
	 * Tests removing a course from the catalog
	 */
	@Test
	public void testRemoveCourseFromCatalog() {
		CourseCatalog c = new CourseCatalog();
		c.loadCoursesFromFile(validTestFile);
        assertEquals(8, c.getCourseCatalog().length);
        c.removeCourseFromCatalog("CSC116", "003");
        assertEquals(7, c.getCourseCatalog().length);
	}
	
	/**
	 * Tests getting a course from the course catalog
	 */
	@Test
	public void testGetCourseFromCatalog() {
		CourseCatalog c = new CourseCatalog();
		Course course = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLL_CAP, MEETING_DAYS, START_TIME, END_TIME);
		c.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLL_CAP, MEETING_DAYS, START_TIME, END_TIME);
		assertEquals(course, c.getCourseFromCatalog(NAME, SECTION));
	}
	
	/**
	 * Tests saving the edited course catalog
	 */
	@Test
	public void testSaveCourseFromCatalog() {
		CourseCatalog c = new CourseCatalog();
		assertEquals(0, c.getCourseCatalog().length);
		c.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLL_CAP, MEETING_DAYS, START_TIME, END_TIME);
		c.saveCourseCatalog("test-files/course_catalog_test_records.txt");
		
	}

}
