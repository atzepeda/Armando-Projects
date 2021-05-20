package edu.ncsu.csc216.pack_scheduler.directory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the faculty directory class
 * @author nmaco
 * @author atzep
 */
public class FacultyDirectoryTest {
	/** Valid course records */
	private final String validTestFile = "test-files/faculty_records.txt";
	/** Test first name */
	private static final String FIRST_NAME = "Stu";
	/** Test last name */
	private static final String LAST_NAME = "Dent";
	/** Test id */
	private static final String ID = "sdent";
	/** Test email */
	private static final String EMAIL = "sdent@ncsu.edu";
	/** Test password */
	private static final String PASSWORD = "pw";
	/** Test max credits */
	private static final int MAX_CREDITS = 3;

	/**
	 * Resets course_records.txt for use in other tests.
	 * 
	 * @throws Exception
	 *             if something fails during setup.
	 */
	@Before 
	public void setUp() throws Exception {
		// Reset student_records.txt so that it's fine for other needed tests
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "expected_full_faculty_records.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "faculty_records.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
	}
	
	/**
	 * Tests FacultyDirectory() is empty
	 */
	@Test
	public void testFacultyDirectory() {
		// Test that the FacultyDirectory is initialized to an empty list
		FacultyDirectory fd = new FacultyDirectory();
		assertFalse(fd.removeFaculty("sesmith5"));
		assertEquals(0, fd.getFacultyDirectory().length);
	}
	
	/**
	 * Tests FacultyDirectory.testNewFacultyDirectory().
	 */
	@Test
	public void testNewFacultyDirectory() {
		// Test that if there are students in the directory, they
		// are removed after calling newFacultyDirectory().
		FacultyDirectory fd = new FacultyDirectory();

		fd.loadFacultyFromFile(validTestFile);
		assertEquals(8, fd.getFacultyDirectory().length);

		fd.newFacultyDirectory();
		assertEquals(0, fd.getFacultyDirectory().length);
	}
	
	/**
	 * Tests FacultyDirectory.loadFacultyFromFile().
	 */
	@Test
	public void testLoadFacultyFromFile() {
		FacultyDirectory fd = new FacultyDirectory();

		// Test valid file
		fd.loadFacultyFromFile(validTestFile);
		assertEquals(8, fd.getFacultyDirectory().length);

		// tests file that does not exist
		try {
			fd.loadFacultyFromFile("invalid_file");
			fail("Attempted to load in records from a file that does not exist and records were still loaded in.");
		} catch (IllegalArgumentException e) {
			assertEquals("Unable to read file invalid_file", e.getMessage());
		}
	}
	
	/**
	 * Tests FacultyDirectory.addFaculty().
	 */
	@Test
	public void testAddFaculty() {
		FacultyDirectory fd = new FacultyDirectory();

		// Test valid Faculty
		fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_CREDITS);
		String[][] facultyDirectory = fd.getFacultyDirectory();
		assertEquals(1, facultyDirectory.length);
		assertEquals(FIRST_NAME, facultyDirectory[0][0]);
		assertEquals(LAST_NAME, facultyDirectory[0][1]);
		assertEquals(ID, facultyDirectory[0][2]);

		// tests Faculty is not added when new student ID matches ID of student in
		// directory already
		assertFalse(fd.addFaculty("Zahir", "King", "sdent", "orci.Donec@ametmassaQuisque.com", "pw", "pw", 3));
	}
	
	/**
	 * Tests FacultyDirectory.removeStudent().
	 */
	@Test
	public void testRemoveFaculty() {
		FacultyDirectory fd = new FacultyDirectory();

		// Add students and remove
		fd.loadFacultyFromFile(validTestFile);
		assertEquals(8, fd.getFacultyDirectory().length);
		assertTrue(fd.removeFaculty("ebriggs"));
		String[][] facultyDirectory = fd.getFacultyDirectory();
		assertEquals(7, facultyDirectory.length);
		assertEquals("Fiona", facultyDirectory[1][0]);
		assertEquals("Meadows", facultyDirectory[1][1]);
		assertEquals("fmeadow", facultyDirectory[1][2]);
	}
	
	/**
	 * Tests FacultyDirectory.saveFacultyDirectory().
	 */
	@Test
	public void testSaveFacultyDirectory() {
		FacultyDirectory fd = new FacultyDirectory();
		// Add a student
		fd.addFaculty("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", "pw", "pw", 2);
		assertEquals(1, fd.getFacultyDirectory().length);
		fd.saveFacultyDirectory("test-files/actual_faculty_records.txt");
		//checkFiles("test-files/expected_faculty_records.txt", "test-files/actual_faculty_records.txt");

		// tests that file cannot be written to a location it cannot access
		try {
			fd.saveFacultyDirectory("/home/sesmith5/actual_faculty_records.txt");
			fail("Attempted to write to a directory location that doesn't exist or without the appropriate permissions and the write happened.");
		} catch (IllegalArgumentException e) {
			assertEquals("Unable to write to file /home/sesmith5/actual_faculty_records.txt", e.getMessage());
		}
	}
	
	/**
	 * Tests FacultyDirectory.getFacultyById().
	 */
	@Test
	public void testgetFacultyById() {
		FacultyDirectory fd = new FacultyDirectory();
		// Add a faculty
		fd.addFaculty("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", "pw", "pw", 2);
		assertEquals("orci.Donec@ametmassaQuisque.com", fd.getFacultyById("zking").getEmail());
	}
}
