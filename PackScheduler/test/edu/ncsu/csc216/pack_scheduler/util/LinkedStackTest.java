package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.EmptyStackException;

import org.junit.Test;

/**
 * Tests the linkedstack class
 * @author nmaco
 * @author atzep
 */
public class LinkedStackTest {

	/** linkedstack of integers */
	LinkedStack<Integer> linkedStackInteger;
	/** linkedstack of strings */
	LinkedStack<String> linkedStackString;
	
	/**
	 * Tests the linkedstack
	 */
	@Test
	public void testLinkedStack() {
		
		//Test adding and removing single element to/from stack
		linkedStackInteger = new LinkedStack<Integer>(10);
		linkedStackInteger.push(5);
		assertEquals(linkedStackInteger.size(), 1);
		int elementOne = linkedStackInteger.pop();
		assertEquals(elementOne, 5);
		
		//Test adding and removing multiple elements to/from stack
		linkedStackInteger.push(3);
		linkedStackInteger.push(8);
		linkedStackInteger.push(5);
		assertEquals(linkedStackInteger.size(), 3);
		elementOne = linkedStackInteger.pop();
		int elementTwo = linkedStackInteger.pop();
		int elementThree = linkedStackInteger.pop();
		assertEquals(elementOne, 5);
		assertEquals(elementTwo, 8);
		assertEquals(elementThree, 3);
		
		//Interchange adding and removing elements to/from stack
		linkedStackInteger.push(3);
		linkedStackInteger.push(8);
		elementOne = linkedStackInteger.pop();
		assertEquals(elementOne, 8);
		assertEquals(linkedStackInteger.size(), 1);
		linkedStackInteger.push(5);
		linkedStackInteger.push(10);
		elementTwo = linkedStackInteger.pop();
		assertEquals(elementTwo, 10);
		assertEquals(linkedStackInteger.size(), 2);
		
		//Test removing from empty list
		linkedStackInteger = new LinkedStack<Integer>(10);
		try {
			linkedStackInteger.pop();
			fail();
		} catch (EmptyStackException e) {
			assertEquals(linkedStackInteger.size(), 0);
		}
		
		//Test setting the capacity
		linkedStackInteger.push(1);
		linkedStackInteger.push(2);
		linkedStackInteger.push(3);
		linkedStackInteger.setCapacity(3);
		try {
			linkedStackInteger.setCapacity(2);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(linkedStackInteger.size(), 3);
		}
	}
	
	/**
     * Test isEmpty()
     */
    @Test
    public void testIsEmpty() {
        LinkedStack<Integer> linked = new LinkedStack<Integer>(2);
        assertEquals(0, linked.size());
        try {
            linked.push(3);
            linked.push(4);
            assertFalse(linked.isEmpty());
            linked.pop();
            linked.pop();
            assertTrue(linked.isEmpty());
        } catch (IllegalArgumentException e) {
            fail();
        }

    }
}
