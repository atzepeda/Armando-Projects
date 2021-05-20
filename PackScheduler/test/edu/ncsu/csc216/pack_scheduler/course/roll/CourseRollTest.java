package edu.ncsu.csc216.pack_scheduler.course.roll;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * Tests the CourseRoll object class
 * 
 * @author Rolf Lewis
 */
public class CourseRollTest {

	/** Tests getEnrollmentCap functionality */
	@Test
	public void testGetEnrollmentCap() {
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 15, "A");
		CourseRoll roll = c.getCourseRoll();
		assertEquals(15, roll.getEnrollmentCap());
		
		roll.setEnrollmentCap(25);
		assertEquals(25, roll.getEnrollmentCap());
	}
	
	/** Tests setEnrollmentCap functionality */
	@Test
	public void testSetEnrollmentCap() {
		Course c = new Course("CSC116", "Introduction to Java", "001", 4, "atzep", 15, "A");
		CourseRoll roll = c.getCourseRoll();
		
		try {
			roll.setEnrollmentCap(9);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(15, roll.getEnrollmentCap());
		}
		
		try {
			roll.setEnrollmentCap(300);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(15, roll.getEnrollmentCap());
		}
	}
	
	/** Tests drop functionality */
	@Test
	public void testDrop() {
		Course c = new Course("CSC316", "Advanced Java Concepts", "001", 4, "atzep", 100, "A");
		CourseRoll roll = c.getCourseRoll();
		
		try {
			roll.drop(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(100, roll.getEnrollmentCap());
		}
	}
	
	/** Tests checks to see if a student can enroll in a class */
	@Test
	public void testCanEnroll() {
		Course c = new Course("CSC200", "Computer Skills", "001", 4, "atzep", 10, "A");
		CourseRoll roll = c.getCourseRoll();
		
		assertFalse(roll.canEnroll(null));
		
		Student s1 = new Student("first", "last", "123", "email@email.com", "pass", 18);
		Student s2 = new Student("bobby", "tarantino", "123", "email@email.com", "pass", 18);
		Student s3 = new Student("henry", "parker", "1234", "email@email.com", "pass", 18);
		Student s4 = new Student("jack", "reacher", "4321", "email@email.com", "pass", 18);
		Student s5 = new Student("jen", "fuller", "135", "email@email.com", "pass", 18);
		Student s6 = new Student("jack", "reacher", "531", "email@email.com", "pass", 18);
		Student s7 = new Student("jack", "reacher", "142", "email@email.com", "pass", 18);
		Student s8 = new Student("jack", "reacher", "124", "email@email.com", "pass", 18);
		Student s9 = new Student("jack", "reacher", "512", "email@email.com", "pass", 18);
		Student s10 = new Student("jack", "reacher", "632", "email@email.com", "pass", 18);
		Student s11 = new Student("jack", "reacher", "987", "email@email.com", "pass", 18);
		Student s12 = new Student("Captain", "Jack", "435", "email@email.com", "pass", 18);
		
		roll.enroll(s1);
		assertFalse(roll.canEnroll(s2));
		
		s2 = new Student("bobby", "tarantino", "321", "email@email.com", "pass", 18);
		roll.enroll(s2);
		roll.enroll(s3);
		roll.enroll(s4);
		roll.enroll(s5);
		roll.enroll(s6);
		roll.enroll(s7);
		roll.enroll(s8);
		roll.enroll(s9);
		roll.enroll(s10);
		roll.enroll(s11);
		
		assertTrue(roll.canEnroll(s12));
	}
	
	/** Test enroll capability of courseRoll */
	@Test
	public void testEnroll() {
		Course c = new Course("CSC200", "Computer Skills", "001", 4, "atzep", 10, "A");
		CourseRoll roll = c.getCourseRoll();
		Student s1 = new Student("first", "last", "123", "email@email.com", "pass", 18);
		Student s2 = new Student("bobby", "tarantino", "321", "email@email.com", "pass", 18);
		Student s3 = new Student("henry", "parker", "1234", "email@email.com", "pass", 18);
		Student s4 = new Student("jack", "reacher", "4321", "email@email.com", "pass", 18);
		Student s5 = new Student("jen", "fuller", "135", "email@email.com", "pass", 18);
		Student s6 = new Student("jack", "reacher", "531", "email@email.com", "pass", 18);
		Student s7 = new Student("jack", "reacher", "142", "email@email.com", "pass", 18);
		Student s8 = new Student("jack", "reacher", "124", "email@email.com", "pass", 18);
		Student s9 = new Student("jack", "reacher", "512", "email@email.com", "pass", 18);
		Student s10 = new Student("jack", "reacher", "632", "email@email.com", "pass", 18);
		Student s11 = new Student("jack", "reacher", "987", "email@email.com", "pass", 18);
		roll.enroll(s1);
		roll.enroll(s2);
		roll.enroll(s3);
		roll.enroll(s4);
		roll.enroll(s5);
		roll.enroll(s6);
		roll.enroll(s7);
		roll.enroll(s8);
		roll.enroll(s9);
		roll.enroll(s10);
		assertEquals(0, roll.getOpenSeats());
		assertEquals(0, roll.getNumberOnWaitlist());
		roll.enroll(s11);
		assertEquals(1, roll.getNumberOnWaitlist());
		roll.drop(s1);
		assertEquals(0, roll.getOpenSeats());
		assertEquals(0, roll.getNumberOnWaitlist());
		roll.setEnrollmentCap(11);
		assertEquals(1, roll.getOpenSeats());
		roll.enroll(s1);
		assertEquals(0, roll.getOpenSeats());
	}
	
	/**
	 * Tests the getRoll() method
	 */
	@Test
	public void testGetRoll2DArray(){
        Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
        CourseRoll roll = c.getCourseRoll();
        Student s1 = new Student("Nick", "Macon", "nmacon", "nmacon@email.com", "pw");
        Student s2 = new Student("John", "Cena", "jcena", "jcena@email.com", "pw");
        roll.enroll(s1);
        roll.enroll(s2);
        String[][] s = roll.get2DArrayRoll();
        assertEquals("Nick", s[0][0]);
        assertEquals("Macon", s[0][1]);
        assertEquals("nmacon", s[0][2]);
        assertEquals("John", s[1][0]);
        assertEquals("Cena", s[1][1]);
        assertEquals("jcena", s[1][2]);
        
    }

}
