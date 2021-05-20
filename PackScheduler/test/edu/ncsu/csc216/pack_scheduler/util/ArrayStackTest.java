package edu.ncsu.csc216.pack_scheduler.util;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.EmptyStackException;

import org.junit.Test;



/**
 * Tests the arraystack class
 * @author nmaco
 * @author atzep
 */
public class ArrayStackTest {
	/** arraystack of integers */
	ArrayStack<Integer> arrayStackInteger;
	/** arraystack of strings */
    ArrayStack<String> arrayStackString;
	
	/**
	 * Tests the arraystack
	 */
	@Test
	public void testArrayStack() {
		//Test adding and removing single element to/from stack
		arrayStackInteger = new ArrayStack<Integer>(10);
		arrayStackInteger.push(5);
		assertEquals(arrayStackInteger.size(), 1);
		int elementOne = arrayStackInteger.pop();
		assertEquals(elementOne, 5);
		
		//Test adding and removing multiple elements to/from stack
		arrayStackInteger.push(3);
		arrayStackInteger.push(8);
		arrayStackInteger.push(5);
		assertEquals(arrayStackInteger.size(), 3);
		elementOne = arrayStackInteger.pop();
		int elementTwo = arrayStackInteger.pop();
		int elementThree = arrayStackInteger.pop();
		assertEquals(elementOne, 5);
		assertEquals(elementTwo, 8);
		assertEquals(elementThree, 3);
		
		//Interchange adding and removing elements to/from stack
		arrayStackInteger.push(3);
		arrayStackInteger.push(8);
		elementOne = arrayStackInteger.pop();
		assertEquals(elementOne, 8);
		assertEquals(arrayStackInteger.size(), 1);
		arrayStackInteger.push(5);
		arrayStackInteger.push(10);
		elementTwo = arrayStackInteger.pop();
		assertEquals(elementTwo, 10);
		assertEquals(arrayStackInteger.size(), 2);
		
		//Test removing from empty list
		arrayStackInteger = new ArrayStack<Integer>(10);
		try {
			arrayStackInteger.pop();
			fail();
		} catch (EmptyStackException e) {
			assertEquals(arrayStackInteger.size(), 0);
		}
		
		//Test setting the capacity
		arrayStackInteger.push(1);
		arrayStackInteger.push(2);
		arrayStackInteger.push(3);
		arrayStackInteger.setCapacity(3);
		try {
			arrayStackInteger.setCapacity(2);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(arrayStackInteger.size(), 3);
		}
	}
	
	/**
     * Test isEmpty()
     */
    @Test
    public void testIsEmpty() {
        ArrayStack<Integer> array = new ArrayStack<Integer>(2);
        assertEquals(0, array.size());
        try {
            array.push(3);
            array.push(4);
            assertFalse(array.isEmpty());
            array.pop();
            array.pop();
            assertTrue(array.isEmpty());
        } catch (IllegalArgumentException e) {
            fail();
        }
    }
}