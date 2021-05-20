package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractSequentialList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Creates the linkedlist
 * @author nmaco
 * @author atzep
 * @param <E> elements in the list
 */
public class LinkedList<E> extends AbstractSequentialList<E> {
	ListNode front;
	ListNode back;
	int size;

	/**
	 * Constucts a linked list
	 */
	public LinkedList() {
		front = new ListNode(null);
		back = new ListNode(null);
		front.next = back;
		back.prev = front;
		size = 0;
	}
	
	@Override
	public void add(int index, E element) {
		if (element == null) {
			throw new NullPointerException();
		}
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		if (this.contains(element)){
        	throw new IllegalArgumentException();
        }
        
		ListIterator<E> iterator = listIterator(index);
        iterator.add(element);
	}
	
	@Override
	public E set(int index, E element) {
		if (element == null) {
			throw new NullPointerException();
		}
		if (this.contains(element)) {
			throw new IllegalArgumentException();
		}
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}

		ListIterator<E> list = listIterator(index);
		E e = list.next();
		list.set(element);
		return e;
	}

	/**
	 * The size of the list
	 * @return the list size
	 */
	public int size() {
		return size;
	}

	/**
	 * Constructs the iterator for the linked list
	 * @param x size of the list
	 * @return the list iterator
	 */
	public ListIterator<E> listIterator(int x) {
		LinkedListIterator list = new LinkedListIterator(x);
		return list;
	}

	/**
	 * Creates list nodes for the linked ist
	 * @author nmaco
	 * @author atzep
	 */
	private class ListNode {
		E data;
		ListNode next;
		ListNode prev;

		public ListNode(E data) {
			this.data = data;
			//this.next = null;
			//this.prev = null;
		}

		/**
		 * Creates the list node for the linked list
		 * @param data the data in the node
		 * @param prev the previous node
		 * @param next the next node
		 */
		ListNode(E data, ListNode prev, ListNode next){
			this.data = data;
			this.prev = prev;
			this.next = next;
		}
	}

	/**
	 * Creates the linked list iterator
	 * @author nmaco
	 * @author atzep
	 */
	private class LinkedListIterator implements ListIterator<E> {
		ListNode previous;
		ListNode next;
		int previousIndex;
		int nextIndex;
		ListNode lastRetrieved;

		public LinkedListIterator(int index) {
			if (index < 0 || index > size) {
				throw new IndexOutOfBoundsException();
			}
			previous = front;
			next = front.next;
			for (int i = 0; i < index; i++) {
				previous = previous.next;
				next = next.next;
			}
			previousIndex = index - 1;
			nextIndex = index;
			lastRetrieved = null;
		}
		
		@Override
		public boolean hasNext() {
			return next.data != null;
		}
		
		@Override
		public boolean hasPrevious() {
			return previous.data != null;
		}
		
		@Override
		public int nextIndex() {
			return nextIndex;
		}
		
		@Override
		public int previousIndex() {
			return previousIndex;
		}
		
		@Override
		public E next() {
			if (next.data == null) {
                throw new NoSuchElementException();
            }
            lastRetrieved = next;
            nextIndex++;
            previousIndex++;
            previous = next;
            next = next.next;
            return (E) lastRetrieved.data;
		}
		
		@Override
		public E previous() {
			if (previous.data == null) {
                throw new NoSuchElementException();
            }
            lastRetrieved = previous;
            nextIndex--;
            previousIndex--;
            next = previous;
            previous = previous.prev;
            return (E) lastRetrieved.data;
		}
		
		@Override
		public void add(E e) {
			if (e == null){
        		throw new NullPointerException();
        	}
        	ListNode add = new ListNode(e, previous, next);
            previous.next = add; 
            next.prev = add;
            nextIndex = nextIndex + 1;
            previousIndex++;
            lastRetrieved = null;
            size++;
		}
		
		@Override
		public void set(E e) {
			if (lastRetrieved == null) {
				throw new IllegalStateException();
			}
			if (contains(e)){
				throw new IllegalArgumentException();
			}
			if (e == null) {
				throw new NullPointerException();
			}
			lastRetrieved.data = e;
		}
		
		@Override
		public void remove() {
			if (lastRetrieved == null) {
				throw new IllegalStateException();
			}
			if (lastRetrieved.data == previous.data) {
				previousIndex--;
				nextIndex--;
				previous = lastRetrieved.prev;
			} else {
				next = lastRetrieved.next;
			}
			previous.next = next;
			next.prev = previous;
			lastRetrieved = null;
			size--;

		}
	}

}


