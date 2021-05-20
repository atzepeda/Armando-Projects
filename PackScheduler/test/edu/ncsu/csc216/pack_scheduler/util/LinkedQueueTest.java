package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.NoSuchElementException;

import org.junit.Test;

/**
 * Tests the linkedqueue class
 * @author nmaco
 * @author atzep
 */
public class LinkedQueueTest {
	/** linkedqueue of integers */
	LinkedQueue<Integer> linkedQueueInteger;
	
	/**
	 * Tests the linkedqueue
	 */
	@Test
	public void testLinkedQueue() {
		//Test adding and removing single element to/from stack
		linkedQueueInteger = new LinkedQueue<Integer>(10);
		linkedQueueInteger.enqueue(5);
		assertEquals(linkedQueueInteger.size(), 1);
		int elementOne = linkedQueueInteger.dequeue();
		assertEquals(elementOne, 5);
		
		//Test adding and removing multiple elements to/from stack
		linkedQueueInteger.enqueue(3);
		linkedQueueInteger.enqueue(8);
		linkedQueueInteger.enqueue(5);
		assertEquals(linkedQueueInteger.size(), 3);
		elementOne = linkedQueueInteger.dequeue();
		int elementTwo = linkedQueueInteger.dequeue();
		int elementThree = linkedQueueInteger.dequeue();
		assertEquals(elementOne, 3);
		assertEquals(elementTwo, 8);
		assertEquals(elementThree, 5);
		
		//Interchange adding and removing elements to/from stack
		linkedQueueInteger.enqueue(3);
		linkedQueueInteger.enqueue(8);
		elementOne = linkedQueueInteger.dequeue();
		assertEquals(elementOne, 3);
		assertEquals(linkedQueueInteger.size(), 1);
		linkedQueueInteger.enqueue(5);
		linkedQueueInteger.enqueue(10);
		elementTwo = linkedQueueInteger.dequeue();
		assertEquals(elementTwo, 8);
		assertEquals(linkedQueueInteger.size(), 2);
		
		//Test removing from empty list
		linkedQueueInteger = new LinkedQueue<Integer>(10);
		try {
			linkedQueueInteger.dequeue();
			fail();
		} catch (NoSuchElementException e) {
			assertEquals(linkedQueueInteger.size(), 0);
		}
		
		//Test setting the capacity
		linkedQueueInteger.enqueue(1);
		linkedQueueInteger.enqueue(2);
		linkedQueueInteger.enqueue(3);
		linkedQueueInteger.setCapacity(3);
		try {
			linkedQueueInteger.setCapacity(2);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(linkedQueueInteger.size(), 3);
		}
	}
	
	/**
     * test IsEmpty()
     */
	@Test
	public void testIsEmpty() {
	    LinkedQueue<Integer> linked = new LinkedQueue<Integer>(2);
        
        assertTrue(linked.isEmpty());
        try{
            linked.enqueue(1);
            assertEquals(1, linked.size());
        } catch (IllegalArgumentException e){
            fail();
        }
        assertFalse(linked.isEmpty());
	}
}
