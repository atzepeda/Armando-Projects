package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Tests the linkedlistrecurseive java class
 * @author nmaco
 * @author atzep
 */
public class LinkedListRecursiveTest {

	/**
	 * Sets up the linkedlist for testing
	 */
	@Test
	public void setUp() {
		LinkedListRecursive<?> test = new LinkedListRecursive<Object>();
		assertEquals(0, test.size());
	}
	
	/**
	 * Tests the get functionality
	 */
	@Test
	public void testGet() {
		LinkedListRecursive<String> tests = new LinkedListRecursive<String>();
		tests.add("one");
		tests.add("two");
		assertEquals("one", tests.get(0));
		try {
			tests.get(-1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(2, tests.size());
		}
	}
	
	/**
	 * Tests the add functionality for the linked list
	 */
	@Test
	public void testAdd() {
		LinkedListRecursive<String> tests = new LinkedListRecursive<String>();
		assertTrue(tests.isEmpty());
		tests.add("one");
		assertEquals(1, tests.size());
		assertEquals("one", tests.get(0));
		tests.add("two");
		assertEquals(2, tests.size());
		assertEquals("two", tests.get(1));
		
		try{
			tests.add(-1, "fail");
			fail();
		} catch(IndexOutOfBoundsException e){
			assertEquals(2, tests.size());
		}
		try{
			tests.add(1, null);
			fail();
		} catch(NullPointerException e){
			assertEquals(2, tests.size());
		}
		try {
			tests.add("one");
			fail();
		} catch (IllegalArgumentException e){
			assertEquals(2, tests.size());
		}
	}
	
	/**
	 * Test the remove functionality for a linkedlist
	 */
	@Test
	public void testRemove() {
		LinkedListRecursive<String> tests = new LinkedListRecursive<String>();
		tests.add("one");
		tests.add("two");
		tests.add("three");
		tests.remove(0);
		assertEquals(2, tests.size());
		assertEquals("two", tests.get(0));
		tests.remove(1);
		assertEquals(1, tests.size());
		assertEquals("two", tests.get(0));
	}
	
	/**
	 * Tests the set functionality of a linked list
	 */
	@Test
	public void testSet() {
		LinkedListRecursive<String> tests = new LinkedListRecursive<String>();
		tests.add("one");
		tests.add("two");
		tests.add("three");
		assertEquals("one", tests.set(0, "four"));
		assertEquals(3, tests.size());
		assertEquals("four", tests.get(0));
	}
}
