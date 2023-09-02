package edu.ncsu.csc216.turn_taker.model.util;

import java.util.NoSuchElementException;

/**
 * Maintains a queue of element. Elements are added to the end of the queue and
 * removed from the front of the queue. This queue provides the functionality
 * for swapping neighboring elements.
 * 
 * @author Sarah Heckman
 * 
 * @param <E>
 *            queue element
 */
public interface SwapQueue<E> {

	// Query Operations
	/**
	 * Returns the number of elements in this queue. If this queue contains more
	 * than Integer.MAX_VALUE elements, returns Integer.MAX_VALUE.
	 *
	 * @return the number of elements in this list
	 */
	int size();

	/**
	 * Returns true if this queue contains no elements.
	 *
	 * @return true if this queue contains no elements
	 */
	boolean isEmpty();

	// Modification Operations

	/**
	 * Adds the specified element to the end of the queue
	 *
	 * @param e
	 *            element to be appended to this queue
	 * @return true if element is added to the queue
	 * @throws NullPointerException
	 *             if e is null
	 * @throws IllegalArgumentException
	 *             if list already contains e
	 */
	boolean add(E e);

	/**
	 * Removes the element at the front of the queue. Shifts any subsequent elements
	 * to the left (subtracts one from their indices). Returns the element that was
	 * removed from the queue.
	 *
	 * @return the element previously at the front of the queue
	 * @throws NoSuchElementException
	 *             if the queue is empty
	 */
	E remove();

	/**
	 * Swaps the element at the specified index with either the element before or
	 * the element after.
	 * 
	 * @param idx
	 *            index of the element to swap
	 * @param direction
	 *            swap with the element before if direction is true and element
	 *            after if direction is false
	 * @throws IllegalArgumentException
	 *             if the swap cannot be completed because there isn't an element
	 *             before or after the given element
	 */
	void swap(int idx, boolean direction);

	// Search Operations

	/**
	 * Retrieves, but does not remove, the front of this queue.
	 * 
	 * @return the element at the front of the queue
	 * @throws NoSuchElementException
	 *             if the queue is empty
	 */
	E peek();

	/**
	 * Returns true if the queue contains the given element and false otherwise.
	 * 
	 * @param e
	 *            element to search for
	 * @return true if element is found
	 */
	boolean contains(E e);

}
