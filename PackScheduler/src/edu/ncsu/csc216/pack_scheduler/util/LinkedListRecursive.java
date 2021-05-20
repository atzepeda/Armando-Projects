package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Creates a recursive linked list
 * @author nmaco
 * @author atzep
 * @param <E> elements in the linked list
 */
public class LinkedListRecursive<E> {

	/** the linked list size */
	private int size;
	/** the beginning of the linked list */
	private ListNode front;

	/**
	 * Constructs a recursive linked list
	 */
	public LinkedListRecursive() {
		front = null;
		size = 0;
	}

	/**
	 * Checks if the linked list is empty
	 * @return true or false
	 */
	public boolean isEmpty() {
		if(size == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the size of the linked list
	 * @return the list size
	 */
	public int size() {
		return size;
	}

	/**
	 * Adds an element to the linked list
	 * @param element being added
	 * @return true or false
	 */
	public boolean add(E element) {
		if (contains(element)) {
			throw new IllegalArgumentException();
		}
		if (isEmpty()) {
			front = new ListNode(element, front);
			size++;
			return true;
		}
		add(size(), element);
		return true;
	}

	/**
	 * Adds an element at a specific index
	 * @param index to be added to
	 * @param element being added
	 */
	public void add(int index, E element) {
		if (contains(element)) {
			throw new IllegalArgumentException();
		}
		if (element == null) {
			throw new NullPointerException();
		}
		if (index < 0 || index > size()) {
			throw new IndexOutOfBoundsException();
		}

		if (index == 0) {
			front = new ListNode(element, front);
		} else {
			front.add(index - 1, element);
		}
		size++;
	}

	/**
	 * Gets the element at the index
	 * @param index containing the element
	 * @return the element at the index
	 */
	public E get(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		return front.get(index);
	}

	/**
	 * Removes the element
	 * @param element being removed
	 * @return true of false
	 */
	public boolean remove(E element) {
		if (element == null) {
			return false;
		}
		if (size() == 0) {
			return false;
		}
		if (front.data.equals(element)) {
			size--;
			front = front.next;
			return true;
		}
		if (contains(element)) {
			return front.remove(element);
		}
		return false;
	}

	/**
	 * Removes an element at a specific index
	 * @param index containing the element
	 * @return the removed element
	 */
	public E remove(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		if (index == 0) {
			size--;
			E temp = front.data;
			front = front.next;
			return temp;
		}
		size--;
		return front.remove(index);
	}

	/**
	 * Sets a new element at a certain index
	 * @param index to be set
	 * @param element being set
	 * @return the set element
	 */
	public E set(int index, E element) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		if (element == null) {
			throw new NullPointerException();
		}
		if (contains(element))
			throw new IllegalArgumentException();

		return front.set(index, element);
	}

	/**
	 * Checks if the linked list contains an element
	 * @param element being checked
	 * @return true or false
	 */
	public boolean contains(E element) {
		if(isEmpty()) {
			return false;
		}
		return front.contains(element);
	}

	/**
	 * Creates a listnode object
	 * @author nmaco
	 * @author atzep
	 */
	private class ListNode {
		/** the element data */
		public E data;
		/** the next node in the list */
		public ListNode next;

		/**
		 * Constructs a list node
		 * @param data in the node
		 * @param next node in the list
		 */
		ListNode(E data, ListNode next) {
			this.data = data;
			this.next = next;
		}

		private void add(int index, E element) {
			if (index == 0) {
				next = new ListNode(element, next);
			} else {
				if (next == null) {
					throw new IllegalArgumentException();
				}
				next.add(index - 1, element);
			}
		}

		private E get(int index) {
			if (index == 0) {
				return data;
			}
			return next.get(index - 1);
		}

		private E remove(int index) {
			if (index == 1) {
				E temp = next.data;
				next = next.next;
				return temp;
			}

			return next.remove(index - 1);
		}

		public boolean remove(E element) {
			if (next.data.equals(element)) {
				size--;
				next = next.next;
				return true;
			}

			return next.remove(element);
		}

		public E set(int index, E element) {
			if (index == 0) {
				E temp = data;
				data = element;
				return temp;
			}
			return next.set(index - 1, element);
		}

		private boolean contains(E element) {
			if (element.equals(this.data)) {
				return true;
			} else if (next == null) {
				return false;
			}

			return next.contains(element);
		}

	}
}
