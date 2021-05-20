package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the Arraylist class
 * @author Nick Macon
 * @author Rolf Lewis
 *
 */
public class ArrayListTest {

	ArrayList<Integer> arrayListInts = new ArrayList<Integer>();
	ArrayList<String> arrayListStrings = new ArrayList<String>();

	/**
	 * Sets up the arraylist 
	 */
	@Before
	public void setUp() {
		arrayListInts = new ArrayList<Integer>();
	}

	/**
	 * Tests creating an arraylist of ints
	 */
	@Test
	public void testArrayListInt() {
		assertEquals(arrayListInts.size(), 0);	
	}

	/** 
	 * Tests creating an arraylist of strings
	 */
	@Test
	public void testArrayListString() {
		assertEquals(arrayListStrings.size(), 0);	
	}
	
	/**
	 * Tests getting the arraylist
	 */
	@Test
	public void testGet() {
		arrayListInts.add(0, new Integer(0));
		arrayListInts.add(1, new Integer(1));
		arrayListInts.add(2, new Integer(2));
		arrayListInts.add(3, new Integer(3));
		arrayListInts.add(4, new Integer(4));
		
		assertEquals(arrayListInts.get(2), new Integer(2));
		assertEquals(arrayListInts.get(4), new Integer(4));
		assertEquals(arrayListInts.get(0), new Integer(0));
		
		arrayListStrings.add(0, "One");
		arrayListStrings.add(1, "Two");
		arrayListStrings.add(2, "Three");
		arrayListStrings.add(3, "Four");
		arrayListStrings.add(4, "Five");
		
		assertEquals(arrayListStrings.get(2), "Three");
		assertEquals(arrayListStrings.get(3), "Four");
		assertEquals(arrayListStrings.get(0), "One");
	}

	/**
	 * Tests adding to the arraylist
	 */
	@Test
	public void testAdd() {
		arrayListInts.add(0, new Integer(0));
		arrayListInts.add(1, new Integer(1));
		arrayListInts.add(2, new Integer(2));
		arrayListInts.add(3, new Integer(3));
		arrayListInts.add(4, new Integer(4));
		
		assertEquals(arrayListInts.get(2), new Integer(2));
		assertEquals(arrayListInts.get(4), new Integer(4));
		assertEquals(arrayListInts.get(0), new Integer(0));
		
		arrayListInts.add(0, new Integer(5));
		arrayListInts.add(3, new Integer(6));
		arrayListInts.add(7, new Integer(7));
		
		assertEquals(arrayListInts.get(0), new Integer(5));
		assertEquals(arrayListInts.get(1), new Integer(0));
		assertEquals(arrayListInts.get(2), new Integer(1));
		assertEquals(arrayListInts.get(3), new Integer(6));
		assertEquals(arrayListInts.get(4), new Integer(2));
		assertEquals(arrayListInts.get(5), new Integer(3));
		assertEquals(arrayListInts.get(6), new Integer(4));
		assertEquals(new Integer(7), arrayListInts.get(7)	);
		
		arrayListStrings.add(0, "One");
		arrayListStrings.add(1, "Two");
		arrayListStrings.add(2, "Three");
		arrayListStrings.add(3, "Four");
		arrayListStrings.add(4, "Five");
		
		assertEquals(arrayListStrings.get(2), "Three");
		assertEquals(arrayListStrings.get(3), "Four");
		assertEquals(arrayListStrings.get(0), "One");

	}

	/**
	 * Tests removing from the arraylist
	 */
	@Test
	public void testRemove() {
		ArrayList<String> tests = new ArrayList<String>();
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
	 * Tests setting an element in the arraylist
	 */
	@Test
	public void testSet() {
		ArrayList<String> tests = new ArrayList<String>();
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
