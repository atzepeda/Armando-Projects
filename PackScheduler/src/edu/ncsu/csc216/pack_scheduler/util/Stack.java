package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Interface for the Stack
 * @author atzep
 * @author nmaco
 * @param <E> the element in the stack
 */
public interface Stack<E> {

	/**
	 * Pushes the element to the stack
	 * @param element being pushed
	 */
	void push(E element);
	
	/**
	 * Pops the element from the list
	 * @return the element
	 */
	E pop();
	
	/**
	 * Checks to see if the spot is empty
	 * @return true or false
	 */
	boolean isEmpty();
	
	/**
	 * The size of the stack
	 * @return the stack size
	 */
	int size();
	
	/**
	 * Sets the capacity for the stack
	 * @param capacity of the stack
	 */
	void setCapacity(int capacity);
}
