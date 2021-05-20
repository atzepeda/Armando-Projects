package edu.ncsu.csc216.pack_scheduler.user.schedule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * Tests the schedule class
 * @author Nick Macon
 * @author Rolf Lewis
 *
 */
public class ScheduleTest {

	/**
	 * Test the constructor
	 */
    @Test
    public void testSchedule() {
    	Schedule schedule = new Schedule();
    	assertEquals(schedule.getTitle(), "My Schedule");
    }
    
    /**
     * Tests the adding a course to the schedule
     */
    @Test
    public void testAddCourseToSchedule() {
    	Schedule schedule = new Schedule();
    	Course course = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith", 150, "TH", 1330, 1445);
    	Course course2 = new Course("FS101", "Potato Chips", "001", 4, "sesmith", 150, "TH", 1430, 1445);
    	assertTrue(schedule.addCourseToSchedule(course));
    	try {
    		schedule.addCourseToSchedule(course);
    	} catch (IllegalArgumentException e){
    		assertEquals("You are already enrolled in " + course.getName(), e.getMessage());
    	}
    	try {
    		schedule.addCourseToSchedule(course2);
    	} catch (IllegalArgumentException e){
    		assertEquals("The course cannot be added due to a conflict.", e.getMessage());
    	}
    }
    
    /**
     * Tests removing a course from the schedule
     */
    @Test
    public void testRemoveCourseFromSchedule() {
    	Schedule schedule = new Schedule();
    	Course course1 = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith", 150, "TH", 1330, 1445);
    	Course course2 = new Course("CSC116", "Programming Concepts - Java", "002", 4, "sesmith", 150, "TH", 1330, 1445);
    	schedule.addCourseToSchedule(course1);
    	assertTrue(schedule.removeCourseFromSchedule(course1));
    	assertFalse(schedule.removeCourseFromSchedule(course2));
    	
    }
    
    /**
     * Tests resetting the schedule
     */
    @Test
    public void testResetSchedule() {
    	Schedule schedule = new Schedule();
    	Course course = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith", 150, "TH", 1330, 1445);
    	schedule.addCourseToSchedule(course);
    	
    	schedule.resetSchedule();
    	
    	assertFalse(schedule.removeCourseFromSchedule(course));
    }
    
    /**
     * Tests getting the schedule
     */
    @Test
    public void testGetScheduledCourses() {
    	Schedule schedule = new Schedule();
    	Course course = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith", 150, "TH", 1330, 1445);
    	schedule.addCourseToSchedule(course);
    	String[][] s = schedule.getScheduledCourses();
    	assertEquals(s[0][0], "CSC216");
    	assertEquals(s[0][1], "001");
    	assertEquals(s[0][2], "Programming Concepts - Java");
    	assertEquals(s[0][3], "TH 1:30PM-2:45PM"); 
    }
    
    /**
     * Test setting a new schedule title
     */
    @Test
    public void testSetTitle() {
    	Schedule schedule = new Schedule();
    	schedule.setTitle("Lab Group 4's Schedule");
    	assertEquals(schedule.getTitle(), "Lab Group 4's Schedule");
    }
}
