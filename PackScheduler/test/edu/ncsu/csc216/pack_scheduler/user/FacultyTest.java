package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Tests the faculty class
 * @author nmaco
 * @author atzep
 */
public class FacultyTest {
	/** First name of student */
	public static final String FIRST_NAME = "Mark";
	/** Last name of student */
	public static final String LAST_NAME = "Kalaska";
	/** Unique ID of student */
	public static final String ID = "123";
	/** Email of student account */
	public static final String EMAIL = "email@email.com";
	/** Password of student account */
	public static final String PASSWORD = "pass";
	/** Max amount of courses for a given student */
	public static final int MAX_COURSES = 3;

	/**
	 * Tests creating a new faculty object
	 */
	@Test
	public void testFacultyStringStringStringStringStringInt() {
		// test null first name
		Faculty s = null;
		try {
			s = new Faculty(null, LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}
		// test empty first name
		s = null;
		try {
			s = new Faculty("", LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}
		// test null last name
		s = null;
		try {
			s = new Faculty(FIRST_NAME, null, ID, EMAIL, PASSWORD, MAX_COURSES);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}
		// test empty last name
		s = null;
		try {
			s = new Faculty(FIRST_NAME, "", ID, EMAIL, PASSWORD, MAX_COURSES);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}
		// test null student ID
		s = null;
		try {
			s = new Faculty(FIRST_NAME, LAST_NAME, null, EMAIL, PASSWORD, MAX_COURSES);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}
		// test empty student ID
		s = null;
		try {
			s = new Faculty(FIRST_NAME, LAST_NAME, "", EMAIL, PASSWORD, MAX_COURSES);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}
		// test null email
		s = null;
		try {
			s = new Faculty(FIRST_NAME, LAST_NAME, ID, null, PASSWORD, MAX_COURSES);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}
		// test empty email
		s = null;
		try {
			s = new Faculty(FIRST_NAME, LAST_NAME, ID, "", PASSWORD, MAX_COURSES);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}
		// test email without a "@" character
		s = null;
		try {
			s = new Faculty(FIRST_NAME, LAST_NAME, ID, "sbalagoncsu.edu", PASSWORD, MAX_COURSES);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}
		// test email without a "." character
		s = null;
		try {
			s = new Faculty(FIRST_NAME, LAST_NAME, ID, "sbalago@ncsuedu", PASSWORD, MAX_COURSES);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}
		// test email with index of last "." preceding "@"
		s = null;
		try {
			s = new Faculty(FIRST_NAME, LAST_NAME, ID, "s.balago@ncsuedu", PASSWORD, MAX_COURSES);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}
		// test null password
		s = null;
		try {
			s = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, null, MAX_COURSES);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}
		// test empty password
		s = null;
		try {
			s = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, "", MAX_COURSES);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}
		// test max courses length less than 3
		s = null;
		try {
			s = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 0);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}
		// test max courses length greater than 18
		s = null;
		try {
			s = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 19);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}
		// test a valid construction with 6 parameters
		s = null;
		try {
			s = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);
			assertEquals(FIRST_NAME, s.getFirstName());
			assertEquals(LAST_NAME, s.getLastName());
			assertEquals(ID, s.getId());
			assertEquals(EMAIL, s.getEmail());
			assertEquals(PASSWORD, s.getPassword());
			assertEquals(MAX_COURSES, s.getMaxCourses());
		} catch (IllegalArgumentException e) {
			fail();
		}
	}
	/**
	 * Tests setting max courses
	 */
	@Test
	public void testSetMaxCourses() {
		//Construct a valid Faculty
		Faculty f = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);
		assertEquals(FIRST_NAME, f.getFirstName());
		assertEquals(LAST_NAME, f.getLastName());
		assertEquals(ID, f.getId());
		assertEquals(EMAIL, f.getEmail());
		assertEquals(PASSWORD, f.getPassword());
		assertEquals(MAX_COURSES, f.getMaxCourses());

		// courses less than 1
		try {
			f.setMaxCourses(0);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, f.getFirstName());
			assertEquals(LAST_NAME, f.getLastName());
			assertEquals(ID, f.getId());
			assertEquals(EMAIL, f.getEmail());
			assertEquals(PASSWORD, f.getPassword());
			assertEquals(MAX_COURSES, f.getMaxCourses());
		}

		// courses greater than 3
		try {
			f.setMaxCourses(4);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, f.getFirstName());
			assertEquals(LAST_NAME, f.getLastName());
			assertEquals(ID, f.getId());
			assertEquals(EMAIL, f.getEmail());
			assertEquals(PASSWORD, f.getPassword());
			assertEquals(MAX_COURSES, f.getMaxCourses());
		}
		f.setMaxCourses(3);
		assertEquals(FIRST_NAME, f.getFirstName());
		assertEquals(LAST_NAME, f.getLastName());
		assertEquals(ID, f.getId());
		assertEquals(EMAIL, f.getEmail());
		assertEquals(PASSWORD, f.getPassword());
		assertEquals(3, f.getMaxCourses());
	}
	/**
	 * Tests equals(). Checks that different objects are not equal and
	 * that objects of the same state are equal.
	 */
	@Test
	public void testEqualsObject() {
		User f1 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);
		User s2 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);
		User s3 = new Faculty("different", LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);
		User s4 = new Faculty(FIRST_NAME, "different", ID, EMAIL, PASSWORD, MAX_COURSES);
		User s5 = new Faculty(FIRST_NAME, LAST_NAME, "different", EMAIL, PASSWORD, MAX_COURSES);
		User s6 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, "different", MAX_COURSES);
		User s7 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 2);

		// Test for equality in both directions
		assertTrue(f1.equals(s2));
		assertTrue(s2.equals(f1));

		// Test for each of the fields
		assertFalse(f1.equals(s3));
		assertFalse(s2.equals(s3));
		assertFalse(f1.equals(s4));
		assertFalse(f1.equals(s5));
		assertFalse(f1.equals(s6));
		assertFalse(f1.equals(s7));
	}

	/**
	 * Tests hashCode() and that two equal objects have the same hash code.
	 */
	@Test
	public void testHashCode() {
		User f1 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		User f2 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		User f3 = new Faculty("different", LAST_NAME, ID, EMAIL, PASSWORD);
		User f4 = new Faculty(FIRST_NAME, "different", ID, EMAIL, PASSWORD);
		User f5 = new Faculty(FIRST_NAME, LAST_NAME, "different", EMAIL, PASSWORD);
		User f6 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, "different");
		User f7 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 2);

		// test for the same hash code for the same values
		assertEquals(f1.hashCode(), f2.hashCode());

		// test for each of the fields
		assertNotEquals(f1.hashCode(), f3.hashCode());
		assertNotEquals(f1.hashCode(), f4.hashCode());
		assertNotEquals(f1.hashCode(), f5.hashCode());
		assertNotEquals(f1.hashCode(), f6.hashCode());
		assertNotEquals(f1.hashCode(), f7.hashCode());
	}

	/**
	 * Test toString() and that valid String interpretation of Faculty is generated.
	 */
	@Test
	public void testToString() {
		User f1 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 2);
		String str1 = "Mark,Kalaska,123,email@email.com,pass,2";
		assertEquals(str1, f1.toString());

		User f2 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);
		String str2 = "Mark,Kalaska,123,email@email.com,pass,3";
		assertEquals(str2, f2.toString());
	}

	/**
	 * Tests the compareTo method
	 */
	@Test
	public void testCompareTo() {
		Faculty f1 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		Faculty f2 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		Faculty f3 = new Faculty("different", LAST_NAME, ID, EMAIL, PASSWORD);
		Faculty f4 = new Faculty(FIRST_NAME, "different", ID, EMAIL, PASSWORD);
		Faculty f5 = new Faculty(FIRST_NAME, LAST_NAME, "different", EMAIL, PASSWORD);
		if (f2.compareTo(f1) != 0) {
			fail();
		}
		if (f2.compareTo(f3) >= 0) {
			fail();
		}
		if (f2.compareTo(f3) >= -1) {
			fail();
		}
		if (f2.compareTo(f4) >= -1) {
			fail();
		}
		if (f2.compareTo(f5) >= -1) {
			fail();
		}
	}
}

