package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.course.validator.InvalidTransitionException;

/**
 * Tests the ConflictException class.
 * @author Rolf Lewis
 */
public class InvalidTransitionExceptionTest {

	/**
	 * Test method for InvalidTransitionException constructor that accepts a String.
	 */
	@Test
	public void testInvalidTransitionExceptionString() {
		InvalidTransitionException ce = new InvalidTransitionException("Custom exception message");
		assertEquals("Custom exception message", ce.getMessage());
	}

	/**
	 * Test method for InvalidTransitionException constructor that has no parameter.
	 */
	@Test
	public void testInvalidTransitionException() {
		InvalidTransitionException ce = new InvalidTransitionException();
		assertEquals("Invalid FSM Transition.", ce.getMessage());
	}

}
