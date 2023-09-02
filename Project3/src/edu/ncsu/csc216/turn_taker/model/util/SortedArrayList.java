/**
 * 
 */
package edu.ncsu.csc216.turn_taker.model.util;

/**
 * SortedArrayList class can be used to create a SortedArrayList object that
 * store a list of objects with a customized SortedList implementation.
 * 
 * @author wnwang
 * @author Stuart Reges
 * @param <E>
 *            list element
 */
public class SortedArrayList<E extends Comparable<E>> implements SortedList<E> {

	/**
	 * Integer constant holding the default size of the SortedArrayList.
	 */
	private static final int RESIZE = 0;
	/**
	 * Elements array of the SortedArrayList.
	 */
	private E[] list;
	/**
	 * Integer value holding the size of the SortedArrayList.
	 */
	private int size;

	/**
	 * Constructs a SortedArrayList by calling constructor with size constant.
	 */
	public SortedArrayList() {
		this(RESIZE);
	}

	/**
	 * Constructs a SortedArrayList with given state of the fields.
	 * 
	 * @param size
	 *            the initial number of elements in the list
	 */
	@SuppressWarnings("unchecked")
	public SortedArrayList(int size) {
		if (size < 0)
			throw new IllegalArgumentException();
		list = (E[]) new Comparable[size];
		this.size = 0;
	}

	/**
	 * Returns the number of elements in this list. If this list contains more than
	 * Integer.MAX_VALUE elements, returns Integer.MAX_VALUE.
	 *
	 * @return the number of elements in this list
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Returns true if this list contains no elements.
	 *
	 * @return true if this list contains no elements
	 */
	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Returns true if this list contains the specified element. More formally,
	 * returns true if and only if this list contains at least one element a such
	 * that (o==null ? a==null : o.equals(a)).
	 *
	 * @param e
	 *            element whose presence in this list is to be tested
	 * @return true if this list contains the specified element
	 */
	@Override
	public boolean contains(E e) {
		return indexOf(e) != -1;
	}

	/**
	 * Adds the specified element to list in sorted order
	 *
	 * @param e
	 *            element to be appended to this list
	 * @return true (as specified by Collection#add)
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
		int index = 0;
		for (E element : list) {
			if (element == null || e.compareTo(element) < 0)
				break;
			index++;
		}
		ensureCapacity(size() + 1);
		for (int i = size; i > index; i--)
			list[i] = list[i - 1];
		list[index] = e;
		size++;
		return true;
	}

	/**
	 * Returns the element at the specified position in this list.
	 *
	 * @param index
	 *            index of the element to return
	 * @return the element at the specified position in this list
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of range
	 */
	@Override
	public E get(int index) {
		if (index < 0 || index >= size())
			throw new IndexOutOfBoundsException();
		return list[index];
	}

	/**
	 * Removes the element at the specified position in this list. Shifts any
	 * subsequent elements to the left (subtracts one from their indices). Returns
	 * the element that was removed from the list.
	 *
	 * @param index
	 *            the index of the element to be removed
	 * @return the element previously at the specified position
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of range
	 */
	@Override
	public E remove(int index) {
		if (index < 0 || index >= size())
			throw new IndexOutOfBoundsException();
		E element = get(index);
		for (int i = index; i < size - 1; i++)
			list[i] = list[i + 1];
		list[size - 1] = null;
		size--;
		return element;
	}

	/**
	 * Returns the index of the first occurrence of the specified element in this
	 * list, or -1 if this list does not contain the element. More formally, returns
	 * the lowest index i such that (o==null ? get(i)==null : o.equals(get(i))), or
	 * -1 if there is no such index.
	 *
	 * @param e
	 *            element to search for
	 * @return the index of the first occurrence of the specified element in this
	 *         list, or -1 if this list does not contain the element
	 */
	@Override
	public int indexOf(E e) {
		int index = 0;
		for (int i = 0; i < size; i++) {
			if (e.compareTo(list[i]) == 0)
				return index;
			index++;
		}
		return -1;
	}

	/**
	 * Ensure the underlying array has the given capacity by doubling the capacity.
	 * 
	 * @param capacity
	 *            expected list capacity
	 */
	@SuppressWarnings("unchecked")
	private void ensureCapacity(int capacity) {
		if (capacity > list.length) {
			E[] newList = (E[]) new Comparable[list.length * 2 + 1];
			for (int i = 0; i < size; i++)
				newList[i] = list[i];
			list = newList;
		}
	}

}