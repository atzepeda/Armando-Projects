package edu.ncsu.csc216.collections.list;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the SortedList Object and its methods for varying cases of functionality.
 * 
 * @author Shalini Balagopal Nick Macon Yue Yu
 */
public class SortedListTest {

	/**
	 * Tests that SortedList constructor creates an empty list and adds elements correctly
	 */
	@Test
	public void testSortedList() {
		SortedList<String> list = new SortedList<String>();
		
		// checking list is empty
		assertEquals(0, list.size());
		assertFalse(list.contains("apple"));
		
		// adding 11 elements to ensure list can grow correctly
		list.add("first");
		list.add("second");
		list.add("third");
		list.add("fourth");
		list.add("fifth");
		list.add("sixth");
		list.add("seventh");
		list.add("eighth");
		list.add("ninth");
		list.add("tenth");
		list.add("eleventh");
		
		// checking list has 11 elements
		assertEquals(11, list.size());
		
	}

	/**
	 * Tests add() functionality with alphabetized strings
	 * Checks if multiple elements are able to be added at different positions
	 */
	@Test
	public void testAdd() {
		SortedList<String> list = new SortedList<String>();
		
		list.add("banana");
		assertEquals(1, list.size());
		assertEquals("banana", list.get(0));
		
		//add at beginning
		list.add("apple");
        assertEquals("apple", list.get(0));
        
        //add end
        list.add("elderflower");
        assertEquals("elderflower", list.get(2));
        
        //add middle
        list.add("cantaloupe");
        assertEquals("cantaloupe", list.get(2));
        
        //add null
        try {
            list.add(null);
            fail();
        } catch (NullPointerException e) {
            assertEquals(4, list.size());
        }
        
        //add duplicate
        try {
            list.add("elderflower");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(4, list.size());
        }
	}
	
	/**
	 * Tests get() functionality in retrieving elements using their index integer
	 * Checks if elements are able to be retrieved from the list with appropriate calls
	 */
	@Test
	public void testGet() {
		SortedList<String> list = new SortedList<String>();
		
		// Test getting an element from an empty list
		try {
			list.get(1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(0, list.size());
		}
		
		list.add("apple");
		list.add("banana");
		list.add("cantaloupe");
		
		// Test getting an element at an index < 0
		try {
			list.get(-1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(3, list.size());
		}
		
		// Test getting an element at index size
		try {
			list.get(list.size());
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(3, list.size());
		}
		
	}
	
	/**
	 * Tests remove() functionality in deleting elements from list
	 * Checks if elements are able to be removed from the list
	 */
	@Test
	public void testRemove() {
		SortedList<String> list = new SortedList<String>();
		
		// remove from empty list
		try{
		    list.remove(0);
			fail();
		}
		catch(IndexOutOfBoundsException e){
		    assertEquals(0, list.size());
		}
		
		// add elements
		list.add("first");
		list.add("second");
		list.add("third");
		list.add("fourth");
		
		// remove from element less than 0
		try {
		    list.remove(-2);
			fail();
		} catch (IndexOutOfBoundsException e){
		    assertEquals(4, list.size());
		}
		
		// remove an element at index size()
		try {
		    list.remove(list.size());
			fail();
		} catch (IndexOutOfBoundsException e){
		    assertEquals(4, list.size());
		}
		
		// remove a middle element
		list.remove(2);
        assertEquals(3, list.size());
        assertEquals("third", list.get(2));
		
		// remove the last element
		list.remove(2);
		assertEquals(2, list.size());
		assertEquals("fourth", list.get(1));
		
		// remove the first element
		list.remove(0);
		assertEquals(1, list.size());
		assertEquals("fourth", list.get(0));
		
		// remove the last element
		list.remove(0);
		assertEquals(0, list.size());
	}
	
	/**
	 * Tests indexOf() in receiving an integer value which matches the string's index
	 * Checks for the ability to find elements in the list
	 */
	@Test
	public void testIndexOf() {
		SortedList<String> list = new SortedList<String>();
		
		// Test indexOf on an empty list
		assertEquals(-1, list.indexOf("grape"));
		
		list.add("apple");
		list.add("banana");
		list.add("cantaloupe");
		list.add("dragonfruit");
		list.add("elderflower");
		
		// Test various calls to indexOf for elements in the list
		assertEquals(1, list.indexOf("banana"));
		assertEquals(4, list.indexOf("elderflower"));
		assertEquals(2, list.indexOf("cantaloupe"));

		// and not in the list
		assertEquals(-1, list.indexOf("watermelon"));
		assertEquals(-1, list.indexOf("pear"));
		
		// Test checking the index of null
		try {
			list.indexOf(null);
			fail();
		} catch (NullPointerException e) {
			assertEquals(5, list.size());
		}
	}
	
	/**
	 * Tests clear() and the ability to clear the list
	 */
	@Test
	public void testClear() {
		SortedList<String> list = new SortedList<String>();

		// Add some elements
		list.add("first");
		list.add("second");
		list.add("third");
		list.add("fourth");
		
		// Clear the list
		list.clear();
		
		// Test that the list is empty
		assertEquals(0, list.size());
	}

	/**
	 * Tests isEmpty() will return the correct boolean if the list is empty
	 */
	@Test
	public void testIsEmpty() {
		SortedList<String> list = new SortedList<String>();
		
		// Test that the list starts empty
		assertEquals(0, list.size());
		
		// Add at least one element
		list.add("first");
		list.add("second");
		list.add("third");
		list.add("fourth");
		
		// Check that the list is no longer empty
		assertFalse(list.isEmpty());
	}

	/**
	 * Tests contains() to see if the list contains certain elements
	 */
	@Test
	public void testContains() {
		SortedList<String> list = new SortedList<String>();
		
		//  Test the empty list case
		assertFalse(list.contains("first"));
		
		// Add some elements
		list.add("first");
		list.add("second");
		list.add("third");
		list.add("fourth");
		
		// Test some true and false cases
		assertTrue(list.contains("second"));
		assertTrue(list.contains("third"));
		
		assertFalse(list.contains("fifth"));
		assertFalse(list.contains("sixth"));
	}
	
	/**
	 * Tests equals() method
	 * Compares to check if multiple list elements are the same
	 */
	@Test
	public void testEquals() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();
		
		list1.add("apple");
		list1.add("banana");
		list1.add("cantaloupe");
		list1.add("dragonfruit");
		list1.add("elderflower");
		
		list2.add("apple");
		list2.add("banana");
		list2.add("cantaloupe");
		list2.add("dragonfruit");
		list2.add("elderflower");
		
		list3.add("first");
		list3.add("second");
		list3.add("third");
		list3.add("fourth");
		list3.add("fifth");
		
		// Asserts two lists are the same in both directions
		assertTrue(list1.equals(list2));
		assertTrue(list2.equals(list1));
		
		// Asserts list 1 and 3 are not the same in both directions
		assertFalse(list1.equals(list3));
		assertFalse(list3.equals(list1));

		// Asserts list 2 and 3 are not the same in both directions
		assertFalse(list2.equals(list3));
		assertFalse(list3.equals(list2));
	}
	
	/**
	 * Tests hashCode() functionality to check if matching lists and non-matching lists
	 * have the same and different hash codes, respectively
	 */
	@Test
	public void testHashCode() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();
		
		list1.add("apple");
		list1.add("banana");
		list1.add("cantaloupe");
		list1.add("dragonfruit");
		list1.add("elderflower");
		
		list2.add("apple");
		list2.add("banana");
		list2.add("cantaloupe");
		list2.add("dragonfruit");
		list2.add("elderflower");
		
		list3.add("first");
		list3.add("second");
		list3.add("third");
		list3.add("fourth");
		list3.add("fifth");
		
		// Test for same hashCodes between two lists
		assertEquals(list1.hashCode(), list2.hashCode());
		
		// Test for different hashCodes between two lists
		assertNotEquals(list1.hashCode(), list3.hashCode());
		assertNotEquals(list2.hashCode(), list3.hashCode());
	}

}