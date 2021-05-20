package edu.ncsu.csc216.pack_scheduler.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.junit.Before;
import org.junit.Test;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;

/**
 * Tests the facultyrecordio class
 * @author nmaco
 * @author atzep
 */
public class FacultyRecordIOTest {
	
	private String hashPW;
    private static final String HASH_ALGORITHM = "SHA-256";
    private final String validTestFile = "test-files/faculty_records.txt";
    private final String invalidTestFile = "test-files/invalid_faculty_records.txt";
    
    private final String validFaculty1 = "Ashely,Witt,awitt,mollis@Fuscealiquetmagna.net,pw,2";
    private final String validFaculty2 = "Fiona,Meadows,fmeadow,pharetra.sed@et.org,pw,3";
    private final String validFaculty3 = "Brent,Brewer,bbrewer,sem.semper@orcisem.co.uk,pw,1";
    private final String validFaculty4 = "Halla,Aguirre,haguirr,Fusce.dolor.quam@amalesuadaid.net,pw,3";
    private final String validFaculty5 = "Kevyn,Patel,kpatel,risus@pellentesque.ca,pw,1";
    private final String validFaculty6 = "Elton,Briggs,ebriggs,arcu.ac@ipsumsodalespurus.edu,pw,3";
    private final String validFaculty7 = "Norman,Brady,nbrady,pede.nonummy@elitfermentum.co.uk,pw,1";
    private final String validFaculty8 = "Lacey,Walls,lwalls,nascetur.ridiculus.mus@fermentum.net,pw,2";

    
    private String[] validFaculties = { validFaculty1, validFaculty2, validFaculty3, validFaculty4, validFaculty5,
            validFaculty6, validFaculty7, validFaculty8};
    
    /**
     * Sets up the isntance to test
     */
    @Before
	public void setUp() {
		try {
            String password = "pw";
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            digest.update(password.getBytes());
            hashPW = new String(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            fail("Unable to create hash during setup");
        }
	}
    
    /**
     * Tests reading faculty records
     */
    @Test
    public void testReadFacultyRecords() {
        edu.ncsu.csc216.pack_scheduler.util.LinkedList<Faculty> faculty;
        try {
            faculty = FacultyRecordIO.readFacultyRecords(invalidTestFile);
            assertEquals(0, faculty.size());
        } catch (FileNotFoundException e) {
            fail("Unexpected FileNotFoundException");
        }
        try {
            faculty = FacultyRecordIO.readFacultyRecords(validTestFile);
            assertEquals(8, faculty.size());
            for (int i = 0; i < validFaculties.length; i++) {
                validFaculties[i] = validFaculties[i].replace(",pw,", "," + hashPW + ",");
                assertEquals(validFaculties[i], faculty.get(i).toString());
            }
        } catch (FileNotFoundException e) {
            fail("Unexpected error reading " + validTestFile);
        }
    }
    
    /**
     * Tests writing faculty records
     */
    @Test
    public void testWriteFacultyRecords() {
        Faculty faculty1 = new Faculty("Ashely", "Witt", "awitt", "mollis@Fuscealiquetmagna.net", hashPW, 2);
        Faculty faculty2 = new Faculty("Fiona", "Meadows", "fmeadow", "pharetra.sed@et.org", hashPW, 3);
        Faculty faculty3 = new Faculty("Brent", "Brewer", "bbrewer", "sem.semper@orcisem.co.uk", hashPW, 1);

        edu.ncsu.csc216.pack_scheduler.util.LinkedList<Faculty> faculties = new edu.ncsu.csc216.pack_scheduler.util.LinkedList<Faculty>();
        faculties.add(faculty1);
        faculties.add(faculty2);
        faculties.add(faculty3);

        try {
            FacultyRecordIO.writeFacultyRecords("test-files/actual_faculty_records.txt", faculties);
        } catch (IOException e) {
            fail("Cannot write to faculty records file");
        }

    }
}
