package edu.ncsu.csc216.pack_scheduler.io;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * Tests the StudentRecordIO class
 * 
 * @author Shalini Balagopal Nick Macon
 */
public class StudentRecordIOTest {

	/** Valid student construction */
	private String validStudent0 = "Zahir,King,zking,orci.Donec@ametmassaQuisque.com,pw,15";
	/** Valid student construction */
	private String validStudent1 = "Cassandra,Schwartz,cschwartz,semper@imperdietornare.co.uk,pw,4";
	/** Valid student construction */
	private String validStudent2 = "Shannon,Hansen,shansen,convallis.est.vitae@arcu.ca,pw,14";
	/** Valid student construction */
	private String validStudent3 = "Demetrius,Austin,daustin,Curabitur.egestas.nunc@placeratorcilacus.co.uk,pw,18";
	/** Valid student construction */
	private String validStudent4 = "Raymond,Brennan,rbrennan,litora.torquent@pellentesquemassalobortis.ca,pw,12";
	/** Valid student construction */
	private String validStudent5 = "Emerald,Frost,efrost,adipiscing@acipsumPhasellus.edu,pw,3";
	/** Valid student construction */
	private String validStudent6 = "Lane,Berg,lberg,sociis@non.org,pw,14";
	/** Valid student construction */
	private String validStudent7 = "Griffith,Stone,gstone,porta@magnamalesuadavel.net,pw,17";
	/** Valid student construction */
	private String validStudent8 = "Althea,Hicks,ahicks,Phasellus.dapibus@luctusfelis.com,pw,11";
	/** Valid student construction */
	private String validStudent9 = "Dylan,Nolan,dnolan,placerat.Cras.dictum@dictum.net,pw,5";

	/** String Array to hold all the valid students in the correct order */
	private String [] validStudents = {validStudent3, validStudent6, validStudent4, validStudent5,
			validStudent2, validStudent8, validStudent0, validStudent9, validStudent1,
			validStudent7};

	/** String value of hashed password */
	private String hashPW;
	/** Valid student records */
	private final String validTestFile = "test-files/student_records.txt";
	/** Invalid student records */
	private final String invalidTestFile = "test-files/invalid_student_records.txt";
	/** String value of hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";

	/**
	 * Resets hashed value for testing purposes
	 */
	@Before
	public void setUp() {
		try {
            String password = "pw";
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            digest.update(password.getBytes());
            hashPW = new String(digest.digest());

            for (int i = 0; i < validStudents.length; i++) {
                validStudents[i] = validStudents[i].replace(",pw,", "," + hashPW + ",");
            }
        } catch (NoSuchAlgorithmException e) {
            fail("Unable to create hash during setup");
        }
	}

	/**
	 * Checks two files that are passed in as parameters and compares them line by line.
	 * @param expFile file that has the correct contents for comparison against the actual file
	 * @param actFile file whose contents are being compared against the expected file
	 */
	private void checkFiles(String expFile, String actFile) {
		try {
            Scanner expScanner = new Scanner(new FileInputStream(expFile));
            Scanner actScanner = new Scanner(new FileInputStream(actFile));

            while (expScanner.hasNextLine() && actScanner.hasNextLine()) {
                String exp = expScanner.nextLine();
                String act = actScanner.nextLine();
                assertEquals("Expected: " + exp + " Actual: " + act, exp, act);
            }
            if (expScanner.hasNextLine()) {
                fail("The expected results expect another line " + expScanner.nextLine());
            }
            if (actScanner.hasNextLine()) {
                fail("The actual results has an extra, unexpected line: " + actScanner.nextLine());
            }

            expScanner.close();
            actScanner.close();
        } catch (IOException e) {
            fail("Error reading files.");
        }
	}

	/**
	 * Tests to see if a file not found exception is thrown if unable to access
	 * the file to read.
	 */
	@Test
	public void testReadStudentRecords() {
		try {
			SortedList<Student> students = StudentRecordIO.readStudentRecords(validTestFile);
			assertEquals(10, students.size());

			for (int i = 0; i < validStudents.length; i++) {
				assertEquals(validStudents[i], students.get(i).toString());
			}
		} catch (FileNotFoundException e) {
			fail("Cannot read from" + "test-files/student_records.txt");
		}

		try {
			SortedList<Student> students = StudentRecordIO.readStudentRecords(invalidTestFile);
			assertEquals(0, students.size());
		} catch (FileNotFoundException e) {
			fail("Unexpected FileNotFoundException");
		}
	}

	/**
	 * Tests if an IO exception is through if unable to write to the file
	 */
	@Test
	public void testWriteStudentRecords() {
		SortedList<Student> students = new SortedList<Student>();
        students.add(new Student("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", hashPW, 15));

        try {
            StudentRecordIO.writeStudentRecords("test-files/actual_student_records.txt", students);
        } catch (IOException e) {
            fail("Cannot write to student records file"); 
        }

        checkFiles("test-files/expected_student_records.txt",
                "test-files/actual_student_records.txt");
	}

	/**
	 * Tests if an IO Exception is thrown due to lack of permission to write to
	 * directory containing the file.
	 * (If test fails on machine, check to see if it passes on Jenkins)
	 */
	@Test
	public void testWriteStudentRecordsNoPermissions() {
		SortedList<Student> students = new SortedList<Student>();
        students.add(new Student("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", hashPW, 15));
        try {
            StudentRecordIO.writeStudentRecords("/home/sesmith5/actual_student_records.txt", students);
            fail("Attempted to write to a directory location that doesn't exist or without the appropriate permissions and the write happened.");
        } catch (IOException e) {
            assertEquals("/home/sesmith5/actual_student_records.txt (Permission denied)", e.getMessage());
        }

	}
}
