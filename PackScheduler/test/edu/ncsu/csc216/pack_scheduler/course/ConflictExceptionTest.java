package edu.ncsu.csc216.pack_scheduler.course;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the ConflictException class.
 * @author Shalini
 */
public class ConflictExceptionTest {

	/**
	 * Test method for ConflictException constructor that accepts a String.
	 */
	@Test
	public void testConflictExceptionString() {
	    ConflictException ce = new ConflictException("Custom exception message");
	    assertEquals("Custom exception message", ce.getMessage());
	}

	/**
	 * Test method for ConflictException constructor that has no parameter.
	 */
	@Test
	public void testConflictException() {
		ConflictException ce = new ConflictException();
		assertEquals("Schedule conflict.", ce.getMessage());
	}

}
