package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Interface for the queue
 * @author atzep
 * @author nmaco
 * @param <E> the element in the queue
 */
public interface Queue<E> {

	/**
	 * Adds a element to the queue
	 * @param element to add
	 */
	void enqueue(E element);
	
	/**
	 * Removes an element from the queue
	 * @return the element removed
	 */
	E dequeue();
	
	/**
	 * Checks to see if the queue is empty
	 * @return true or false
	 */
	boolean isEmpty();
	
	/**
	 * The size of the queue
	 * @return the size
	 */
	int size();
	
	/**
	 * Sets the capacity of the queue
	 * @param capacity of queue
	 */
	void setCapacity(int capacity);
}
