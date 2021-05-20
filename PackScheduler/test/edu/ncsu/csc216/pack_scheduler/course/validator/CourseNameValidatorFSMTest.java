package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.course.validator.CourseNameValidatorFSM;

/**
 * Tests the Provided CourseNameValidatorFSM code.
 * 
 * @author Rolf Lewis
 */
public class CourseNameValidatorFSMTest {
	
	CourseNameValidatorFSM validator = new CourseNameValidatorFSM();

	/**
	 * Tests invalid transitions while in Initial State
	 */
	@Test
	public void isValidTestStateInitialInvalid() {
		try {
			validator.isValid("1SCA216");
			fail();
		} catch (InvalidTransitionException e) {
			//Do nothing
		}
		
		try {
			validator.isValid("-SCA216");
			fail();
		} catch (InvalidTransitionException e) {
			//Do nothing
		}
	}
	
	/**
	 * Tests valid transitions while in Initial State
	 */
	@Test
	public void isValidTestStateInitialValid() {
		try {
			validator.isValid("CSCA216");
		} catch (InvalidTransitionException e) {
			fail();
		}
	}
	
	/**
	 * Tests invalid transitions while in Letter State
	 */
	@Test
	public void isValidTestStateLInvalid() {
		try {
			validator.isValid("C1CA216");
			fail();
		} catch (InvalidTransitionException e) {
			//Do nothing
		}
	}
	
	/**
	 * Tests valid transitions while in letter State
	 */
	@Test
	public void isValidTestStateLValid() {
		try {
			validator.isValid("CSCA216");
		} catch (InvalidTransitionException e) {
			fail();
		}
	}
	
	/**
	 * Tests invalid transitions while in Letter State
	 */
	@Test
	public void isValidTestStateLLInvalid() {
		try {
			validator.isValid("CS1A216");
			fail();
		} catch (InvalidTransitionException e) {
			//Do nothing
		}
	}
	
	/**
	 * Tests valid transitions while in letter State
	 */
	@Test
	public void isValidTestStateLLValid() {
		try {
			validator.isValid("CSCA216");
		} catch (InvalidTransitionException e) {
			fail();
		}
	}
	
	/**
	 * Tests invalid transitions while in Letter State
	 */
	@Test
	public void isValidTestStateLLLInvalid() {
		try {
			validator.isValid("CSC1216");
			fail();
		} catch (InvalidTransitionException e) {
			//Do nothing
		}
	}
	
	/**
	 * Tests valid transitions while in letter State
	 */
	@Test
	public void isValidTestStateLLLValid() {
		try {
			validator.isValid("CSCA216");
		} catch (InvalidTransitionException e) {
			fail();
		}
	}
	
	/**
	 * Tests invalid transitions while in Letter State
	 */
	@Test
	public void isValidTestStateLLLLInvalid() {
		try {
			validator.isValid("CSCAA16");
			fail();
		} catch (InvalidTransitionException e) {
			//Do nothing
		}
	}
	
	/**
	 * Tests valid transitions while in letter State
	 */
	@Test
	public void isValidTestStateLLLLValid() {
		try {
			validator.isValid("CSCA216");
		} catch (InvalidTransitionException e) {
			fail();
		}
	}
	
	/**
	 * Tests invalid transitions while in digit State
	 */
	@Test
	public void isValidTestStateDInvalid() {
		try {
			validator.isValid("CSCA2A6");
			fail();
		} catch (InvalidTransitionException e) {
			//Do nothing
		}
	}
	
	/**
	 * Tests valid transitions while in digit State
	 */
	@Test
	public void isValidTestStateDValid() {
		try {
			validator.isValid("CSCA216");
		} catch (InvalidTransitionException e) {
			fail();
		}
	}
	
	/**
	 * Tests invalid transitions while in digit State
	 */
	@Test
	public void isValidTestStateDDInvalid() {
		try {
			validator.isValid("CSCA21A");
			fail();
		} catch (InvalidTransitionException e) {
			//Do nothing
		}
	}
	
	/**
	 * Tests valid transitions while in digit State
	 */
	@Test
	public void isValidTestStateDDValid() {
		try {
			validator.isValid("CSCA216");
		} catch (InvalidTransitionException e) {
			fail();
		}
	}
	
	/**
	 * Tests invalid transitions while in digit State
	 */
	@Test
	public void isValidTestStateDDDInvalid() {
		try {
			validator.isValid("CSC2161");
			fail();
		} catch (InvalidTransitionException e) {
			//Do nothing
		}
	}
	
	/**
	 * Tests valid transitions while in digit State
	 */
	@Test
	public void isValidTestStateDDDValid() {
		try {
			validator.isValid("CSCA216");
		} catch (InvalidTransitionException e) {
			fail();
		}
		
		try {
			validator.isValid("CSCA216A");
		} catch (InvalidTransitionException e) {
			fail();
		}
	}
	
	/**
	 * Tests invalid transitions while in suffix State
	 */
	@Test
	public void isValidTestStatSuffixInvalid() {
		try {
			validator.isValid("CSCA2161");
			fail();
		} catch (InvalidTransitionException e) {
			//Do nothing
		}
		
		try {
			validator.isValid("CSCA216AA");
			fail();
		} catch (InvalidTransitionException e) {
			//Do nothing
		}
		
		try {
			validator.isValid("CSCA216A1");
			fail();
		} catch (InvalidTransitionException e) {
			//Do nothing
		}
	}
	
	/**
	 * Tests valid transitions while in valid State
	 */
	@Test
	public void isValidTestStateSuffixValid() {
		try {
			validator.isValid("CSCA216A");
		} catch (InvalidTransitionException e) {
			fail();
		}
	}
}
