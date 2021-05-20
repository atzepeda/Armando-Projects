package edu.ncsu.csc216.pack_scheduler.user;
import static org.junit.Assert.*;

import org.junit.Test;
/**
 * Tests the Student Object
 * @author Shalini Balagopal Nick Macon
 *
 */
public class StudentTest {

	/** First name of student */
	public static final String FIRST_NAME = "Nick";
	/** Last name of student */
	public static final String LAST_NAME =  "Macon";
	/** Unique ID of student */
	public static final String ID = "001";
	/** Email of student account */
	public static final String EMAIL = "namacon@ncsu.edu";
	/** Password of student account */
	public static final String PASSWORD = "password";
	/** Max amount of credits for a given student */
	public static final int MAX_CREDITS = 18;
	
	/**
	 * Tests the Student constructor with all field parameters.
	 */
	@Test
	public void testStudentStringStringStringStringStringInt() {
		// test null first name
		Student s = null;
		try {
		    s = new Student(null, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		    fail();
		} catch (IllegalArgumentException e) {
		    assertNull(s);
		}
		// test empty first name
		s = null;
		try {
		    s = new Student("", LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		    fail();
		} catch (IllegalArgumentException e) {
		    assertNull(s);
		}
		// test null last name
		s = null;
		try {
		    s = new Student(FIRST_NAME, null, ID, EMAIL, PASSWORD, MAX_CREDITS);
		    fail();
		} catch (IllegalArgumentException e) {
		    assertNull(s);
		}
		// test empty last name
		s = null;
		try {
		    s = new Student(FIRST_NAME, "", ID, EMAIL, PASSWORD, MAX_CREDITS);
		    fail();
		} catch (IllegalArgumentException e) {
		    assertNull(s);
		}
		// test null student ID
		s = null;
		try {
		    s = new Student(FIRST_NAME, LAST_NAME, null, EMAIL, PASSWORD, MAX_CREDITS);
		    fail();
		} catch (IllegalArgumentException e) {
		    assertNull(s);
		}
		// test empty student ID
		s = null;
		try {
		    s = new Student(FIRST_NAME, LAST_NAME, "", EMAIL, PASSWORD, MAX_CREDITS);
		    fail();
		} catch (IllegalArgumentException e) {
		    assertNull(s);
		}
		// test null email
		s = null;
		try {
		    s = new Student(FIRST_NAME, LAST_NAME, ID, null, PASSWORD, MAX_CREDITS);
		    fail();
		} catch (IllegalArgumentException e) {
		    assertNull(s);
		}
		// test empty email
		s = null;
		try {
		    s = new Student(FIRST_NAME, LAST_NAME, ID, "", PASSWORD, MAX_CREDITS);
		    fail();
		} catch (IllegalArgumentException e) {
		    assertNull(s);
		}
		// test email without a "@" character
		s = null;
		try {
		    s = new Student(FIRST_NAME, LAST_NAME, ID, "sbalagoncsu.edu", PASSWORD, MAX_CREDITS);
		    fail();
		} catch (IllegalArgumentException e) {
		    assertNull(s);
		}
		// test email without a "." character
		s = null;
		try {
		    s = new Student(FIRST_NAME, LAST_NAME, ID, "sbalago@ncsuedu", PASSWORD, MAX_CREDITS);
		    fail();
		} catch (IllegalArgumentException e) {
		    assertNull(s);
		}
		// test email with index of last "." preceding "@"
		s = null;
		try {
		    s = new Student(FIRST_NAME, LAST_NAME, ID, "s.balago@ncsuedu", PASSWORD, MAX_CREDITS);
		    fail();
		} catch (IllegalArgumentException e) {
		    assertNull(s);
		}
		// test null password
		s = null;
		try {
		    s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, null, MAX_CREDITS);
		    fail();
		} catch (IllegalArgumentException e) {
		    assertNull(s);
		}
		// test empty password
		s = null;
		try {
		    s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, "", MAX_CREDITS);
		    fail();
		} catch (IllegalArgumentException e) {
		    assertNull(s);
		}
		// test max credits length less than 3
		s = null;
		try {
		    s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 0);
		    fail();
		} catch (IllegalArgumentException e) {
		    assertNull(s);
		}
		// test max credits length greater than 18
		s = null;
		try {
		    s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 19);
		    fail();
		} catch (IllegalArgumentException e) {
		    assertNull(s);
		}
		// test a valid construction with 6 parameters
		s = null;
		try {
			s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
			assertEquals(FIRST_NAME, s.getFirstName());
			assertEquals(LAST_NAME, s.getLastName());
			assertEquals(ID, s.getId());
			assertEquals(EMAIL, s.getEmail());
			assertEquals(PASSWORD, s.getPassword());
			assertEquals(MAX_CREDITS, s.getMaxCredits());
		} catch (IllegalArgumentException e) {
			fail();
		}
	}

	/**
	 * Tests the Student constructor with all 5 parameters
	 */
	@Test
	public void testStudentStringStringStringStringString() {
		// test a valid construction with 5 parameters
		Student s = null;
		try {
			s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
			assertEquals(FIRST_NAME, s.getFirstName());
			assertEquals(LAST_NAME, s.getLastName());
			assertEquals(ID, s.getId());
			assertEquals(EMAIL, s.getEmail());
			assertEquals(PASSWORD, s.getPassword());
			assertEquals(18, s.getMaxCredits());
		} catch (IllegalArgumentException e) {
			fail();
		}
	}

	/**
	 * Tests setFirstName() and that it does not affect other setters.
	 */
	@Test
	public void testSetFirstName() {
		//Construct a valid Student
		User s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		assertEquals(FIRST_NAME, s.getFirstName());
		assertEquals(LAST_NAME, s.getLastName());
		assertEquals(ID, s.getId());
		assertEquals(EMAIL, s.getEmail());
		assertEquals(PASSWORD, s.getPassword());
		
		// test setting firstName to null does not change anything else
		try {
			s.setFirstName(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, s.getFirstName());
			assertEquals(LAST_NAME, s.getLastName());
			assertEquals(ID, s.getId());
			assertEquals(EMAIL, s.getEmail());
			assertEquals(PASSWORD, s.getPassword());
		}
		
		// test setting firstName as an empty string does not change anything else
		try {
			s.setFirstName("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, s.getFirstName());
			assertEquals(LAST_NAME, s.getLastName());
			assertEquals(ID, s.getId());
			assertEquals(EMAIL, s.getEmail());
			assertEquals(PASSWORD, s.getPassword());
			
		}
		// valid setup
		s.setFirstName("Shalini");
		assertEquals("Shalini", s.getFirstName());
		assertEquals(LAST_NAME, s.getLastName());
		assertEquals(ID, s.getId());
		assertEquals(EMAIL, s.getEmail());
		assertEquals(PASSWORD, s.getPassword());
	}

	/**
	 * Tests setLastName() and that it does not affect other setters.
	 */
	@Test
	public void testSetLastName() {
		//Construct a valid Student
		User s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		assertEquals(FIRST_NAME, s.getFirstName());
		assertEquals(LAST_NAME, s.getLastName());
		assertEquals(ID, s.getId());
		assertEquals(EMAIL, s.getEmail());
		assertEquals(PASSWORD, s.getPassword());
		try {
			s.setLastName(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, s.getFirstName());
			assertEquals(LAST_NAME, s.getLastName());
			assertEquals(ID, s.getId());
			assertEquals(EMAIL, s.getEmail());
			assertEquals(PASSWORD, s.getPassword());
		}
		try {
			s.setLastName("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, s.getFirstName());
			assertEquals(LAST_NAME, s.getLastName());
			assertEquals(ID, s.getId());
			assertEquals(EMAIL, s.getEmail());
			assertEquals(PASSWORD, s.getPassword());
		}
		s.setLastName("Balagopal");
		assertEquals(FIRST_NAME, s.getFirstName());
		assertEquals("Balagopal", s.getLastName());
		assertEquals(ID, s.getId());
		assertEquals(EMAIL, s.getEmail());
		assertEquals(PASSWORD, s.getPassword());
	}

	/**
	 * Tests setEmail() and that it does not affect other setters.
	 */
	@Test
	public void testSetEmail() {
		User s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		assertEquals(FIRST_NAME, s.getFirstName());
		assertEquals(LAST_NAME, s.getLastName());
		assertEquals(ID, s.getId());
		assertEquals(EMAIL, s.getEmail());
		assertEquals(PASSWORD, s.getPassword());
		
		// test null email
		try {
		    s.setEmail(null);
		    fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, s.getFirstName());
			assertEquals(LAST_NAME, s.getLastName());
			assertEquals(ID, s.getId());
			assertEquals(EMAIL, s.getEmail());
			assertEquals(PASSWORD, s.getPassword());
		}
		// test empty email
		try {
		    s.setEmail("");
		    fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, s.getFirstName());
			assertEquals(LAST_NAME, s.getLastName());
			assertEquals(ID, s.getId());
			assertEquals(EMAIL, s.getEmail());
			assertEquals(PASSWORD, s.getPassword());
		}
		// test email without a "@" character
		try {
		    s.setEmail("sbalagoncsu.edu");
		    fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, s.getFirstName());
			assertEquals(LAST_NAME, s.getLastName());
			assertEquals(ID, s.getId());
			assertEquals(EMAIL, s.getEmail());
			assertEquals(PASSWORD, s.getPassword());
		}
		// test email without a "." character
		try {
		    s.setEmail("sbalago@ncsuedu");
		    fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, s.getFirstName());
			assertEquals(LAST_NAME, s.getLastName());
			assertEquals(ID, s.getId());
			assertEquals(EMAIL, s.getEmail());
			assertEquals(PASSWORD, s.getPassword());
		}
		// test email with index of last "." preceding "@"
		try {
		    s.setEmail("s.balago@ncsuedu");
		    fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, s.getFirstName());
			assertEquals(LAST_NAME, s.getLastName());
			assertEquals(ID, s.getId());
			assertEquals(EMAIL, s.getEmail());
			assertEquals(PASSWORD, s.getPassword());
		}
	}

	/**
	 * Tests setPassword() and that it does not affect other setters.
	 */
	@Test
	public void testSetPassword() {
		//Construct a valid Student
		User s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		assertEquals(FIRST_NAME, s.getFirstName());
		assertEquals(LAST_NAME, s.getLastName());
		assertEquals(ID, s.getId());
		assertEquals(EMAIL, s.getEmail());
		assertEquals(PASSWORD, s.getPassword());
		try {
			s.setPassword(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, s.getFirstName());
			assertEquals(LAST_NAME, s.getLastName());
			assertEquals(ID, s.getId());
			assertEquals(EMAIL, s.getEmail());
			assertEquals(PASSWORD, s.getPassword());
		}
		try {
			s.setPassword("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, s.getFirstName());
			assertEquals(LAST_NAME, s.getLastName());
			assertEquals(ID, s.getId());
			assertEquals(EMAIL, s.getEmail());
			assertEquals(PASSWORD, s.getPassword());
		}
		s.setPassword("pass");
		assertEquals(FIRST_NAME, s.getFirstName());
		assertEquals(LAST_NAME, s.getLastName());
		assertEquals(ID, s.getId());
		assertEquals(EMAIL, s.getEmail());
		assertEquals("pass", s.getPassword());
		
	}

	/**
	 * Tests setMaxCredits() and that it does not affect other setters.
	 */
	@Test
	public void testSetMaxCredits() {
		//Construct a valid Student
		Student s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		assertEquals(FIRST_NAME, s.getFirstName());
		assertEquals(LAST_NAME, s.getLastName());
		assertEquals(ID, s.getId());
		assertEquals(EMAIL, s.getEmail());
		assertEquals(PASSWORD, s.getPassword());
		assertEquals(MAX_CREDITS, s.getMaxCredits());
		
		// credits less than 3
		try {
			s.setMaxCredits(2);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, s.getFirstName());
			assertEquals(LAST_NAME, s.getLastName());
			assertEquals(ID, s.getId());
			assertEquals(EMAIL, s.getEmail());
			assertEquals(PASSWORD, s.getPassword());
			assertEquals(MAX_CREDITS, s.getMaxCredits());
		}
		
		// credits greater than 18
		try {
			s.setMaxCredits(19);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, s.getFirstName());
			assertEquals(LAST_NAME, s.getLastName());
			assertEquals(ID, s.getId());
			assertEquals(EMAIL, s.getEmail());
			assertEquals(PASSWORD, s.getPassword());
			assertEquals(MAX_CREDITS, s.getMaxCredits());
		}
		s.setMaxCredits(18);
		assertEquals(FIRST_NAME, s.getFirstName());
		assertEquals(LAST_NAME, s.getLastName());
		assertEquals(ID, s.getId());
		assertEquals(EMAIL, s.getEmail());
		assertEquals(PASSWORD, s.getPassword());
		assertEquals(18, s.getMaxCredits());
	}

	/**
	 * Tests equals(). Checks that different objects are not equal and
	 * that objects of the same state are equal.
	 */
	@Test
	public void testEqualsObject() {
		User s1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		User s2 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		User s3 = new Student("different", LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		User s4 = new Student(FIRST_NAME, "different", ID, EMAIL, PASSWORD, MAX_CREDITS);
		User s5 = new Student(FIRST_NAME, LAST_NAME, "different", EMAIL, PASSWORD, MAX_CREDITS);
		User s6 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, "different", MAX_CREDITS);
		User s7 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 15);

		// Test for equality in both directions
		assertTrue(s1.equals(s2));
		assertTrue(s2.equals(s1));

		// Test for each of the fields
		assertFalse(s1.equals(s3));
		assertFalse(s2.equals(s3));
		assertFalse(s1.equals(s4));
		assertFalse(s1.equals(s5));
		assertFalse(s1.equals(s6));
		assertFalse(s1.equals(s7));
	}

	/**
	 * Tests hashCode() and that two equal objects have the same hash code.
	 */
	@Test
	public void testHashCode() {
		User s1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		User s2 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		User s3 = new Student("different", LAST_NAME, ID, EMAIL, PASSWORD);
		User s4 = new Student(FIRST_NAME, "different", ID, EMAIL, PASSWORD);
		User s5 = new Student(FIRST_NAME, LAST_NAME, "different", EMAIL, PASSWORD);
		User s6 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, "different");
		User s7 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 15);
		
		// test for the same hash code for the same values
		assertEquals(s1.hashCode(), s2.hashCode());
		
		// test for each of the fields
		assertNotEquals(s1.hashCode(), s3.hashCode());
		assertNotEquals(s1.hashCode(), s4.hashCode());
		assertNotEquals(s1.hashCode(), s5.hashCode());
		assertNotEquals(s1.hashCode(), s6.hashCode());
		assertNotEquals(s1.hashCode(), s7.hashCode());
	}

	/**
	 * Test toString() and that valid String interpretation of Student is generated.
	 */
	@Test
	public void testToString() {
        User s1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 15);
		String str1 = "Nick,Macon,001,namacon@ncsu.edu,password,15";
		assertEquals(str1, s1.toString());

		User s2 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		String str2 = "Nick,Macon,001,namacon@ncsu.edu,password,18";
		assertEquals(str2, s2.toString());
	}
	
	/**
     * Tests the compareTo method
     */
    @Test
    public void testCompareTo() {
    		Student s1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		Student s2 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		Student s3 = new Student("different", LAST_NAME, ID, EMAIL, PASSWORD);
		Student s4 = new Student(FIRST_NAME, "different", ID, EMAIL, PASSWORD);
		Student s5 = new Student(FIRST_NAME, LAST_NAME, "different", EMAIL, PASSWORD);
    		if (s2.compareTo(s1) != 0) {
            fail();
        }
        if (s2.compareTo(s3) >= 0) {
            fail();
        }
        if (s2.compareTo(s3) >= -1) {
            fail();
        }
        if (s2.compareTo(s4) >= -1) {
            fail();
        }
        if (s2.compareTo(s5) >= -1) {
            fail();
        }
    }

}
