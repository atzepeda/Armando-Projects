package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * Implements LinkedList data structure
 * @author Rolf Lewis
 * @author Nick Macon
 * @author yueyu
 *
 * @param <E> Generic object type
 */
public class LinkedAbstractList<E> extends AbstractList<E> {

	/** Counter for the size of the list **/
	private int size;

	/** Max size that the list can have **/
	private int capacity;

	/** The first node in the list **/
	private ListNode front;
	
	/** The last node in the list **/
	private ListNode back;

	/**
	 * Constructor of a LinkedAbstractList
	 * @param capacity Maximum size that the List can have
	 */
	public LinkedAbstractList(int capacity) {
		if(capacity < 0) {
			throw new IllegalArgumentException("Capacity cannot be less than 0");
		}
		front = null;
		size = 0;
		setCapacity(capacity);
	}

	/**
	 * Removes the node that is at the given index, and tightens the list.
	 * @param index index of the node to remove
	 */
	@Override
	public E remove(int index){
		if (index < 0 || index > (size() - 1)) {
			throw new IndexOutOfBoundsException();
		}
		if (size() == 0) {
			throw new IndexOutOfBoundsException();
		}

		E remove;

		if(index == 0){
			remove = front.data;
			front = front.next;
		}
		else{
			ListNode temp = front;
			for (int i = 1; i < index; i++) {
				temp = temp.next;
			}
			remove = temp.next.data;
			temp.next = temp.next.next;
			if(index == size - 1) {
				back = temp;
			}
		}
		this.size--;
		return remove;
	}

	/**
	 * Returns the current size of the list.
	 * @return the current size of the list.
	 */
	public int size() {
		return this.size;
	}

	@Override
	public void add(int index, E object) {
		if (this.size == this.capacity) throw new IllegalArgumentException();
		if (object == null) throw new NullPointerException();
		if (index < 0 || index > this.size()) throw new IndexOutOfBoundsException();

		for (int i = 0; i < this.size(); i++) {
			if (this.get(i).equals(object)) throw new IllegalArgumentException();
		}

		if (index ==  size && size == 0) {
			ListNode newListNode = new ListNode(object);
			this.front = newListNode;
			this.back = newListNode;
		} else  if (index == size) {
			ListNode newListNode = new ListNode(object);
			back.next = newListNode;
			back = back.next;
		} else if (index == 0) {
			ListNode newListNode = new ListNode(object, this.front);
			this.front = newListNode;
		} else {
			ListNode temp = this.front;
			for (int i = 0; i < index - 1; i++) {
				temp = temp.next;
			}
			ListNode newListNode = new ListNode(object, temp.next);
			temp.next = newListNode;
		}

		this.size++;
	}

	@Override
	public E get(int index) {
		if (index < 0 || index >= this.size()) {
			throw new IndexOutOfBoundsException();
		}

		ListNode temp = this.front;
		for (int i = 1; i <= index; i++) {
			temp = temp.next;
		}

		return temp.data;
	}

	@Override
	public E set(int index, E object) {
		if (index < 0 || index >= this.size()) throw new IndexOutOfBoundsException();
		if (object == null) throw new NullPointerException();

		for (int i = 0; i < this.size; i++) {
			if (this.get(i).equals(object)) {
				throw new IllegalArgumentException();
			}
		}
		
		ListNode	  temp = this.front;
		for (int i = 0; i < index; i++) {
			temp = temp.next;
		}
		E data = temp.data;
		temp.data = object;
		return data;
	}
	
	/**
	 * Sets the capacity 
	 * @param capacity the capacity
	 */
	public void setCapacity(int capacity) {
		if(capacity < 0 || capacity < this.size) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
	}

	/**
	 * Inner class that implements the listnode object, that stores a reference to the next node, and its own data.
	 * @author Rolf Lewis
	 * @author Nick Macon
	 *
	 */
	private class ListNode {
		private E data;
		private ListNode next;

		public ListNode(E data) {
			this.data = data;
		}

		public ListNode(E data, ListNode next) {
			this(data);
			this.next = next;
		}
	}


}
