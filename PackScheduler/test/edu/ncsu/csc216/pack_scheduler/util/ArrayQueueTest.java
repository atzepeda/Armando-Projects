package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.NoSuchElementException;

import org.junit.Test;

/**
 * Tests the arrayqueue class
 * @author nmaco
 * @author atzep
 */
public class ArrayQueueTest {
	/** The arrayqueue */
	ArrayQueue<Integer> arrayQueueInteger;
	
	/**
	 * Tests the arrayqueue 
	 */
	@Test
	public void testArrayQueue() {
		//Test adding and removing single element to/from stack
		arrayQueueInteger = new ArrayQueue<Integer>(10);
		arrayQueueInteger.enqueue(5);
		assertEquals(arrayQueueInteger.size(), 1);
		int elementOne = arrayQueueInteger.dequeue();
		assertEquals(elementOne, 5);
		
		//Test adding and removing multiple elements to/from stack
		arrayQueueInteger.enqueue(3);
		arrayQueueInteger.enqueue(8);
		arrayQueueInteger.enqueue(5);
		assertEquals(arrayQueueInteger.size(), 3);
		elementOne = arrayQueueInteger.dequeue();
		int elementTwo = arrayQueueInteger.dequeue();
		int elementThree = arrayQueueInteger.dequeue();
		assertEquals(elementOne, 3);
		assertEquals(elementTwo, 8);
		assertEquals(elementThree, 5);
		
		//Interchange adding and removing elements to/from stack
		arrayQueueInteger.enqueue(3);
		arrayQueueInteger.enqueue(8);
		elementOne = arrayQueueInteger.dequeue();
		assertEquals(elementOne, 3);
		assertEquals(arrayQueueInteger.size(), 1);
		arrayQueueInteger.enqueue(5);
		arrayQueueInteger.enqueue(10);
		elementTwo = arrayQueueInteger.dequeue();
		assertEquals(elementTwo, 8);
		assertEquals(arrayQueueInteger.size(), 2);
		
		//Test removing from empty list
		arrayQueueInteger = new ArrayQueue<Integer>(10);
		try {
			arrayQueueInteger.dequeue();
			fail();
		} catch (NoSuchElementException e) {
			assertEquals(arrayQueueInteger.size(), 0);
		}
		
		//Test setting the capacity
		arrayQueueInteger.enqueue(1);
		arrayQueueInteger.enqueue(2);
		arrayQueueInteger.enqueue(3);
		arrayQueueInteger.setCapacity(3);
		try {
			arrayQueueInteger.setCapacity(2);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(arrayQueueInteger.size(), 3);
		}
	}
	
	/**
     * test IsEmpty()
     */
	@Test
	public void testIsEmpty() {
	    ArrayQueue<Integer> array = new ArrayQueue<Integer>(2);
	    assertTrue(array.isEmpty());
	    try{
            array.enqueue(1);
            assertEquals(1, array.size());
        } catch (IllegalArgumentException e){
            fail();
        }
	    assertFalse(array.isEmpty());   
	}
	
	/**
	 * test remove()
	 */
	@Test
	public void testRemove() {
		ArrayQueue<Integer> array = new ArrayQueue<Integer>(5);
		array.enqueue(1);
		array.enqueue(2);
		array.enqueue(3);
		array.enqueue(4);
		array.enqueue(5);
		
		array.remove(3);
		int number = array.dequeue();
		assertEquals(number, 1);
		number = array.dequeue();
		assertEquals(number, 2);
		number = array.dequeue();
		assertEquals(number, 4);
		number = array.dequeue();
		assertEquals(number, 5);
	}
}
