/**
 * 
 */
package edu.ncsu.csc216.turn_taker.model.util;

import java.util.NoSuchElementException;

/**
 * SwapLinkedQueue class can be used to create a SwapLinkedQueue object that
 * store a list of objects with queue implementation.
 * 
 * @author wnwang
 * @author Stuart Reges
 * @param <E>
 *            queue element
 */
public class SwapLinkedQueue<E> implements SwapQueue<E> {

	/**
	 * Integer value holding the size of the SwapLinkedQueue.
	 */
	private int size;
	/**
	 * ListNode holding first value in list.
	 */
	private ListNode front;
	/**
	 * ListNode holding last value in list.
	 */
	private ListNode back;

	/**
	 * Constructs a SwapLinkedQueue with default state of the fields.
	 */
	public SwapLinkedQueue() {
		size = 0;
		front = new ListNode(null);
		back = new ListNode(null);
		front.next = back;
		back.prev = front;
	}

	/**
	 * Returns the number of elements in this queue. If this queue contains more
	 * than Integer.MAX_VALUE elements, returns Integer.MAX_VALUE.
	 *
	 * @return the number of elements in this list
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Returns true if this queue contains no elements.
	 *
	 * @return true if this queue contains no elements
	 */
	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

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
	@Override
	public boolean add(E e) {
		if (e == null)
			throw new NullPointerException();
		if (contains(e))
			throw new IllegalArgumentException();
		ListNode newListNode = new ListNode(back.prev, e, back);
		back.prev = newListNode;
		newListNode.prev.next = newListNode;
		size++;
		return true;
	}

	/**
	 * Returns true if the queue contains the given element and false otherwise.
	 * 
	 * @param e
	 *            element to search for
	 * @return true if element is found
	 */
	@Override
	public boolean contains(E e) {
		ListNode current = front.next;
		while (
		// current != null &&
		current.data != null) {
			if (current.data.equals(e))
				return true;
			current = current.next;
		}
		return false;
	}

	/**
	 * Removes the element at the front of the queue. Shifts any subsequent elements
	 * to the left (subtracts one from their indices). Returns the element that was
	 * removed from the queue.
	 *
	 * @return the element previously at the front of the queue
	 * @throws NoSuchElementException
	 *             if the queue is empty
	 */
	@Override
	public E remove() {
		if (isEmpty())
			throw new NoSuchElementException();
		E element = front.next.data;
		front.next = front.next.next;
		front.next.prev = front;
		size--;
		return element;
	}

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
	@Override
	public void swap(int idx, boolean direction) {
		if (idx < 0 || idx > size - 1)
			throw new IllegalArgumentException();
		if (!direction && idx == size - 1)
			throw new IllegalArgumentException();
		if (direction && idx == 0)
			throw new IllegalArgumentException();
		// try {
		E swap = null;
		ListNode current = front;
		for (int i = 0; i <= idx; i++)
			current = current.next;
		if (direction) {
			swap = current.prev.data;
			current.prev.data = current.data;
		} else {
			swap = current.next.data;
			current.next.data = current.data;
		}
		// if (swap == null)
		// throw new IllegalArgumentException();
		current.data = swap;
		// } catch (NullPointerException e) {
		// throw new IllegalArgumentException();
		// }
	}

	/**
	 * Retrieves, but does not remove, the front of this queue.
	 * 
	 * @return the element at the front of the queue
	 * @throws NoSuchElementException
	 *             if the queue is empty
	 */
	@Override
	public E peek() {
		if (isEmpty())
			throw new NoSuchElementException();
		return front.next.data;
	}

	/**
	 * ListNode is a class for storing a single node of a linked list.
	 * 
	 * @author wnwang
	 *
	 */
	private class ListNode {
		/**
		 * Element data stored in this node.
		 */
		private E data;
		/**
		 * ListNode link to previous node in the list.
		 */
		private ListNode prev;
		/**
		 * ListNode link to next node in the list.
		 */
		private ListNode next;

		/**
		 * Constructs a ListNode with data and null link of both prev and next.
		 * 
		 * @param data
		 *            element data to be set
		 */
		public ListNode(E data) {
			this(null, data, null);
		}

		/**
		 * Constructs a ListNode with data and given link of both prev and next.
		 * 
		 * @param prev
		 *            link to previous node of the ListNode
		 * @param data
		 *            element data of the ListNode
		 * @param next
		 *            link to next node of the ListNode
		 */
		public ListNode(ListNode prev, E data, ListNode next) {
			this.prev = prev;
			this.data = data;
			this.next = next;
		}
	}

}
