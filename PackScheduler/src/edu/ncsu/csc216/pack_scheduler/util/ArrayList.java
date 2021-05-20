package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * The arraylist for the schedule
 * @author Rolf Lewis
 * @author Nick Macon
 * @param <E> the element
 */
public class ArrayList<E> extends AbstractList<E> {

	/** the initial size of the array */
	private static final int INIT_SIZE = 10;

	/** the list of elements */
	private E[] list;

	/** the size */
	private int size = 0;

	/**
	 * Creates the array list with an initial size
	 */
	@SuppressWarnings("unchecked")
	public ArrayList() {
		list = (E[]) (new Object[INIT_SIZE]);
	}

	/**
	 * Adds an element to the array
	 */
	@Override
	public void add(int index, E e) {

		growArray();

		if (e == null) throw new NullPointerException("Attempted to Add Null Object to ArrayList");

		for (int i = 0; i < this.size(); i++) {
			if (list[i].equals(e)) throw new IllegalArgumentException("Attempted to Add Duplicate Object to ArrayList");
		}

		if (index < 0 || index > this.size()) throw new IndexOutOfBoundsException("Attempted to add object out of bounds");

		for (int i = this.size() - 1; i >= index; i--) {
			list[i + 1] = list[i];
		}

		list[index] = e;
		size++;
	}

	/**
	 * Grows the array to allow for more elements to be added
	 */
	@SuppressWarnings("unchecked")
	private void growArray() {
		if (this.size == list.length) {
			E[] newList = (E[]) (new Object[list.length * 2]);
			for (int j = 0; j < list.length; j++) {
				newList[j] = list[j];
			}
			list = newList;
		}
	}

	/**
	 * Removes the element at the specified index of the array
	 * @param index the space in the arraylist
	 * @return the new arraylist
	 */
	public E remove(int index) {
		if (index < 0 || index > (size() - 1)) {
			throw new IndexOutOfBoundsException();
		}
		if (size() == 0) {
			throw new IndexOutOfBoundsException();
		}
		E remove = list[index];
		list[index] = null;
		for (int i = index; i < size; i++) {
			if (i == size - 1) {
				list[i] = null;
			} else {
				list[i] = list[i + 1];
			}
		}
		size--;
		return remove;
	}

	/**
	 * Sets the element at the specified index
	 * @param index the space in the arraylist
	 * @param e the elements being set
	 * @return the new arraylist
	 */
	public E set(int index, E e) {
		if (e == null) {
			throw new NullPointerException();
		}

		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}

		if(size() == 0){
			throw new IndexOutOfBoundsException();
		}

		for (int i = 0; i <= size; i++) {
			if (e.equals(list[i])) {
				throw new IllegalArgumentException();
			}
		}

		E remove = list[index];
		list[index] = e;
		return remove;

	}

	/**
	 * Gets the element at the specified index
	 * @param index the space in the arraylist
	 * @return the arraylist
	 */
	public E get(int index) {
		if(index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}

		return list[index];
	}

	/**
	 * Returns the size of the array
	 * @return the size of the array
	 */
	public int size() {
		return size;

	}


}
