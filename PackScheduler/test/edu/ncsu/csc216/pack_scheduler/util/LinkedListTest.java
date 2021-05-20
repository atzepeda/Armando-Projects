package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the linkedlist class
 * @author nmaco
 * @author atzep
 */
public class LinkedListTest {

	LinkedList<String> linkedList = new LinkedList<String>();
	
	/**
	 * Sets up the test file
	 */
	@Before
	public void setUp() {
		linkedList = new LinkedList<String>();
	}

	/**
	 * Tests adding to the arraylist
	 */
	@Test
	public void testAdd() {
		//Add elements sequentially
		LinkedList<String> testList = new LinkedList<String>();
		String one = "one";
		String two = "two";
		String three = "three";
		testList.add(0, one);
		assertEquals(1, testList.size());
		testList.add(1, two);
		assertEquals(2, testList.size());
		testList.add(2, three);
		assertEquals(3, testList.size());
		testList.add(0, "four");
		assertEquals(0, testList.indexOf("four"));
		assertEquals("four", testList.get(0));
		try {
			testList.add(1, three);
		}
		catch(IllegalArgumentException e){
			//skip this line
		}
		
		try {
			testList.add(100, one);
		}
		catch(IndexOutOfBoundsException e){
			//skip this line
		}
		try {
			testList.add(-1, one);
		}
		catch(IndexOutOfBoundsException e){
			//skip this line
		}
		try {
			testList.add(0, null);
		}
		catch(NullPointerException e){
			//skip this line
		}

	}

	/**
	 * Tests removing from the arraylist
	 */
	@Test
	public void testRemove() {
		LinkedList<String> tests = new LinkedList<String>();
		String one  = "one";
		String two = "two";
		String three = "three";
		tests.add(one);
		//tests.add(1, two);
		//tests.remove(1);
		//assertEquals("one", tests.get(0));

		try{
			tests.remove(-1);
			fail();
		}
		catch(IndexOutOfBoundsException e){
			//skip this line
		}
		
		try{
			tests.remove(2);
			fail();
		}
		catch(IndexOutOfBoundsException e){
			//skip this line
		}
		
		assertEquals(1, tests.size());
		assertEquals("one", tests.remove(0));
		assertEquals(0, tests.size());
		
		tests.add(two);
		tests.add(three);
		try{
			tests.listIterator().previous();
		} catch(NoSuchElementException e){
			//passed
		}
	}

	/**
	 * Tests setting an element in the arraylist
	 */
	@Test
	public void testSet() {
		LinkedList<String> tests = new LinkedList<String>();
		String one = "one";
		String two = "two";
		tests.add(0, one);
		tests.add(1, two);
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
			assertEquals("one", tests.get(0));
		}
		try {
			tests.set(3, "Four");
			fail();
		} catch (IndexOutOfBoundsException e){
			//skip this line
		}
		try {
			tests.set(1, one);
			fail();
		} catch (IllegalArgumentException e){
			//skip this line
		}
	}

	/**
	 * Tests the linked list get function
	 */
	@Test
	public void testGet() {
		LinkedList<String> tests = new LinkedList<String>();
		String one = "one";
		String two = "two";

		tests.add(0, one);
		tests.add(1, two);

		assertEquals("one", tests.get(0));

		try{
			tests.listIterator(1).previous();
		} catch (NoSuchElementException e){
			fail();
		}

		try {
			tests.get(-1);
			fail();
		} catch (IndexOutOfBoundsException e){
			//skip
		}

		try {
			tests.get(2);
			fail();
		} catch (IndexOutOfBoundsException e){
			//skip
		}
	}
}
