package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the generic LinkedAbstractList Class
 * 
 * @author Rolf Lewis
 */
public class LinkedAbstractListTest {

	LinkedAbstractList<Integer> linkedAbstractListInts;
	LinkedAbstractList<String> linkedAbstractListStrings;

	/**
	 * Tests creating an LinkedAbstractList of ints
	 */
	@Test
	public void testLinkedAbstractListInt() {
		linkedAbstractListInts = new LinkedAbstractList<Integer>(15);
		assertEquals(linkedAbstractListInts.size(), 0);	
	}

	/** 
	 * Tests creating an LinkedAbstractList of strings
	 */
	@Test
	public void testLinkedAbstractListString() {
		linkedAbstractListStrings = new LinkedAbstractList<String>(15);
		assertEquals(linkedAbstractListStrings.size(), 0);	
	}
	
	/**
	 * Tests getting the LinkedAbstractList
	 */
	@Test
	public void testGet() {
		linkedAbstractListInts = new LinkedAbstractList<Integer>(15);
		
		linkedAbstractListInts.add(0, new Integer(0));
		linkedAbstractListInts.add(1, new Integer(1));
		linkedAbstractListInts.add(2, new Integer(2));
		linkedAbstractListInts.add(3, new Integer(3));
		linkedAbstractListInts.add(4, new Integer(4));
		
		assertEquals(linkedAbstractListInts.get(2), new Integer(2));
		assertEquals(linkedAbstractListInts.get(4), new Integer(4));
		assertEquals(linkedAbstractListInts.get(0), new Integer(0));
		
		linkedAbstractListStrings = new LinkedAbstractList<String>(15);
		
		linkedAbstractListStrings.add(0, "One");
		linkedAbstractListStrings.add(1, "Two");
		linkedAbstractListStrings.add(2, "Three");
		linkedAbstractListStrings.add(3, "Four");
		linkedAbstractListStrings.add(4, "Five");
		
		assertEquals(linkedAbstractListStrings.get(2), "Three");
		assertEquals(linkedAbstractListStrings.get(3), "Four");
		assertEquals(linkedAbstractListStrings.get(0), "One");
	}

	/**
	 * Tests adding to the LinkedAbstractList
	 */
	@Test
	public void testAdd() {
		linkedAbstractListStrings = new LinkedAbstractList<String>(15);
		
		linkedAbstractListStrings.add(0, "Zero");
		linkedAbstractListStrings.add(1, "One");
		linkedAbstractListStrings.add(2, "Two");
		linkedAbstractListStrings.add(3, "Three");
		linkedAbstractListStrings.add(4, "Four");
		
		assertEquals(linkedAbstractListStrings.get(2), "Two");
		assertEquals(linkedAbstractListStrings.get(4), "Four");
		assertEquals(linkedAbstractListStrings.get(0), "Zero");
		
		linkedAbstractListStrings.add(0, "Five");
		linkedAbstractListStrings.add(3, "Six");
		linkedAbstractListStrings.add(7, "Seven");
		
		assertEquals(linkedAbstractListStrings.get(0), "Five");
		assertEquals(linkedAbstractListStrings.get(1), "Zero");
		assertEquals(linkedAbstractListStrings.get(2), "One");
		assertEquals(linkedAbstractListStrings.get(3), "Six");
		assertEquals(linkedAbstractListStrings.get(4), "Two");
		assertEquals(linkedAbstractListStrings.get(5), "Three");
		assertEquals(linkedAbstractListStrings.get(6), "Four");
		assertEquals("Seven", linkedAbstractListStrings.get(7));
		
		linkedAbstractListStrings.add(0, "UNO");
		linkedAbstractListStrings.add(1, "DOS");
		linkedAbstractListStrings.add(2, "TRES");
		linkedAbstractListStrings.add(3, "CUATRO");
		linkedAbstractListStrings.add(4, "CINCO");
		
		assertEquals(linkedAbstractListStrings.get(2), "TRES");
		assertEquals(linkedAbstractListStrings.get(3), "CUATRO");
		assertEquals(linkedAbstractListStrings.get(0), "UNO");

	}

	/**
	 * Tests removing from the LinkedAbstractList
	 */
	@Test
	public void testRemove() {
		LinkedAbstractList<String> tests = new LinkedAbstractList<String>(15);
		tests.add(0, "One");
		tests.add(1, "Two");
		tests.remove(1);
		assertEquals("One", tests.get(0));

		try{
			tests.remove(-1);
			fail();
		}
		catch(IndexOutOfBoundsException e){
			assertEquals("One", tests.get(0));
		}
	}

	/**
	 * Tests setting an element in the LinkedAbstractList
	 */
	@Test
	public void testSet() {
		LinkedAbstractList<String> tests = new LinkedAbstractList<String>(5);
		tests.add(0, "One");
		tests.add(1, "Two");
		tests.set(1, "Three");
		assertEquals("Three", tests.get(1));

		try{
			tests.set(1, null);
			fail();
		} catch(NullPointerException e){
			assertEquals("Three", tests.get(1));
		}

		try{
			tests.set(-1, "Invalid");
			fail();
		} catch(IndexOutOfBoundsException e) {
			assertEquals("One", tests.get(0));
		}
	}
}
